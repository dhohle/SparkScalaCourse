package com.sundogsoftware.spark.self.dataset.ml

import org.apache.log4j._
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, StructType}

object LinearRegressionDataFrameDataset {

  case class RegressionSchema(label:Double, features_raw:Double)

  def main(args:Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .appName("LinearRegressionDF")
      .master("local[*]")
      .getOrCreate()

    // Normalize the data (Bell curves)

    val regressionSchema = new StructType()
      .add("label", DoubleType, true)
      .add("features_raw", DoubleType, true)

    import spark.implicits._
    val dsRaw = spark.read
      .option("sep", ",")
      .schema(regressionSchema)
      .csv("data/regression.txt")
      .as[RegressionSchema]

    // needed for linear regression
    val assembler = new VectorAssembler()
      .setInputCols(Array("features_raw"))
      .setOutputCol("features")

    val df = assembler.transform(dsRaw)
      .select("label", "features")

    //Let's split our data into training data and testing data
    val trainTest = df.randomSplit(Array(0.5,0.5))
    val trainingDF = trainTest(0)
    val testDF = trainTest(1)

    // Now create our linear regression model
    val lir = new LinearRegression()
      .setRegParam(.3)
      .setElasticNetParam(.8)
      .setMaxIter(100)
      .setTol(1E-6)

    // Train the model using our training data
    val model = lir.fit(trainingDF)

    // Now apply the trained model to the test data
    // cache is needed for big datasets to speed it up - to avoid multiple recalculations
    // this adds a new column "prediction" to the test dataframe
    val fullPredictions = model.transform(testDF).cache()

    //extract the predictions and the "known" correct labels - from Spark to Scala
    val preditionAndLabel = fullPredictions.select("prediction", "label").collect()

    // print out the predicted and actual values for each point
    preditionAndLabel.foreach(println)

    spark.stop()
  }

}
