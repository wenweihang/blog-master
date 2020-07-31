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
 * Created by liuxuelian on 2020/07/24 18:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {
    private Integer id;

    @NotNull(message = "文章标题不能为空")
    @Length(max = 100, message = "文章标题不能超过100个字符")
    private String title;

    @NotNull(message = "文章内容不能为空")
    private String content;

    private Boolean status;

    private Integer views; // 文章访问量

    private Boolean allowComment; // 是否允许评论：true允许 false禁止

    private Long createTime;

    private Long lastUpdateTime;

    private Integer sort; // 文章显示顺序

    private String name; // 分类名称（冗余）
}
