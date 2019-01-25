package com.foobar

import java.util.UUID

import com.couchbase.spark.sql._
import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object CouchbasePipeline extends App {

  val config = ConfigFactory.load()
  //Couchbase Configuration
  val bucketName = config.getString("couchbase.bucketName")
  val couchbaseHost = config.getString("couchbase.host")
  val couchbaseUsername = config.getString("couchbase.username")
  val couchbasePassword = config.getString("couchbase.password")

  //Spark Configuration
  val sparkIp = config.getString("spark.url")

  //Cassandra Configuration
  val keyspaceName = config.getString("cassandra.keyspaceName")
  val tableName = config.getString("cassandra.tableName")
  val idField = config.getString("cassandra.idField")
  val cassandraHost = config.getString("cassandra.host")
  val cassandraPort = config.getInt("cassandra.port")
  val cassandraUsername = config.getString("cassandra.username")
  val cassandraPassword = config.getString("cassandra.password")

  val conf = new SparkConf()
    .setAppName(s"CouchbaseCassandraTransferPlugin")
    .setMaster(sparkIp)
    .set(s"spark.couchbase.bucket.${bucketName}", "")
    .set("spark.couchbase.nodes", couchbaseHost)
    .set("spark.couchbase.username", couchbaseUsername)
    .set("spark.couchbase.password", couchbasePassword)
    .set("spark.cassandra.connection.host", cassandraHost)
    .set("spark.cassandra.connection.port", cassandraPort.toString)
    .set("spark.cassandra.auth.username", cassandraUsername)
    .set("spark.cassandra.auth.password", cassandraPassword)
  val spark = SparkSession.builder().config(conf).getOrCreate()
  val sc = spark.sparkContext

  val cassandraRDD = spark.read
    .format("org.apache.spark.sql.cassandra")
    .options(Map("table" -> tableName, "keyspace" -> keyspaceName))
    .load()


  import org.apache.spark.sql.functions._

  val uuidUDF = udf(CouchbaseHelper.getUUID _)
  val rddToBeWritten = if (cassandraRDD.columns.contains(idField)) {
    cassandraRDD.withColumn("META_ID", cassandraRDD(idField))
  } else {
    cassandraRDD.withColumn("META_ID", uuidUDF())
  }
  rddToBeWritten.write.couchbase()
}

object CouchbaseHelper {

  def getUUID: String = UUID.randomUUID().toString
}
