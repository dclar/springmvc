package org.dclar.storm.showcase.kafka.fromkafka;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

import java.util.HashMap;
import java.util.Map;

public class KafkaTopology {

    public static void main(String[] args) {

        final TopologyBuilder tp = new TopologyBuilder();
        String port = "9092";


        tp.setSpout("kafka_spout", new KafkaSpout<>(KafkaSpoutConfig.builder("centos01:" + port, "test3").build()), 1);

        tp.setBolt("SentenceBolt", new SentenceBolt(), 1).globalGrouping("kafka_spout");
        tp.setBolt("PrinterBolt", new PrinterBolt(), 1).globalGrouping("SentenceBolt");

        LocalCluster cluster = new LocalCluster();

        Config conf = new Config();
        // Submit topology for execution
        cluster.submitTopology("KafkaToplogy", conf, tp.createTopology());
        try {
            // Wait for some time before exiting
            System.out.println("Waiting to consume from kafka");
            //Thread.sleep(60000);
        } catch (Exception exception) {
            System.out.println("Thread interrupted exception : " + exception);
        }
        // kill the KafkaTopology
        //cluster.killTopology("KafkaToplogy");
        // shut down the storm test cluster
        //cluster.shutdown();

    }
}
