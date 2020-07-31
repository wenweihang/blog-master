package com.zoe.blog.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举常用的API操作码
 * Created by liuxuelian on 2020/07/24 00:49
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经失效"),
    FORBIDDEN(403, "没有相关权限");

    private int code;
    private String msg;
}
