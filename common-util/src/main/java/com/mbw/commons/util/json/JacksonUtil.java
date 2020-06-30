package com.mbw.commons.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbw.commons.lang.json.JacksonFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Jackson 工具类
 *
 * @author Mabowen
 * @date 2020-05-20 17:08
 */
@Slf4j
public class JacksonUtil {

    public static String toJson(Object var) {
        try {
            ObjectMapper objectMapper = JacksonFactory.getInstance()
                    .getObjectMapper();
            return objectMapper.writeValueAsString(var);
        } catch (JsonProcessingException e) {
            log.error("Object to json string error: {}" + e.getMessage(), e);
            return StringUtils.EMPTY;
        }
    }

    public static <T> T toObject(String var, Class<T> clz) {
        try {
            ObjectMapper objectMapper = JacksonFactory.getInstance()
                    .getObjectMapper();

            return objectMapper.readValue(var, clz);
        } catch (JsonProcessingException e) {
            log.error("json string to Object error: {}" + e.getMessage(), e);
            return null;
        }
    }
}
