package com.mbw.commons.util.convert;

import com.mbw.commons.lang.enums.EnumDataType;
import com.mbw.commons.util.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2019-12-13 17:56
 */
@Slf4j
public class DataConvertUtil {

    public static void convertDataByType(String variate, String value, Integer type, Map<String, Object> map) {
        if (StringUtils.isNotBlank(variate) && StringUtils.isNotBlank(value) && type != null) {
            value = value.trim();
            if (EnumDataType.INT.getValue().equals(type)) {
                 map.put(variate, Integer.parseInt(value));
            } else if (EnumDataType.FLOAT.getValue().equals(type)) {
                map.put(variate, Float.parseFloat(value));
            } else if (EnumDataType.DOUBLE.getValue().equals(type)) {
                map.put(variate, Double.parseDouble(value));
            } else if (EnumDataType.STRING.getValue().equals(type)) {
                map.put(variate, value);
            } else if (EnumDataType.DATE.getValue().equals(type)) {
                Date date = DateUtil.parse(value, "yyyy-MM-dd HH:mm:ss");
                map.put(variate, date);
            } else if (EnumDataType.BOOL.getValue().equals(type)) {
                 if ("true".equals(value.toLowerCase())) {
                     map.put(variate, Boolean.TRUE);
                 } else {
                     map.put(variate, Boolean.FALSE);
                 }
            }
        }
    }
}
