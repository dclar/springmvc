package org.dclar.flume.showcase.util;

import org.junit.Test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class TestSendJson {


    @Test
    public void sendJson() throws Exception {

        URL url = new URL("http://centos01:8888");


        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        String json = "{\"name\":\"tom\"}";
        os.write(json.getBytes());
        os.close();

    }
}
