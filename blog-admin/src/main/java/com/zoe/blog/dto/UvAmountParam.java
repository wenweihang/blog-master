package com.zoe.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by liuxuelian on 2020/07/31 12:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UvAmountParam implements Serializable {
    private Integer uv;
    private Integer comments;
}
