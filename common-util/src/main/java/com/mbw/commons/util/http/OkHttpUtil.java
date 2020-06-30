package com.mbw.commons.util.http;

import com.mbw.commons.lang.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * okHttp3 工具类
 *
 * @author Mabowen
 * @date 2020-06-30 13:39
 */
@Slf4j
public class OkHttpUtil {
    private static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON_CONTENT_TYPE = MediaType.get("application/json; charset=utf-8");

    public static String doGet(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().source().readByteString().string(StandardCharsets.UTF_8);
            } else {
                return StringUtils.EMPTY;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static String doPost(String url, String jsonParams) {
        try {
            RequestBody body = RequestBody.create(JSON_CONTENT_TYPE, jsonParams);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().source().readByteString().string(StandardCharsets.UTF_8);
            } else {
                return StringUtils.EMPTY;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
