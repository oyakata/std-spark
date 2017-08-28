name := "std-spark"

version := "1.0"

scalaVersion := "2.11.11"

resolvers += "velvia maven" at "http://dl.bintray.com/velvia/maven"
resolvers += Resolver.bintrayRepo("komiya-atsushi", "maven")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.1.0"  % "provided" exclude("org.scalatest", "scalatest_2.11"),
  "org.apache.spark" %% "spark-mllib" % "2.1.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.1.0" % "provided",
  "com.amazon.emr" % "emr-dynamodb-hadoop" % "4.2.0",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.10.75",
  "org.json4s" %% "json4s-jackson" % "3.2.11",
  "com.typesafe" % "config" % "1.3.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalactic" %% "scalactic" % "3.0.1",
  "com.github.fommil.netlib" % "all" % "1.1.2" pomOnly()
)

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xml" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".css" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".types" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
  case "production.conf" => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
