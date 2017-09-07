package com.example.utils.email;

import com.example.utils.extend.ConsoleUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Test class of EmailTemplate.
 *
 * @author Kenny Fang
 * @version 1.0
 * @date 2017/9/7 22:03
 */
@Slf4j
public class EmailTemplateTests {
    public final static String currentPath = "src/test/java/com/example/utils/email/";

    private static Map<String, String> valuesMap = new HashMap<>();
    static {
        valuesMap.put("userName", "Fang");
        valuesMap.put("age", "18");
        valuesMap.put("sex", "f");
        valuesMap.put("birthday", "1985-08-22");
        valuesMap.put("separator", "------------------------------------------------\n");
        valuesMap.put("introduction", "I Love U ~v~");
        valuesMap.put("contact", "QQ: \t\t12341234124\nEmail: \t\taaa@gmail.com");
    }

    public static void main(String[] args) {
        parseString();

        parseFile();
    }

    /**
     * Parse from String.
     */
    private static void parseString() {
        ConsoleUtils.printlnRed("########## parse from String ##########");

        prepareExtendsValue(valuesMap);

        StringBuilder templateStringBuilder = new StringBuilder();

        templateStringBuilder.append("Hello {called} {userName} :)\n\n");
        templateStringBuilder.append("    Your birthday {birthday} is coming!\n");
        templateStringBuilder.append("You are {sex}, and {age} years old\n");
        templateStringBuilder.append("{separator}\n");
        templateStringBuilder.append("{introduction}\n");
        templateStringBuilder.append("{separator}\n");
        templateStringBuilder.append("{contact}\n");


        System.out.println(EmailTemplate.parse(templateStringBuilder.toString(), valuesMap));
    }

    /**
     * Parse from File.
     */
    private static void parseFile() {
        ConsoleUtils.printlnRed("######### parse from String ##########");

        prepareExtendsValue(valuesMap);

        try {
            String email = EmailTemplate.parse(new File(currentPath + "emailTemplate.et"), valuesMap);

            System.out.println(email);
        } catch (IOException e) {
            log.error("Read email temple file error: ", e);
        }
    }


    /**
     * Prepare extends values.
     * <pre>
     *   if ("f".equals(sex)) {
     *      if (age > 18) {
     *          valuesMap.put("called", "Mrs.");
     *      } else {
     *          valuesMap.put("called", "Ms.");
     *      }
     *   }
     *
     *   if ("m".equals(sex)) {
     *       if (age > 18) {
     *           valuesMap.put("called", "Mr.");
     *       } else {
     *           valuesMap.put("called", "boy");
     *       }
     *   }
     * </pre>
     *
     * @param valuesMap
     */
    private static void prepareExtendsValue(Map<String, String> valuesMap) {
        int age = Integer.valueOf(valuesMap.get("age"));
        String sex = valuesMap.get("sex").toLowerCase();

        if ("f".equals(sex)) {
            if (age > 18) {
                valuesMap.put("called", "Mrs.");
            } else {
                valuesMap.put("called", "Ms.");
            }
        }

        if ("m".equals(sex)) {
            if (age > 18) {
                valuesMap.put("called", "Mr.");
            } else {
                valuesMap.put("called", "boy");
            }
        }
    }
}
