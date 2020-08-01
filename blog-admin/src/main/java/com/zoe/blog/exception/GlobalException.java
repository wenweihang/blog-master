package com.zoe.blog.exception;

import com.zoe.blog.controller.admin.ArticleController;
import com.zoe.blog.utils.CommonResult;
import com.zoe.blog.utils.LogUtil;
import org.apache.log4j.Logger;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * 全局异常统一处理
 * Created by liuxuelian on 2020/07/24 02:49
 */
@RestControllerAdvice
public class GlobalException {

    private static final Logger LOGGER =  Logger.getLogger(ArticleController.class);

    /**
     * 处理带有@Valid注解的参数校验产生的错误
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        LogUtil.create();
        LOGGER.error("@Valid校验异常: " + e.getBindingResult().getFieldError().getDefaultMessage());

        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            return CommonResult.failed(400, bindingResult.getFieldError().getDefaultMessage());
        }
        return CommonResult.failed(500, "未知错误，请联系管理员");
    }

    /**
     * 参数格式错误
     * @param e
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public CommonResult HttpMessageNotReadableHandler(HttpMessageNotReadableException e) {
        LogUtil.create();
        LOGGER.error("参数解析异常: " + e.getHttpInputMessage());
        return CommonResult.validateFailed("参数无法正常解析");
    }


    /**
     * 403: 没有权限
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public CommonResult accessDeniedException(AccessDeniedException e) {
        LogUtil.create();
        LOGGER.warn("没有权限异常: " + e.getMessage());

        return CommonResult.forbidden();
    }

    /**
     * 自定义异常
     * @param e
     */
    @ExceptionHandler(value = RestApiException.class)
    public CommonResult restApiException(RestApiException e) {
        LogUtil.create();
        LOGGER.error("自定义异常: " + e.getMessage());
        return CommonResult.failed(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResult exception(Exception e) {
        LogUtil.create();
        LOGGER.error("其他异常: " + e.getMessage());
        return CommonResult.failed(500, e.getMessage());
    }
}
