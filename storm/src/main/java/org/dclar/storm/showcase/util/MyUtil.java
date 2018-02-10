package org.dclar.storm.showcase.util;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.storm.shade.org.apache.zookeeper.*;
import org.dclar.storm.showcase.util.entity.ProcessEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 向NC中输出log信息,使用nc命令输出
 *
 * @author dclar
 */
public class MyUtil {


    public static void main(String[] args) throws IOException {


        for (int i = 0; i < 10; i++) {
            log(new MyUtil(), "test" + i);
        }


    }


    public static ProcessEntity getEntity(Object object, String msg) {

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


            return new ProcessEntity(time, host.toString(), pid, tinfo, oinfo, msg);

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

        return null;

    }


    //
    public static void log(Object object, String msg) {

        HttpClientContext context = HttpClientContext.create();
        ProcessEntity processEntity = getEntity(object, msg);

        BasicCookieStore cookieStore = new BasicCookieStore();

        HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

        //final HttpGet request = new HttpGet("http://www.github.com");

        String time = processEntity.getTime();
        String host = processEntity.getHost();
        String pid = processEntity.getPid();
        String tinfo = processEntity.getTinfo();
        String oinfo = processEntity.getOinfo();


        StringBuffer stringBuffer = new StringBuffer();

        try {
            stringBuffer.append("time=");
            stringBuffer.append(URLEncoder.encode(time, "utf-8")).append("&");
            stringBuffer.append("host=");
            stringBuffer.append(URLEncoder.encode(host, "utf-8")).append("&");
            stringBuffer.append("pid=");
            stringBuffer.append(URLEncoder.encode(pid, "utf-8")).append("&");
            stringBuffer.append("tinfo=");
            stringBuffer.append(URLEncoder.encode(tinfo, "utf-8")).append("&");
            stringBuffer.append("oinfo=");
            stringBuffer.append(URLEncoder.encode(oinfo, "utf-8")).append("&");
            stringBuffer.append("msg=");
            stringBuffer.append(URLEncoder.encode(msg, "utf-8"));

            HttpPost post = new HttpPost("http://192.168.1.100:8080/log/write?" + stringBuffer.toString());


            client.execute(post, context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化zk的idx的值为0
     *
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
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
