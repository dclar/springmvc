package org.dclar.zookeeper.showcase.util;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Write2zk {

    public static void main(String[] args) throws Exception {
        go("/flume/foo", "/Users/yl/opt/flume-avro.conf");
    }


    public static void go(String path, String writeFilePath) throws Exception {

        String conn = "centos01:2181,centos02:2181,centos03:2181";
        ZooKeeper zooKeeper = new ZooKeeper(conn, 50000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("over: " + event);
            }
        });
        Stat stat = new Stat();

        try {
            zooKeeper.getData(path, false, stat);
        } catch (KeeperException.NoNodeException e) {

            if (path.startsWith("/")) {

                String[] arrPath = path.split("/");

                StringBuffer addedPath = new StringBuffer();
                for (String s : arrPath) {
                    if (s != null && s.trim().length() != 0) {
                        addedPath.append("/").append(s);
                        zooKeeper.create(addedPath.toString(), new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

                    }
                }
            } else {
                throw new Exception("path格式有误");
            }
        }
        //zooKeeper.getData("/flume/foo", false, stat);

        int v = stat.getVersion();
        FileInputStream fis = new FileInputStream(writeFilePath);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        fis.close();
        zooKeeper.setData(path, data, v);
    }

    public static void createNode() {

    }

}
