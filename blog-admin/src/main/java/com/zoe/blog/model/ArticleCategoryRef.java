package com.zoe.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by liuxuelian on 2020/07/24 20:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCategoryRef implements Serializable {
    private Integer id;

    @NotNull(message = "文章id不能为空")
    private Integer articleId;

    @NotNull(message = "分类id不能为空")
    private Integer categoryId;
}
