package com.example.utils.exception;

import com.example.utils.Result;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import java.util.List;

import static com.example.utils.Result.fail;
import static com.example.utils.constant.enums.ExceptionEnum.*;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApplicationContext applicationContext;

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error("服务端异常:", e);

        return fail(UNKNOWN);
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public void throwOut(RuntimeException e) {
        log.warn("异步请求超时");
        throw  e;
    }

    @ExceptionHandler(RuntimeException.class)
    public Result exception(RuntimeException e) {

        Result result;

        log.warn("服务端告警:", e);

        if (e instanceof LoginException) {
            result = fail(LOGIN);
        } else if (e instanceof TokenInvalidException) {
            result = fail(TOKEN_INVALID);
        } else if (e instanceof AccountExistedException) {
            result = fail(ACCOUNT_EXISTED);
        }else if (e instanceof DataNotExistedException) {
            result = fail(DATA_NOT_EXISTED);
        } else if (e instanceof AccountNotExistedException) {
            result = fail(ACCOUNT_NOT_EXISTED);
        } else {
            if (allowExceptionDetailMessage()) {
                result = fail(e);
            } else {
                result = fail(UNKNOWN);
            }
        }

        return result;
    }

    private boolean allowExceptionDetailMessage() {

        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        List<String> activeProfileList = Lists.newArrayList(activeProfiles);
        List<String> profiles = Lists.newArrayList("default", "local", "dev");
        activeProfileList.retainAll(profiles);

        return !activeProfileList.isEmpty();
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class,
            IllegalArgumentException.class, MissingServletRequestParameterException.class})
    public Result argumentBindException(Exception e) {
        log.warn("服务端告警:", e);

        return fail(ARGUMENT_INVALID);
    }
}
