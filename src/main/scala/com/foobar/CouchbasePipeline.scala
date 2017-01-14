package com.foobar

import java.util.UUID

import com.couchbase.spark.sql._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object CouchbasePipeline extends App {

  //Couchbase Configuration
  val bucketName = "foobar"
  val couchbaseHost = "localhost"
  val idFeild = "id"

  //Cassandra Configuration
  val keyspaceName = "foobar"
  val tableName = "testcouchbase"
  val cassandraHost = "localhost"
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


  import org.apache.spark.sql.functions._

  val uuidUDF = udf(CouchbaseHelper.getUUID _)
  val rddToBeWritten = if (cassandraRDD.columns.contains(idFeild)) {
    cassandraRDD.withColumn("META_ID", cassandraRDD(idFeild))
  } else {
    cassandraRDD.withColumn("META_ID", uuidUDF())
  }
  rddToBeWritten.write.couchbase()
}

object CouchbaseHelper {

  def getUUID: String = UUID.randomUUID().toString
}

