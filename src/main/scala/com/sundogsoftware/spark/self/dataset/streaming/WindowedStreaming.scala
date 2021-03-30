package com.sundogsoftware.spark.self.dataset.streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, current_timestamp, regexp_extract, window}

object WindowedStreaming {



  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .appName("StructuredStreamingWindows")
      .master("local[*]")
      .getOrCreate()

    // streaming source that monitors the data/logs directory for text files
    val accessLines = spark.readStream.text("data/logs")

    // Regular Expressions to extract pieces of Apache Access log lines
    val contentSizeExp = "\\s(\\d+)$"
    val statusExp = "\\s(\\d{3})\\s"
    val generalExp = "\"(\\S+)\\s(\\S+)\\s*(\\S*)\""
    val timeExp = "\\[(\\d{2}/\\w{3}/\\d{4}:\\d{2}:\\d{2}:\\d{2} -\\d{4})]"
    val hostExp = "(^\\S+\\.[\\S+\\.]+\\S+)\\s"

    // Apply these regular expressions to create structure from the unstructured text
    val logDF = accessLines.select(
      regexp_extract(col("value"), hostExp, 1).alias("host"),
      regexp_extract(col("value"), timeExp, 1).alias("timestamp"),
      regexp_extract(col("value"), generalExp, 1).alias("method"),
      regexp_extract(col("value"), generalExp, 2).alias("endpoint"),
      regexp_extract(col("value"), generalExp, 3).alias("protocol"),
      regexp_extract(col("value"), statusExp, 1).cast("Integer").alias("status"),
      regexp_extract(col("value"), contentSizeExp, 1).cast("Integer").alias("content_size")
    )


    val windowed = logDF
      .withColumn("eventTime", current_timestamp())
      .groupBy(window(col("eventTime"), windowDuration = "30 seconds", slideDuration = "10 seconds"), col("endpoint"))
      .count().orderBy(col("count").desc).limit(10)


    // Display the stream to the console
    val query = windowed
      .writeStream
      .outputMode("complete")
      .format("console")
      .queryName("counts")
      .start()


    // wait for termination
    query.awaitTermination()
  }
}
