package com.mbw.commons.lang.exception;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-02 09:38
 */
public class OkHttpException extends RuntimeException{
    private static final long serialVersionUID = -5463708362018677133L;

    public OkHttpException() {
    }

    public OkHttpException(String message) {
        super(message);
    }

    public OkHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public OkHttpException(Throwable cause) {
        super(cause);
    }
}
