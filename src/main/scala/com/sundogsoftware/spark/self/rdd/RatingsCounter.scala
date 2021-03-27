package com.sundogsoftware.spark.self.rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object RatingsCounter {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "RatingsCounter")

    val lines = sc.textFile("data/ml-100k/u.data")

    val ratings = lines.map(x => x.split("\t")(2))

    val results = ratings.countByValue();

    val sortedResults = results.toSeq.sortBy(_._1)
    //    val sortedResults = results.toSeq.sortBy(_._2)

    sortedResults.foreach(println)
  }

}
