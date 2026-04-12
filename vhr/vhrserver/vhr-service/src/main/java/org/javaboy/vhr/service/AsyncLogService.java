package org.javaboy.vhr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 异步日志服务
 * 所有方法都使用 @Async 异步执行，不阻塞主业务
 */
@Service
public class AsyncLogService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncLogService.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 异步记录操作日志
     *
     * @param username 操作人
     * @param operation 操作描述
     * @param request HTTP请求（可为null）
     */
    @Async("logExecutor")
    public void recordOperation(String username, String operation, HttpServletRequest request) {
        try {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("【操作日志】");
            logBuilder.append(" 时间: ").append(LocalDateTime.now().format(FORMATTER));
            logBuilder.append(" | 操作人: ").append(username);
            logBuilder.append(" | 操作: ").append(operation);

            if (request != null) {
                String ip = getIpAddress(request);
                String url = request.getRequestURI();
                String method = request.getMethod();
                logBuilder.append(" | IP: ").append(ip);
                logBuilder.append(" | 请求: ").append(method).append(" ").append(url);
            }

            logger.info("当前线程: {}", Thread.currentThread().getName());
            // 输出到日志文件
            logger.info(logBuilder.toString());

        } catch (Exception e) {
            // 日志记录失败不影响主业务，只记录错误
            logger.error("异步日志记录失败: {}", e.getMessage());
        }
    }

    /**
     * 异步记录业务日志（带额外参数）
     */
    @Async("logExecutor")
    public void recordBusinessLog(String username, String operation, Object bizData) {
        try {
            String log = String.format("【业务日志】时间: %s | 操作人: %s | 操作: %s | 数据: %s",
                    LocalDateTime.now().format(FORMATTER),
                    username,
                    operation,
                    bizData != null ? bizData.toString() : "null");
            logger.info(log);
        } catch (Exception e) {
            logger.error("业务日志记录失败: {}", e.getMessage());
        }
    }

    /**
     * 异步记录异常日志
     */
    @Async("logExecutor")
    public void recordExceptionLog(String username, String operation, Exception e) {
        try {
            String log = String.format("【异常日志】时间: %s | 操作人: %s | 操作: %s | 异常: %s",
                    LocalDateTime.now().format(FORMATTER),
                    username,
                    operation,
                    e.getMessage());
            logger.error(log, e);
        } catch (Exception ex) {
            // 静默失败
        }
    }

    /**
     * 获取客户端真实IP
     */
    private String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个代理的情况，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}
