package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.utils.StringUtils.replaceUpperCaseAll;
import static com.example.utils.StringUtils.insert;
import static com.example.utils.constant.Symbols.UNDERLINE;


/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public interface DbUtils {

    /**
     * <pre>
     *     desc(ignore case) -> DESC
     *     asc(ignore case) -> ASC
     *     createTime -> create_time
     * </pre>
     * @param value sql order snippets
     * @return sql order after corrected
     *
     * @see #convertPropertiesToDbColumn(String)
     */
    static String correctOrderCodeStyle(String value) {

        String order = replaceUpperCaseAll(value, "desc", "asc");
        return convertPropertiesToDbColumn(order);
    }

    /**
     * convert properties to db column code style.
     * <pre>
     *     createTime -> create_time
     * </pre>
     * @param value
     * @return
     */
    static String convertPropertiesToDbColumn(String value) {

        Matcher matcher = Pattern.compile("[a-z0-9]+[A-Z]").matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group();
            matcher.appendReplacement(sb, insert(match.toLowerCase(), match.length() - 1, UNDERLINE));

        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
