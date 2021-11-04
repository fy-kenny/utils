package com.example.utils;

import lombok.Data;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@Data
public class ImportExportStrategy {

    private String fileName;
    private String[] headers;
    private String[] columns;
}
