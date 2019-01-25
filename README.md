# cassandra-couchbase-transfer-plugin

This tool allows you to transfer data from Cassandra to Couchbase , just by doing some small configurations :)
##Configurations:

All the configurations can be done by setting the **environment variables**

###Couchbase Configuration:


|   Configuration Name  |   Default Value   |   Description |
| :---------------------: | :-----------------: | :--------------: |
|   COUCHBASE_URL       |   "localhost"     | The hostname for the Couchbase.|
|   COUCHBASE_BUCKETNAME|   "foobar"        | The bucket name to which data needs to be transferred.|
|   COUCHBASE_USERNAME  |   ""              | The username to use to authenticate with Couchbase. |
|   COUCHBASE_PASSWORD  |   ""              | The password to use to authenticate with Couchbase. |

### Cassandra Configuration:

| Configuration Name | Default Value | Description |
| :-----------------: | :------------: | :----------: |
| CASSANDRA_URL | "localhost" | The hostname for the Cassandra. |
| CASSANDRA_PORT | 9042 | The port for the Cassandra. |
| CASSANDRA_KEYSPACENAME | "foobar" | The keyspace name for the Cassandra |
| CASSANDRA_TABLENAME | "testcouchbase" | The table name that needs to be transferred. |
| CASSANDRA_ID_FIELD_NAME | "id" | The field name that should be used as Couchbase Document Id, if the field name does not matches any column it gives a random id to the document. |
| CASSANDRA_USERNAME | "" | User account to use to authenticate with Cassandra. |
| CASSANDRA_PASSWORD | "" | Password to use to authenticate with Cassandra. |


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
2. Build the Docker container

> docker build -t shiv4nsh/cassandra-couchbase-transfer-plugin:latest .

3. Run the Docker container

> docker run -d --rm \
    -e COUCHBASE_URL=couchbase \
    -e COUCHBASE_BUCKETNAME=foobar \
    -e COUCHBASE_USERNAME=Administrator \
    -e COUCHBASE_PASSWORD=passsword \
    -e CASSANDRA_URL=cassandra \
    -e CASSANDRA_KEYSPACENAME=foobar \
    -e CASSANDRA_TABLENAME=testcouchbase \
    -e CASSANDRA_ID_FIELD_NAME=id \
    -e CASSANDRA_USERNAME=cassandra \
    -e CASSANDRA_PASSWORD=cassandra \
    shiv4nsh/cassandra-couchbase-transfer-plugin:latest

##TODO: Future Improvements
* Add keyspace prefix to Couchbase document id. By default, the keyspace prefix should be "${CASSANDRA_KEYSPACENAME}::${CASSANDRA_TABLENAME}::${id}"
* Add 'type' field to Couchbase document. By default, the field name should be 'type' and the value should be "${CASSANDRA_KEYSPACENAME}::${CASSANDRA_TABLENAME}"
* Allow customization of 'type' field name
* Allow customization of keyspace prefix
* Allow customization of type field value
