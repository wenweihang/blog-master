package com.zoe.blog.dao;

import com.zoe.blog.model.LoginUser;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by liuxuelian on 2020/07/24 22:31
 */
@Repository
public interface LoginUserDao {
    LoginUser findByUsername(String username);

    void updateLastLoginTime(String username, Long lastLoginTime);

}
