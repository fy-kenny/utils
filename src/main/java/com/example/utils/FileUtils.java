package com.example.utils;

import java.util.Objects;

import static com.example.utils.constant.Symbols.SLASH;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public interface FileUtils {

        static String getFileType(String contentType) {

            String fileType;

            if (Objects.isNull(contentType) || contentType.isEmpty()) {
                contentType = "unknown";
            }

            if (!contentType.contains(SLASH)) {
                fileType = contentType;
            } else {
                fileType = contentType.split(SLASH)[0];;
            }

            return fileType;
        }
    }
