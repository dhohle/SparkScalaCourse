package com.sundogsoftware.spark.self.dataset.advanced

import org.apache.log4j._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object D1_MovieSimilarities {

  // map from file
  case class Movies(userID: Int, movieID: Int, rating: Int, timestamp: Long)

  // map from file
  case class MovieNames(movieID: Int, movieTitle: String)


  case class MoviePairs(movie1: Int, movie2: Int, rating1: Int, rating2: Int)

  case class MoviePairsSimilarity(movie1: Int, movie2: Int, score: Double, numPairs: Long)


  def computeCosineSimilarity(spark: SparkSession, data: Dataset[MoviePairs]): Dataset[MoviePairsSimilarity] = {
    // Compute xx, xy, and yy columns
    val pairScores = data
      .withColumn("xx", col("rating1") * col("rating1"))
      .withColumn("yy", col("rating2") * col("rating2"))
      .withColumn("xy", col("rating1") * col("rating2"))

    // compute numerator, denominator and numPairs columns
    val calculateSimilarity = pairScores
      .groupBy("movie1", "movie2")
      .agg(
        sum(col("xy")).alias("numerator"),
        (sqrt(sum(col("xx"))) * sqrt(sum(col("yy")))).alias("denominator"),
        count(col("xy")).alias("numPairs")
      )

    // calculate score and select only needed columns (movie1, movie2, score, numPairs
    import spark.implicits._
    val result = calculateSimilarity
      .withColumn("score",
        when(col("denominator") =!= 0, col("numerator") / col("denominator"))
          .otherwise(null)
      ).select("movie1", "movie2", "score", "numPairs")
      .as[MoviePairsSimilarity]

    result
  }

  def getMovieName(movieNames: Dataset[MovieNames], movieId: Int): String = {
    val result = movieNames.filter(col("movieID") === movieId)
      .select("movieTitle").collect()(0)
    result(0).toString
  }


  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    //    val movieID = if (args.length > 0) args(0).toInt else 56

    val movieIDs = Array(96, 1, 65)

    val spark = SparkSession.builder()
      .appName("MovieSimilarities")
      .master("local[*]")
      .getOrCreate()

    // Schema when reading u.item
    val moviesNamesSchema = new StructType()
      .add("movieID", IntegerType, true)
      .add("movieTitle", StringType, true)

    // Schema when reading u.data
    val moviesSchema = new StructType()
      .add("userID", IntegerType, true)
      .add("movieID", IntegerType, true)
      .add("rating", IntegerType, true)
      .add("timestamp", LongType, true)

    println("\nLoading movie names...")
    import spark.implicits._
    val movieNames = spark.read
      .option("sep", "|")
      .option("charset", "ISO-8859-1")
      .schema(moviesNamesSchema)
      .csv("data/ml-100k/u.item")
      .as[MovieNames]

    val movies = spark.read
      .option("sep", "\t")
      .schema(moviesSchema)
      .csv("data/ml-100k/u.data")
      .as[Movies]

    // filter only the relevant ratings information
    val ratings = movies.select("userId", "movieId", "rating")

    // Emit every movie rated together by the same user.
    // Self-join to find every combination.
    // Select movie pairs and rating pairs
    val moviePairs = ratings.as("ratings1")
      // && $"ratings1.movieId" < $"ratings2.movieId" to prevent duplicates
      .join(ratings.as("ratings2"), $"ratings1.userId" === $"ratings2.userId" && $"ratings1.movieId" < $"ratings2.movieId")
      .select(
        $"ratings1.movieId".alias("movie1"),
        $"ratings2.movieId".alias("movie2"),
        $"ratings1.rating".alias("rating1"),
        $"ratings2.rating".alias("rating2"),
      ).as[MoviePairs]

    // Caching is especially important when you want to build a live system
    val moviePairsSimilarities = computeCosineSimilarity(spark, moviePairs).cache()

    for (movieID <- movieIDs) {
      val scoreThreshold = .97f
      val coOccurrenceThreshold = 50.0f

      //      val movieID: Int = args(0).toInt

      // Filter for movies with this sim that are "good" as defined by out quality threshold above
      val filteredResults = moviePairsSimilarities
        .filter((col("movie1") === movieID || col("movie2") === movieID) &&
          col("score") > scoreThreshold && col("numPairs") > coOccurrenceThreshold)

      // sort by quality score
      val results = filteredResults.sort(col("score").desc).take(10)

      println("\nTop 10 similar movies for " + getMovieName(movieNames, movieID))
      for (result <- results) {
        var similarMovieID = result.movie1
        if (similarMovieID == movieID) {
          similarMovieID = result.movie2
        }

        println(getMovieName(movieNames, similarMovieID) + "\t score: " + result.score + "\tstrength: " + result.numPairs)
      }

    }

  }

}
