name := "cassandra-couchbase-transfer-plugin"

version := "1.0"

scalaVersion := "2.11.8"

organization := "com.foobar"

libraryDependencies ++= {
  val sparkV = "2.0.2"
  Seq(
    "log4j" % "log4j" % "1.2.17",
    "org.slf4j" % "slf4j-api" % "1.7.21",
    "org.apache.spark" %% "spark-core" % sparkV ,
    "org.apache.spark" %% "spark-sql" % sparkV ,
    "org.apache.spark" %% "spark-streaming" % sparkV ,
    "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.0-M3",
    "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    "com.couchbase.client" %% "spark-connector" % "2.0.0",
    "com.typesafe" % "config" % "1.3.1"
  )
}
