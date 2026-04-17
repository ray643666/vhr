
package org.javaboy.vhr.controller.config;

import org.javaboy.vhr.exception.GlobalExceptionHandler;
import org.javaboy.vhr.service.ConfigTestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * ConfigTestController 单元测试
 */
@DisplayName("配置测试控制器测试")
class ConfigTestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ConfigTestService configTestService;

    @InjectMocks
    private ConfigTestController configTestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(configTestController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("测试配置功能接口 - 成功调用")
    void testConfigEndpoint_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/config/test"))
                .andExpect(status().isOk())
                .andDo(print());

        // Verify
        verify(configTestService, times(1)).testConfig();
    }

    @Test
    @DisplayName("测试获取配置值接口 - 获取存在的配置")
    void testGetConfigValue_ExistingKey() throws Exception {
        // Arrange
        String key = "vhr.file.upload-path";
        String expectedValue = "./uploads";
        when(configTestService.getConfigValue(eq(key))).thenReturn(expectedValue);

        // Act & Assert
        mockMvc.perform(get("/config/getConfigValue")
                        .param("key", key))
                .andExpect(status().isOk())
                .andDo(print());

        // Verify
        verify(configTestService, times(1)).getConfigValue(eq(key));
    }

    @Test
    @DisplayName("测试获取配置值接口 - 获取不存在的配置")
    void testGetConfigValue_NonExistentKey() throws Exception {
        // Arrange
        String key = "non.existent.key";
        when(configTestService.getConfigValue(eq(key))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/config/getConfigValue")
                        .param("key", key))
                .andExpect(status().isOk())
                .andDo(print());

        // Verify
        verify(configTestService, times(1)).getConfigValue(eq(key));
    }

    @Test
    @DisplayName("测试获取配置值接口 - 空字符串Key")
    void testGetConfigValue_EmptyKey() throws Exception {
        // Arrange
        String key = "";
        when(configTestService.getConfigValue(eq(key))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/config/getConfigValue")
                        .param("key", key))
                .andExpect(status().isOk())
                .andDo(print());

        // Verify
        verify(configTestService, times(1)).getConfigValue(eq(key));
    }

    @Test
    @DisplayName("测试获取配置值接口 - null Key")
    void testGetConfigValue_NullKey() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/config/getConfigValue"))
                .andExpect(status().isInternalServerError())
                .andDo(print());

        // Verify
        verify(configTestService, never()).getConfigValue(any());
    }

    @Test
    @DisplayName("测试获取文件上传路径配置")
    void testGetFileUploadPathConfig() throws Exception {
        // Arrange
        String key = "vhr.file.upload-path";
        String uploadPath = "/custom/uploads";
        when(configTestService.getConfigValue(eq(key))).thenReturn(uploadPath);

        // Act & Assert
        mockMvc.perform(get("/config/getConfigValue")
                        .param("key", key))
                .andExpect(status().isOk())
                .andDo(print());

        // Verify
        verify(configTestService, times(1)).getConfigValue(eq(key));
    }

    @Test
    @DisplayName("测试获取短信API配置")
    void testGetSmsApiConfig() throws Exception {
        // Arrange
        String key = "vhr.sms.api-url";
        String apiUrl = "https://api.sms.com";
        when(configTestService.getConfigValue(eq(key))).thenReturn(apiUrl);

        // Act & Assert
        mockMvc.perform(get("/config/getConfigValue")
                        .param("key", key))
                .andExpect(status().isOk())
                .andDo(print());

        // Verify
        verify(configTestService, times(1)).getConfigValue(eq(key));
    }

    @Test
    @DisplayName("测试获取开关配置")
    void testGetSwitchConfig() throws Exception {
        // Arrange
        String key = "vhr.switch.audit";
        String auditEnabled = "true";
        when(configTestService.getConfigValue(eq(key))).thenReturn(auditEnabled);

        // Act & Assert
        mockMvc.perform(get("/config/getConfigValue")
                        .param("key", key))
                .andExpect(status().isOk())
                .andDo(print());

        // Verify
        verify(configTestService, times(1)).getConfigValue(eq(key));
    }

    @Test
    @DisplayName("测试多次调用配置测试接口")
    void testConfigEndpoint_MultipleCalls() throws Exception {
        // Act & Assert - 第一次调用
        mockMvc.perform(get("/config/test"))
                .andExpect(status().isOk());

        // Act & Assert - 第二次调用
        mockMvc.perform(get("/config/test"))
                .andExpect(status().isOk());

        // Verify
        verify(configTestService, times(2)).testConfig();
    }

    @Test
    @DisplayName("测试获取配置值接口 - 特殊字符Key")
    void testGetConfigValue_SpecialCharacterKey() throws Exception {
        // Arrange
        String key = "vhr.file.allowed-extensions";
        String extensions = "jpg,png,pdf,doc,docx";
        when(configTestService.getConfigValue(eq(key))).thenReturn(extensions);

        // Act & Assert
        mockMvc.perform(get("/config/getConfigValue")
                        .param("key", key))
                .andExpect(status().isOk())
                .andDo(print());

        // Verify
        verify(configTestService, times(1)).getConfigValue(eq(key));
    }

    @Test
    @DisplayName("测试配置服务抛出异常时的处理")
    void testGetConfigValue_ServiceThrowsException() throws Exception {
        // Arrange
        String key = "vhr.file.upload-path";
        when(configTestService.getConfigValue(eq(key)))
                .thenThrow(new RuntimeException("配置读取失败"));

        // Act & Assert
        mockMvc.perform(get("/config/getConfigValue")
                        .param("key", key))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.msg").exists())
                .andDo(print());

        // Verify
        verify(configTestService, times(1)).getConfigValue(eq(key));
    }
}