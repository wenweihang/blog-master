package com.zoe.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by liuxuelian on 2020/07/28 15:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEnabledParam implements Serializable {
    @NotNull(message = "评论id不能为空")
    private Integer id;

    @NotNull(message = "评论是否显示不能为空")
    private Boolean enabled;
}
