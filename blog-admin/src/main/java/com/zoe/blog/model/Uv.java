package com.zoe.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuxuelian on 2020/07/30 17:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Uv implements Serializable {
    private String date;
    private Date dateTime;
    private Integer uv;
    private Integer comments;
}
