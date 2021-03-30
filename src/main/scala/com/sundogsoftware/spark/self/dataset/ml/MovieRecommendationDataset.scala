package com.sundogsoftware.spark.self.dataset.ml

import org.apache.log4j._
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.{SparkSession, _}
import org.apache.spark.sql.types._

import scala.collection.mutable

object MovieRecommendationDataset {

  case class MoviesNames(movieId: Int, movieTitle: String)

  case class Rating(userID: Int, movieID: Int, rating: Float)

  // Get movie name by given dataset and id
  def getMovieName(movieNames: Array[MoviesNames], movieId: Int): String = movieNames.filter(_.movieId == movieId)(0).movieTitle

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .appName("ALSExample")
      .master("local[*]")
      .getOrCreate()

    println("Loading movie names")
    val moviesNamesSchema = new StructType()
      .add("movieID", IntegerType, true)
      .add("movieTitle", StringType, true)

    val moviesSchema = new StructType()
      .add("userID", IntegerType, true)
      .add("movieID", IntegerType, true)
      .add("rating", FloatType, true)
      .add("timestamp", LongType, true)


    import spark.implicits._
    val names = spark.read
      .option("sep", "|")
      .option("charset", "ISO-8859-1")
      .schema(moviesNamesSchema)
      .csv("data/ml-100k/u.item")
      .as[MoviesNames]

    // collect names list object in Scala
    val namesList = names.collect()

    val ratings = spark.read
      .option("sep", "\t")
      .schema(moviesSchema)
      .csv("data/ml-100k/u.data")
      .as[Rating]

    println("\nTraining recommendation model...")

    val als = new ALS()
      .setMaxIter(5)
      .setRegParam(.01)
      .setUserCol("userID")
      .setItemCol("movieID")
      .setRatingCol("rating")

    val model = als.fit(ratings)

    // Get top-10 recommendations for the user we specified
    val userID: Int = 0
    val users = Seq(userID).toDF("userID")
    val recommendations = model.recommendForUserSubset(users, 10)

    // Display them (oddly, this is the hardest part!)
    println("\nTop 10 recommendations for user ID " + userID + ":")

    for (userRecs <- recommendations) {
      val myRecs = userRecs(1)
      val temp = myRecs.asInstanceOf[mutable.WrappedArray[Row]]
      for (rec <- temp) {
        val movie = rec.getAs[Int](0)
        val rating = rec.getAs[Float](1)
        val movieName = getMovieName(namesList, movie)
        println(movieName, rating)
      }
    }

    spark.stop()
  }

}
