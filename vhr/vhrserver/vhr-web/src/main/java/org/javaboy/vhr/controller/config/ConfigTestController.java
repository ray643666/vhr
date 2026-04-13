package org.javaboy.vhr.controller.config;

import org.javaboy.vhr.model.Menu;
import org.javaboy.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@RefreshScope   // 关键注解：开启动态刷新
public class ConfigTestController {
    @Value("${vhr.file.upload-path:默认路径}")
    private String uploadPath;

    @Value("${employee.import.max-size:1000}")
    private Integer maxImportSize;

    @GetMapping("/test")
    public String test() {
        return String.format("上传路径: %s, 最大导入数量: %d", uploadPath, maxImportSize);
    }
}