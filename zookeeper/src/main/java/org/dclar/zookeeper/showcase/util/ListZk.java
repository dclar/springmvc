package org.dclar.zookeeper.showcase.util;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class ListZk {

    public static void main(String[] args) throws Exception {
        listAllZnode();
    }

    public static void listAllZnode() throws Exception {
        String conn = "centos05:2181,centos06:2181,centos07:2181";
        ZooKeeper zooKeeper = new ZooKeeper(conn, 50000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
            }
        });

        printZnode("/");

    }

    private static void printZnode(String path) throws Exception {


        System.out.println(path);
        String conn = "centos05:2181,centos06:2181,centos07:2181";
        ZooKeeper zooKeeper = new ZooKeeper(conn, 50000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
            }
        });
        List<String> list = zooKeeper.getChildren(path, false);
        if (path.equals("/")) {
            path = "";
        }
        for (String p : list) {

            printZnode(path + "/" + p);
        }
    }

}

