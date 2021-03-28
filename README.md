# SparkScalaCourse 
## [Apache Spark with Scala - Hands On with Big Data!](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data)

### Personal introduction to Spark with Scala
First off, all the source files are in src/main/scala/com/sundogsoftware/spark/example  
My own implementation are in the */self with folder structure depending on the type of files (RDD, Dataset, ...)
  The text files are in /data/*
 
 Source files: [sundog-education](https://sundog-education.com/sparkscala/)
 
 ----
First, for a little bit of reintroduction to Scala, the /scala_basic folder has some examples of coding in Scala.

| Lesson | Source |  Note |
|--|--|--|
| [Lesson 6](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5363784#overview) | [scala-1](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/scala_basic/LearningScala1-1.sc) | Scala `Primitives` |
| [Lesson 7](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5363790#overview) | [scala-2](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/scala_basic/LearningScala2-1.sc) | `Conditions` and `Loops` |
| [Lesson 8](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5363796#overview) | [scala-3](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/scala_basic/LearningScala3-1.sc) | Introduction for `functions`, creating, passing, ... |
| [Lesson 9](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5363798#overview) | [scala-4](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/scala_basic/LearningScala4-1.sc) | Data Structures: mainly `List` and `Map` and predefined function of them |

---
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
Then an introduction to `DataSet`/`DataFrames`: [Video 23](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582410)


| Video | Source | Note |
|--|--|--|
| [Video 24](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582416) | [SparkSQLDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/SparkSQLDataset.scala) | First create a `case class` for a `schema` for the `dataset`. Before mapping the data to the defined `case class` the internals of spark need to be `imported` using: `import spark.implicits._`. Map the date to the schema, after reading the data, by calling `as[CaseClassName]`. To be able to call an `SQL` query, you need to define a table name, like `dataset.createOrReplaceTempView("tableName")`. Then you can call an `SQL` query like: `SELECT * FROM tableName WHERE foo > 123` |
| [Video 25](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582420) | [DataFramesDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/DataFramesDataset.scala) | Most is the same as the previous one, except now we use the `spark.sql.functions` instead of `SQL queries directly`. The `where` statement becomes: `.filter(people("age") < 21)`; In `RDD`'s we had the `groupByKey` command, here we've got `groupBy`, like: `groupBy("age").count()` or update the fields with `select(people("name"), people("age") + 10)`  |
| [Video 26](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/5582424) + [Video 27](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022596) | [FriendsByAgeDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/FriendsByAgeWithDataset.scala) | Some examples such as `groupBy(col).avg().sort()` and `groupBy().agg(round(avg(colname), scale=2)).sort()` and `groupBy().agg(round(avg(),scale=2).alias("alias")).sort()`|
| [Video 28](https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022618) | [WordCountDataset](https://github.com/dhohle/SparkScalaCourse/blob/master/src/main/scala/com/sundogsoftware/spark/self/dataset/WordCountDataset.scala) |  |
|  |  |  |
|  |  |  |
|  |  |  |
|  |  |  |
|  |  |  |
