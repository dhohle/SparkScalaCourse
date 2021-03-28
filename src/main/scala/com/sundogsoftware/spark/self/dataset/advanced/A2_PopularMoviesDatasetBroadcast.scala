package com.sundogsoftware.spark.self.dataset.advanced

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, LongType, StructType}

import scala.io.{Codec, Source}

object A2_PopularMoviesDatasetBroadcast {

  case class Movies(userID: Int, movieID: Int, rating:Int, timestamp: Long)

  /** Load a Map of movie IDs to movie Names - in Scala, not Spark*/
  def loadMovieNames(): Map[Int, String] ={
    // Handle character encoding issues
    implicit val codec : Codec = Codec("ISO-8859-1")// Current encoding of the u.item file (not UTF-8)

    var movieNames: Map[Int, String] = Map()

    val lines = Source.fromFile("data/ml-100k/u.item")
    for (line <- lines.getLines()){
      val fields = line.split('|')
      if(fields.length>1)
        movieNames += (fields(0).toInt -> fields(1))
    }
    lines.close()
    movieNames
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("PopularMovies")
      .getOrCreate()

    val nameDict = spark.sparkContext.broadcast(loadMovieNames())

    val movieRatingSchema = new StructType()
      .add("userID", IntegerType, true)
      .add("movieID", IntegerType, true)
      .add("rating", IntegerType, true)
      .add("timestamp", LongType, true)

    import spark.implicits._
    val df = spark.read
      .option("sep", "\t")
      .schema(movieRatingSchema)
      .csv("data/ml-100k/u.data")
      .as[Movies]

    // group the movies and count occurrances
    val topMovieIDs = df.groupBy("movieID").count()
    // Create an anonymous function, using the broadcasted nameDict dictionary, where the value of movieID is called on
    // resulting in the value of the nameDict to be retrieved
    val lookupName: Int => String = (movieID:Int) => nameDict.value(movieID)

    // Wrap the anonymous function as a Used Defined Function
    val lookupNameUDF = udf(lookupName)

    // Now create a new column, with the result of the `udf` applied to the column `movieID`, return the name of the movie
    val moviesWithNames = topMovieIDs.withColumn("movieTitle", lookupNameUDF(col("movieID")))

    // sort results
    val sortedMoviesWithNames = moviesWithNames.sort("count")


    // print results
    sortedMoviesWithNames.show(sortedMoviesWithNames.count().toInt, truncate = false)

    spark.stop()
  }
}
