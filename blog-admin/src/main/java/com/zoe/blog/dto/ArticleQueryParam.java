package com.zoe.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by liuxuelian on 2020/07/31 13:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQueryParam implements Serializable {
    private String title;
    private String viewsOrder;
    private Integer pageNum;
    private Integer pageSize;
}
