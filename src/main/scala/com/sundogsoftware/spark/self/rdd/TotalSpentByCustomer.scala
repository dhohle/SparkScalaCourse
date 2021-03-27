package com.sundogsoftware.spark.self.rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object TotalSpentByCustomer {

  def main(args: Array[String]): Unit = {

    def parseLine(line: String) = {
      val segments = line.split(",")
      val customerId = segments(0)
      val cost = segments(2)
      (customerId.toInt, cost.toFloat)
    }

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "TotalSpentByCustomer")

    val lines = sc.textFile("data/customer-orders.csv")

    val parsedLines = lines.map(parseLine)

    val mappedInput = parsedLines.reduceByKey(_ + _)

    val inverseKeyVal = mappedInput.map((x) => (x._2, x._1))

    val sorted = inverseKeyVal.sortByKey()

    // collect to get the results from the "cloud" to this application
    val results = sorted.collect()

    results.foreach((x) => println(s"User with id ${x._2} spend $$${x._1},- USD"))
    //    results.foreach(println)
  }


}
