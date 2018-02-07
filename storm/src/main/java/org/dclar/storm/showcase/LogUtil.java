package org.dclar.storm.showcase;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 向NC中输出log信息,使用nc命令输出
 *
 * @author dclar
 */
public class LogUtil {

    private static OutputStream os;

    static {
        Runtime r = Runtime.getRuntime();
        Process p = null;
        try {
            p = r.exec("nc 192.168.1.158 80");
            os = p.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {

        log();

    }

    public static void log() throws IOException {

        for (int i = 0; i < 100; i++) {
            os.write(("hello world " + i + " \n").getBytes());
        }
        os.flush();

        os.close();

    }
}
