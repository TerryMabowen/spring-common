package com.mbw.common.core.crawler.test1;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-03 09:40
 */
public class Test1 {
    private Date expiresTime = null;
    private String cookie = "";
    private String wzwschallenge = "";
    private Integer num = 1;

    @Test
    public void f1() {
        f2();
    }

    public void f2() {
        try {
            String requestUrl = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html";
            f3(requestUrl);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void f3(String url) {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader("Cookie", cookie);
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            httpGet.setHeader("Cache-Control", "no-cache");
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            //判断响应状态为200，进行处理
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                Header[] cookieHeader = httpResponse.getHeaders("Set-Cookie");
                if (cookieHeader.length > 0) {
                    Header header = cookieHeader[0];
                    String value = header.getValue();
                    String[] split = value.split(";");
                    if (split.length == 4) {
                        cookie = split[0].trim();
                        System.out.println("cookie ------- " + cookie);
                        String expires = split[3].trim().split("=")[1].trim().replace(",", "");
                        System.out.println("expires ------- " + expires);
//                        expiresTime = DateUtil.parse(expires, "dd-MM-yyyy HH:mm:ss");
                    }
//                    System.out.println("Set-Cookie ------ " + value);
                }
//                System.out.println("cookieHeader: " + Arrays.toString(cookieHeader));
                Header[] wzwsRayHeader = httpResponse.getHeaders("WZWS-RAY");
                if (wzwsRayHeader.length > 0) {
                    Header header = wzwsRayHeader[0];
                    String value = header.getValue();
                    System.out.println("WZWS-RAY ------ " + value);
                }
//                System.out.println("wzwsRayHeader: " + Arrays.toString(wzwsRayHeader));
                String html = EntityUtils.toString(httpEntity, "UTF-8");
                Document document = Jsoup.parse(html);
                Element element = document.text("请开启JavaScript并刷新该页.");
                if (element == null) {
                    System.out.println("正常页面");
                } else {
                    System.out.println("cookie失效");
                    f4();
                }
            } else {
                System.out.println("响应结果异常：" + httpResponse.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void f4() {
        try {
            // http://www.pbc.gov.cn/WZWSREL3poZW5nY2VodW9iaXNpLzEyNTIwNy8xMjUyMTcvMTI1OTI1L2luZGV4Lmh0bWw=
            String url = "http://www.pbc.gov.cn/WZWSREL3poZW5nY2VodW9iaXNpLzEyNTIwNy8xMjUyMTcvMTI1OTI1L2luZGV4Lmh0bWw=?wzwschallenge=V1pXU19DT05GSVJNX1BSRUZJWF9MQUJFTDM0MzYxMzM=";
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Cookie", cookie);
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            httpGet.setHeader("Cache-Control", "no-cache");
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println(httpResponse.toString());
            //判断响应状态为200，进行处理
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                System.out.println(EntityUtils.toString(httpEntity, "UTF-8"));
            } else {
                System.out.println("响应结果异常：" + httpResponse.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
