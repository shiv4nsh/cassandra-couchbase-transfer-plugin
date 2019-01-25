FROM mozilla/sbt:latest AS builder

WORKDIR /app
COPY . .

RUN sbt assembly


FROM openjdk:8-alpine

WORKDIR /app

COPY --from=builder /app/target/scala-2.11/cassandra-couchbase-transfer-plugin.jar .

ENV SPARK_URL="local[*]"

CMD java -jar cassandra-couchbase-transfer-plugin.jar
