package com.zoe.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoe.blog.utils.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义返回结果：未登录或登录过期
 * @author liuxuelian
 * @date 2020-07-18 15:55
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        System.out.println("没有登录或登录过期");
        response.getWriter().println(new ObjectMapper().writeValueAsString(CommonResult.unauthorized()));
        response.getWriter().flush();
    }
}
