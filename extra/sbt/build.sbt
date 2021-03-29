name := "MovieSimilarities1MDataset"

version := "1.0"

organization := "com.sundogsoftware"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
"org.apache.spark" %% "spark-core" % "2.4.5" % "provided",
"org.apache.spark" %% "spark-sql" % "2.4.5" % "provided"
)
