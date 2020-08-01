package com.zoe.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuxuelian on 2020/08/01 14:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogQueryParam implements Serializable {
    private String level;
    private String date; // 格式: "2020-07-31"
    private Integer pageNum;
    private Integer pageSize;
}
