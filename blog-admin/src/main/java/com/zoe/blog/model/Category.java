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
 * Created by liuxuelian on 2020/07/24 19:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    private Integer id;

    @NotNull(message = "分类名称不能为空")
    @Length(min = 2, max = 20, message = "分类名称的长度要在2到20个字符之间")
    private String name;

    private Integer sort;

    private Long createTime;

    private Boolean enabled;

}
