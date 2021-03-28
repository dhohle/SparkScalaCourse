package com.sundogsoftware.spark.self.dataset

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{avg, round}


object FriendsByAgeWithDataset {

  case class Friend(id: Int, name: String, age: Int, friends: Int)

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("FriendsByAgeWithDataset")
      .getOrCreate()

    import spark.implicits._
    val friends = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/fakefriends.csv")
      .as[Friend]


    val friendsByAge =friends.select("age", "friends")

    println("clean")
    friendsByAge.groupBy("age").avg("friends").show()

    //
    println("sorted")
    friendsByAge.groupBy("age").avg("friends").sort("age").show()


    println("Formatted")
    friendsByAge.groupBy("age")
      .agg(round(avg("friends"), 2))
      .sort("age").show()

    println("Formatted with alias")
    friendsByAge.groupBy("age")
      .agg(round(avg("friends"), 2).alias("friends_avg"))
      .sort("age").show()


    spark.stop()
  }


}
