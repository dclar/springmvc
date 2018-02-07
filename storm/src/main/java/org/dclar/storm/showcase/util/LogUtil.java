package org.dclar.storm.showcase.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 向NC中输出log信息,使用nc命令输出
 *
 * @author dclar
 */
public class LogUtil {

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


    public static String addSpace(int n) {
        String str = "";
        for (int i = 0; i < n; i++) {
            str = str + " ";
        }
        return str;
    }
}
