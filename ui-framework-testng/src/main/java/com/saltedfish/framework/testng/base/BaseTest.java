package com.saltedfish.framework.testng.base;

import com.saltedfish.framework.config.ConfigManager;
import com.saltedfish.framework.config.FrameworkConfig;
import com.saltedfish.framework.driver.DriverFactory;
import com.saltedfish.framework.driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * BaseTest 为所有 TestNG 测试类提供统一的浏览器生命周期管理。
 *
 * 主要职责：
 *  1. 在每个测试方法执行前创建 WebDriver 实例，并根据配置打开基础 URL；
 *  2. 在每个测试方法执行后关闭并清理 WebDriver。
 *
 * 注意：
 *  - 本类位于 TestNG 适配层，强依赖 TestNG 注解；
 *  - 任何基于 TestNG 的业务测试类都可以直接继承本类。
 */
public abstract class BaseTest {

    /**
     * 每个测试方法执行前都会回调本方法。
     * 使用 @BeforeMethod 注解可以保证在 @Test 方法之前执行。
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // 通过 DriverFactory 创建默认配置下的浏览器实例
        DriverFactory factory = new DriverFactory();
        DriverManager.setDriver(factory.createDefaultDriver());
        DriverManager.getDriver().manage().window().maximize();
        // 如果配置中定义了 baseUrl，则在测试起始时自动打开
        FrameworkConfig config = ConfigManager.getConfig();
        String baseUrl = config.getBaseUrl();
        if (baseUrl != null && !baseUrl.isBlank()) {
            DriverManager.getDriver().get(baseUrl);
        }
    }

    /**
     * 每个测试方法执行完成后都会回调本方法。
     * 使用 @AfterMethod 注解保证在 @Test 方法之后执行。
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // 统一关闭并清理当前线程的 WebDriver
        DriverManager.quitDriver();
    }
}
