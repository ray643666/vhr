package org.javaboy.vhr.exception;

import org.javaboy.vhr.model.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @作者 江南一点雨
 * @公众号 江南一点雨
 * @微信号 a_java_boy
 * @GitHub https://github.com/lenve
 * @博客 http://wangsong.blog.csdn.net
 * @网站 http://www.javaboy.org
 * @时间 2019-10-01 16:53
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联数据，操作失败!");
        }
        return RespBean.error("数据库异常，操作失败!");
    }

    /**
     * 处理参数校验异常（@Valid + BindingResult）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespBean handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        logger.warn("参数校验失败：{}", errorMsg);
        return RespBean.error(errorMsg);
    }

    /**
     * 处理Spring Security权限不足异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RespBean handleAccessDeniedException(AccessDeniedException e) {
        logger.warn("权限不足：{}", e.getMessage());
        return RespBean.error("权限不足，无法访问该资源");
    }

    /**
     * 系统自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public RespBean handleBusinessException(Exception e) {
        logger.warn("系统自定义业务异常：", e);
        return RespBean.error("系统异常，请联系管理员");
    }


    /**
     * 处理其他未捕获的通用异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean handleException(Exception e) {
        logger.error("系统异常：", e);
        return RespBean.error("系统异常，请联系管理员");
    }
}