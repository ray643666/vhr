package org.javaboy.vhr.model;

import org.javaboy.vhr.enums.ResultCode;

/**
 * 统一返回对象Result
 * @param <T>
 */
public class Result<T> {
    private int code;       // 状态码
    private String message; // 提示信息
    private T data;         // 数据内容

    // 私有化构造器，强制使用静态方法创建实例
    private Result() {}

    // ========== 成功响应 ==========
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = success();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data, String message) {
        Result<T> result = success();
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> success(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        return result;
    }

    // ========== 失败响应 ==========
    public static <T> Result<T> fail(ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }

    public static <T> Result<T> fail(ResultCode resultCode, String message) {
        Result<T> result = fail(resultCode);
        result.setMessage(message); // 支持动态覆盖消息
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
