package com.sundogsoftware.spark.self.rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object FriendsByAge {

  def parseLine(line: String): (Int, Int) = {

    val fields = line.split(",")
    val age = fields(2).toInt
    val numFriends = fields(3).toInt
    (age, numFriends)
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "FriendsByAge")

    // (id, name, age, numFriends)
    val lines = sc.textFile("data/fakefriends-noheader.csv")

    // from (id, name, age, numFriends) -> (age, numFriends)
    val rdd = lines.map(parseLine)

    val totalsByAge = rdd
      // from (age, numFriends) -> (age, (numFriends, 1))
      // more specifically: numFriends -> (numFriends, 1); because mapVALUES
      .mapValues(x => (x, 1))
      // add all numFriends and 1's per age
      .reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))

    // now all numFriends and num users are counted, divide total number of friends by the number of users (per age)
    val averagesByAge = totalsByAge.mapValues(x => x._1 / x._2)

    // Collect the results from the RDD -> Scala
    val results = averagesByAge.collect()

    // sort the results (on key) and print for each age the (age, avgNumFriends)
    results.sorted.foreach(println)
  }

}
