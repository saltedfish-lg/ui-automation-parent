package com.saltedfish.framework.config;

/**
 * FrameworkConfig 用于承载框架的运行时配置。
 *
 * 典型配置项包括：
 *  - baseUrl：被测系统基础地址；
 *  - browser：浏览器类型，例如 CHROME / EDGE；
 *  - headless：是否启用无头模式；
 *  - weComWebhookUrl：企业微信机器人 WebHook 地址；
 *  - dingTalkWebhookUrl：钉钉机器人 WebHook 地址；
 *  - explicitWaitSec：默认显式等待时间（秒）；
 *  - implicitWaitSec：默认隐式等待时间（秒）；
 *  - pageLoadTimeoutSec：页面加载超时时间（秒）。
 */
public class FrameworkConfig {

    /**
     * 被测系统的基础 URL，例如 https://test.xxx.com
     */
    private String baseUrl;

    /**
     * 浏览器类型，与 DriverType 枚举对应，例如 "CHROME"、"EDGE" 等。
     */
    private String browser;

    /**
     * 是否使用无头模式（true 表示开启，false 表示不开启）。
     */
    private Boolean headless;

    /**
     * 企业微信机器人 WebHook 地址。
     * 如果为空，则不发送企业微信通知。
     */
    private String weComWebhookUrl;

    /**
     * 钉钉机器人 WebHook 地址。
     * 如果为空，则不发送钉钉通知。
     */
    private String dingTalkWebhookUrl;

    /**
     * 默认显式等待时间（秒）。
     * 建议 5~15 之间，根据系统响应速度调整。
     */
    private Integer explicitWaitSec;

    /**
     * 默认隐式等待时间（秒）。
     * 建议保持较低值（例如 0~3），仅作兜底。
     */
    private Integer implicitWaitSec;

    /**
     * 页面加载超时时间（秒）。
     * 用于控制 driver.get() / 页面跳转的超时。
     */
    private Integer pageLoadTimeoutSec;

    /**
     * 【可选】自定义 ChromeDriver 路径。
     *  - 场景：某些机器上 Selenium Manager 或 PATH 管理驱动有问题，
     *          希望强制使用指定位置的 chromedriver.exe。
     *  - 为空或空字符串时，DriverFactory 中可以选择交给 Selenium Manager 自动处理。
     */
    private String chromeDriverPath;

    // --- getter / setter ---

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Boolean getHeadless() {
        return headless;
    }

    public void setHeadless(Boolean headless) {
        this.headless = headless;
    }

    public String getWeComWebhookUrl() {
        return weComWebhookUrl;
    }

    public void setWeComWebhookUrl(String weComWebhookUrl) {
        this.weComWebhookUrl = weComWebhookUrl;
    }

    public String getDingTalkWebhookUrl() {
        return dingTalkWebhookUrl;
    }

    public void setDingTalkWebhookUrl(String dingTalkWebhookUrl) {
        this.dingTalkWebhookUrl = dingTalkWebhookUrl;
    }

    public Integer getExplicitWaitSec() {
        return explicitWaitSec;
    }

    public void setExplicitWaitSec(Integer explicitWaitSec) {
        this.explicitWaitSec = explicitWaitSec;
    }

    public Integer getImplicitWaitSec() {
        return implicitWaitSec;
    }

    public void setImplicitWaitSec(Integer implicitWaitSec) {
        this.implicitWaitSec = implicitWaitSec;
    }

    public Integer getPageLoadTimeoutSec() {
        return pageLoadTimeoutSec;
    }

    public String getChromeDriverPath() {
        return chromeDriverPath;
    }

    public void setChromeDriverPath(String chromeDriverPath) {
        this.chromeDriverPath = chromeDriverPath;
    }

    public void setPageLoadTimeoutSec(Integer pageLoadTimeoutSec) {
        this.pageLoadTimeoutSec = pageLoadTimeoutSec;
    }
}
