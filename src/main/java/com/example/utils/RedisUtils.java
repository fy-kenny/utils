package com.example.utils;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.google.common.collect.Lists;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

/**
 * @author Kenny Fang
 */
public interface RedisUtils {

  /**
   * Return redis keys as original sort by the specified {@code pattern} and {@code redisTemplate}
   *
   * @param pattern the pattern to match
   * @param first only scan first matched key
   * @return matched redis keys
   */
  static List<String> scan(final String pattern, boolean first,
      RedisTemplate<String, String> redisTemplate) {

    return redisTemplate.execute((RedisCallback<List<String>>) connection -> {

      List<String> binaryKeys = Lists.newArrayList();

      ScanOptions scanOptions = ScanOptions.scanOptions()
          .match(pattern)
          .count(100)
          .build();

      Cursor<byte[]> cursor = connection.scan(scanOptions);
      while (cursor.hasNext()) {
        binaryKeys.add(new String(cursor.next()));
        // only find the first one
        if (first) {
          break;
        }
      }

      return binaryKeys;
    });
  }

  /**
   * Return redis keys as original sort by the specified {@code pattern} and {@code redisTemplate}
   *
   * @param pattern the pattern to match
   * @return matched redis keys
   */
  static List<String> scan(final String pattern,
      RedisTemplate<String, String> redisTemplate) {
    return scan(pattern, false, redisTemplate);
  }

  /**
   * Return the first key.
   *
   * @param pattern the pattern to match
   * @return matched redis key, null if not found
   */
  static String scanFirst(final String pattern,
      RedisTemplate<String, String> redisTemplate) {
    List<String> keys = scan(pattern, true, redisTemplate);
    if (isEmpty(keys)) {
      return null;
    }

    return keys.get(0);
  }

  /**
   * Load data from redis and copy properties to the given {@code object}.
   */
  static void hashLoad(String key, Object object,
      HashOperations<String, String, Object> hashOperations)
      throws InvocationTargetException, IllegalAccessException {

    Map<String, Object> map = hashOperations.entries(key);
    BeanUtils.populate(object, map);
  }

  static List<Object> hashBatchLoad(final Set<String> keys,
      RedisTemplate<String, String> redisTemplate) {

    return redisTemplate
        .executePipelined((RedisCallback<List<Map<String, String>>>) connection -> {

          StringRedisConnection stringRedisConn = (StringRedisConnection) connection;

          keys.forEach(stringRedisConn::hGetAll);

          return null;
        });
  }

}
