package com.mbw.common.core.crawler.okhttp;

import com.mbw.commons.core.api.OkHttpBaseManager;
import com.mbw.commons.core.api.OkHttpBaseResponse;
import com.mbw.commons.core.api.OkHttpConfig;
import com.mbw.commons.core.api.domain.LoginResponseData;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-03 15:42
 */
public class TestApiService extends OkHttpBaseManager {
    @Override
    public OkHttpBaseResponse<LoginResponseData> refreshAccessToken() {
        String baseUrl = "https://srh.bankofchina.com";
        return null;
    }

    @Test
    public void f() {
        f1();
    }

    private void f1() {
        try {
            OkHttpConfig okHttpConfig = getOkHttpConfig();
            if (okHttpConfig == null) {
                okHttpConfig = new OkHttpConfig();
                okHttpConfig.setBaseUrl("https://srh.bankofchina.com");
                okHttpConfig.setTimeout(5000);

                setOkHttpConfig(okHttpConfig);
            }
            TestApiProxy api = (TestApiProxy)createApiProxy(TestApiProxy.class);
            Call<OkHttpBaseResponse<String>> call = api.baiduSearch();
            Response<OkHttpBaseResponse<String>> response = call.execute();
            if (response.isSuccessful()) {
                System.out.println(response.body());
            } else {
                System.out.println("请求失败：" + response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
