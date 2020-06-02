package com.mbw.commons.core.api;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * OkHttp 配置
 *
 * @author Mabowen
 * @date 2020-06-02 09:14
 */
@Data
@Component
public class OkHttpConfig {
    /**
     * 基本url
     */
    private String baseUrl;

    /**
     *
     */
    private String appId;

    /**
     * Secret 密钥
     */
    private String appSecret;

    /**
     * 请求超时时间，单位ms
     */
    private Integer timeout;
}
