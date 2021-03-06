package com.mbw.commons.lang.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-05-20 17:08
 */
public class JacksonFactory {
    private static JacksonFactory jsonFactory;

    @Getter
    private ObjectMapper objectMapper;

    public static JacksonFactory getInstance() {
        if (jsonFactory != null) {
            return jsonFactory;
        }

        synchronized (JacksonFactory.class) {
            if (jsonFactory == null) {
                jsonFactory = new JacksonFactory();
            }

            jsonFactory.init();
        }

        return jsonFactory;
    }

    private void init() {
        objectMapper = new ObjectMapper();
    }
}
