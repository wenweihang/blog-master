package com.zoe.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by liuxuelian on 2020/07/27 22:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleParam implements Serializable {
    @NotNull(message = "分配角色的用户id不能为空")
    private Integer userId;
    @NotNull(message = "分配角色的名称不能为空")
    private String roleNames;
}
