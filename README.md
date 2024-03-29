# SparkScalaCourse 
## [Apache Spark with Scala - Hands On with Big Data!](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data)

Note to the unlikely reader. This document is not meant for anybody to understand. If this were to help you, great! But it is mainly for me to get a better understanding by writing stuff out.

### Personal introduction to Spark with Scala
First off, all the source files are in src/main/scala/com/sundogsoftware/spark/example  
My own implementation are in the */self with folder structure depending on the type of files (RDD, Dataset, ...)
  The text files are in /data/*
 
 Source files: [sundog-education](https://sundog-education.com/sparkscala/)
 
 ----
## Section 2: Scala Crash Course
First, for a little bit of reintroduction to Scala, the /scala_basic folder has some examples of coding in Scala.

| Lesson | Source |  Note |
|--|--|--|
| [Video 6](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5363784#overview) | [scala-1](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/scala_basic/LearningScala1-1.sc) | Scala `Primitives` |
| [Video 7](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5363790#overview) | [scala-2](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/scala_basic/LearningScala2-1.sc) | `Conditions` and `Loops` |
| [Video 8](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5363796#overview) | [scala-3](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/scala_basic/LearningScala3-1.sc) | Introduction for `functions`, creating, passing, ... |
| [Video 9](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5363798#overview) | [scala-4](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/scala_basic/LearningScala4-1.sc) | Data Structures: mainly `List` and `Map` and predefined function of them |

---
## Section 3: Using Resilient Distributed Datasets (RDDs)
First off the Spark 1.x implementation using `RDD`'s

Introduction to RDD: [Lesson 10](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364918#overview)

| Lesson | Source | Notes |
|--|--|--|
| [Video 11](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364924) | [Rating Counter](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/rdd/RatingsCounter.scala) | Load Lines, map by `\t` `countByValue` sort results by `.toSeq.sortBy(_,_1)` |
| [Video 13](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364932) | [Friends by Age](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/rdd/FriendsByAge.scala) | Load lines, `mapValues(x => (x, 1))` to count the values `1` by `reduceByKey((x,y) => (x._1 + y._1, x._2 + y._2)` which takes the `(x, 1)` (where x = age:Int). Then `mapValues(_._1 /_._2)` |
| [Video 15](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364940) | [MinTemperature](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/rdd/MinTemperatures.scala) & [MaxTemperature](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/rdd/MaxTemperatures.scala) | Load lines, `filter` on value, `map` to `Tuple(stationID, temperature)`; find minimum by `reduceByKey((x, y) => min(x, y))`; maxTemp is likewise |
| [Video 17](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364950#overview) | [WordCount](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/rdd/WordsCountInBookBasic.scala) | Simple count by using `flatMap` to map words to rows by: `.flatMap(x => x.split(" "))` |
| [Video 18](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364956) | [WordCountBetter](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/rdd/WordsCountInBookBetter.scala) | Update to WordCount, with 	`Regex` for `split("\\W+")`, map to lowerCase `map(_.toLowerCase())`	 |
| [Video 19](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364962) | [WordCountBetterSorted](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/rdd/WordsCountInBookBetterSorted.scala) | Starts with WordCountBetter, then count words by `.map((_, 1)).reduceByKey(_ + _)` then sort with `.map(x => (x._2, x._1)).sortByKey()` |
| [Video 20](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364964) | [TotalSpentByCustomer](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/rdd/TotalSpentByCustomer.scala) | Recap |

---
## Section 4: SparkSQL, DataFrames, and DataSets
Then an introduction to `DataSet`/`DataFrames`: [Video 23](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582410)
More information on DataSets [apache](https://spark.apache.org/docs/latest/api/scala/org/apache/spark/sql/Dataset.html)


| Video | Source | Note |
|--|--|--|
| [Video 24](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582416) | [SparkSQLDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/SparkSQLDataset.scala) | First create a `case class` for a `schema` for the `dataset`. Before mapping the data to the defined `case class` the internals of spark need to be `imported` using: `import spark.implicits._`. Map the date to the schema, after reading the data, by calling `as[CaseClassName]`. To be able to call an `SQL` query, you need to define a table name, like `dataset.createOrReplaceTempView("tableName")`. Then you can call an `SQL` query like: `SELECT * FROM tableName WHERE foo > 123` |
| [Video 25](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582420) | [DataFramesDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/DataFramesDataset.scala) | Most is the same as the previous one, except now we use the `spark.sql.functions` instead of `SQL queries directly`. The `where` statement becomes: `.filter(people("age") < 21)`; In `RDD`'s we had the `groupByKey` command, here we've got `groupBy`, like: `groupBy("age").count()` or update the fields with `select(people("name"), people("age") + 10)`  |
| [Video 26](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582424) + [Video 27](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022596) | [FriendsByAgeDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/FriendsByAgeWithDataset.scala) | Some examples such as `groupBy(col).avg().sort()` and `groupBy().agg(round(avg(colname), scale=2)).sort()` and `groupBy().agg(round(avg(),scale=2).alias("alias")).sort()`|
| [Video 28](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022618) | [WordCountDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/WordCountDataset.scala) | First, read text as `DataFrame` with the line as column. Then flatMap the line to new rows using `df.select(explode(split($"value", "\\W+")).alias("word"))`. LowerCase all words using `.select(lower($"word").alias("word"))`. Group the words by word and count occurrances using `.groupBy("word").count()`. Finally print it out. Below that is an example using `RDD`'s |
| [Video 29](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022638) | [MinTemperatureDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/MinTemperatureDataset.scala) + [MaxTemperatureDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/MaxTemperaturDataset.scala) | Create Temperature `case class`, and an explicit `schema` (because there's no header). Load the data using the `schema`, then load the `csv`, then parse to the `case class` using `as[Temperature]` (again, `import spark.implicits._` before reading the data file, for mapping to Temperature). Filter data like `.filter($"measureType" === "TMIN")`. Select the columns needed: `.select(col1, col2)`. Find minimum temperatures per station, like: `groupBy("station").min("temperature")`. Convert temperature, like `.withColumn("temperature", round($"min(temperature)" *.1f + (9f/5f) + 32f, scale=2)).select("station", "temperature").sort("temperature")`. Here we first said we want to create a new column (named temperature), which is filled with rounded value from the old `min(temperature)` column (created from the previous `min("temperature")` call) with the transformation of celcius to fahrenheit (times 10, because the dataset is strange). Then `select` the columns needed, and `sort` on the temperature column. Then collect results from `spark` to `scala` and print the results. |
| [Video 30](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022652) | [TotalSpendByCustomer](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/TotalSpendByCustomerDataset.scala) | Read dataset, `groupBy("customer")` then either `.agg(round(sum($"cost").alias("total_spent"))` or `.sum("cost").withColumn("total_spent", round($"sum(cost)", 2))` ; then `select("customer", "total_spent").orderBy("total_spent")` and print. Both the .agg(round) and .sum("cost") have the same results. At the moment, I don't know whether there is a substantial difference, either in flexibility or speed. |

## 5: Advanced Examples of Spark Programs
Introduction 

Source root: [advanced](https://github.com/dhohle/SparkScalaCourse/tree/master/src/main/scala/com/sundogsoftware/spark/self/dataset/advanced)

|  |  |  |
|--|--|--|
| [Video 32](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364972) | [MovieRatingDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/advanced/A1_MovieRatingDataset.scala) | 1) Find the 10 most 'watched' movies (according to this dataset). Load data as usual. However, since we only need the `movieID`, the `case class schema` only has `movieID`, the other columns will be disregarded. After loading the data in a `DataSet`, we `groupBy("movieID").count().orderBy(desc("count"))` and print the first 10 like `show(10)`. Note the `count` where we sort on, is a column created by counting the movies, by calling `.count()`. By default, Spark presumed a `comma-separated file`, since this file is a tab separated file, we need to tell Spark to load it in with `\t` before the `csv` file is loaded, using `.option("sep", "\t")` |
| [Video 33](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364974) | [PopularMoviesBroadcast](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/advanced/A2_PopularMoviesDatasetBroadcast.scala) | Combine the previous result, with another input - loaded using Scala - and sent to the cluster as a `Broadcast variable`. Broadcast the loaded Map using `val nameDict = spark.sparkContext.broadcast(loadMovieNames())`. This changes the `Map[Int,String]` type to `Broadcast[Map[Int, String]]`. Now the Map - from `loadMovieNames` are broadcasted to all computers in the cluster. Load the movie data, like in `MovieRatingDataset`. Now, create an anonymous function to map an `Int` (movieID) to a `String` (movieName) by using `nameDict` (the broadcasted dictionary) and get the value (String) for the key (Int) - per row. Function like `val lookupName: Int => String = (movieID:Int) => nameDict.value(movieID)`. This function needs to be wrapped up as an `udf` (User Defined Function) by `val lookupNameUDF = udf(functionName)`. Finally, map the movieID to the name by creating a new column and apply the `udf` to the value of column `movieID`, like: `movieCounts.withColumn("movieTitle", lookupNameUDF(col("movieID)))`. The sort and print |
| [Video 34](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364976) | [MostPopularSuperhero](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/advanced/B1_MostPopularSuperheroDataset.scala) | Load 2 data files. Not a lot special going on though |
| [Video 35](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22043502) + [Video 36](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22043518) | [MostObscureSuperheroDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/advanced/B2_MostObscureSuperheroDataset.scala) | This starts like the previous, but find the least popular ones. Whereas for the latter we expected only one, for this we expect multiple. So, first, find the minimum number of co-occurrences by `.agg(min("connections")).first().getLong(0)`. Use that number to filter the previous result on `.filter($"connections" === minNumber)`. Then we need to join the `mostObscure` with the `names` dataframes. They both have `id` as identifier, so this will work `mostObscure.join(names, "id")`. Then print and done.  |
| [Video 37](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364980) + [Video 38](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5591610) + [Video 39](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5591610) | [BFSinRDD](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/rdd/advanced/C1_DegreesOfSeparationBetweenSuperHeroes.scala) + [BFSinDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/advanced/C2_DegreesOfSeparationUsingDatasets.scala) | Find the degrees of separation between super-heroes. Using RDD's and Datasets. The dataset version is ~3 times slower, but its implementation has some interesting features, like `withColumn`, `when` and `otherwise` and `join` |
| [Video 40](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364988) + [Video 41](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364990) | [MovieSimilarities](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/advanced/D1_MovieSimilarities.scala) | Item-Based Collaborative Filtering, with `caching` and `self-join` |
| [Video 41](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5364992) | [MoviesSimilarityUpdate](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/advanced/D1_MovieSimilaritiesUpdate.scala) |  |


# Running on a Cluster

## Packaging and Monitoring
## Using IntelliJ
[Create a Jar using IntelliJ](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5436234). 
- File->Project Structure->Artifacts->+->Jar->Empty
- Select necessary elements and name the jar; check `include in project build`-> OK
- `Ctrl+F9` to build project -> {root}/out/artifacts/{projectName}/{jarName}
- For Testing purposes (with local files), navigate to {root} dir
	- {pathToSparkSubmit} --class {pathToMainClass} {pathToJar}
	- `Y:\spark-3.1.1\bin\spark-submit.cmd --class com.sundogsoftware.spark.self.cluster.HelloWorld F:\IntelliJ\scala\SparkScalaCourse\out\artifacts\sparkCourse\sparkCourse.jar`

### Packaging with SBT
[See Video](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5436346)

http://media.sundog-soft.com/SparkScala/sbt.zip
See: `extra/sbt` for the test run.
See:  `extra/sbt/build.sbt` here the default Spark packages are set to `provided` because it is assumed those packages are in the production environment. If they are not expected to be found there, leave the `provided` bit out, so those dependencies will be added to the `jar`
See: `extra/sbt/project/assembly.sbt` which says `addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.10")
`
To be sure: start `cmd` with `administrator rights`

Go to the `sbt` folder `{root}/extra/sbt` and type: `sbt assembly`
Then `cd ` to `target\scala-2.11`; this is where the `jar` is
[Video 45](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22045942): 

Create a new directory for that project (if multiple main classes are in the current project).
Create a `build.sbt` file to package one application:
```
name := "SparkScalaCourse"
version := "0.1"
scalaVersion := "2.12.12"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.1.1"  % "provided",
  "org.apache.spark" %% "spark-sql" % "3.1.1" % "provided"
)
```
-	Create file in `project/assembly.txt` and add 
	- `addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.10")`
- Copy the relevant source files in `src/main/scala/*`
- In `terminal` run `sbt assembly`
	- This creates a new directory `target/scala-2.12` with the containing `jar`
- Since we need a local data file, make sure `data\1800.csv` can be reached from the current `path`
- Then execute on the cluster using:
`Y:\spark-3.1.1\bin\spark-submit.cmd  target\scala-2.12\SparkScalaCourse-assembly-0.1.jar`
[Video 46](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22045960): how teacher did it
- Be aware of local files
- If running on cluster, remove (or change) `master("local[*]")`
- Check Spark version in the `{SparkRoot}/RELEASE`
- Check compatible Scala version for Spark: [see](https://spark.apache.org/downloads.html)  then check [latest Scala build](https://www.scala-lang.org/download/all.html)

## Cloud Settings
-  `--master`
	- yarn - for running a YARN / Hadoop cluster
	- hostname:port - for connecting to a master on a Spark standalone cluster
	- mesos://masternode:port
	- A master in your SparkConf will override this!
- `--num-executors`
	- Must set explicitly with YARN, only 2 by default
- `--executor-memory`
	- Make sure you don't try to use more memory than you have
- `--total-executor-cores`

## Amazon Elastic MapReduce
[Video 47](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5365002) Introduction to Elastic MapReduce
[Video 48](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5365004) Example in EMR and how to

## Repartitioning
[Video 49](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5365008)
- Running our movie similarity script as-is might not work at all.
	- The self-join is expensive, and Spark won\t distribute it on its own
- Use `.repartition()` on a DataFrame , or `.partitionBy()` on an RDD before running a large operation that benefits from partitioning
	- `Join()`, `cogroup()`, `groupWith()`, `join()`, `leftOuterJoin()`, `rightOuterJoin()`, `gorupByKey`, `reduceByKey()`, `combineByKey()`, and `lookUp()`
	- Those operations will preserve your partitioning in their result too
- Too few partitions won't take full advantage of your cluster
- Too many partitions results in too much overhead from shuffling data
- At least as many partitions as you have cores, or executors that fit within your available memory
- `partitionBy(100)` is usually a reasonable place to start for large operations

## Best practices for Running on a Cluster
[Video 50](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5457856)
- Avoid specifying Spark configuration in your driver script (including master) - this way, we'll use the defaults EMR sets up instead, as well as any command-line options you pass into `spark-submit`from your master node
- If executors start failing, you may need to adjust the memory each executor has. For example: `spark-submit --executor-memory 1g {jarfile} {params}` 
- Can use `--master yarn`  to run on a YARN cluster (but most of the time the default cluster values are good)
- EMR sets this up by default
- Get your scripts & data someplace where EMR can access them easily
	- AWS's S3 is a good choice - just use s3n:// URL's when specifying file paths, and make sure your file permissions make them accessible
- Spin up an EMR cluster for Spark using the AWS console
- Get the external DNS name for the master node, and log into it using the `"hadoop"`user account and your private key file
- Copy your driver program's JAR file and any files it needs
- Run spark-submit and watch the output
- (terminate the cluster when done)

## Troubleshooting Cluster Jobs
- Master will run a console on port `4040`
	- But in EMR, it's next to impossible to actually connect to it from outside
	- If you have your own cluster running on your own network, life's a little easier in that respect
- Local Example: 
	- Start `example/MovieSimilarities1MDatasetLocal`
	- Go to : `http://127.0.0.1:4040/jobs/`
- Logs
	- in standalone mode, they're in the web UI
	- In YARN though, the logs are distributed. You need to collect them after the fact using `yarn logs --aplicationID <app ID>`
- While your driver runs the script, it will log error like executors failing to issue heartbeats
	- This generally means you are asking too much of each executor
	- You may need more of them
	- Each executor may need more memory
	- Or use `partitionBy()` to demand less work from individual executors by using smaller partitions
- Managing Dependencies
	- Remember your executors aren't necessarily on the same box as your driver script
	- User broadcast variables to share data outside of `RDD`'s or `DataSets`
	- Need some Java or Scala package that's not pre-loaded on EMR?
		- Bundle them into your JAR with sbt assembly
		- Or use `--jar` with `spark-submit`to ass individual libraries that are on the master
		- Try to just avoid using obscure packages you don't need in the first place. 

# Machine Learning with Spark ML
## [MLLib](https://spark.apache.org/mllib/) 

### [Capabilities](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5365026)
- Feature Extraction
	- Term Frequency/ Inverse Document Frequency
- Basic Statistics
	- Chi-squared, Correlation (Spearman, Pearson), min, max, mean, variance
- Linear and logistic regression
- SVM
- Naïve Bayes Classifier
- Decision Trees
- K-Means
- PCA 
- Recommendations using Alternating Least Squares

Previous API was called `MLLib` and used `RDD`'s and some specialized data structures - this is deprecated and being discarded since `Spark 3.0`
The newer `ML`library just uses dataframes for everything


## Spark ML examples

| Video | Code | Notes |
|--|--|--|
| [Video 53](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5365028) | [MovieRecommendation](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/ml/MovieRecommendationDataset.scala) | Alternating Least Square algorithm. This is a simple example of how to use a ML algorithm, it performs really bad on this dataset though |
| [Video 54](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5365042) + [Video 55](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582472) | [LinearRegressionDataFrame](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/ml/LinearRegressionDataFrameDataset.scala) | Linear Regression. |
| [Video 56](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22063906) | [RealEstateDecisionTree](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/ml/RealEstateDecisionTree.scala) | [Decision trees](https://spark.apache.org/docs/latest/ml-classification-regression.html#decision-trees) |
| [Video 57](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22063912) | [RealEstateDecisionTreeSunDog](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/ml/RealEstateDecisionTreeCourse.scala) | Decision Trees, differences. No explicit schema, used `option("inferschema", "true")` instead. No Pipeline, no featuresIndexer. Far simpler implementation than mine. I think some code from my implementation are not used, because this implementation throws errors on the `case class` types (basically all Floats need to be Doubles), incl named type (transactionData -> transactionDate) |

# Spark Streaming
[Start Video 58](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5365054)
- Analyses continual streams of data
	- Common example: processing log data from a website or server
- Data is aggregated and analyzed at some given interval
- Can take data from some port, Amazon Kinesis, HDFS, Kafka, Flume and others
- "Checkpointing" stores state to disk periodically for fault tolerance 
- DStream
	- A "Dstream" object breaks up the stream into distinct `RDD`'s
	- Used a StreamingContext
	- The RDD's only contain one little chunck of incoming data
	- "Windowed operations" can combine results from multiple batches over some sliding time window
		- see `window()`, `reduceByWindow()`, `reduceByKeyAndWindow()`
	- `updateStateByKey()`
		- Lets you maintain a state across many batches as time goes on
		- For example, running counts of some event

[Real-time Monitoring of the Most Popular Hashtags on Twitter](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022796) with [Code](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/example/PopularHashtags.scala)

## Structured Streaming
[Video 60](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582486)
- Spark 2 introduces "structured Streaming"
- Uses DataSets as its primary API
- Image a DataSet that just keeps getting appended to forever, and you query it whenever you want
- Streaming is now real-time and not based on "micro-batches"
Example:
```scala
val inputDF = spark.readStream.json("s3://logs)
inputDF.groupBy($"action", window($"time", "1 hour")).count()
	.writeStream.format("jdbc").start("jdbc:mysql://...")
```
### Streaming Example
[Video 61](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022812) with [Code](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/streaming/StructuredStreaming.scala)
The streaming part is:
```scala
val accessLines = spark.readStream.text("data/logs")
val logDF = accessLines.select(col("status"))
val statusCountDF = logDF.groupBy("status").count()
// Display the stream to the console  
val query = statusCountDF  
  .writeStream  
  .outputMode("complete")  
  .format("console")  
  .queryName("counts")  
  .start()
```
This will keeps checking the dir `data/logs` and when a new file appears, that file is processed

### Sliding Windows example
[Video 62](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22067484)
[Video 63](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22067500)
[Code](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/streaming/WindowedStreaming.scala)

## GraphX and Pregel
Works with RDD's, and is not being actively worked on... But here the [superhero example](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/rdd/graphx/GraphXPregel.scala) 

# Finishing course:
Book recommendations: 
- [O'Reailly - Learning Spark](https://www.oreilly.com/library/view/learning-spark-2nd/9781492050032/) - July 2020
- [Advanced Analytics with Spark](https://www.oreilly.com/library/view/advanced-analytics-with/9781491912751/) - April 2015 (seems too old)
- [Spark Website](https://spark.apache.org/)
	- [Spark SQL](https://spark.apache.org/sql/)
	- [Spark Streaming](https://spark.apache.org/streaming/)
	- [Spark MLlib](https://spark.apache.org/mllib/)
	- [Spark GraphX](https://spark.apache.org/graphx/)
