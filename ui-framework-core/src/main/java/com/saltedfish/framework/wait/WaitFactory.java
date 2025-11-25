package com.saltedfish.framework.wait;

import com.saltedfish.framework.config.ConfigManager;
import com.saltedfish.framework.config.FrameworkConfig;
import com.saltedfish.framework.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * WaitFactory 封装了常用的显式等待操作。
 *
 * 设计目标：
 *  1. 所有等待逻辑集中管理，便于统一调整超时时间与策略；
 *  2. Page / Test 代码只调用这里的方法，而不直接 new WebDriverWait；
 *  3. 等待时间从框架配置中读取，避免写死常量值。
 *
 * 注意：
 *  - 日志不再在每次等待时打印显式等待时间，避免大量重复日志；
 *  - 显式等待时间只在 ConfigManager 加载配置时确定，必要时可以在启动阶段打印一次。
 */
public final class WaitFactory {

    private WaitFactory() {
        // 工具类禁止实例化
    }

    /**
     * 根据配置构造一个 WebDriverWait 实例。
     *
     * @return WebDriverWait 对象，用于执行显式等待
     */
    private static WebDriverWait newWait() {
        WebDriver driver = DriverManager.getDriver();
        FrameworkConfig config = ConfigManager.getConfig();

        // 从配置中读取显式等待时间，如果为空则使用 10 秒兜底
        int explicitWaitSec = config.getExplicitWaitSec() != null
                ? config.getExplicitWaitSec()
                : 10;

        return new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSec));
    }

    /**
     * 等待元素可点击并返回该元素。
     *
     * @param element 目标元素
     * @return 处于可点击状态的元素
     */
    public static WebElement waitUntilClickable(WebElement element) {
        return newWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * 等待元素可见并返回该元素。
     *
     * @param element 目标元素
     * @return 处于可见状态的元素
     */
    public static WebElement waitUntilVisible(WebElement element) {
        return newWait().until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * 等待定位到的元素可见，并返回该元素。
     *
     * @param locator 元素定位器（By）
     * @return 可见状态的元素
     */
    public static WebElement waitUntilVisible(By locator) {
        return newWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * 等待一组元素全部可见，并返回元素列表。
     *
     * @param locator 元素定位器（By）
     * @return 所有可见元素列表，如果超时会抛出 TimeoutException
     */
    public static List<WebElement> waitUntilAllVisible(By locator) {
        return newWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * 等待元素出现在 DOM 中（不要求可见）。
     *
     * @param locator 元素定位器（By）
     * @return 出现在 DOM 中的元素
     */
    public static WebElement waitUntilPresent(By locator) {
        return newWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * 等待元素从页面上消失（例如 loading 遮罩）。
     *
     * @param locator 需要等待消失的元素定位器
     * @return true 表示元素已不可见或不存在；false 表示未在超时时间内消失
     */
    public static boolean waitUntilInvisible(By locator) {
        return newWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * 等待当前 URL 包含指定片段。
     *
     * @param fragment URL 中预期出现的子串，例如 "/home"、"/dashboard"
     * @return true 表示条件满足；false 表示超时
     */
    public static boolean waitUntilUrlContains(String fragment) {
        if (fragment == null || fragment.isBlank()) {
            throw new IllegalArgumentException("URL 片段不能为空");
        }
        return newWait().until(ExpectedConditions.urlContains(fragment));
    }
}
