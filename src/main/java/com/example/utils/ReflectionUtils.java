package com.example.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Kenny Fang
 */
public interface ReflectionUtils {

  static void setField(String fieldName, Object target, Object value) {
    Field field = org.springframework.util.ReflectionUtils
        .findField(target.getClass(), fieldName);

    assert field != null;
    field.setAccessible(true);

    org.springframework.util.ReflectionUtils.setField(field, target, value);
  }

  static Object getField(String fieldName, Object target) {

    Field field = org.springframework.util.ReflectionUtils
        .findField(target.getClass(), fieldName);

    assert field != null;
    field.setAccessible(true);

    return org.springframework.util.ReflectionUtils.getField(field, target);
  }

  static Object invokeMethod(String methodName, Object target, Object... value) {
    Method method = org.springframework.util.ReflectionUtils
        .findMethod(target.getClass(), methodName);

    assert method != null;
    return org.springframework.util.ReflectionUtils.invokeMethod(method, target, value);
  }
}
