package org.javaboy.vhr.config;

import org.javaboy.vhr.constants.ConfigConst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DynamicConfig 单元测试
 */
@DisplayName("动态配置类测试")
class DynamicConfigTest {

    private DynamicConfig dynamicConfig;

    @BeforeEach
    void setUp() {
        dynamicConfig = new DynamicConfig();
        dynamicConfig.init();
    }

    @Test
    @DisplayName("测试获取文件上传路径配置")
    void testGetFileUploadPath() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_FILE_UPLOAD_PATH);
        assertEquals("./uploads", value);
    }

    @Test
    @DisplayName("测试获取文件最大大小配置")
    void testGetFileMaxSize() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_FILE_MAX_SIZE);
        assertEquals("10485760", value);
    }

    @Test
    @DisplayName("测试获取文件允许扩展名配置")
    void testGetFileAllowedExtensions() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_FILE_ALLOWED_EXTENSIONS);
        assertEquals("jpg,png,pdf", value);
    }

    @Test
    @DisplayName("测试获取员工导入启用状态配置")
    void testGetEmployeeImportEnabled() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_EMPLOYEE_IMPORT_ENABLED);
        assertEquals("false", value);
    }

    @Test
    @DisplayName("测试获取员工导入最大数量配置")
    void testGetEmployeeImportMaxSize() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_EMPLOYEE_IMPORT_MAX_SIZE);
        assertEquals("5000", value);
    }

    @Test
    @DisplayName("测试获取员工导入批次大小配置")
    void testGetEmployeeImportBatchSize() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_EMPLOYEE_IMPORT_BATCH_SIZE);
        assertEquals("500", value);
    }

    @Test
    @DisplayName("测试获取短信API地址配置（默认null）")
    void testGetSmsApiUrl() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_API_URL);
        assertNull(value);
    }

    @Test
    @DisplayName("测试获取短信AppKey配置（默认null）")
    void testGetSmsAppKey() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_APP_KEY);
        assertNull(value);
    }

    @Test
    @DisplayName("测试获取短信AppSecret配置（默认null）")
    void testGetSmsAppSecret() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_APP_SECRET);
        assertNull(value);
    }

    @Test
    @DisplayName("测试获取短信超时时间配置")
    void testGetSmsTimeout() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_TIMEOUT);
        assertEquals("30000", value);
    }

    @Test
    @DisplayName("测试获取审计开关配置")
    void testGetSwitchAudit() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_SWITCH_AUDIT);
        assertEquals("false", value);
    }

    @Test
    @DisplayName("测试获取邮件通知开关配置")
    void testGetSwitchEmailNotification() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_SWITCH_EMAIL_NOTIFICATION);
        assertEquals("true", value);
    }

    @Test
    @DisplayName("测试获取日志级别配置")
    void testGetSwitchLogLevel() {
        String value = dynamicConfig.getConfigValue(ConfigConst.KEY_SWITCH_LOG_LEVEL);
        assertEquals("INFO", value);
    }

    @Test
    @DisplayName("测试获取不存在的配置Key")
    void testGetNonExistentKey() {
        String value = dynamicConfig.getConfigValue("non.existent.key");
        assertNull(value);
    }

    @Test
    @DisplayName("测试获取null Key")
    void testGetNullKey() {
        String value = dynamicConfig.getConfigValue(null);
        assertNull(value);
    }

    @Test
    @DisplayName("测试获取空字符串Key")
    void testGetEmptyKey() {
        String value = dynamicConfig.getConfigValue("");
        assertNull(value);
    }

    @Test
    @DisplayName("测试修改配置后获取最新值")
    void testGetUpdatedConfigValue() {
        DynamicConfig.FileConfig fileConfig = new DynamicConfig.FileConfig();
        fileConfig.setUploadPath("/custom/path");
        fileConfig.setMaxSize(20971520L);
        dynamicConfig.setFile(fileConfig);

        assertEquals("/custom/path", dynamicConfig.getConfigValue(ConfigConst.KEY_FILE_UPLOAD_PATH));
        assertEquals("20971520", dynamicConfig.getConfigValue(ConfigConst.KEY_FILE_MAX_SIZE));
    }

    @Test
    @DisplayName("测试修改短信配置后获取最新值")
    void testGetUpdatedSmsConfig() {
        DynamicConfig.SmsConfig smsConfig = new DynamicConfig.SmsConfig();
        smsConfig.setApiUrl("https://api.sms.com");
        smsConfig.setAppKey("test-key");
        smsConfig.setAppSecret("test-secret");
        smsConfig.setTimeout(60000);
        dynamicConfig.setSms(smsConfig);

        assertEquals("https://api.sms.com", dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_API_URL));
        assertEquals("test-key", dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_APP_KEY));
        assertEquals("test-secret", dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_APP_SECRET));
        assertEquals("60000", dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_TIMEOUT));
    }

    @Test
    @DisplayName("测试修改开关配置后获取最新值")
    void testGetUpdatedSwitchConfig() {
        DynamicConfig.SwitchConfig switchConfig = new DynamicConfig.SwitchConfig();
        switchConfig.setAuditEnabled(true);
        switchConfig.setEmailNotification(false);
        switchConfig.setLogLevel("DEBUG");
        dynamicConfig.setSwitches(switchConfig);

        assertEquals("true", dynamicConfig.getConfigValue(ConfigConst.KEY_SWITCH_AUDIT));
        assertEquals("false", dynamicConfig.getConfigValue(ConfigConst.KEY_SWITCH_EMAIL_NOTIFICATION));
        assertEquals("DEBUG", dynamicConfig.getConfigValue(ConfigConst.KEY_SWITCH_LOG_LEVEL));
    }

    @Test
    @DisplayName("测试所有配置常量都被注册")
    void testAllConfigKeysRegistered() {
        assertNotNull(dynamicConfig.getConfigValue(ConfigConst.KEY_FILE_UPLOAD_PATH));
        assertNotNull(dynamicConfig.getConfigValue(ConfigConst.KEY_FILE_MAX_SIZE));
        assertNotNull(dynamicConfig.getConfigValue(ConfigConst.KEY_FILE_ALLOWED_EXTENSIONS));
        assertNotNull(dynamicConfig.getConfigValue(ConfigConst.KEY_EMPLOYEE_IMPORT_ENABLED));
        assertNotNull(dynamicConfig.getConfigValue(ConfigConst.KEY_EMPLOYEE_IMPORT_MAX_SIZE));
        assertNotNull(dynamicConfig.getConfigValue(ConfigConst.KEY_EMPLOYEE_IMPORT_BATCH_SIZE));
        assertNull(dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_API_URL));
        assertNull(dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_APP_KEY));
        assertNull(dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_APP_SECRET));
        assertNotNull(dynamicConfig.getConfigValue(ConfigConst.KEY_SMS_TIMEOUT));
        assertNotNull(dynamicConfig.getConfigValue(ConfigConst.KEY_SWITCH_AUDIT));
        assertNotNull(dynamicConfig.getConfigValue(ConfigConst.KEY_SWITCH_EMAIL_NOTIFICATION));
        assertNotNull(dynamicConfig.getConfigValue(ConfigConst.KEY_SWITCH_LOG_LEVEL));
    }

    @Test
    @DisplayName("测试configMap初始化后不为空")
    void testConfigMapInitialized() {
        assertTrue(dynamicConfig.getConfigMap().size() > 0);
        assertEquals(13, dynamicConfig.getConfigMap().size());
    }

    @Test
    @DisplayName("测试内部类FileConfig默认值")
    void testFileConfigDefaultValues() {
        DynamicConfig.FileConfig fileConfig = new DynamicConfig.FileConfig();
        assertEquals("./uploads", fileConfig.getUploadPath());
        assertEquals(10485760L, fileConfig.getMaxSize());
        assertEquals(3, fileConfig.getAllowedExtensions().size());
    }

    @Test
    @DisplayName("测试内部类ImportConfig默认值")
    void testImportConfigDefaultValues() {
        DynamicConfig.ImportConfig importConfig = new DynamicConfig.ImportConfig();
        assertFalse(importConfig.getEnabled());
        assertEquals(5000, importConfig.getMaxSize());
        assertEquals("500", importConfig.getBatchSize());
    }

    @Test
    @DisplayName("测试内部类SmsConfig默认值")
    void testSmsConfigDefaultValues() {
        DynamicConfig.SmsConfig smsConfig = new DynamicConfig.SmsConfig();
        assertNull(smsConfig.getApiUrl());
        assertNull(smsConfig.getAppKey());
        assertNull(smsConfig.getAppSecret());
        assertEquals(30000, smsConfig.getTimeout());
    }

    @Test
    @DisplayName("测试内部类SwitchConfig默认值")
    void testSwitchConfigDefaultValues() {
        DynamicConfig.SwitchConfig switchConfig = new DynamicConfig.SwitchConfig();
        assertFalse(switchConfig.getAuditEnabled());
        assertTrue(switchConfig.getEmailNotification());
        assertEquals("INFO", switchConfig.getLogLevel());
    }
}
