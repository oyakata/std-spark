import org.apache.spark.sql.{SaveMode, SparkSession}

object Hello {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().appName("Hello").getOrCreate()
        import spark.implicits._
        val columnNames = Seq("id", "gender", "age")
        val userinfo = spark.read.format("csv").option("header", "false").load("./foo.csv").toDF(columnNames: _*)
        userinfo.createOrReplaceTempView("hello")

        val sql = 
            """
              |SELECT
              |  id,
              |  t.key,
              |  t.value
              |FROM hello
              |LATERAL VIEW EXPLODE (
              |  MAP (
              |    'gender', gender,
              |    'age', age
              |  )
              |) t AS key, value
              |WHERE gender = 'female' AND t.value != ''
            """.stripMargin
        val record = spark.sql(sql)
        record.write.mode(SaveMode.Overwrite).format("csv").option("header", "false").option("codec", "org.apache.hadoop.io.compress.GzipCodec").save("out.csv")
    }
}
