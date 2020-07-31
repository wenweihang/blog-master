package com.zoe.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码的加密和密码判断是否相等
 * Created by liuxuelian on 2020/07/27 16:02
 */
@Component
public class RestBCryptPasswordEncoder {
    /**
     * 加密
     * @param rawPassword
     * @return
     */
    public String passwrodEncoder(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(rawPassword);
    }

    /**
     * 判断密码是否相等
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    public Boolean passwordMatch(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }
}
