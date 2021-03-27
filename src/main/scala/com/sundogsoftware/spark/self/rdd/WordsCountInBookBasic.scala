package com.sundogsoftware.spark.self.rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object WordsCountInBookBasic {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "WordCount")

    val input = sc.textFile("data/book.txt")

    val words = input.flatMap(x => x.split(" "))

    val wordCounts = words.countByValue()

    wordCounts.foreach(println)

  }

}
