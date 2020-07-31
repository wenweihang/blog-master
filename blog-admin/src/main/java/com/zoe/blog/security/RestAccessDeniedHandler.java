package com.zoe.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoe.blog.utils.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义返回结果：没有访问权限时返回
 * @author liuxuelian
 * @date 2020-07-18 15:59
 */
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        System.out.println("没有权限");
        response.getWriter().println(new ObjectMapper().writeValueAsString(CommonResult.forbidden()));
        response.getWriter().flush();
    }
}
