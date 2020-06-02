package com.mbw.commons.core.api;

/**
 * OkHttp response
 *
 * @author Mabowen
 * @date 2020-06-02 09:36
 */
public class OkHttpBaseResponse<T> {
    private Integer code;

    private String message;

    private T data;
}
