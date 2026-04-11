package org.javaboy.vhr.enums;

/**
 * 返回对象状态码
 */
public enum ResultCode {
    // 成功
    SUCCESS(200, "操作成功"),

    // 客户端错误 (4xx)
    BAD_REQUEST(400, "请求参数有误"),
    UNAUTHORIZED(401, "未授权"),
    NOT_FOUND(404, "请求的资源不存在"),

    // 服务器错误 (5xx)
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_BUSY(503, "服务繁忙，请稍后再试"),

    // 业务自定义错误
    USER_NOT_FOUND(1001, "用户不存在"),
    INVALID_PASSWORD(1002, "密码错误");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
