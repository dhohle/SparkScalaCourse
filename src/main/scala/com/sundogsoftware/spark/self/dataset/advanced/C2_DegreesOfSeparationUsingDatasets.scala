package com.sundogsoftware.spark.self.dataset.advanced


import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Dataset, SparkSession}

object C2_DegreesOfSeparationUsingDatasets {


  val startCharacterID = 5306;
  val targetCharacterID = 14

  case class SuperHero(value: String)

  case class BFSNode(id: Int, connections: Array[Int], distance: Int, color: String)


  def createStartingDs(spark: SparkSession): Dataset[BFSNode] = {
    import spark.implicits._
    val inputFile = spark.read
      .textFile("data/Marvel-graph.txt")
      .as[SuperHero]

    val connections = inputFile
      .withColumn("id", split(col("value"), " ")(0).cast("int"))
      .withColumn("connections", slice(split(col("value"), " "), 2, 9999).cast("array<int>"))
      .select("id", "connections")

    val result = connections
      .withColumn("distance", when(col("id") === startCharacterID, 0).when(col("id") =!= startCharacterID, 9999))
      .withColumn("color", when(col("id") === startCharacterID, "GRAY").when(col("id") =!= startCharacterID, "WHITE"))
      .as[BFSNode]

    result
  }

  def exploreNode(spark: SparkSession, ds: Dataset[BFSNode], iteration: Int): (Dataset[BFSNode], Long) = {
    import spark.implicits._
    val rawExploreDS = ds
      .filter($"color" === "GRAY")
      .select($"id", explode($"connections").alias("child")).distinct()

    val hitCount = rawExploreDS.filter($"child" === targetCharacterID).count()
    val exploreDS = rawExploreDS.distinct().select("child")

    // All parent become explored after fetting exploreDS so we marked as "BLACK"
    val exploring = ds.
      withColumn("color", when(col("color") === "GRAY", "BLACK").otherwise($"color"))
      .as[BFSNode]

    val result = exploring
      .join(exploreDS, exploring("color") === "WHITE" && exploring("id") === exploreDS("child"), "leftouter")
      .withColumn("distance", when(col("child").isNotNull, iteration).otherwise($"distance"))
      .withColumn("color", when(col("color") === "WHITE" && col("child").isNotNull, "GRAY").otherwise($"color"))
      .select("id", "connections", "distance", "color")
      .as[BFSNode]

    (result, hitCount)
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .appName("DegreesOfSeparation")
      .master("local[*]")
      .getOrCreate()

    var hitCount: Long = 0

    var iterationDs = createStartingDs(spark)

    for (iteration <- 1 to 10) {
      println("Running BFS Iteration#" + iteration)
      val resultExplore = exploreNode(spark, iterationDs, iteration)
      iterationDs = resultExplore._1
      hitCount += resultExplore._2

      if (hitCount > 0) {
        println("Hit the target Character! From " + hitCount + " different direction(s).")
        return
      }
    }

  }
}
