package com.example.utils;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public interface TemplateUtils {

    String DEFAULT_VALUE = StringUtils.EMPTY;
    /**
     * Return new String after replaced keys "{key}" with related key -> value.
     * <pre>
     *     if {"id": 1} then
     *      A:{id} => A:1
     * </pre>
     *
     * @param templateString original String template
     * @param valuesMap value map
     * @return new string after replace variables place holders
     */
    static String fillData(String templateString, Map<String, Object> valuesMap) {
        return fillData(templateString, valuesMap, DEFAULT_VALUE);
    }

    /**
     * Return new String after replaced keys "{key}" with related key -> value.
     *
     * @param templateString original String template
     * @param key place holder
     * @param valueString String value
     * @return new string after replace variable place holders
     */
    static String fillData(String templateString, String key, String valueString) {
        return templateString.replaceAll("\\{" + key + "}", valueString);
    }

    /**
     * Return new String after replaced keys "{key}" with related key -> value. will call to
     * method {@link Object#toString()}.
     *
     * @param templateString original String template
     * @param key place holder
     * @param object value
     * @return new string after replace variable place holders
     */
    static String fillData(String templateString, String key, Object object) {
        return fillData(templateString, key, object.toString());
    }


    /**
     * Return new String after replaced keys "{key}" with related key -> value.
     * If value cannot be found will use {@code defaultValue} instead.
     *  <pre>
     *     if {"id": 1} then
     *     A:{id} => A:1
     *  </pre>
     *
     * @param templateString original String template
     * @param valuesMap value map
     * @param defaultValue {@code DEFAULT_VALUE} will be set if {@code defaultValue} is null
     * @return new string after replace variables place holders
     */
    static String fillData(String templateString, Map<String, Object> valuesMap,
        String defaultValue) {

        if (StringUtils.isBlank(defaultValue)) {
            defaultValue = DEFAULT_VALUE;
        }

        String ret = templateString;

        for (Map.Entry<String,Object> entry : valuesMap.entrySet()) {
            Object valueObj = entry.getValue();
            String valueString = (null == valueObj) ? defaultValue : valueObj.toString();

            ret = ret.replaceAll("\\{" + entry.getKey() + "}", valueString);
        }

        return ret;
    }
}
