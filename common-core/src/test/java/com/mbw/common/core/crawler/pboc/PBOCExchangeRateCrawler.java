package com.mbw.common.core.crawler.pboc;

import com.mbw.commons.util.date.DateUtil;
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
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;

/**
 * 中国人民银行汇率爬虫
 *
 * @author Mabowen
 * @date 2020-06-02 17:15
 */
public class PBOCExchangeRateCrawler {
    private String cookie = "";
    private String wzwschallenge = "V1pXU19DT05GSVJNX1BSRUZJWF9MQUJFTDM0MzYxMzM=";

    public static void main(String[] args) {

    }

    @Test
    public void f1() {
        f2();
    }

    public void f2() {
        try {
            String[] currencies = new String[] {"美元", "日元", "港币"};
            String todayDate = DateUtil.format(Calendar.getInstance().getTime(), "yyyy-MM-dd");
            f3(todayDate, currencies[0], 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void f3(String todayDate, String currencyName, int pageNo) {
        try {
            String requestUrl = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html";
            String cookie = "wzws_cid=ed47f087c058bf854f00ec8cb3ebede8667557e5038e8de9486d1612b49da353a1312e6079fb40c11d9f1611efd1ac6442e4a8131ac8d7d5f6b5410cf4517458b3532a645f1b3377bcfcb4841163a20a";
            String html = f4(requestUrl, cookie);
            f5(html, currencyName, todayDate);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Test
    public void f6() {
        try {
            // http://www.pbc.gov.cn/WZWSREL3poZW5nY2VodW9iaXNpLzEyNTIwNy8xMjUyMTcvMTI1OTI1L2luZGV4Lmh0bWw=
            String url = "http://www.pbc.gov.cn/WZWSREL3poZW5nY2VodW9iaXNpLzEyNTIwNy8xMjUyMTcvMTI1OTI1L2luZGV4Lmh0bWw=?wzwschallenge=V1pXU19DT05GSVJNX1BSRUZJWF9MQUJFTDM0MzYxMzM=";
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Cookie", "wzws_cid=0a9638013f7c8dc7f77676a13505c8a7169244bcc546e820bf1343c3336b957f95af8d3aeb0d9481b9bf90a05edb539829744252ba2ddaa18f3b617c4258455957e6b3fec036d1f439cbfcc5b4f2bb2c");
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            httpGet.setHeader("Cache-Control", "no-cache");
            httpGet.setHeader("Host", "www.pbc.gov.cn");
            httpGet.setHeader("Referer", "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html");
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
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

    public void f7(String wzwschallenge, String url) {

    }

    public String f4(String url, String cookie) {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader("Cookie", cookie);
            httpGet.addHeader("Sec-Fetch-Dest", "document");
            httpGet.addHeader("Sec-Fetch-Mode", "navigate");
            httpGet.addHeader("Sec-Fetch-Site", "navigate");
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
            httpGet.setHeader("Cache-Control", "no-cache");
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            //判断响应状态为200，进行处理
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                System.out.println(httpResponse.toString());
                String html = EntityUtils.toString(httpEntity, "UTF-8");
                Document document = Jsoup.parse(html);
                Element element = document.text("请开启JavaScript并刷新该页.");
                if (element == null) {
                    System.out.println("正常页面");
                } else {
                    System.out.println("cookie失效" + element.toString());
                }
                return EntityUtils.toString(httpEntity, "UTF-8");
            } else {
                System.out.println("响应结果异常：" + httpResponse.toString());
                return "";
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void f5(String html, String currencyName, String todayDate) {
        try {
            //采用Jsoup解析
            Document doc = Jsoup.parse(html);
            //获取title
            Elements titleElements = doc.select("div[class=mainw950]")
                    .select("tbody")
                    .select("tr")
                    .select("font[class=newslist_style]")
                    .select("a");
            System.out.println("titleElements: " + titleElements.toString());
            for (Element titleElement : titleElements) {
                System.out.println(String.format("标题：%s", titleElement.text().trim()));
            }
            //获取发布时间
            Elements pubTimeElements = doc.select("div[class=mainw950]")
                    .select("tbody")
                    .select("tr")
                    .select("span[class=hui12]");
            System.out.println("pubTimeElements: " + pubTimeElements.toString());
            for (Element pubTimeElement : pubTimeElements) {
                System.out.println(String.format("发布时间：%s", pubTimeElement.text().trim()));
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
