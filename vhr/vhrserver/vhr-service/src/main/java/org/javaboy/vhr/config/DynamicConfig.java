package org.javaboy.vhr.config;

import com.alibaba.druid.util.StringUtils;
import lombok.Data;
import org.javaboy.vhr.constants.ConfigConst;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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
     * 配置项Map集合，key为配置项Key，value为对应的Supplier
     */
    private Map<String, Supplier<String>> configMap = new HashMap<>();

    /**
     * 初始化配置项Map集合, 将配置项Key与对应的Supplier绑定
     */
    @PostConstruct
    public void init() {
        configMap.put(ConfigConst.KEY_FILE_UPLOAD_PATH, () -> file.getUploadPath());
        configMap.put(ConfigConst.KEY_FILE_MAX_SIZE, () -> file.getMaxSize().toString());
        configMap.put(ConfigConst.KEY_FILE_ALLOWED_EXTENSIONS, () -> String.join(",", file.getAllowedExtensions()));
        configMap.put(ConfigConst.KEY_EMPLOYEE_IMPORT_ENABLED, () -> employee.getEnabled().toString());
        configMap.put(ConfigConst.KEY_EMPLOYEE_IMPORT_MAX_SIZE, () -> employee.getMaxSize().toString());
        configMap.put(ConfigConst.KEY_EMPLOYEE_IMPORT_BATCH_SIZE, () -> employee.getBatchSize());
        configMap.put(ConfigConst.KEY_SMS_API_URL, () -> sms.getApiUrl());
        configMap.put(ConfigConst.KEY_SMS_APP_KEY, () -> sms.getAppKey());
        configMap.put(ConfigConst.KEY_SMS_APP_SECRET, () -> sms.getAppSecret());
        configMap.put(ConfigConst.KEY_SMS_TIMEOUT, () -> sms.getTimeout().toString());
        configMap.put(ConfigConst.KEY_SWITCH_AUDIT, () -> switches.getAuditEnabled().toString());
        configMap.put(ConfigConst.KEY_SWITCH_EMAIL_NOTIFICATION, () -> switches.getEmailNotification().toString());
        configMap.put(ConfigConst.KEY_SWITCH_LOG_LEVEL, () -> switches.getLogLevel());

    }

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

    /**
     * 根据配置Key获取配置值
     */
    public String getConfigValue(String key) {
        Supplier<String> supplier = configMap.get(key);
        return supplier != null ? supplier.get() : null;
    }

    /**
     * 获取所有配置项
     */
    public Map<String, String> getAllConfig() {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, Supplier<String>> entry : configMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().get());
        }
        return map;
    }
}
