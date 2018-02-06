package org.dclar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CookieTest {

    public static void main(String[] args) throws Exception{


        URL url = new URL("http://10.0.47.66/users/sign_in?utf8=%E2%9C%93&authenticity_token=w1%2BsRoJ4XGRDRM7NCwaUHSRAQt2gfaYQmtacVZt04jD9yMj09WqbjMUupWd%2BAbOwUpwsz3vL3DOdJ5EOWYK64A%3D%3D&user%5Blogin%5D=darcula&user%5Bpassword%5D=13579246810&user%5Bremember_me%5D=0");

        HttpURLConnection resumeConnection = (HttpURLConnection) url
                .openConnection();

        resumeConnection.setRequestProperty("Cookie", "sidebar_collapsed=false; _gitlab_session=22e978e61775bcf4da566d74bc03a989");

        resumeConnection.connect();

        InputStream urlStream = resumeConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlStream));
        String ss = null;
        String total = "";
        while ((ss = bufferedReader.readLine()) != null) {
            total += ss.concat("\r\n");
        }

        System.out.println(total);
    }
}
