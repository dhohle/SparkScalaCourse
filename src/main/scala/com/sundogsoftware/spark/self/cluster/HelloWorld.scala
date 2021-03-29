package com.sundogsoftware.spark.self.cluster

import org.apache.spark._
import org.apache.log4j._

object HelloWorld{

  def main(args:Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc =new SparkContext("local[*]", "HelloWord")

    val lines = sc.textFile("data/ml-100k/u.data")

    val numLines = lines.count()

    println("Hello World! The u.data file has "+numLines+" lines.")

    sc.stop()
  }


}
