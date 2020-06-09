package com.mbw.commons.core.api;

import lombok.Data;
import lombok.ToString;

/**
 * OkHttp response
 *
 * @author Mabowen
 * @date 2020-06-02 09:36
 */
@Data
@ToString
public class OkHttpBaseResponse<T> {
    private Integer code;

    private String message;

    private T data;
}
