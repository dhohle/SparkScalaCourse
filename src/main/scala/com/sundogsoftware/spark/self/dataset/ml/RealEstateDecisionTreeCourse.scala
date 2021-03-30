package com.sundogsoftware.spark.self.dataset.ml

import org.apache.log4j._
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.{VectorAssembler, VectorIndexer}
import org.apache.spark.ml.regression.{DecisionTreeRegressionModel, DecisionTreeRegressor}
import org.apache.spark.sql._
import org.apache.spark.sql.types._


object RealEstateDecisionTreeCourse {

  case class RealEstateData(no: Int, TransactionDate: Double, HouseAge: Double, DistanceToMrt: Double,
                            NumberConvenienceStores: Int, Latitude: Double, Longitude: Double, PriceOfUnitArea: Double)

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Load Spark
    val spark = SparkSession
      .builder()
      .appName("RealEstate")
      .master("local[*]")
      .getOrCreate()

    // Load Data
    import spark.implicits._
    val realEstateData = spark.read
      .option("sep", ",")
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/realestate.csv")
      .as[RealEstateData]

    // map the input variables to intermediary stage
    val assembler = new VectorAssembler()
      .setInputCols(Array("HouseAge", "DistanceToMRT", "NumberConvenienceStores"))
      .setOutputCol("features")
    // transform input data to intermediary stage using the assembler's OutputCol as input variables
    val df = assembler.transform(realEstateData)
      .select("PriceOfUnitArea", "features")
    // Split data into train + test
    val Array(trainingDF, testDF) = df.randomSplit(Array(.7,.3))

    // train a decision tree; with label variable and mapped features
    val dt = new DecisionTreeRegressor()
      .setFeaturesCol("features")
      .setLabelCol("PriceOfUnitArea")

    // train the model on train data
    val model = dt.fit(trainingDF)

    // apply the model on test data
    val fullPredictions = model.transform(testDF)

    // print predictions on test data
    val predictionAndLabel = fullPredictions.select("prediction", "PriceOfUnitArea").collect()

    predictionAndLabel.foreach(println)

    spark.stop()
  }

}
