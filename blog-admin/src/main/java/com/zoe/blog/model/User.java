package com.zoe.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuxuelian on 2020/07/24 02:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Integer id;

    @NotNull(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户名的长度要在2到20之间")
    private String username;

    @NotNull(message = "密码不能为空")
    @Length(max = 80, message = "密码的长度不能超过80个字符")
    private String password;

    @Length(max = 10, message = "昵称的长度不能超过10个字符")
    private String nickname;

    @Length(max = 30, message = "邮箱的长度不能超过30个字符")
    private String email;

    @Length(max = 200, message = "简介的长度不能超过200个字符")
    private String intro;

    @Length(max = 200, message = "头像的长度不能超过200个字符")
    private String avatar;

    private Boolean enabled;

    private Long lastLoginTime;

    private Long createTime;
}
