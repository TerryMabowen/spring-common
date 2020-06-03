package com.mbw.common.core.crawler.boc;

import com.mbw.commons.util.date.DateUtil;
import org.apache.commons.compress.utils.Lists;
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

import java.net.URLEncoder;
import java.util.*;

/**
 * java 爬虫
 * 爬去中国银行币种汇率信息
 *
 * @author Mabowen
 * @date 2020-06-02 08:58
 */
@Deprecated
public class BOCCurrencyCrawler {

    @Test
    public void f1() {
        f2();
    }

    public void f2() {
        try {
            String[] currencies = new String[] {"美元", "日元", "港币"};
            String todayDate = DateUtil.format(Calendar.getInstance().getTime(), "yyyy-MM-dd");
            Map<String, ExchangeRateCrawlerData> map = new HashMap<>();
            for (String currencyName : currencies) {
                int pageNo = 1;
                ExchangeRateCrawlerData bocCrawlerData = f3(todayDate, currencyName, pageNo);
                map.put(currencyName, bocCrawlerData);
                //等10s
                Thread.sleep(10000);
            }
            for (Map.Entry<String, ExchangeRateCrawlerData> entry : map.entrySet()) {
                System.out.println(String.format("货币：%s, 汇率信息：%s", entry.getKey(), entry.getValue().toString()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ExchangeRateCrawlerData f3(String todayDate, String currencyName, int pageNo) {
        try {
//            String REQUEST_URL = "https://srh.bankofchina.com/search/whpj/search_cn.jsp";
            String requestUrl = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html";
            StringBuilder url = new StringBuilder(requestUrl);
            url.append("?erectDate=").append(todayDate);
            url.append("&nothing=").append(todayDate);
            url.append("&pjname=").append(URLEncoder.encode(currencyName, "UTF-8"));
            url.append("&page=").append(pageNo);
            System.out.println(String.format("拼接好的url{%s}", url.toString()));
            String html = f4(url.toString());
            return f5(html, currencyName, todayDate);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public String f4(String url) {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader("Referer", "https://srh.bankofchina.com/search/whpj/search_cn.jsp");
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
                return EntityUtils.toString(httpEntity, "UTF-8");
            } else {
                System.out.println("响应结果异常：" + httpResponse.toString());
                return "";
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public ExchangeRateCrawlerData f5(String html, String currencyName, String todayDate) {
        try {
            //采用Jsoup解析
            Document doc = Jsoup.parse(html);
            //获取html标签中的内容
            Elements elements = doc.select("div[class=wrapper]")
                    .select("div[class=BOC_main publish]")
                    .select("table")
                    .select("tbody")
                    .select("tr");
            List<ExchangeRateCrawlerData> dataList = Lists.newArrayList();
            for (Element element : elements) {
                Elements tds = element.children();
                if (tds != null && tds.size() > 0 && currencyName.equalsIgnoreCase(tds.get(0).text().trim())) {
                    ExchangeRateCrawlerData bocCrawlerData = new ExchangeRateCrawlerData();
//                    bocCrawlerData.setCurrencyName(tds.get(0).text().trim());
//                    bocCrawlerData.setBuyingRate(tds.get(1).text().trim());
//                    bocCrawlerData.setCashBuyingRate(tds.get(2).text().trim());
//                    bocCrawlerData.setSellingRate(tds.get(3).text().trim());
//                    bocCrawlerData.setCashSellingRate(tds.get(4).text().trim());
//                    bocCrawlerData.setMiddleRate(tds.get(5).text().trim());
//                    bocCrawlerData.setPubTime(tds.get(6).text().trim());
                    dataList.add(bocCrawlerData);
                }
            }
            if (!dataList.isEmpty()) {
                return dataList.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
