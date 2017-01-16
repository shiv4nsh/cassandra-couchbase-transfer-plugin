# cassandra-couchbase-transfer-plugin

This tool allows you to transfer data from Cassandra to Couchbase , just by doing some small configurations :) 
##Configurations:

All the configurations can be done by setting the **environment variables**
###Couchbase Configuration:


|   Configuration Name  |   Default Value   |   Description |
| :---------------------: | :-----------------: | :--------------: |
|   COUCHBASE_URL       |   "localhost"     | The URL for the Couchbase.|
|   COUCHBASE_BUCKETNAME|   "foobar"        | The bucket name to which data needs to be transferred.|

### Cassandra Configuration:

| Configuration Name | Default Value | Description |
| :-----------------: | :------------: | :----------: |
| CASSANDRA_URL | "localhost" | The URL for the Cassandra. |
| CASSANDRA_PORT | 9042 | The port for the Cassandra. |
| CASSANDRA_KEYSPACENAME | "foobar" | The keyspace name for the Cassandra |
| CASSANDRA_TABLENAME | "testcouchbase" | The table name that needs to be transferred. |
| CASSANDRA_ID_FEILD_NAME | "id" | The field name that should be used as Couchbase Document Id, if the field name does not matches any column it gives a random id to the document. |


##Code in Action:

###Cassandra Side:
So this is how data looks on Cassandra Side:
![cassandra1](https://cloud.githubusercontent.com/assets/12807854/21962011/40c941ca-db3f-11e6-845f-aa3390054981.png)

###Couchbase Side:

**Case 1:** When id exists and same can be used as Couchbase Document Id.
![couchbase_with_id](https://cloud.githubusercontent.com/assets/12807854/21962012/40ed50ec-db3f-11e6-91e0-482c24346fec.png)

**Case 2:** When id name does not exist and we need to assign Random id to Documents.

![couchbase_idchanged](https://cloud.githubusercontent.com/assets/12807854/21962013/40ef49ba-db3f-11e6-82b0-9c6fbbc50257.png)

##How to Run the Cassandra-Couchbase Transfer plugin:

Steps to run the code are :

1. Download the code from the repository.
2. Configure the environment Variables according to the configuration.

> export  COUCHBASE_URL="localhost"

> export COUCHBASE_BUCKETNAME="foobar"

> export CASSANDRA_URL="localhost"

> export CASSANDRA_PORT=9042

> export CASSANDRA_KEYSPACENAME="foobar"

> export CASSANDRA_TABLENAME="testcouchbase"

> export CASSANDRA_ID_FEILD_NAME="id"

> export SPARK_IP="local[*]"

3. Run the project using ```sbt run```

