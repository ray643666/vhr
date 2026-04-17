package org.javaboy.vhr.service;

import lombok.extern.slf4j.Slf4j;
import org.javaboy.vhr.config.DynamicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class ConfigTestService {

    @Autowired
    private DynamicConfig dynamicConfig;

    public void testConfig() {
        log.info("上传文件目录:" + dynamicConfig.getFile().getUploadPath());
        log.info("上传文件白名单:" + dynamicConfig.getFile().getAllowedExtensions());
        log.info("导入是否启动开关:" + dynamicConfig.getEmployee().getEnabled());
        log.info("sms调用API:" + dynamicConfig.getSms().getApiUrl());
        log.info("开关配置:" + dynamicConfig.getSwitches().getAuditEnabled());
    }

    /**
     * 根据配置Key获取配置值
     */
    public String getConfigValue(String key) {
        return dynamicConfig.getConfigValue(key);
    }

    /**
     * 获取所有配置项
     */
    public Map<String, String> getAllConfig() {
        return dynamicConfig.getAllConfig();
    }
}
