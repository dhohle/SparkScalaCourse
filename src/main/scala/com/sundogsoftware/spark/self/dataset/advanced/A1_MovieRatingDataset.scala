package com.sundogsoftware.spark.self.dataset.advanced

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.desc
import org.apache.spark.sql.types.{IntegerType, LongType, StructType}

object A1_MovieRatingDataset {

  //  case class MovieData(userID: Int, movieID: Int, rating:Int, timestamp: Long)
  final case class Movie(movieID: Int)

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("PopularMovies")
      .getOrCreate()

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
      .as[Movie]

    val topMovieIDs = df
      .groupBy("movieID")
      .count()
      .orderBy(desc("count"))

    topMovieIDs.show(10)

    spark.stop()

  }
}
