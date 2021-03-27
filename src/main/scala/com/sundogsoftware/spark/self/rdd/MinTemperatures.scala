package com.sundogsoftware.spark.self.rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

import scala.math.min

object MinTemperatures {

  def main(args: Array[String]): Unit = {

    def parseLine(line: String) = {
      val fields = line.split(",")
      val stationId = fields(0)
      val entryType = fields(2)
      val temperatory = fields(3).toFloat * .1f * (9f / 5f) + 32f
      (stationId, entryType, temperatory)
    }

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "MinTemperatures")
    val lines = sc.textFile("data/1800.csv")

    val parsedLines = lines.map(parseLine)

    val minTemps = parsedLines.filter(x => x._2 == "TMIN")

    val stationTemps = minTemps.map(x => (x._1, x._3.toFloat))

    val minTempsByStation = stationTemps.reduceByKey((x, y) => min(x, y))

    val results = minTempsByStation.collect()

    for (result <- results.sorted) {
      val station = result._1
      val temp = result._2
      val formattedTemp = f"$temp%.2f F"
      println(s"$station minimum temperature: $formattedTemp")
    }

  }

}
