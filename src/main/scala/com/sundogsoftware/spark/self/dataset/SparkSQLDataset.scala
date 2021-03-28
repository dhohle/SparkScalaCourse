package com.sundogsoftware.spark.self.dataset

import org.apache.spark._
import org.apache.log4j._
import org.apache.spark.sql.SparkSession

object SparkSQLDataset {

  // Type must be predefined
  case class Person(id:Int, name:String, age:Int, friends:Int)

  def main(args:Array[String]): Unit ={

    Logger.getLogger("org").setLevel(Level.ERROR)

    // User SparkSession interface
    val spark = SparkSession
      .builder
      .appName("SparkSQL")
      .master("local[*]")
      .getOrCreate()

    // Load each line of the source data into a Dataset
    import spark.implicits._
    val schemaPeople = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/fakefriends.csv")
      .as[Person]// converts dataframe to dataset using (case class) Person
    // without as[Person], this code will still work, but will not be checked at compile time, also some compile-run optimalization are not done

    schemaPeople.printSchema()

    // Create a view/table with name people
    schemaPeople.createOrReplaceTempView("people")

    //
    val teenagers = spark.sql("SELECT * FROM people WHERE age >=13 AND age <= 19")

    val results = teenagers.collect()

    results.foreach(println)

    spark.stop()
  }

}
