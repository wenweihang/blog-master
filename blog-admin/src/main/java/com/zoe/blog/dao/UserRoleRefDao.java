package com.zoe.blog.dao;

import com.zoe.blog.model.Role;
import com.zoe.blog.model.UserRoleRef;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 11:43
 */
@Repository
public interface UserRoleRefDao {

    Role getRoleByUserId(Integer userId);

    int insert(UserRoleRef roleRef);

    void deleteByUserId(@Param("userId") Integer userId);

    void deleteByRoleId(@Param("roleId") Integer roleId);
}
