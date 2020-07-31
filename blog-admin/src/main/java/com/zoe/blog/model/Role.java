package com.zoe.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuxuelian on 2020/07/24 01:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private Integer id;

    @NotNull(message = "角色名称不能为空")
    @Length(min = 2, max = 20, message = "角色名称的长度要在2到20之间")
    private String name;

    @Length(max = 50, message = "角色描述不能超过50个字符")
    private String description;

    private Long createTime;
}
