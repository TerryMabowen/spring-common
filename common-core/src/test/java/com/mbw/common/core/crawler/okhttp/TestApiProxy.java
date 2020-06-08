package com.mbw.common.core.crawler.okhttp;

import com.mbw.commons.core.api.OkHttpBaseResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-03 15:43
 */
public interface TestApiProxy {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/search/whpj/search_cn.jsp")
    public Call<OkHttpBaseResponse<String>> baiduSearch();
}
