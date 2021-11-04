package com.example.utils;

import com.example.utils.exception.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

import static com.example.utils.ReflectionUtils.getFieldsByAnnotation;
import static com.example.utils.ReflectionUtils.hasFieldAnnotation;
import static com.example.utils.constant.Constants.GB2312;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Export data as csv file
 *
 * @author Kenny Fang
 * @since 0.0.2
 */
public interface CsvUtils {

    String FILE_EXTENSION_CSV = ".csv";

    static String getFileExtension() {
        return FILE_EXTENSION_CSV;
    }

    /**
     * Return file name with file extension, the file extension will be appended if no present.
     *
     * @param fileName the name of file
     * @return file name with file extension
     */
    static String appendFileExtensionIfNotPresent(String fileName) {

        if (!fileName.endsWith(getFileExtension())) {
            fileName += getFileExtension();
        }

        return fileName;
    }

    /**
     * Write data as csv file to the giving response by the giving fileName headers and columns, it will be collected
     * automatically if not present
     *
     * @param dataList the data list of csv file
     * @param clazz    the actual class of <b>dataList</b>
     * @param fileName the name of exporting file
     * @param headers  the headers
     * @param columns  the property of the exporting object which should be exported
     * @param response where to write
     *
     * @throws IOException io exception is occurred
     *
     * @see #write(List, String, String[], String[], HttpServletResponse)
     * @see #write(List, HttpServletResponse)
     * @see #write(List, Class, HttpServletResponse)
     */
    static void write(List dataList, Class<?> clazz, String fileName, String[] headers, String[] columns,
                      HttpServletResponse response) throws IOException {

        if (Objects.isNull(clazz)) {
            validate(dataList);

            clazz = dataList.get(0).getClass();
        }

        if (null == fileName || "".equals(fileName)) {
            fileName = getFileNameWithFileExtension(clazz);
        } else {
            fileName = appendFileExtensionIfNotPresent(fileName);
        }

        if (null == headers || 0 == headers.length) {
            headers = getHeaders(clazz);
        }

        if (null == columns || 0 == columns.length) {
            columns = getColumns(clazz);
        }

        if (columns.length != headers.length) {
            headers = getHeadersByColumns(columns, clazz);
            columns = Arrays.stream(columns)
                    .filter(Objects::nonNull)
                    .toArray(String[]::new);
        }

        prepareResponse(response, fileName);

        Writer writer = new OutputStreamWriter(response.getOutputStream(), GB2312);
        ICsvBeanWriter csvBeanWriter = null;
        try {
            csvBeanWriter = new CsvBeanWriter(writer, new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE)
                    .useQuoteMode(new AlwaysQuoteMode())
                    .build());
            csvBeanWriter.writeHeader(headers);

            for (Object data : dataList) {
                csvBeanWriter.write(data, columns, getCellProcessors(clazz, columns, true));
            }

        } finally {
            if (Objects.nonNull(csvBeanWriter)) {
                csvBeanWriter.close();
            }
        }
    }

    /**
     * @throws DataNotExistedException when dataList is null or empty
     *
     * @see #write(List, Class, String, String[], String[], HttpServletResponse)
     * @see #write(List, HttpServletResponse)
     * @see #write(List, Class, HttpServletResponse)
     * @see #write(List, ImportExportStrategy, HttpServletResponse)
     * @see #write(List, Class, ImportExportStrategy, HttpServletResponse)
     */
    static void write(List dataList, String fileName, String[] headers, String[] columns,
                      HttpServletResponse response) throws IOException {

        validate(dataList);

        Class clazz = dataList.get(0).getClass();

        write(dataList, clazz, fileName, headers, columns, response);
    }

    /**
     * @throws DataNotExistedException when dataList is null or empty
     *
     * @see #write(List, String, String[], String[], HttpServletResponse)
     * @see #write(List, Class, String, String[], String[], HttpServletResponse)
     * @see #write(List, HttpServletResponse)
     * @see #write(List, Class, HttpServletResponse)
     * @see #write(List, ImportExportStrategy, HttpServletResponse)
     */
    static void write(List dataList, Class clazz, ImportExportStrategy importExportStrategy,
                      HttpServletResponse response) throws IOException {


        write(dataList, clazz, importExportStrategy.getFileName(), importExportStrategy.getHeaders(),
                importExportStrategy.getColumns(), response);
    }

    /**
     * @throws DataNotExistedException when dataList is null or empty
     *
     * @see #write(List, String, String[], String[], HttpServletResponse)
     * @see #write(List, Class, String, String[], String[], HttpServletResponse)
     * @see #write(List, HttpServletResponse)
     * @see #write(List, Class, HttpServletResponse)
     * @see #write(List, Class, ImportExportStrategy, HttpServletResponse)
     */
    static void write(List dataList, ImportExportStrategy importExportStrategy,
                      HttpServletResponse response) throws IOException {

        write(dataList, null, importExportStrategy.getFileName(), importExportStrategy.getHeaders(),
                importExportStrategy.getColumns(), response);
    }

    /**
     * Write data as csv file to the giving response and collected fileName headers columns
     * automatically by the giving class
     *
     * @param dataList the data list of csv file
     * @param clazz    the actual class of <b>dataList</b>
     * @param response where to write
     *
     * @throws IOException io exception is occurred
     *
     * @see #write(List, String, String[], String[], HttpServletResponse) )
     * @see #write(List, Class, String, String[], String[], HttpServletResponse)
     * @see #write(List, HttpServletResponse)
     * @see #write(List, Class, HttpServletResponse)
     * @see #write(List, Class, ImportExportStrategy, HttpServletResponse)
     */
    static void write(List dataList, Class clazz, HttpServletResponse response) throws IOException {

        String fileName = getFileNameWithFileExtension(clazz);
        String[] headers = getHeaders(clazz);
        String[] columns = getColumns(clazz);

        write(dataList, clazz, fileName, headers, columns, response);
    }

    /**
     * @throws DataNotExistedException when dataList is null or empty
     *
     * @see #write(List, String, String[], String[], HttpServletResponse) )
     * @see #write(List, Class, String, String[], String[], HttpServletResponse)
     * @see #write(List, Class, HttpServletResponse)
     * @see #write(List, Class, HttpServletResponse)
     * @see #write(List, Class, ImportExportStrategy, HttpServletResponse)
     */
    static void write(List dataList, HttpServletResponse response) throws IOException {

        write(dataList, (Class)null, response);
    }

    static void validate(List dataList) {
        if (null == dataList || dataList.isEmpty()) {
            throw new DataNotExistedException();
        }
    }

    /**
     * Find headers one by one, and return when it be found first.
     * <p>
     * class {@link ImportExport#headers()} -> which be annotated with {@link ImportExport#value()} ->
     * which be annotated with {@link ApiModelProperty#value()} or else all declare field name
     *
     * @param clazz the class of object which is waiting to export
     * @return the headers
     */
    static String[] getHeaders(Class clazz) {

        List<String> headerList = Lists.newArrayList();

        if (clazz.isAnnotationPresent(ImportExport.class)) {
            ImportExport importExport = (ImportExport) clazz.getAnnotation(ImportExport.class);
            String[] headers = importExport.headers();
            if (0 != headers.length) {
                headerList.addAll(Arrays.asList(headers));
            }
        } else {
            if (hasFieldAnnotation(clazz, ImportExport.class)) {
                List<Field> fieldList = getFieldsByAnnotation(clazz, ImportExport.class);
                for (Field field : fieldList) {
                    ImportExport importExport = field.getAnnotation(ImportExport.class);
                    String header = importExport.value();
                    if (header.isEmpty() && field.isAnnotationPresent(ApiModelProperty.class)) {
                        ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                        header = apiModelProperty.value();
                    }
                    if (header.isEmpty()) {
                        header = field.getName();
                    }
                    headerList.add(header);
                }
            } else if (hasFieldAnnotation(clazz, ApiModelProperty.class)) {
                List<Field> fieldList = getFieldsByAnnotation(clazz, ApiModelProperty.class);
                for (Field field : fieldList) {
                    ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                    String header = apiModelProperty.value();
                    if (header.isEmpty()) {
                        header = field.getName();
                    }
                    headerList.add(header);
                }
            } else {
                headerList.addAll(Arrays.asList(getColumns(clazz)));
            }
        }

        return headerList.toArray(new String[]{});
    }

    /**
     * Return headers by the giving columns. Collecting header one by one and avoid column which can not be found
     *
     * @param clazz the class of object which is waiting to export
     * @return the headers of columns
     */
    static String[] getHeadersByColumns(String[] columns, Class clazz) {

        List<String> headerList = Lists.newArrayList();

        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            try {
                String header = "";
                Field field = clazz.getDeclaredField(column);
                if (field.isAnnotationPresent(ApiModelProperty.class)) {
                    ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                    header = apiModelProperty.value();
                }
                if (header.isEmpty()) {
                    header = field.getName();
                }
                headerList.add(header);
            } catch (NoSuchFieldException e) {
                // ignored
                System.out.println("column = " + column);
                columns[i] = null;
            }
        }
        ;

        return headerList.toArray(new String[0]);
    }

    /**
     * Return columns by the giving class. Collecting columns one by one, and return when it be found first.
     * <p>
     * class {@link ImportExport#columns()} -> field names which be annotated with {@link ImportExport} ->
     * field names which be annotated with {@link ApiModelProperty} or else all declare field name
     *
     * @param clazz the class of object which is waiting to export
     * @return the columns
     */
    static String[] getColumns(Class clazz) {

        List<String> columnList = Lists.newArrayList();

        List<Field> fieldList = Lists.newArrayList();

        if (clazz.isAnnotationPresent(ImportExport.class)) {
            ImportExport importExport = (ImportExport) clazz.getAnnotation(ImportExport.class);
            String[] columns = importExport.columns();
            if (0 != columns.length) {
                columnList.addAll(Arrays.asList(columns));
            }
        } else {
            if (hasFieldAnnotation(clazz, ImportExport.class)) {
                fieldList = getFieldsByAnnotation(clazz, ImportExport.class);
            } else if (hasFieldAnnotation(clazz, ApiModelProperty.class)) {
                fieldList = getFieldsByAnnotation(clazz, ApiModelProperty.class);

            } else {
                Field[] fields = clazz.getDeclaredFields();
                fieldList.addAll(Arrays.asList(fields));
            }

            List<Field> ignoreFieldList = getFieldsByAnnotation(clazz, Ignore.class);
            fieldList.removeAll(ignoreFieldList);

            for (Field field : fieldList) {
                columnList.add(field.getName());
            }
        }

        return columnList.toArray(new String[]{});
    }

    /**
     * Return file name by the giving class. Collecting fileName one by one, and return when it be found first.
     * <p>
     * {@link ImportExport#value()} -> {@link ApiModel#value()} -> {@link Class#getSimpleName()}
     *
     * @param clazz the class of object which is waiting to export
     * @return the name of exporting file
     */
    static String getFileName(Class clazz) {

        String fileName = "";

        if (clazz.isAnnotationPresent(ImportExport.class)) {
            ImportExport importExport = (ImportExport) clazz.getAnnotation(ImportExport.class);
            fileName = importExport.value();
        }

        if (fileName.isEmpty() && clazz.isAnnotationPresent(ApiModel.class)) {
            ApiModel apiModel = (ApiModel) clazz.getAnnotation(ApiModel.class);
            fileName = apiModel.value();
        }

        if (fileName.isEmpty()) {
            fileName = clazz.getSimpleName();
        }

        return fileName;
    }

    /**
     * Return file name and it must have file extension.
     */
    static String getFileNameWithFileExtension(Class clazz) {

        return appendFileExtensionIfNotPresent(getFileName(clazz));
    }

    /**
     * set the content type or header or other be with the giving {@link HttpServletResponse}
     * set the exporting file name.
     *
     * @param response
     * @param fileName
     */
    static void prepareResponse(HttpServletResponse response, String fileName) {

//        fileName = new String(fileName.getBytes(), UTF_8);
//        response.setLocale(Locale.CHINA);
        response.setContentType("text/csv");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            // ignored
        }

    }

    static <T> List<T> read(Class<T> clazz, String[] columns, InputStream inputStream) throws IOException {

        ICsvBeanReader csvBeanReader = null;
        try {
            csvBeanReader = new CsvBeanReader(new InputStreamReader(inputStream, GB2312), CsvPreference.STANDARD_PREFERENCE);

            // skip first header line
            String[] headers = csvBeanReader.getHeader(true);

            if (Objects.isNull(columns) || 0 == columns.length) {
                columns = getColumns(clazz);
            }

            List<T> dataList = new ArrayList<>();

            T data;
            while ((data = csvBeanReader.read(clazz, columns, getCellProcessors(clazz, columns, false))) != null) {
                dataList.add(data);
            }

            return dataList;
        } finally {
            if (Objects.nonNull(csvBeanReader)) {
                csvBeanReader.close();
            }
        }
    }

    static <T> CellProcessor[] getCellProcessors(Class<T> clazz, String[] columns, boolean write) {

        List<CellProcessor> cellProcessors = Lists.newArrayList();

        List<Field> fieldList = Lists.newArrayList();
        for(String column : columns) {
            try {
                fieldList.add(clazz.getDeclaredField(column));
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException();
            }
        }

        for (Field field : fieldList) {
            cellProcessors.add(getCellProcessor(field, write));
        }

        return cellProcessors.toArray(new CellProcessor[]{});
    }

    static <T> CellProcessor[] getCellProcessors(Class<T> clazz, boolean write) {

        return getCellProcessors(clazz, getColumns(clazz), write);
    }

    static Map<Class<?>, CellProcessor> getCellProcessorMap(boolean write) {
        Map<Class<?>, CellProcessor> cellProcessorMap = Maps.newHashMap();
        cellProcessorMap.put(String.class, new Optional());
        cellProcessorMap.put(Integer.class, new Optional(new ParseInt()));
        cellProcessorMap.put(Long.class, new Optional(new ParseLong()));
        cellProcessorMap.put(Boolean.class, new Optional(new ParseBool()));
        if (write) {
            cellProcessorMap.put(Date.class, new Optional(new FmtDate("yyyy-MM-dd HH:mm:ss")));
        } else {
            cellProcessorMap.put(Date.class, new Optional(new ParseDate("yyyy-MM-dd HH:mm:ss")));
        }
        // according to your specific model
//        cellProcessorMap.put(GenderEnum.class, new Optional(new ParseEnum(GenderEnum.class)));
//        cellProcessorMap.put(BodyPartEnum.class, new Optional(new ParseEnum(BodyPartEnum.class)));
//        cellProcessorMap.put(BodySubPartEnum.class, new Optional(new ParseEnum(BodySubPartEnum.class)));
//        cellProcessorMap.put(ActionEnum.class, new Optional(new ParseEnum(ActionEnum.class)));
//        cellProcessorMap.put(SymptomLevelEnum.class, new Optional(new ParseEnum(SymptomLevelEnum.class)));
//        cellProcessorMap.put(StatusEnum.class, new Optional(new ParseEnum(StatusEnum.class)));

        return cellProcessorMap;
    }

    static CellProcessor getCellProcessor(Field field, boolean write) {
        return getCellProcessorMap(write).get(field.getType());
    }

}
