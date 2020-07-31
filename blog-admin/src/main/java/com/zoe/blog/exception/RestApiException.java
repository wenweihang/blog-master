package com.zoe.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liuxuelian on 2020/07/31 15:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestApiException extends RuntimeException {
    private Integer code;
    private String message;

    public RestApiException(String message) {
        super(message);
    }
}
