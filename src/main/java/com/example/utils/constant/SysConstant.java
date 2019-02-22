package com.example.utils.constant;

/**
 * @author Kenny Fang
 */
public interface SysConstant {

  /**
   * {@code workId} of snowflake, default is 1
   */
  int DEFAULT_WORK_ID = 1;
  /**
   * {@code datacenterId }of snowflake, default is 1
   */
  int DEFAULT_DATA_CENTER_ID = 1;
  /**
   * use optimized system clock to get millisecond, default is {@code true}
   */
  boolean DEFAULT_USE_OPTIMIZED_CLOCK = true;

  String COMMA = ",";

  char CHAR_ONE = '1';

  String FORMAT_YYMMDD = "yyMMdd";

}
