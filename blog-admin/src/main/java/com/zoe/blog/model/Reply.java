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
public class Reply implements Serializable {
    private Integer id;

    @NotNull(message = "回复的内容不能为空")
    @Length(max = 500, message = "回复的内容长度不能超过500个字符")
    private String content;

    private Boolean enabled;

    private Long createTime;

    private Integer commentId;


}
