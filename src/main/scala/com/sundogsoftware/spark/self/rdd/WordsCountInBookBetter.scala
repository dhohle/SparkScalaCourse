package com.sundogsoftware.spark.self.rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object WordsCountInBookBetter {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "WordCountBetter")

    val input = sc.textFile("data/book.txt")

    val words = input.flatMap(x => x.split("\\W+"))

    val lowercaseWords = words.map(_.toLowerCase())

    val wordCounts = lowercaseWords.countByValue()

    wordCounts.foreach(println)
  }

}
