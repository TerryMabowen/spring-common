package com.mbw.commons.core.api;

import com.mbw.commons.core.api.domain.CourseScheduleData;
import com.mbw.commons.core.api.domain.LoginResponseData;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * OkHttp 请求代理
 * 例子，使用时根据业务自建
 * @author Mabowen
 * @date 2020-06-02 09:07
 */
public interface OkHttpRequestProxy {

    /**
     * 	登录获取Token
     * @param appId
     * @param appSecret
     * @return
     */
    @GET("/online/v1/common/getAccessToken")
    public Call<OkHttpBaseResponse<LoginResponseData>> getAcessToken(@Query("AppId") String appId,
                                                                     @Query("AppSecret") String appSecret);

    /**
     * 	登录获取Token
     * @param appId
     * @param appSecret
     * @return
     */
    @FormUrlEncoded
    @POST("/bell/v1/shared/getOAuthToken")
    public Call<OkHttpBaseResponse<LoginResponseData>> getAcessToken2(@Field("appId") String appId,
                                                                     @Field("appSecret") String appSecret);

    /**
     * 排课接口
     * @return
     */
    @POST("/online/v1/course/setSchedule")
    public Call<OkHttpBaseResponse<String>> setSchedule(@Body CourseScheduleData courseScheduleData);
}
