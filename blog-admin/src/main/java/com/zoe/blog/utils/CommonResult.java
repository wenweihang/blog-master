package com.zoe.blog.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回对象
 * Created by liuxuelian on 2020/07/24 00:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 成功返回结果
     * @param data
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回结果
     * @param msg
     * @param data
     */
    public static <T> CommonResult<T> success(String msg, T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 失败返回结果
     * @param errorCode
     */
    public static <T> CommonResult<T> failed(ResultCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMsg(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode
     * @param msg
     */
    public static <T> CommonResult<T> failed(ResultCode errorCode, String msg) {
        return new CommonResult<>(errorCode.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     * @param code
     * @param msg
     */
    public static <T> CommonResult<T> failed(int code, String msg) {
        return new CommonResult<>(code, msg, null);
    }

    /**
     * 失败返回结果
     * @param msg
     */
    public static <T> CommonResult<T> failed(String msg) {
        return failed(ResultCode.FAILED, msg);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param msg
     */
    public static <T> CommonResult<T> validateFailed(String msg) {
        return failed(ResultCode.VALIDATE_FAILED, msg);
    }

    /**
     * 未登录或token失效返回结果
     */
    public static <T> CommonResult<T> unauthorized() {
        return failed(ResultCode.UNAUTHORIZED);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden() {
        return failed(ResultCode.FORBIDDEN);
    }
}
