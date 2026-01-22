package com.drzhao.portal.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code; // 200成功, 500失败
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.code = 200;
        r.message = "Success";
        r.data = data;
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.code = 500;
        r.message = msg;
        return r;
    }
}