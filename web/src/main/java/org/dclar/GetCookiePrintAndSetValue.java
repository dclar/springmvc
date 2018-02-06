package org.dclar;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.dclar.h2e.tag.util.TagUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class GetCookiePrintAndSetValue {

    public static void main(String args[]) throws Exception {


//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpClientContext context = HttpClientContext.create();
//
////        CookieStore cookieStore = new BasicCookieStore();
////
////        BasicClientCookie cookie = new BasicClientCookie("_gitlab_session",
////                "22e978e61775bcf4da566d74bc03a989");
////        cookieStore.addCookie(cookie);
////        context.setCookieStore(cookieStore);
//        CloseableHttpResponse response =
//
//                httpclient.execute(new HttpPost("http://10.0.47.66/users/sign_in?utf8=%E2%9C%93&authenticity_token=PZw%2BYTbIzi0WNhRXQeUoxJ4MGRRs1x9MpJZyHeI6AN%2F60Bf6RxMWFg6Vu55jYGrMqArqO%2B4OrC3UHopPba%2FL8w%3D%3D&user%5Blogin%5D=darcula&user%5Bpassword%5D=13579246810&user%5Bremember_me%5D=0"), context);
//

        HttpClient client1 = HttpClientBuilder.create().build();
        HttpClientContext context = HttpClientContext.create();
        HttpResponse response1 = client1.execute(new HttpGet("http://10.0.47.66"), context);

        CookieStore cookieStore1 = context.getCookieStore();

        List<Cookie> cookies = cookieStore1.getCookies();


        String cookieValue = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("_gitlab_session")) {
                cookieValue = c.getValue();
            }
        }

        InputStream inputStream1 = response1.getEntity().getContent();
        BufferedReader bufferedReader1 = new BufferedReader(
                new InputStreamReader(inputStream1));
        String ss1 = null;
        String total1 = "";
        while ((ss1 = bufferedReader1.readLine()) != null) {
            total1 += ss1;
        }

        String token = TagUtil.cutDownTagStartUntilMatchFirstEnd(total1, "<meta name=\"csrf-token\" content=\"", "\"", 1);

        token = URLEncoder.encode(token, "UTF-8");


        BasicCookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie1 = new BasicClientCookie("_gitlab_session", cookieValue);
        //BasicClientCookie cookie2 = new BasicClientCookie("sidebar_collapsed", "false");
        cookie1.setDomain("10.0.47.66");
        //cookie2.setDomain("10.0.47.66");
        cookieStore.addCookie(cookie1);
        //cookieStore.addCookie(cookie2);
        HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

        //final HttpGet request = new HttpGet("http://www.github.com");

        HttpPost post = new HttpPost("http://10.0.47.66/users/sign_in?utf8=%E2%9C%93&authenticity_token=" + token + "&user%5Blogin%5D=darcula&user%5Bpassword%5D=13579246810&user%5Bremember_me%5D=0");

        HttpResponse response = client.execute(post, context);

        InputStream inputStream = response.getEntity().getContent();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String ss = null;
        String total = "";
        while ((ss = bufferedReader.readLine()) != null) {
            total += ss.concat("\r\n");
        }
        System.out.println(total);

        CookieStore cookieStore2 = context.getCookieStore();
        List<Cookie> cookies2 = cookieStore1.getCookies();


        String cookie = null;

        for (Cookie cookie2 : cookies2) {
            if (cookie2.getName().equals("_gitlab_session")) {
                cookie = cookie2.getName().concat("=").concat(cookie2.getValue());
            }
        }


        URL url = new URL("http://10.0.47.66/semantic/SemanticCube-parent/issues/16");

        HttpURLConnection resumeConnection = (HttpURLConnection) url
                .openConnection();

        resumeConnection.setRequestProperty("Cookie", cookie);

        resumeConnection.connect();

        InputStream urlStream = resumeConnection.getInputStream();
        BufferedReader bufferedReader3 = new BufferedReader(
                new InputStreamReader(urlStream));
        String ss2 = null;
        String total2 = "";
        while ((ss2 = bufferedReader3.readLine()) != null) {
            total2 += ss2;
        }

        System.out.println(total2);

        total2 = TagUtil.cutDownTagStartUntilMatchFirstEnd(total2, "<textarea", "</textarea>", 0);
        total2 = total2.replace("&lt;", "<");
        total2 = total2.replace("&gt;", ">");
        total2 = total2.replace("&quot;", "\"");

        System.out.println(total2);


    }

}
