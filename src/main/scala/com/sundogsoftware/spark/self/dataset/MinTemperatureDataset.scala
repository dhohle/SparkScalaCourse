package com.sundogsoftware.spark.self.dataset

import org.apache.spark._
import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object MinTemperatureDataset {

  case class Temperature(stationID:String, date:Int, measure_type: String, temperature: Float)

  def main(args:Array[String]): Unit ={

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

    // Filter out all but TMIN entries
    val minTemps = ds.filter($"measure_type" === "TMIN")

    // Select only stationID and temperature
    val stationTemps = minTemps.select("stationID", "temperature")

    // Aggregate to find minimum temperature for every station
    val minTempsByStation = stationTemps.groupBy("stationID").min("temperature")//.alias("min(temperature")

    // convert temperature to fahrenheit and sort the dataset
    val minTempsByStationF = minTempsByStation
      .withColumn("temperature", round($"min(temperature)" * .1f * (9f/5f) + 32f, 2))
      .select("stationID", "temperature").sort("temperature")

    // Collect, format, and print the results
    val results = minTempsByStationF.collect()

    for (result <- results){
      val station = result(0)
      val temp = result(1).asInstanceOf[Float]
      val formattedTemp = f"$temp%.2f F"
      println(s"$station minimum temperature: $formattedTemp")
    }

  }

}
