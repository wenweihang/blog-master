package com.zoe.blog.dao;

import com.zoe.blog.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 11:08
 */
@Repository
public interface UserDao {

    List<User> getList(String username);

    List<User> search(String username);

    User findByUserId(Integer id);

    int insert(User user);

    int update(User user);

    int modifyPassword(Integer id, String newPassword);

    int delete(Integer id);

}
