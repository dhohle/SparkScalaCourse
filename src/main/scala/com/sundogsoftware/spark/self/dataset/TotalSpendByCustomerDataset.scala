package com.sundogsoftware.spark.self.dataset

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object TotalSpendByCustomerDataset {

  case class CustomerOrder(customerID: Int, orderID: Int, cost:Double)

  def main(args:Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)


    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("CustomerOrderDS")
      .getOrCreate()

    val coSchema = new StructType()
      .add("customerID", IntegerType, nullable = true)
      .add("orderID", IntegerType, nullable = true)
      .add("cost", DoubleType, nullable = true)

    import spark.implicits._
    val df = spark.read
      .schema(coSchema)
      .csv("data/customer-orders.csv")
      .as[CustomerOrder]


    val resultDF = df.groupBy("customerID")
//      .sum("cost").withColumn("total_spent", round($"sum(cost)", 2))
      .agg(round(sum($"cost"), 2).alias("total_spent"))
      .select("customerID", "total_spent")
      .orderBy("total_spent")

    // print from Spark
    resultDF.show(resultDF.count.toInt)


    // print from Scala (information retrieved from Spark)
    println(resultDF.schema.toString())
    val result = resultDF.collect()
    result.foreach(x => println(s"Customer with ID ${x.get(0)} spend $$${x.get(1)} USD"))

    spark.stop()
  }

}
