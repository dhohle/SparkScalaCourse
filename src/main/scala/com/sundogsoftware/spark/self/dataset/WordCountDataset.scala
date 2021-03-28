package com.sundogsoftware.spark.self.dataset

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object WordCountDataset {

  // value is important, as it is the default inferred header name
  case class Line(value: String)

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("FriendsByAgeWithDataset")
      .getOrCreate()

    //
    import spark.implicits._
    val df = spark.read.text("data/book.txt").as[Line]

    // split all lines, then explode to one row per word (name it word) and select all
    val words = df.select(explode(split($"value", "\\W+")).alias("word"))
      // filter all empty strings
      .filter($"word" =!= "")

    // select the lowercased words, name it ward (again)
    val lowercased = words.select(lower($"word").alias("word"))

    // group by word and count duplicated
    val wordCounts = lowercased.groupBy("word").count()

    // sort on count
    val wordCountSorted = wordCounts.sort("count")

    // makes sure all rows are shown
    wordCountSorted.show(wordCountSorted.count().toInt)


    // ANOTHER WAY TO DO IT (Blending RDD's and Datasets)
    val bookRDD = spark.sparkContext.textFile("data/book.txt")
    val wordsRDD = bookRDD.flatMap(_.split("\\W+"))
    val wordsDS = wordsRDD.toDS()// converts to dataset

    val lowercasedWordsDS = wordsDS.select(lower($"value").alias("word"))
    val wordCountsDS = lowercasedWordsDS.groupBy("word").count();
    val wordCountsSortedDS = wordCountsDS.sort("count")
    wordCountsSortedDS.show(wordCountsSortedDS.count().toInt)



    spark.stop()
  }

}
