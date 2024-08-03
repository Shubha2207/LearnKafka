package org.example.demos.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Properties;
import java.util.stream.IntStream;

public class KafkaProducerClazz {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerClazz.class);
    public static final String DEMO_TOPIC = "demo_topic";

    public static void publish() {
        log.info("Producing Records");

        // Create Producer Properties
        String bootstrapServers = "127.0.0.1:9092";
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Read More: https://kafka.apache.org/documentation/#producerconfigs

        // Create Kafka Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // send data to topic - asynchronous
        ProducerRecord<String, String> producerRecord;

        /**
         * Publish records
         */
        IntStream.range(1, 6).parallel().forEach(index -> {
            ProducerRecord<String, String> record = new ProducerRecord<>(DEMO_TOPIC, "Message-" + index);
            producer.send(record);
        });

        /**
         * Producer callbacks and sticky partitions
         * callback function invoked by producer is onCompletion()
         *
         * Here in the output you can see msgs are sent to same partition this because
         * kafka (>v2.4.0) is trying to improve the  performance by sending to same partition
         *
         * To actually see the round-robin algo working, add pause of 1000ms
         */
        IntStream.range(1, 20).parallel().forEach(index -> {
            ProducerRecord<String, String> record = new ProducerRecord<>(DEMO_TOPIC, "Message-" + index);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if(Objects.isNull(exception)){
                        log.info("Published Successfully: \n"+
                                "Topic: "+metadata.topic()+"\n"+
                                "Partition: "+metadata.partition()+"\n"+
                                "Offset: "+metadata.offset()+"\n\n");
                    }
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        /**
         * Producer with Key
         *
         * Key is required is ordering of msg is important
         * because msg with same key end up in same partition
         */
        IntStream.range(1, 20).parallel().forEach(index -> {
            int key = index % 2;
            ProducerRecord<String, String> record = new ProducerRecord<>(DEMO_TOPIC, String.valueOf(key), "Message-" + index);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if(Objects.isNull(exception)){
                        log.info("Published Successfully: \n"+
                                "Record: "+record.key()+"<>"+record.value()+"\n"+
                                "Topic: "+metadata.topic()+"\n"+
                                "Partition: "+metadata.partition()+"\n"+
                                "Offset: "+metadata.offset()+"\n\n");
                    }
                }
            });
        });


        /**
         * The flush() will force all the data that was in .send() to be produced
         * and close() stops the producer.
         */
        producer.flush();
        producer.close();

        log.info("Published Records");
    }
}
