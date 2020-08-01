package com.zoe.blog.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoe.blog.exception.RestApiException;
import org.apache.log4j.MDC;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义写入数据库参数
 * @author liuxuelian
 * @date 2020-07-19 12:43
 */
public class LogUtil {

    public static void create(Object... args) {
        create();

        // 获取方法的参数名和值
        Map<String, Object> paramNameAndValues = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                paramNameAndValues.put("arg" + i, args[i]);
            }
        }
        try {
            MDC.put("params", new ObjectMapper().writeValueAsString(paramNameAndValues));
        } catch (JsonProcessingException e) {
            throw new RestApiException(400, "解析参数异常: ".concat(e.getMessage()));
        }

    }

    public static void create() {
        MDC.put("username", IPUtil.getUsername());
        MDC.put("ip", IPUtil.getIp());
        MDC.put("nowTime", new Date().getTime());
    }
}
