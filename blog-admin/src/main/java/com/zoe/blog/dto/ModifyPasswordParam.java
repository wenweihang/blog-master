package com.zoe.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by liuxuelian on 2020/07/27 19:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPasswordParam implements Serializable {
    @NotNull(message = "用户id不能为空")
    private Integer id;

    @NotNull(message = "原密码不能为空")
    @Length(min = 2, max = 20, message = "原密码长度要在2到20之间")
    private String rawPassword;

    @NotNull(message = "新密码不能为空")
    @Length(min = 2, max = 20, message = "新密码长度要在2到20之间")
    private String newPassword;
}
