package com.zoe.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by liuxuelian on 2020/07/28 11:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {

    private Integer id;

    @NotNull(message = "评论内容不能为空")
    @Length(max = 500, message = "评论内容的长度不能超过500个字符")
    private String content;

    private Long createTime;

    private Boolean enabled;

    @NotNull(message = "评论的访客昵称不能为空")
    @Length(min = 2, max = 10, message = "评论访客的名称的长度要在2到10之间")
    private String visitorNickname;

    @NotNull(message = "评论的访客的邮箱不能为空")
    @Length(max = 30, message = "评论的访客的邮箱的长度不能超过30个字符")
    private String visitorEmail;

    private Integer articleId;

    private Article article;

    private Reply reply;


}
