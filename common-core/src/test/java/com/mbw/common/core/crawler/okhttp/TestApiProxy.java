package com.mbw.common.core.crawler.okhttp;

import com.mbw.commons.core.api.OkHttpBaseResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-03 15:43
 */
public interface TestApiProxy {

    @GET("/search/whpj/search_cn.jsp")
    public Call<OkHttpBaseResponse<String>> baiduSearch();
}
