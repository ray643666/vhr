package org.javaboy.vhr.controller.config;

import org.javaboy.vhr.service.ConfigTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @作者 江南一点雨
 * @公众号 江南一点雨
 * @微信号 a_java_boy
 * @GitHub https://github.com/lenve
 * @博客 http://wangsong.blog.csdn.net
 * @网站 http://www.javaboy.org
 * @时间 2019-09-27 7:10
 */
@RestController
@RequestMapping("/config")
public class ConfigTestController {

    @Autowired
    private ConfigTestService configTestService;

    /**
     * 测试配置功能
     * 调用配置测试服务验证动态配置是否正确加载和刷新
     */
    @GetMapping("/test")
    public void test() {
        configTestService.testConfig();
    }

    /**
     * 获取配置项的值
     * @param key       配置项的Key，如 vhr.file.upload-path
     * @return          配置项的值，如果Key不存在则返回null
     */
    @GetMapping("/getConfigValue")
    public String getConfigValue(@RequestParam(required = true) String key) {
        return configTestService.getConfigValue(key);
    }

    /**
     * 获取所有配置项
     */
    @GetMapping("/getAllConfig")
    public Map<String, String> getAllConfig() {
        return configTestService.getAllConfig();
    }
}