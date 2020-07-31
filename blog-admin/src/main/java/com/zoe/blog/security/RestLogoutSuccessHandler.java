package com.zoe.blog.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoe.blog.utils.CommonResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义返回结果：注销登录时
 * @author liuxuelian
 * @date 2020-07-18 16:03
 */
public class RestLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        System.out.println("注销登录");
        response.getWriter().println(new ObjectMapper().writeValueAsString(CommonResult.success(1)));
        response.getWriter().flush();
    }
}
