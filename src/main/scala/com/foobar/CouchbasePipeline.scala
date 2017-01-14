package com.foobar

import com.couchbase.spark.sql._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object CouchbasePipeline extends App {


/*  val config = ConfigFactory.load("application.conf")
  val couchbaseUrl = config.getString("couchbase.url")
  val bucketName = config.getString("couchbase.bucketName")

  val bucketPassword = config.getString("couchbase.bucketPassword")*/
  val bucketName = "foobar"
  val keyspaceName = "foobar"
  val tableName = "testcouchbase"
  val idFeild = "id"
  val cassandraHost = "localhost"
  val couchbaseHost="shiv4nsh"
  val cassandraPort = 9042
  val conf = new SparkConf()
    .setAppName(s"CouchbaseCassandraTransferPlugin")
    .setMaster("local[*]")
    .set(s"com.couchbase.bucket.$bucketName", "")
    .set("com.couchbase.nodes", couchbaseHost)
    .set("spark.cassandra.connection.host", cassandraHost)
    .set("spark.cassandra.connection.port", cassandraPort.toString)
  val spark = SparkSession.builder().config(conf).getOrCreate()
  val sc = spark.sparkContext

  val cassandraRDD = spark.read
    .format("org.apache.spark.sql.cassandra")
    .options(Map("table" -> tableName, "keyspace" -> keyspaceName))
    .load()
  cassandraRDD.write.couchbase(Map(idFeild -> "uid"))
}
