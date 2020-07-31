package com.zoe.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by liuxuelian on 2020/07/27 22:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignCategoryParam implements Serializable {
    @NotNull(message = "分配分类的文章id不能为空")
    private Integer articleId;
    @NotNull(message = "分配分类的名称不能为空")
    private String categoryName;
}
