package com.example.utils;

import static com.example.utils.constant.IssueHistoryEnum.MORE;
import static com.example.utils.constant.IssueHistoryEnum.YESTERDAY;
import static com.example.utils.constant.StringExtUtils.COMMA;
import static com.example.utils.constant.SysConstant.FORMAT_YYMMDD;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.example.utils.constant.IssueHistoryEnum;
import com.example.utils.constant.IssueTimes;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author Kenny Fang
 */
public interface LotteryUtils {

  static String getLotteryNumber(final String drawNumber, final boolean sorted, String delimiter) {

    List<Integer> drawNumberList = getLotteryNumberAsIntegerList(drawNumber, sorted);

    if (null == delimiter) {
      delimiter = EMPTY;
    }
    return drawNumberList.stream().map(Object::toString).collect(Collectors.joining(delimiter));
  }

  static String getDrawNumberAsString(final List<Integer> drawNumberList,
      final boolean sorted,
      String delimiter) {

    List<Integer> tempDrawNumberList = Lists.newArrayList();
    tempDrawNumberList.addAll(drawNumberList);

    Stream<Integer> drawNumberStream = tempDrawNumberList.stream();
    // draw number list
    if (sorted) {
      drawNumberStream = tempDrawNumberList.stream().sorted();
    }

    if (null == delimiter) {
      delimiter = EMPTY;
    }
    return drawNumberStream.map(Object::toString).collect(Collectors.joining(delimiter));
  }

  /**
   * Return draw number list (only for testing).
   *
   * @param min min num
   * @param max max num
   * @param length lottery number length eg: JL_11X5 = 5
   * @param count count of lottery number
   * @return lottery number list
   */
  static List<String> generateDrawNumber(final int min, final int max, final int length,
      final int count) {
    List<String> drawNumberList = Lists.newArrayList();
    Set<String> drawNumberSet = Sets.newHashSet();
    for (int i = 0; i < count; i++) {
      while (drawNumberSet.size() != length) {
        int num = RandomUtils.nextInt(min, max);
        String numStr = org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(num), 2, '0');
        drawNumberSet.add(numStr);
      }
      List<String> tempDrawNumberList = drawNumberSet.parallelStream().collect(
          Collectors.toList());
      Collections.shuffle(tempDrawNumberList);
      String drawNumberStr = tempDrawNumberList.parallelStream()
          .unordered()
          .collect(Collectors.joining(COMMA));

      drawNumberList.add(drawNumberStr);
      drawNumberSet.clear();
    }

