package com.example.utils;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.utils.ReflectionUtils.isPrimitiveOrPrimitiveClass;
import static com.example.utils.SwaggerUtils.ParameterTypeEnum.QUERY;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public interface SwaggerUtils {

    enum ParameterTypeEnum {
        QUERY,
        PATH,
        BODY;
    }

    static <T> List<Parameter> buildParameters(Class<T> clazz) {

        List<Parameter> parameterList = Lists.newArrayList();

        Field[] fields = clazz.getDeclaredFields();

        AtomicInteger counter = new AtomicInteger(Ordered.LOWEST_PRECEDENCE);
        for (Field field : fields) {
            Class fieldType = field.getType();
            int modifier = field.getModifiers();

            if (!isPrimitiveOrPrimitiveClass(field)
                    || (Modifier.isStatic(modifier) && Modifier.isFinal(modifier))) {
                continue;
            }

            String name = field.getName();
            String description = "";

            if (field.isAnnotationPresent(ApiModelProperty.class)) {
                ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                String apiModelPropertyName = apiModelProperty.name();
                if (!StringUtils.isEmpty(apiModelPropertyName)) {
                    name = apiModelPropertyName;
                }
                description = apiModelProperty.value();
            }
            String modelRef = fieldType.getName();
            String parameterType = QUERY.name();

            ParameterBuilder parameterBuilder = new ParameterBuilder();

            parameterBuilder.name(name)
                    .description(description)
                    .modelRef(new ModelRef(modelRef))
                    .parameterType(parameterType)
                    .order(counter.getAndDecrement());

            parameterList.add(parameterBuilder.build());

//                if (Objects.nonNull(value)) {
//                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
//                    Method writeMethod = descriptor.getWriteMethod();
//                    writeMethod.invoke(obj, convert(fieldType, value));
//                }
        }

        return parameterList;
    }
}
