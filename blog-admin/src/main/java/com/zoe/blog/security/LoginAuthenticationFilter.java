package com.zoe.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoe.blog.dao.LoginUserDao;
import com.zoe.blog.model.LoginUser;
import com.zoe.blog.model.Role;
import com.zoe.blog.utils.CommonResult;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    LoginUserDao userDao;

    protected LoginAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        LoginUser user = new ObjectMapper().readValue(httpServletRequest.getInputStream(), LoginUser.class);

        return getAuthenticationManager().authenticate
                (new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        // 获取用户信息
        LoginUser user = (LoginUser) authResult.getPrincipal();
        // 修改最后登录时间
        userDao.updateLastLoginTime(user.getId(), new Date().getTime());
        // 获取权限
        List<Role> roles = user.getRoles();
        List<String> newRoles = new ArrayList<>();
        for (Role role : roles) {
            newRoles.add(role.getName());
        }

        // 生成Token
        String jwtToken = this.generateToken(authResult);

        Map<String, Object> map = new HashMap<>();
        map.put("token", tokenHead + jwtToken);
        map.put("roles", newRoles);
        map.put("username", user.getUsername());
        map.put("avatar", user.getAvatar());

        String resultJSON = new ObjectMapper().writeValueAsString(CommonResult.success(map));

        response.getWriter().write(resultJSON);
        response.getWriter().flush();
        response.getWriter().close();

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");

        String msg = "";
        if (failed instanceof DisabledException) {
            msg = "账户被禁用，请联系管理员";
        } else if (failed instanceof BadCredentialsException) {
            msg = "用户名或者密码输入错误";
        }

        String resultJSON = new ObjectMapper().writeValueAsString(CommonResult.validateFailed(msg));

        response.getWriter().write(resultJSON);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 生成token
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        StringBuffer sb = new StringBuffer();
        for (SimpleGrantedAuthority authority : authorities) {
            sb.append(authority);
        }

        String jwtToken = Jwts.builder()
                .claim("authorities", sb)
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return jwtToken;
    }
}
