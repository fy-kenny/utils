package com.example.utils.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Email template parse utils.
 *
 * @author Kenny Fang
 * @version 1.0
 * @date 2017/9/7 20:32
 */
public class EmailTemplate {

    /**
     * Parse from email template file
     *
     * @param emailTemplate the email template file
     * @param valuesMap placeholder replace value
     * @return
     * @throws IOException
     */
    public static String parse(File emailTemplate, Map<String, String> valuesMap) throws IOException {
        FileReader fileReader = new FileReader(emailTemplate);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder stringBuilder = new StringBuilder();
        if(fileReader.ready()) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        }

        bufferedReader.close();
        fileReader.close();

        return parse(stringBuilder.toString(), valuesMap);
    }

    /**
     * Parse from email template String.
     *
     * @param emailTemplate the email template String
     * @param valuesMap placeholder replace value
     * @return
     */
    public static String parse(String emailTemplate, Map<String, String> valuesMap) {
        String email = emailTemplate;

        for (String key : valuesMap.keySet()) {
            email = email.replaceAll("\\{" + key + "}", valuesMap.get(key));
        }

        return email;
    }

}
