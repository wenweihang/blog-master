package com.zoe.blog.service;

import com.zoe.blog.dao.LoginUserDao;
import com.zoe.blog.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by liuxuelian on 2020/07/24 22:38
 */
@Service
public class LoginUserService implements UserDetailsService {

    @Autowired
    LoginUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
