package org.javaboy.vhr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 动态配置类
 * 使用 @RefreshScope 支持配置热更新
 * 使用 @ConfigurationProperties 批量绑定配置，避免 @Value 散落在各处
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "vhr")
public class DynamicConfig {

    /**
     * 文件上传配置
     */
    private FileConfig file = new FileConfig();

    /**
     * 员工导入配置
     */
    private ImportConfig employee = new ImportConfig();

    /**
     * 短信服务配置
     */
    private SmsConfig sms = new SmsConfig();

    /**
     * 系统开关配置
     */
    private SwitchConfig switches = new SwitchConfig();

    @Data
    public static class FileConfig {
        private String uploadPath = "./uploads";
        private Long maxSize = 10485760L;  // 10MB
        private List<String> allowedExtensions = Arrays.asList("jpg", "png", "pdf");
    }

    @Data
    public static class ImportConfig {
        private Boolean enabled = false;
        private Integer maxSize = 5000;
        private String batchSize = "500";
    }

    @Data
    public static class SmsConfig {
        private String apiUrl;
        private String appKey;
        private String appSecret;
        private Integer timeout = 30000;
    }

    @Data
    public static class SwitchConfig {
        private Boolean auditEnabled = false;
        private Boolean emailNotification = true;
        private String logLevel = "INFO";
    }
}
