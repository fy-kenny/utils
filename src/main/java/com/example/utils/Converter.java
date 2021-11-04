package com.example.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public interface Converter<T> {

    /**
     * return copy properties to the instance of T, return null if exception occurred
     *
     * @return T
     */
    default T convertTo() {

        T target = null;

        Type genericInterface = this.getClass().getGenericInterfaces()[0];
        if (genericInterface instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
            Type[] typeArray = parameterizedType.getActualTypeArguments();
            if (typeArray != null && typeArray.length > 0) {
                //noinspection unchecked
                Class<T> clazz = (Class<T>) typeArray[0];
                try {
                    target = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {/* ignored*/}
            }
        }

        BeanUtils.copyProperties(this, Objects.requireNonNull(target));

        return target;
    }
}
