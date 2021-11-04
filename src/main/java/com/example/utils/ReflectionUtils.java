package com.example.utils;

import com.google.common.collect.Lists;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Kenny Fang
 * @since 0.0.1
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

  static boolean isPrimitiveOrPrimitiveClass(Field field) {


    boolean isPrimitive = false;
    try {
      Class fieldType = field.getType();
      isPrimitive = fieldType.isPrimitive()
              || fieldType.equals(String.class)
              || ((Class) fieldType.getField("TYPE").get(null)).isPrimitive();
    } catch (IllegalAccessException | NoSuchFieldException e) {
      // ignored
    }

    return isPrimitive;
  }

  static boolean hasFieldAnnotation(Class clazz, Class<? extends Annotation> annotationClass) {

    boolean hasFiledAnnotation = false;

    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      if (field.isAnnotationPresent(annotationClass)) {
        hasFiledAnnotation = true;
      }
    }

    return hasFiledAnnotation;
  }

  static <T extends Annotation> List<T> getFieldAnnotations(Class clazz, Class<T> annotationClass) {

    List<T> annotationList = Lists.newArrayList();

    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      if (field.isAnnotationPresent(annotationClass)) {
        annotationList.add(field.getAnnotation(annotationClass));
      }
    }

    return annotationList;
  }

  static List<Field> getFieldsByAnnotation(Class clazz, Class<? extends Annotation> annotationClass) {

    List<Field> fieldList = Lists.newArrayList();

    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      if (field.isAnnotationPresent(annotationClass)) {
        fieldList.add(field);
      }
    }

    return fieldList;
  }
}
