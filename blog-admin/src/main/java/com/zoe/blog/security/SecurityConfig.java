package com.zoe.blog.security;

import com.zoe.blog.service.LoginUserService;
import com.zoe.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginUserService userService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**",
//                "/fonts/**", "/favicon.ico");
        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources", //用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/swagger-ui.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 关闭跨站请求访问
        http.csrf().disable().cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/article/**").permitAll()
                .antMatchers("/web/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/**").permitAll()
                // swagger2
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/druid/**").permitAll()
                .anyRequest().authenticated()

                // 登录和访问验证
                .and()
                .addFilterBefore(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFliter(), UsernamePasswordAuthenticationFilter.class)

                // 自定义权限拒绝
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint())
//                .accessDeniedHandler(restAccessDeniedHandler())

                // 注销登录~
                .and()
                .logout()
                .logoutSuccessHandler(restLogoutSuccessHandler())
                .deleteCookies("token");
    }

    @Bean
    public JWTAuthenticationFliter jwtAuthenticationFliter() {
        return new JWTAuthenticationFliter();
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        return new LoginAuthenticationFilter("/login", authenticationManager());
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public RestAccessDeniedHandler restAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Bean
    public RestLogoutSuccessHandler restLogoutSuccessHandler() {
        return new RestLogoutSuccessHandler();
    }
}
