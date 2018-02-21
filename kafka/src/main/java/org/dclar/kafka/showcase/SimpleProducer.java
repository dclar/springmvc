package org.dclar.kafka.showcase;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class SimpleProducer {
    private static KafkaProducer<Integer, String> producer;
    private final Properties props = new Properties();

    public SimpleProducer() {
        // brokers
        props.put("bootstrap.servers", "centos01:9092");

        // 准备消息在producer与broker之间传输
        // 对于key 和 value都使用这个串行化
        //  This property specifies the serializer class that needs to be used while preparing the message for transmission
        // from the producer to the broker. In this example, we will be using the string encoder provided by Kafka.
        // By default, the serializer class for the key and message is the same,
        // but we can change the serializer class for the key by using the key.serializer.class property.
        // 代码参看
        // https://kafka.apache.org/0110/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html
        /*
         Properties props = new Properties();
         props.put("bootstrap.servers", "localhost:9092");
         props.put("acks", "all");
         props.put("retries", 0);
         props.put("batch.size", 16384);
         props.put("linger.ms", 1);
         props.put("buffer.memory", 33554432);
         props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
         props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

         Producer<String, String> producer = new KafkaProducer<>(props);
         for (int i = 0; i < 100; i++)
             producer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i), Integer.toString(i)));

         producer.close();
         */
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // This property instructs the Kafka broker to send an acknowledgment to the producer when a message is received.
        // By default, the producer works in the "fire and forget" mode and is not informed in case of message loss.
        props.put("request.required.acks", "1");
        producer = new KafkaProducer(props);
    }

    public static void main(String[] args) {
        SimpleProducer sp = new SimpleProducer();
//        String topic = (String) args[0];
//        String messageStr = (String) args[1];

        // Deprecated
        //KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, messageStr);
        // ProducerRecord<Integer, String> data = new ProducerRecord<>(topic, messageStr);
        ProducerRecord<Integer, String> data = new ProducerRecord<>("test3", "hello world from mac idea");

        producer.send(data);
        producer.close();
    }
}
