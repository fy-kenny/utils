package com.example.utils;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Kenny Fang
 * @since 0.0.1
 */
public interface RequestUtils {

     static <T> T read(HttpServletRequest request, Class<T> clazz) {

        Field[] fields = clazz.getDeclaredFields();

        T obj = null;
        try {
            obj = clazz.newInstance();
            for (Field field : fields) {
                String name = field.getName();
                String value = request.getParameter(name);
//                if (Objects.nonNull(value)) {
                try {
                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
                    Method writeMethod = descriptor.getWriteMethod();
                    writeMethod.invoke(obj, convert(field.getType(), value));
                } catch (IllegalAccessException
                        | IntrospectionException | InvocationTargetException e) {
                    // ignored
                }
//                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            // ignored
        }

        return obj;
    }

    static Object convert(Class<?> target, String s) {
        if (target == Object.class || target == String.class || s == null) {
            return s;
        }
        if (target == Character.class || target == char.class) {
            return s.charAt(0);
        }
        if (target == Byte.class || target == byte.class) {
            return Byte.parseByte(s);
        }
        if (target == Short.class || target == short.class) {
            return Short.parseShort(s);
        }
        if (target == Integer.class || target == int.class) {
            return Integer.parseInt(s);
        }
        if (target == Long.class || target == long.class) {
            return Long.parseLong(s);
        }
        if (target == Float.class || target == float.class) {
            return Float.parseFloat(s);
        }
        if (target == Double.class || target == double.class) {
            return Double.parseDouble(s);
        }
        if (target == Boolean.class || target == boolean.class) {
            return Boolean.parseBoolean(s);
        }
        throw new IllegalArgumentException("Don't know how to convert to " + target);
    }

}
