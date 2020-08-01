package com.zoe.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuxuelian on 2020/08/01 11:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements Serializable {
    private Integer id;
    private String username;
    private String ip;
    private String params;
    private Date createDate;
    private String level;
    private String thread;
    private String location;
    private String message;
}
