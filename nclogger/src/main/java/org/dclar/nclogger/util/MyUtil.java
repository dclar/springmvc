package org.dclar.nclogger.util;

import org.dclar.nclogger.entity.ProcessEntity;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 向NC中输出log信息,使用nc命令输出
 *
 * @author dclar
 */
public class MyUtil {

    private static OutputStream os;


    static Runtime rr = Runtime.getRuntime();

    static {

        try {
            Process p = rr.exec("nc 192.168.1.100 80");
            os = p.getOutputStream();
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {



    }

    //
    public static void log(ProcessEntity processEntity) {

        try {


            String prefix = "[" + processEntity.getTime() + " "
                    + processEntity.getHost()
                    + " " + "进程:"
                    + processEntity.getPid() + " "
                    + processEntity.getTinfo() + " "
                    + processEntity.getOinfo() + "]" + " "
                    + processEntity.getMsg() + addSpace(23 - processEntity.getMsg().length()) + "\n";

            os.write(prefix.getBytes());
            os.flush();

            //os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String addSpace(int n) {
        String str = "";
        for (int i = 0; i < n; i++) {
            str = str + " ";
        }
        return str;
    }


}
