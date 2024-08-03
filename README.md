# Kafka Commands (Linux/Windows WSL)

#### Prerequisite
+ Kafka installed
+ Set Path in `~/.bashrc`
```cmd
nano ~/.bashrc

PATH="$PATH:/home/shupaw/kafka_2.13-3.1.0/bin"

source ~/.bashrc
```

## Get the configurations / properties
```cmd
cd kafka_x.x-x.x.x/config
```


## Start the zookeeper
```cmd
zookeeper-server-start.sh kafka_2.13-3.1.0/config/zookeeper.properties
```

## Start the kafka server
```cmd
kafka-server-start.sh kafka_2.13-3.1.0/config/server.properties
```

## kafka-topics.sh: Kafka topic management

### create a topic
```cmd
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic first-topic

kafka-topics.sh --bootstrap-server localhost:9092 --create --topic second-topic --partitions 3

kafka-topics.sh --bootstrap-server localhost:9092 --create --topic third-topic --partitions 3 --replication-factor 2
```


### list the topics
```cmd
kafka-topics.sh --bootstrap-server localhost:9092 --list
```


### describe the topic
```cmd
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic second-topic
```

## delete the topic
```cmd
kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic first-topic
```
