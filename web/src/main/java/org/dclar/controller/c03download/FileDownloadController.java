package org.dclar.controller.c03download;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.dclar.controller.c03download.entity.NoteDetal;
import org.dclar.controller.c03download.vo.DownloadVo;
import org.dclar.controller.c03download.vo.TestVo;
import org.dclar.controller.exception.ex.InvalidCookieException;
import org.dclar.h2e.processor.Processor;
import org.dclar.h2e.tag.util.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/download")
public class FileDownloadController {

    Logger log = LoggerFactory.getLogger(FileDownloadController.class);

    @Autowired
    Processor processor;

    @RequestMapping(value = "/excel/init", method = RequestMethod.GET)
    @ResponseBody
    public DownloadVo ajaxDownloadExcel(DownloadVo downloadVo, HttpServletRequest request) throws Exception {

        log.debug("========> 开始 ===> 获取 Login cookie");

        String cookie = getLoginCookie();
        log.debug("认证后的cookie是：{}", cookie);

        downloadVo = new DownloadVo();

        downloadVo.setUrl("http://10.0.47.66/semantic/SemanticCube-parent/issues/16");
        downloadVo.setCookieValue(cookie);

        return downloadVo;
    }


    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    @ResponseBody
    public DownloadVo ajaxDownloadExcel(DownloadVo downloadVo, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/downloads/excel/");


        log.debug("========> 开始 ===> 获取 Login cookie");

        String cookie = downloadVo.getCookieValue();
        log.debug("认证后的cookie是：{}", cookie);
        log.debug("<======== 完成 <=== 获取 Login cookie");


        log.debug("========> 开始 ===> 获取issue的内容信息");
        String content = getUrlContent(downloadVo.getUrl(), cookie);
        log.debug("<======== 完成 <=== 获取issue的内容信息");

        Map<String, NoteDetal> map = getDetail(downloadVo.getUrl(), cookie);


        //throw new InvalidCookieException();


        Iterator<Map.Entry<String, NoteDetal>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            NoteDetal v =  (NoteDetal)entry.getValue();
            content = content.replace("." + v.getTaskId().concat("T"), v.getUsername().concat(" ").concat(v.getCompleteTime()));
        }

        Path file = Paths.get(dataDirectory, "write.xls");

//        if (Files.exists(file)) {
//            Files.delete(Paths.get(dataDirectory, "write.xls"));
//        }


        try {
            processor.process(content, dataDirectory.concat("write.xls"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (Files.exists(file)) {

        }

        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();  // includes leading forward slash

        String resultPath = scheme + "://" + serverName + ":" + serverPort + contextPath;

        downloadVo.setFilePath_(resultPath.concat("/download/excel/write.xls"));

        log.debug("下载链接地址是: {}", downloadVo.getFilePath_());


        return downloadVo;

    }


    @RequestMapping("/excel/{fileName:.+}")
    public void downloadPDFResource(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @PathVariable("fileName") String fileName, String cookie, String url) throws Exception {

        //Authorized user will download the file
        String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/downloads/excel/");

        Path file = Paths.get(dataDirectory, fileName);

        if (Files.exists(file)) {

            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    private String getLoginCookie() throws Exception {
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
        log.debug(total);

        CookieStore cookieStore2 = context.getCookieStore();
        List<Cookie> cookies2 = cookieStore1.getCookies();


        String cookie = null;

        for (Cookie cookie2 : cookies2) {
            if (cookie2.getName().equals("_gitlab_session")) {
                cookie = cookie2.getName().concat("=").concat(cookie2.getValue());
                break;
            }
        }

        return cookie;
    }


    /**
     * 通过cookie获取url信息
     *
     * @param s
     * @param cookie
     * @return
     * @throws Exception
     */
    private String getUrlContent(String s, String cookie) throws Exception {
        //String s = "http://10.0.47.66/semantic/SemanticCube-parent/issues/16";

        URL url = new URL(s);

        HttpURLConnection resumeConnection = (HttpURLConnection) url
                .openConnection();

        resumeConnection.setRequestProperty("Cookie", cookie);

        resumeConnection.connect();

        InputStream urlStream = resumeConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlStream));
        String ss = null;
        StringBuffer total = new StringBuffer();
        while ((ss = bufferedReader.readLine()) != null) {
            total.append(ss);
        }

        String out = null;
        out = TagUtil.cutDownTagStartUntilMatchFirstEnd(total.toString(), "<textarea", "</textarea>", 1);
        out = out.replace("&lt;", "<");
        out = out.replace("&gt;", ">");
        out = out.replace("&quot;", "\"");

        return out;

    }


    /**
     * 获得完成状态json
     *
     * @param s
     * @param cookie
     * @return
     * @throws Exception
     */
    private Map<String, NoteDetal> getDetail(String s, String cookie) throws Exception {

        s = s.concat("/").concat("discussions.json");

        URL url = new URL(s);

        HttpURLConnection resumeConnection = (HttpURLConnection) url
                .openConnection();

        resumeConnection.setRequestProperty("Cookie", cookie);

        resumeConnection.connect();

        InputStream urlStream = resumeConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlStream));
        String ss = null;
        String total = "";
        while ((ss = bufferedReader.readLine()) != null) {
            total += ss;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode nodes = objectMapper.readTree(total);

        Iterator iterator = nodes.elements();

        Map<String, NoteDetal> resultMap = new HashMap<>();

        while (iterator.hasNext()) {

            ObjectNode objectNode = (ObjectNode) iterator.next();

            String node = objectNode.get("notes").get(0).get("note").asText().toString();


            if (node.startsWith("marked the task") && node.endsWith("as completed")) {
                String id = node.replace("marked the task **", "");
                id = id.replace("** as completed", "");

                NoteDetal noteDetal = new NoteDetal();
                noteDetal.setTaskId(id);

                String date = objectNode.get("notes").get(0).get("updated_at").asText();
                Date date1 = null;

//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                date1 = dateFormat.parse(date);

                noteDetal.setCompleteTime(objectNode.get("notes").get(0).get("updated_at").asText());
                noteDetal.setUsername(objectNode.get("notes").get(0).get("author").get("name").asText());


                resultMap.put(id, noteDetal);
                log.debug(id);
                log.debug(objectNode.get("notes").get(0).get("author").get("name").asText());
                log.debug(objectNode.get("notes").get(0).get("updated_at").asText());

            }

            if (node.startsWith("marked the task") && node.endsWith("as incomplete")) {
                String id = node.replace("marked the task **", "");
                id = id.replace("** as incomplete", "");

                resultMap.remove(id);
            }


        }

        return resultMap;

    }


//    @RequestMapping(value = "/excel/check", method = RequestMethod.POST)
//    public ResponseEntity check(TestVo testVo) {
//
//        testVo.setStatus("No");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<>(testVo, headers, HttpStatus.OK);
//    }

//    @RequestMapping(value = "/excel/check", method = RequestMethod.POST)
//    @ResponseBody
//    public String check(TestVo testVo) {
//
//        testVo.setStatus("No");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return JSON.toJSONString(testVo);
//    }

    @RequestMapping(value = "/excel/check", method = RequestMethod.POST)
    @ResponseBody
    public TestVo check(@Valid TestVo testVo, BindingResult result) {

        if (result.hasErrors()) {
            testVo.setStatusValue("errors");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return testVo;
    }

}
