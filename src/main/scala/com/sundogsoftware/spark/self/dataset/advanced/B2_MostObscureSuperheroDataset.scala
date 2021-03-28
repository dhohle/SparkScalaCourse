package com.sundogsoftware.spark.self.dataset.advanced

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object B2_MostObscureSuperheroDataset {

  /** Mapping */
  case class SuperHeroNames(id:Int, name:String)
  /** Raw line */
  case class SuperHero(value:String)

  def main(args:Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .appName("MostOscureSuperHero")
      .master("local[*]")
      .getOrCreate()

    val superHeroNamesSchema = new StructType()
      .add("id", IntegerType, true)
      .add("name", StringType, true)



    import spark.implicits._
    val names = spark.read
      .schema(superHeroNamesSchema)
      .option("sep", " ")
      .csv("data/Marvel-names.txt")
      .as[SuperHeroNames]

    /** Schema not needed here, because the file is loaded one line at the time, the and SuperHero class has 'value' as param */
    val lines = spark.read
      .text("data/Marvel-graph.txt")
      .as[SuperHero]


    val connections = lines
      // id is the first entry of the line
      .withColumn("id", split(col("value"), " ")(0))
      // the the total number of items in that line (minus the id) and save that number in column connections
      .withColumn("connections", size(split(col("value"), " "))-1)
      // group by the id, and count all connections. (Since multiple lines can be attributed to a single hero,
      // we need to count over multiple lines; if all connections to a single hero were on one line, this step would
      // not be necessary)
      .groupBy("id").agg(sum("connections").alias("connections"))

    // get the minimum value of column connections; the agg(min)) returns a dataset, use first to the the first row
    // the use getLong(0) to get the long value of the first column (as a value)
    val mostObscureNum = connections.agg(min("connections")).first().getLong(0)

    // filter the dataset on the minimum number of connections
    val mostObscure = connections.filter($"connections" === mostObscureNum)

    // join the datasets mostObscure with names on column id
//    val mostObscureWithName = mostObscure.join(names, "id")
    val mostObscureWithName = mostObscure.join(names, "id")

    // print
    mostObscureWithName.collect().foreach(x => println(s"${x(2)} is the most obscure superhero with ${x(1)} co-appearances."))

    //
    mostObscureWithName.select("name").show()

  }

}
