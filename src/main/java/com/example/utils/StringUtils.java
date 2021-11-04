package com.example.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public interface StringUtils {

    static String insert(String value, int beginIndex, String insert) {

        StringBuilder sb = new StringBuilder();
        sb.append(value.substring(0, beginIndex));
        sb.append(insert);
        sb.append(value.substring(beginIndex).toLowerCase());

        return sb.toString();

    }

    /**
     * convert word that is in {@param words} to case
     * <pre>
     *     upperCase = null, convert all words to the word case that specified in words
     *     upperCase = true, convert all words to upper case
     *     upperCase = false, convert all words to lower case
     * </pre>
     * @param value original string
     * @param upperCase true upper case, false lower case and null keep case in words
     * @param words the words which should be match and the default case style
     * @return string which words is converted
     */
    static String convertWordCaseAll(String value, Boolean upperCase, String... words) {

        String regex = "(?i)(" + words[0];
        for (String word: words) {
            regex += "|" + word;
        }
        regex += ")";

        Map<String, String> lowerCaseToOriginalCaseWordMap = new HashMap<>();
        for (String word: words) {
            lowerCaseToOriginalCaseWordMap.put(word.toLowerCase(), word);
        }

        Matcher matcher = Pattern.compile(regex).matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group();
            if (Objects.isNull(upperCase)) { // replace with input case
                matcher.appendReplacement(sb, lowerCaseToOriginalCaseWordMap.get(match.toLowerCase()));
            } else if (upperCase) {
                matcher.appendReplacement(sb, match.toUpperCase());
            } else {
                matcher.appendReplacement(sb, match.toLowerCase());
            }

        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    static String replaceUpperCaseAll(String value, String... words) {
        return convertWordCaseAll(value, true, words);
    }

    static String replaceLowerCaseAll(String value, String... words) {
        return convertWordCaseAll(value, false, words);
    }

    static String replaceCaseAll(String value, String... words) {
        return convertWordCaseAll(value, null, words);
    }
}
