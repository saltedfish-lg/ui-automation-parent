package com.saltedfish.framework.driver;

import com.saltedfish.framework.config.ConfigManager;
import com.saltedfish.framework.config.FrameworkConfig;
import com.saltedfish.framework.utils.LogUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * DriverFactory 负责根据配置创建不同类型的 WebDriver 实例。
 * 新增点：
 *  - 隐式等待时间从配置读取（implicitWaitSec）；
 *  - 页面加载超时时间从配置读取（pageLoadTimeoutSec）；
 *  - 浏览器类型与是否 headless 从配置读取；
 *  - 未来可扩展远程驱动 / 多浏览器类型。
 */
public class DriverFactory {

    /**
     * 使用配置中的 browser 字段创建默认浏览器实例。
     *
     * @return WebDriver 实例
     */
    public WebDriver createDefaultDriver() {
        FrameworkConfig config = ConfigManager.getConfig();
        String browser = config.getBrowser();
        DriverType type;

        if (browser == null || browser.isBlank()) {
            type = DriverType.CHROME;
        } else {
            type = DriverType.valueOf(browser.trim().toUpperCase());
        }

        return createDriver(type);
    }

    /**
     * 根据指定浏览器类型创建 WebDriver 实例，并统一设置等待与超时。
     *
     * @param type 浏览器类型
     * @return WebDriver 实例
     */
    public WebDriver createDriver(DriverType type) {
        FrameworkConfig config = ConfigManager.getConfig();
        String chromeDriverPath = config.getChromeDriverPath();

        if (chromeDriverPath != null && !chromeDriverPath.isBlank()) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            LogUtil.info("使用自定义 ChromeDriver 路径: " + chromeDriverPath);
        } else {
            LogUtil.info("未配置自定义 ChromeDriver 路径，交由 Selenium Manager 处理。");
        }

        WebDriver driver;

        switch (type) {
            case EDGE -> {
                LogUtil.info("正在创建 Edge 浏览器实例...");
                EdgeOptions options = new EdgeOptions();

                if (Boolean.TRUE.equals(config.getHeadless())) {
                    // Edge 的无头模式参数与 Chrome 一致
                    options.addArguments("--headless=new");
                }
                options.addArguments("--start-maximized");

                // 使用 Selenium Manager 自动管理 EdgeDriver，无需手动设置路径
                driver = new EdgeDriver(options);
            }
            case FIREFOX -> {
                LogUtil.info("正在创建 Firefox 浏览器实例...");
                FirefoxOptions options = new FirefoxOptions();

                if (Boolean.TRUE.equals(config.getHeadless())) {
                    options.addArguments("--headless");
                }
                // Firefox 没有 "--start-maximized"，一般用下面两种方式之一：
                // 1）通过窗口大小参数控制；2）后续在用例中调用 driver.manage().window().maximize()
                // 在 BaseTest.setUp() 中统一处理。
                driver = new FirefoxDriver(options);
            }
            case CHROME -> {
                LogUtil.info("正在创建 Chrome 浏览器实例...");
                ChromeOptions options = new ChromeOptions();

                if (Boolean.TRUE.equals(config.getHeadless())) {
                    options.addArguments("--headless=new");
                }
                options.addArguments("--start-maximized");

                driver = new ChromeDriver(options);
            }
            default -> throw new IllegalArgumentException("不支持的浏览器类型: " + type);
        }

        // 从配置中读取等待时间，如果为空则使用默认值
        int implicitWaitSec = config.getImplicitWaitSec() != null
                ? config.getImplicitWaitSec()
                : 2;
        int pageLoadTimeoutSec = config.getPageLoadTimeoutSec() != null
                ? config.getPageLoadTimeoutSec()
                : 30;

        LogUtil.info("设置隐式等待时间为 " + implicitWaitSec + " 秒");
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(implicitWaitSec));

        LogUtil.info("设置页面加载超时时间为 " + pageLoadTimeoutSec + " 秒");
        driver.manage()
                .timeouts()
                .pageLoadTimeout(Duration.ofSeconds(pageLoadTimeoutSec));

        return driver;
    }
}
