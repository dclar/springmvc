package org.dclar.zookeeper.showcase.util;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.FileInputStream;
import java.io.IOException;

public class Write2zk {

    public static void main(String[] args) throws Exception {
        go("/flume/foo", "/opt/hadoop-2.9.0/app/apache-flume-1.8.0-bin/conf/flume-avro.conf");
    }


    public static void go(String path, String writeFilePath) throws IOException, KeeperException, InterruptedException {

        String conn = "centos01:2181,centos02:2181,centos03:2181";
        ZooKeeper zooKeeper = new ZooKeeper(conn, 50000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("over: " + event);
            }
        });
        Stat stat = new Stat();
        zooKeeper.getData(path, false, stat);
        //zooKeeper.getData("/flume/foo", false, stat);

        int v = stat.getVersion();
        FileInputStream fis = new FileInputStream(writeFilePath);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        fis.close();
        zooKeeper.setData(path, data, v);
    }

}
