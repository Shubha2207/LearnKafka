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

## kafka-topics.sh: Kafka Topic Management

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

## kafka-console-producer.sh: Kafka Console Producer

### produce to topic through interactive session
```cmd
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first-topic
```


### provide additional properties
[Kafka Producer Properties](https://kafka.apache.org/documentation/#producerconfigs)
```cmd
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first-topic 
--producer-property acks=all
```


### produce to topic with key
```cmd
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first-topic
--property parse.key=true --property key.separator=:
```

## kafka-console-consumer.sh: Kafka Console Consumer

### consume latest message from topic
It starts a console-consumer which is part of temporary consumer-group
```cmd
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first-topic
```

### consume message from beginning
```cmd
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first-topic --from-beginning
```


## kafka-consumer-group.sh: Kafka Consumer Group

### consume message from topic with consumer-group
It starts a console-consumer which is part of consumer-group consume-grp-1.
To create more consumers, run the same command in another terminal. and you will see messages are being sent to both consumers in
round-robin fashion.
```cmd
kafka-consumer-group.sh --bootstrap-server localhost:9092 --topic first-topic --group consume-grp-1
```

### list all consumer groups
```cmd
kafka-consumer-group.sh --bootstrap-server localhost:9092 --list
```

### describe a consumer groups
Returns no. of active members, topic, partition each consumer listen to , current-offset, log-end-offset, lag and 
consumer-id
```cmd
kafka-consumer-group.sh --bootstrap-server localhost:9092 --describe --group consume-grp-1
```

### reset offset for a consumer groups
Stop all consumers before resetting the offset
```cmd
kafka-consumer-group.sh --bootstrap-server localhost:9092 --rest-offsets --to-earliest --execute --all-topic
```

### shift offset for a consumer groups
you can shift forward and backward
```cmd
kafka-consumer-group.sh --bootstrap-server localhost:9092 --rest-offsets --shift-by -3 --execute --topic first_topic
```