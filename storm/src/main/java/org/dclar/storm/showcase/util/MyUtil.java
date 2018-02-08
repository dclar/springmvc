package org.dclar.storm.showcase.util;

import org.apache.storm.shade.org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 向NC中输出log信息,使用nc命令输出
 *
 * @author dclar
 */
public class MyUtil {

    private static OutputStream os;


    static Runtime r = Runtime.getRuntime();

    static {

        try {
            Process p = r.exec("nc 192.168.1.100 80");
            os = p.getOutputStream();
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {

        //log(null,null);

    }

    //
    public static void log(Object object, String msg) {

        // 系统时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String time = simpleDateFormat.format(date);

        // hostname
        InetAddress host = null;
        try {
            host = InetAddress.getLocalHost();

            // pid
            RuntimeMXBean r = ManagementFactory.getRuntimeMXBean();
            String pid = r.getName().split("@")[0];

            // thread
            String tname = Thread.currentThread().getName();

            long tid = Thread.currentThread().getId();
            String tinfo = "TID:" + addSpace(4 - String.valueOf(tid).length()) + tid;

            String oclass = object.getClass().getSimpleName();

            oclass = oclass + addSpace(25 - oclass.length());

            int hash = object.hashCode();

            String oinfo = oclass + "@" + hash + addSpace(12 - String.valueOf(hash).length());

            String prefix = "[" + time + " " + host + " " + pid + " " + tinfo + " " + oinfo + "]" + " " + msg + addSpace(23 - msg.length()) + "\n";

            os.write(prefix.getBytes());
            os.flush();

            //os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//
//        for (int i = 0; i < 100; i++) {
//            os.write(("hello world " + i + " \n").getBytes());
//        }


    }

    /**
     * 初始化zk的idx的值为0
     *
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void initZKindex() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("centos01:2181", 1000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
            }
        });

        zooKeeper.create("/index", MyUtil.int2Bytes(0), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    public static String addSpace(int n) {
        String str = "";
        for (int i = 0; i < n; i++) {
            str = str + " ";
        }
        return str;
    }


    public static byte[] int2Bytes(int i) {
        byte[] arr = new byte[4];
        arr[0] = (byte) (i >>> 24);
        arr[1] = (byte) (i >>> 16);
        arr[2] = (byte) (i >>> 8);
        arr[3] = (byte) (i >>> 0);
        return arr;
    }

    public static int bytes2int(byte[] bytes) {
        return bytes[0] << 24 | bytes[1] << 16 | bytes[8] | bytes[3];
    }
}
