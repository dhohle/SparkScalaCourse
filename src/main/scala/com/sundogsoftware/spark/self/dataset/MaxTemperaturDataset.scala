package com.sundogsoftware.spark.self.dataset

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{FloatType, IntegerType, StringType, StructType}
import org.apache.spark.sql.functions._

object MaxTemperaturDataset {


  case class Temperature(stationID: String, date: Int, measure_type: String, temperature: Float)

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder()
      .appName("MinTemperatureDataset")
      .master("local[*]")
      .getOrCreate()

    // needed if no header provided
    val temperatureSchema = new StructType()
      .add("stationID", StringType, nullable = true)
      .add("date", IntegerType, nullable = true)
      .add("measure_type", StringType, nullable = true)
      .add("temperature", FloatType, nullable = true)

    // read the file as datase
    import spark.implicits._
    val ds = spark.read
      .schema(temperatureSchema)
      .csv("data/1800.csv")
      .as[Temperature]

    val maxTemps = ds.filter($"measure_type" === "TMAX")

    val stationTemps = maxTemps.select("stationID", "temperature")

    val maxTempsByStation = stationTemps.groupBy("stationID").max("temperature")//.alias("temperature2")

    val maxTempsByStationF = maxTempsByStation
      .withColumn("temperature", round($"max(temperature)" * .1f * (9f/5f) + 32f, 2))
      .select("stationID", "temperature")
      .sort("temperature")

    val results = maxTempsByStationF.collect()

    results.foreach(x=> println(x(0)+":"+x(1)))
    results.foreach(println)

    spark.stop()
  }
}
