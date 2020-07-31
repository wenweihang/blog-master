package com.zoe.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by liuxuelian on 2020/07/29 12:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebCategoryDto implements Serializable {
    private Integer id;
    private String name;
    private Integer count; // 分类文章总数
}
