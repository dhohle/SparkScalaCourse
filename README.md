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


| Video | Source | Note |
|--|--|--|
| [Video 24](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582416) | [SparkSQLDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/SparkSQLDataset.scala) | First create a `case class` for a `schema` for the `dataset`. Before mapping the data to the defined `case class` the internals of spark need to be `imported` using: `import spark.implicits._`. Map the date to the schema, after reading the data, by calling `as[CaseClassName]`. To be able to call an `SQL` query, you need to define a table name, like `dataset.createOrReplaceTempView("tableName")`. Then you can call an `SQL` query like: `SELECT * FROM tableName WHERE foo > 123` |
| [Video 25](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582420) | [DataFramesDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/DataFramesDataset.scala) | Most is the same as the previous one, except now we use the `spark.sql.functions` instead of `SQL queries directly`. The `where` statement becomes: `.filter(people("age") < 21)`; In `RDD`'s we had the `groupByKey` command, here we've got `groupBy`, like: `groupBy("age").count()` or update the fields with `select(people("name"), people("age") + 10)`  |
| [Video 26](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582424) + [Video 27](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022596) | [FriendsByAgeDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/FriendsByAgeWithDataset.scala) | Some examples such as `groupBy(col).avg().sort()` and `groupBy().agg(round(avg(colname), scale=2)).sort()` and `groupBy().agg(round(avg(),scale=2).alias("alias")).sort()`|
| [Video 28](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022618) | [WordCountDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/WordCountDataset.scala) | First, read text as `DataFrame` with the line as column. Then flatMap the line to new rows using `df.select(explode(split($"value", "\\W+")).alias("word"))`. LowerCase all words using `.select(lower($"word").alias("word"))`. Group the words by word and count occurrances using `.groupBy("word").count()`. Finally print it out. Below that is an example using `RDD`'s |
| [Video 29](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022638) | [MinTemperatureDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/MinTemperatureDataset.scala) + [MaxTemperatureDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/MaxTemperaturDataset.scala) | Create Temperature `case class`, and an explicit `schema` (because there's no header). Load the data using the `schema`, then load the `csv`, then parse to the `case class` using `as[Temperature]` (again, `import spark.implicits._` before reading the data file, for mapping to Temperature). Filter data like `.filter($"measureType" === "TMIN")`. Select the columns needed: `.select(col1, col2)`. Find minimum temperatures per station, like: `groupBy("station").min("temperature")`. Convert temperature, like `.withColumn("temperature", round($"min(temperature)" *.1f + (9f/5f) + 32f, scale=2)).select("station", "temperature").sort("temperature")`. Here we first said we want to create a new column (named temperature), which is filled with rounded value from the old `min(temperature)` column (created from the previous `min("temperature")` call) with the transformation of celcius to fahrenheit (times 10, because the dataset is strange). Then `select` the columns needed, and `sort` on the temperature column. Then collect results from `spark` to `scala` and print the results. |
| [Video 30](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022652) | [TotalSpendByCustomer](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/TotalSpendByCustomerDataset.scala) | Read dataset, `groupBy("customer")` then either `.agg(round(sum($"cost").alias("total_spent"))` or `.sum("cost").withColumn("total_spent", round($"sum(cost)", 2))` ; then `select("customer", "total_spent").orderBy("total_spent")` and print. Both the .agg(round) and .sum("cost") have the same results. At the moment, I don't know whether there is a substantial difference, either in flexibility or speed. |

## 5: Advanced Examples of Spark Programs
