package com.zoe.blog.dao;

import com.zoe.blog.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 02:09
 */
@Repository
public interface RoleDao {

    List<Role> getList(String roleName);

    List<Role> search(String roleName);

    Role findByRoleId(Integer id);

    Role findByRoleName(String roleName);

    int insert(Role role);

    int update(Role role);

    int delete(Integer id);
}
