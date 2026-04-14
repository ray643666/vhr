package org.javaboy.vhr.constants;

/**
 * 配置常量
 * 集中管理配置项的 Key，避免魔法值
 */
public class ConfigConst {

    // Nacos 配置相关
    public static final String NACOS_DATA_ID = "vhr-web.yaml";
    public static final String NACOS_GROUP = "DEFAULT_GROUP";

    // 配置 Key
    public static final String KEY_FILE_UPLOAD_PATH = "vhr.file.upload-path";
    public static final String KEY_FILE_MAX_SIZE = "vhr.file.max-size";
    public static final String KEY_EMPLOYEE_IMPORT_ENABLED = "vhr.employee.import.enabled";
    public static final String KEY_EMPLOYEE_IMPORT_MAX_SIZE = "vhr.employee.import.max-size";
    public static final String KEY_SMS_API_URL = "vhr.sms.api-url";
    public static final String KEY_SWITCH_AUDIT = "vhr.switches.audit-enabled";

    // 配置分组
    public static final String GROUP_FILE = "文件配置";
    public static final String GROUP_IMPORT = "导入配置";
    public static final String GROUP_SMS = "短信配置";
    public static final String GROUP_SWITCH = "开关配置";
}
