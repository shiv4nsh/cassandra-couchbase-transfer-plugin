name := "cassandra-couchbase-transfer-plugin"

version := "1.0"

scalaVersion := "2.11.8"

organization := "com.foobar"

libraryDependencies ++= {
  val sparkV = "2.2.3"
  Seq(
    "log4j" % "log4j" % "1.2.17",
    "org.slf4j" % "slf4j-api" % "1.7.21",
    "org.apache.spark" %% "spark-core" % sparkV ,
    "org.apache.spark" %% "spark-sql" % sparkV ,
    "org.apache.spark" %% "spark-streaming" % sparkV ,
    "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.0-M3",
    "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    "com.couchbase.client" %% "spark-connector" % "2.2.0",
    "com.typesafe" % "config" % "1.3.1"
  )
}

//--------------------------------
//---- sbt-assembly settings -----
//--------------------------------

mainClass in assembly := Some("com.foobar.CouchbasePipeline")

assemblyJarName := "cassandra-couchbase-transfer-plugin.jar"

assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}

assemblyOption in assembly ~= { _.copy(cacheOutput = false) }

// publish to artifacts directory
publishArtifact in(Compile, packageDoc) := false

publishTo := Some(Resolver.file("file", new File("artifacts")))

cleanFiles <+= baseDirectory { base => base / "artifacts" }
