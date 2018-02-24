package org.dclar.storm.showcase.kafka.old;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.dclar.storm.showcase.kafka.fromkafka.PrinterBolt;
import org.dclar.storm.showcase.kafka.fromkafka.SentenceBolt;

public class KafkaTopology {

    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        // zookeeper hosts for the Kafka cluster
        ZkHosts zkHosts = new ZkHosts("centos05:2181");
        // Create the KafkaSpout configuration
        // Second argument is the topic name
        // Third argument is the ZooKeeper root for Kafka
        // Fourth argument is consumer group id
        SpoutConfig kafkaConfig = new SpoutConfig(zkHosts, "test3", "", "id7");
        // Specify that the kafka messages are String
        kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        // We want to consume all the first messages in
        // the topic every time we run the topology to
        // help in debugging. In production, this
        // property should be false
//        kafkaConfig.forceFromStart = true;
        // Now we create the topology
        TopologyBuilder builder = new TopologyBuilder();
        // set the kafka spout class
        builder.setSpout("KafkaSpout", new KafkaSpout(kafkaConfig), 1);
        // configure the bolts
        builder.setBolt("SentenceBolt", new SentenceBolt(), 1).globalGrouping("KafkaSpout");
        builder.setBolt("PrinterBolt", new PrinterBolt(), 1).globalGrouping("SentenceBolt");
        // create an instance of LocalCluster class
        // for executing topology in local mode.
        LocalCluster cluster = new LocalCluster();

        Config conf = new Config();
        // Submit topology for execution
        cluster.submitTopology("KafkaToplogy", conf, builder.createTopology());
        try {
            // Wait for some time before exiting
            System.out.println("Waiting to consume from kafka");
            Thread.sleep(60000);
        } catch (Exception exception) {
            System.out.println("Thread interrupted exception : " + exception);
        }
        // kill the KafkaTopology
        cluster.killTopology("KafkaToplogy");
        // shut down the storm test cluster
        cluster.shutdown();

    }
}
