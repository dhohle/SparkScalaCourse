package com.sundogsoftware.spark.self.rdd.graphx

import org.apache.log4j._
import org.apache.spark._
import org.apache.spark.graphx.{Edge, VertexId}

object GraphXPregel {


  def parseNames(line: String): Option[(VertexId, String)] = {
    val fields = line.split('\"')
    if (fields.length > 1) {
      val heroID: Long = fields(0).trim().toLong
      if (heroID < 6487) {
        return Some(heroID, fields(1))
      }
    }
    None
  }

  def makeEdges(line: String): List[Edge[Int]] = {
    import scala.collection.mutable.ListBuffer
    var edges = new ListBuffer[Edge[Int]]()
    val fields = line.split(" ")
    val origin = fields(0)
    for (x <- 1 until (fields.length - 1)) {
      edges += Edge(origin.toLong, fields(x).toLong, 0)
    }
    edges.toList
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "GraphX")

    val names = sc.textFile("data/marvel-names.txt")
    val verts = names.flatMap(parseNames)

    val lines = sc.textFile("data/marvel-graph.txt")
    val edges = lines.flatMap(makeEdges)

    val default = "Nobody"
    val graph = Graph(verts, edges, default).

  }

}
