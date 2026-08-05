package io.github.exportflow.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
public class ReflectUtils {

    public static String getFieldValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value instanceof String) {
                return (String) value;
            } else if (value instanceof Integer) {
                return value.toString();
            } else if (value instanceof Long) {
                return value.toString();
            } else if (value instanceof Double) {
                return value.toString();
            } else if (value instanceof Float) {
                return value.toString();
            } else if (value instanceof Boolean) {
                return value.toString();
            } else if (value instanceof Date) {
                return DateUtils.formatDateTime((Date) value);
            } else if (value instanceof BigDecimal) {
                return value.toString();
            }
            log.info("fieldName:{},value:{}", fieldName, value);
            return (String) field.get(obj);
        } catch (Exception e) {
            log.error("getFieldValue err", e);
            return null;
        }
    }

}
