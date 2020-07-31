package com.zoe.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文章根据月份进行归档
 * Created by liuxuelian on 2020/07/29 11:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebArchiveDto implements Serializable {
    private Integer count;
    private String archiveTime; // '2020-01' 格式
}
