package com.mbw.commons.core.api;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * OkHttp API 数据
 *
 * @author Mabowen
 * @date 2020-06-02 09:10
 */
@Data
@Builder
@ToString
public class OkHttpApiData {
    /**
     * 请求地址 http://xxxx.ss.cn
     */
    private String url;

    /**
     * 请求参数 ?id=xxx或者{id=xxx,name=xxx}
     */
    private String query;

    /**
     * 请求体
     */
    private String requestBody;

    /**
     * 响应体
     */
    private String responseBody;

    /**
     * 请求时长
     */
    private Long duration;

    /**
     * 请求方式
     */
    private String httpMethod;

    /**
     * 请求状态
     */
    private Integer httpStatus;

    /**
     * 请求信息
     */
    private String httpMessage;
}
