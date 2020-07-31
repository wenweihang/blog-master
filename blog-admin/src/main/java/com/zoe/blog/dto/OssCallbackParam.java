package com.zoe.blog.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * oss上传成功后的回调参数
 * Created by liuxuelian on 2020/07/24 00:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OssCallbackParam {
    @ApiModelProperty("请求的回调地址")
    private String callbackUrl;

    @ApiModelProperty("回调是传入request中的参数")
    private String callbackBody;

    @ApiModelProperty("回调时传入参数的格式，比如表单提交形式")
    private String callbackBodyType;
}
