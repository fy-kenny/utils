package com.example.utils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * @author Kenny Fang
 */
public interface NameUtils {

  String UNDERLINE = "_";

  static String convertDbFieldNameToObjFieldName(String dbFieldName) {
    if (StringUtils.isBlank(dbFieldName)) {
      return dbFieldName;
    }

    String[] dbFieldNameSubArr = dbFieldName.split(UNDERLINE);

    if (dbFieldNameSubArr.length <= 1) {
      return dbFieldName;
    }

    StringBuilder camelcase = new StringBuilder(StringUtils.uncapitalize(dbFieldNameSubArr[0]));
    for (int i = 1; i < dbFieldNameSubArr.length; i++) {
      camelcase.append(StringUtils.capitalize(dbFieldNameSubArr[i]));
    }

    return camelcase.toString();
  }

  static String[] convertDbFieldNameToObjFieldName(String[] dbFieldNames) {
    if (null == dbFieldNames ||
        dbFieldNames.length == 0) {
      return dbFieldNames;
    }

    String[] newDbFieldNames = Arrays.copyOf(dbFieldNames, dbFieldNames.length);

    for (int i = 0; i < dbFieldNames.length; i++) {
      newDbFieldNames[i] = convertDbFieldNameToObjFieldName(dbFieldNames[i]);
    }

    return newDbFieldNames;
  }

  static String convertEnumNameToObjFieldName(Enum enumObj) {
    return convertConstNameToObjFieldName(enumObj.name());
  }

  static String convertConstNameToObjFieldName(String constName) {
    String nameLowerCase = constName.toLowerCase();

    return convertDbFieldNameToObjFieldName(nameLowerCase);
  }

  /**
   * <pre>
   *   hello_world_kenny -> HelloWorldKenny
   * </pre>
   * @param dbFieldName field name
   * @return type name
   */
  static String convertDbFieldNameToTypeName(String dbFieldName) {
    return StringUtils.capitalize(convertDbFieldNameToObjFieldName(dbFieldName));
  }

  /**
   * <pre>
   *   HELLO_WORLD -> HelloWorld
   * </pre>
   *
   * @param enumObj enum constant
   * @return type name
   */
  static String convertEnumNameToTypeName(Enum enumObj) {
    return StringUtils.capitalize(convertEnumNameToObjFieldName(enumObj));
  }

  /**
   * The read method name that should be used to read the property value.
   *
   * @param objFieldName field name
   * @return The method that should be used to read the property value. May return null if the
   * property can't be read.
   */
  static String getReadMethodName(Class<?> clazz, String objFieldName) {
    PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, objFieldName);

    assert pd != null;
    return pd.getReadMethod().getName();
  }

  /**
   * The write method name that should be used to read the property value.
   *
   * @param objFieldName field name
   * @return The method that should be used to write the property value. May return null if the
   * property can't be written.
   */
  static String getWriteMethodName(Class<?> clazz, String objFieldName) {
    PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, objFieldName);

    assert pd != null;
    return pd.getWriteMethod().getName();
  }

}
