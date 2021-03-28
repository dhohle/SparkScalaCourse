package com.sundogsoftware.spark.self.dataset

import org.apache.spark._
import org.apache.log4j._
import org.apache.spark.sql.SparkSession

object DataFramesDataset {

  case class Person(id:Int, name:String, age:Int, friends:Int)

  def main(args:Array[String]): Unit ={

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .appName("SparkSQL")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    val people = spark.read
      .option("header", "true")
      .option("inferSchema", "true")// if inferSchema is true, you must import spark.implicits._ (right before)
      .csv("data/fakefriends.csv")
      .as[Person]

    println("Here is our inferred schema:")
    people.printSchema()

    println("Let's select the name column:")
    people.select("name").show()

    println("Filter out anyone over 21:")
    people.filter(people("age") < 21).show();

    println("Group by age:")
    people.groupBy("age").count().show();

    println("Make everyone 10 years older: ")
    people.select(people("name"), people("age")+10).show()

    spark.stop()
  }

}
