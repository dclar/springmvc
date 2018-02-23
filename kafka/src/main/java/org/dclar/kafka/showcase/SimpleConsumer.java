package org.dclar.kafka.showcase;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * 程序的解读参看：
 * https://www.confluent.io/blog/tutorial-getting-started-with-the-new-apache-kafka-0-9-consumer-client/
 *
 * @author dclar
 */
public class SimpleConsumer {

    public static void go() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "centos01:9092,centos02:9092,centos03:9092");
        props.put("group.id", "test");

        // 代表会自动进行offset的commit的操作 true也是默认设置
        props.put("enable.auto.commit", "true");
        // 上面的offset的commit的操作被设置为这里的1000毫秒提交一次
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // 这里设置的是timeout的时间,即程序中多久没有调用poll则触发timeout机制,对整体消费进行relancing处理
        // 默认是10000ms
        // 注意这里的时间一定要长于在下面
        // ###################################
        // 主处理部分
        // ###################################
        // 运行的时间,否则会触发rebalancing的处理
        props.put("session.timeout.ms", "60000");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test3"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            System.out.println("++++++++++" + records.count() + "+++++++++++++");
            for (ConsumerRecord<String, String> record : records) {
                // ###################################
                // 主处理部分
                // ###################################
                //System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            System.out.println("++++++++++" + records.count() + "is over");

        }

    }


    public static void main(String[] args) {
        go();

    }
}
