package com.zoe.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by liuxuelian on 2020/07/24 02:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRef implements Serializable {
    private Integer id;

    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @NotNull(message = "角色id不能为空")
    private Integer roleId;
}