    return drawNumberList;
  }

  /**
   * Return valid issue rows. return default 100 if rows <= 0, return 300 if rows >= 300
   * <pre>
   * -100 -> 100
   * -1   -> 100
   * null -> 100
   * 0    -> 100
   * 30   -> 30
   * 50   -> 50
   * 100  -> 100
   * 200  -> 200
   * 300  -> 300
   * 500  -> 300
   * 1000 -> 300
   * </pre>
   *
   * @param rows issues
   * @return valid issues
   */
  static int getValidIssueRows(Integer rows) {
    if (null == rows ||
        IssueTimes.TIMES_0.gte(rows)) {
      return IssueTimes.DEFAULT.getTimes();
    } else if (IssueTimes.TIMES_300.lt(rows)) {
      return IssueTimes.TIMES_300.getTimes();
    }

    return rows;
  }

  static List<String> getLotteryNumberAsStringList(final String lotteryNumber) {

    return Arrays.stream(lotteryNumber
        .split(COMMA))
        .sorted()
        .collect(Collectors.toList());
  }

  static List<Integer> getLotteryNumberAsIntegerList(final String lotteryNumber) {

    return getLotteryNumberAsIntegerList(lotteryNumber, false);
  }

  static List<Integer> getLotteryNumberAsIntegerList(final String lotteryNumber,
      final boolean sorted) {

    // draw number list
    Stream<Integer> drawNumberStream = Arrays.stream(lotteryNumber
        .split(COMMA))
        .map(Integer::parseInt);
    if (sorted) {
      drawNumberStream = drawNumberStream.sorted();
    }

    return drawNumberStream.collect(Collectors.toList());
  }

  static List<Integer> getLotteryNumberAsIntegerList(final String lotteryNumber, boolean sorted,
      int count) {
    List<Integer> lotteryNumberList = getLotteryNumberAsIntegerList(lotteryNumber, sorted);

    return lotteryNumberList.parallelStream()
        .limit(count)
        .collect(Collectors.toList());
  }

  static String oneToZero(final String missItem) {
    int oneCount = StringUtils.countMatches(missItem, '1');
    int zeroCount = StringUtils.countMatches(missItem, '0');

    return "" + oneCount + zeroCount;
  }

  static String zeroToOne(final String missItem) {
    int oneCount = StringUtils.countMatches(missItem, '1');
    int zeroCount = StringUtils.countMatches(missItem, '0');

    return "" + zeroCount + oneCount;
  }

  static String zeroToOneToTwo(final String missItem) {
    int zeroCount = StringUtils.countMatches(missItem, '0');
    int oneCount = StringUtils.countMatches(missItem, '1');
    int twoCount = StringUtils.countMatches(missItem, '2');

    return "" + zeroCount + oneCount + twoCount;
  }

  /**
   * Return count by the specified max value zero one two.
   *
   * <pre>
   *  lottery: JL_11X5
   *  sum: 5
   *  zero count: 0-3
   *  one count: 0-4
   *  two count: 0-4
   *
   *  014
   *  023
   *  032
   *  041
   *  104
   *  113
   *  122
   *  131
   *  140
   *  203
   *  212
   *  221
   *  230
   *  302
   *  311
   *  320
   *
   *  count = 16
   * </pre>
   */
  static int count(int sum, int zeroMaxCount, int oneMaxCount, int twoMaxCount) {
    int count = 0;
    for (int zero = 0; zero <= zeroMaxCount; zero++) {
      for (int one = 0; one <= oneMaxCount; one++) {
        int two = sum - zero - one;

        if (two < 0 ||
            two > twoMaxCount) {
          continue;
        }

        count++;
      }
    }

    return count;
  }

  /**
   * Return count of arrangement 0 1 2.
   *
   * @param maxNum max value of number eg: JL_11X5 = 11
   * @param sum lottery number length
   * @return count of arrangement 0 1 2
   */
  static int count012(int maxNum, int sum) {
    int zeroMaxCount = 0;
    int oneMaxCount = 0;
    int twoMaxCount = 0;

    for (int i = 0; i < maxNum; i++) {
      int remainder = i % 3;

      if (0 == remainder) {
        zeroMaxCount++;
      } else if (1 == remainder) {
        oneMaxCount++;
      } else {
        twoMaxCount++;
      }
    }

    return count(sum, zeroMaxCount, oneMaxCount, twoMaxCount);
  }

  /**
   * filter issue history by the specified {@link IssueHistoryEnum}.
   *
   * @param issueVOList the issue history list is used to be filtered
   * @param issueHistoryEnum {@link IssueHistoryEnum}
   * @return issue history list after filtered
   */
  static List<IssueVO> filterIssueHistory(List<IssueVO> issueVOList,
      IssueHistoryEnum issueHistoryEnum) {

    if (CollectionUtils.isEmpty((issueVOList)) ||
        MORE.equals(issueHistoryEnum)) {

      return issueVOList;
    }

    LocalDate date = LocalDate.now();
    if (YESTERDAY.equals(issueHistoryEnum)) {
      date = date.plusDays(-1);
    }

    String yyMMdd = date.format(DateTimeFormatter.ofPattern(FORMAT_YYMMDD));

    return issueVOList.stream()
        .filter(e -> e.getIssue().contains(yyMMdd))
        .collect(Collectors.toList());
  }

  /**
   * Return sum of lottery number.
   *
   * @param lotteryNumber lottery number
   * @return sum
   */
  static int sum(final String lotteryNumber) {
    List<Integer> lotteryNumberIntegerList = getLotteryNumberAsIntegerList(lotteryNumber);

    return lotteryNumberIntegerList.parallelStream().mapToInt(Integer::intValue).sum();
  }

  class IssueVO {

    public Collection<Object> getIssue() {
      return null;
    }
  }
}
