package com.drzhao.portal.infrastructure.exception;

import com.drzhao.portal.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace(); // 在控制台打印错误栈，方便调试
        return Result.error(e.getMessage() != null ? e.getMessage() : "服务器内部错误");
    }
}