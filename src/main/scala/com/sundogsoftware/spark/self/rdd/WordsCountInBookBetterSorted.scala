package com.sundogsoftware.spark.self.rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object WordsCountInBookBetterSorted {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "WordCountBetterSorted")

    val input = sc.textFile("data/book.txt")

    val words = input.flatMap(x => x.split("\\W+"))

    val lowercaseWords = words.map(_.toLowerCase())

    // Count the number of occurrences of each word
    //    val wordCounts = lowercaseWords.map(x => (x, 1)).reduceByKey((x,y) => x+y)
    val wordCounts = lowercaseWords.map((_, 1)).reduceByKey(_ + _)

    // Flip (word, count) tuples to (count, word) and then sort by key (the counts)
    val wordCountsSorted = wordCounts.map(x => (x._2, x._1)).sortByKey()

    val collect = wordCountsSorted.collect()

    collect.foreach(x => println(s"${x._1}:${x._2}"))

    //    wordCountsSorted.foreach(println)
    //    wordCountsSorted.foreach(x => println(x._2 + ":" + x._1))
    //    for (result <- wordCountsSorted) {
    //      val count = result._1
    //      val word = result._2
    //      println(s"$word: $count")
    //    }


  }
}
