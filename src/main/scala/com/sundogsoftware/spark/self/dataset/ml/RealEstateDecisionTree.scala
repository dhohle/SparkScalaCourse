package com.sundogsoftware.spark.self.dataset.ml

import org.apache.log4j._
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.{VectorAssembler, VectorIndexer}
import org.apache.spark.ml.regression.{DecisionTreeRegressionModel, DecisionTreeRegressor}
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.sql._
import org.apache.spark.sql.types._


object RealEstateDecisionTree {

  case class RealEstateData(no: Int, transactionData: Float, houseAge: Float, distanceToMrt: Float,
                            numberConvenienceStores: Int, latitude: Float, longitude: Float, priceOfUnitArea: Float)

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Load Spark
    val spark = SparkSession
      .builder()
      .appName("RealEstate")
      .master("local[*]")
      .getOrCreate()


    // Load Schema
    val realEstateSchema = new StructType()
      .add("No", IntegerType, true)
      .add("TransactionData", FloatType, true)
      .add("HouseAge", FloatType, true)
      .add("DistanceToMRT", FloatType, true)
      .add("NumberConvenienceStores", IntegerType, true)
      .add("Latitude", FloatType, true)
      .add("Longitude", FloatType, true)
      .add("PriceOfUnitArea", FloatType, true)

    // Load Data
    import spark.implicits._
    val realEstateData = spark.read
      .option("sep", ",")
      .option("header", "true")
      .schema(realEstateSchema)
      .csv("data/realestate.csv")
      .as[RealEstateData]

    // Set input and label variables
    val inputVariables = Array("HouseAge", "DistanceToMRT", "NumberConvenienceStores")
    val labelVariable = "PriceOfUnitArea"

    // Features variable is used for internal representation for ML input variables; use VectorAssembler to create them
    val featuresNames = "features"
    val predictionColumn = "prediction"

    // map the input variables to intermediary stage
    val assembler = new VectorAssembler()
      .setInputCols(inputVariables)
      .setOutputCol(featuresNames)

    // transform input data to intermediary stage using the assembler's OutputCol as input variables
    val data = assembler.transform(realEstateData)
      .select(labelVariable, featuresNames)

    // Split data into train + test
    val Array(trainingData, testData) = data.randomSplit(Array(.7,.3))

    // ???
    val featureIndexer = new VectorIndexer()
      .setInputCol(featuresNames)
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4)
      .fit(data)

    // train a decision tree; with label variable and mapped features
    val dt = new DecisionTreeRegressor()
      .setLabelCol(labelVariable)
      .setFeaturesCol(featuresNames)

    // Create a pipeline; for what?
    val pipeline = new Pipeline()
      .setStages(Array(featureIndexer, dt))

    // train the model on train data
    val model = pipeline.fit(trainingData)

    // apply the model on test data
    val predictions = model.transform(testData)

    // print predictions on test data
    predictions.select(predictionColumn, labelVariable, featuresNames).show(5)

    // Select (prediction, true label) and compute test error
    val evaluator = new RegressionEvaluator()
      .setLabelCol(labelVariable)
      .setPredictionCol(predictionColumn)
      .setMetricName("rmse")

    val rmse = evaluator.evaluate(predictions)
    println(s"Root mean Squared Error (RSME) on test data = $rmse")

    val treeModel = model.stages(1).asInstanceOf[DecisionTreeRegressionModel]
    println(s"Learned regression tree model\n ${treeModel.toDebugString}")

    spark.stop()
  }

}
