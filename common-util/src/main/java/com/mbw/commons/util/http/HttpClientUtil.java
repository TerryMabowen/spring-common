package com.mbw.commons.util.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * HttpClient工具类
 *
 * @author Mabowen
 * @date 2020-05-26 20:48
 */
@Slf4j
public class HttpClientUtil {

    public static String doGet(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = null;
        // 响应模型
        CloseableHttpResponse response = null;
        //
        String result = "";
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            URI uri = builder.build();
            log.info(String.format("ApiRequest[%s]", uri.toString()));
            // 创建http GET请求
            httpGet = new HttpGet(uri);
            // 设置连接超时时间和获取数据超时时间，单位毫秒
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(7000)
                    .setConnectionRequestTimeout(2000)
                    .setSocketTimeout(5000)
                    .build();
            httpGet.setConfig(requestConfig);
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            // 返回200，请求成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 结果返回
                result = EntityUtils.toString(responseEntity, "UTF-8");
                log.info(String.format("ApiResponse[%s]", result));
            } else {
                log.error("请求失败！" + response.toString());
            }

            return result;
        } catch (Exception e) {
            log.error("doGet请求错误: " + e.getMessage() + ", 请求url为" + url);
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
