package com.example.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.annotation.*;

/**
 * This annotation is used to annotate a class or a filed which could be import or export data to file and how to.
 * Annotate on the target class means it is import or export model and the value means file name.
 * Annotate on the target filed means it is import or export field and the value means header.
 * if can not find annotation {@link ImportExport}, it will find the value of {@link io.swagger.annotations.ApiModel}
 * as file name, or else it will use the {@link Class#getSimpleName()} as file name.
 * if you annotate on the target class and provide headers they will be used prior, and will not collect the
 * field that annotated with {@link ImportExport}, and columns also. if not provide it will scan fields one by one
 * which annotated with {@link ImportExport} or {@link io.swagger.annotations.ApiModelProperty}
 *
 * @see CsvUtils#getFileName(Class)
 * @see CsvUtils#getFileNameWithFileExtension(Class)
 * @see CsvUtils#getHeaders(Class)
 * @see CsvUtils#getHeadersByColumns(String[], Class)
 * @see CsvUtils#getColumns(Class)
 * @see ImportExport
 * @see ApiModel
 * @see ApiModelProperty
 *
 * @author Kenny Fang
 * @since 0.0.2
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ImportExport {

    /**
     * the header
     */
    String value() default "";

    String[] headers() default {};

    String[] columns() default {};

    /**
     * Whether must not be null
     */
    boolean required() default false;
}
