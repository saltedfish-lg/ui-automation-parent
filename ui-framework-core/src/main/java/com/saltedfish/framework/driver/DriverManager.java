package com.saltedfish.framework.driver;

import org.openqa.selenium.WebDriver;

/**
 * DriverManager 通过 ThreadLocal 为每个线程保存独立的 WebDriver 实例。
 *
 * 设计目的：
 *  1. 支持并行执行 UI 自动化用例：每个线程一个浏览器；
 *  2. 对外提供统一的获取/清理 WebDriver 的入口，避免在项目中到处乱 new；
 *  3. 将浏览器生命周期管理集中起来，降低维护成本。
 */
public final class DriverManager {

    /**
     * 使用 ThreadLocal 存放当前线程的 WebDriver。
     * ThreadLocal 可以保证多线程环境下每个线程有独立的副本，互不干扰。
     */
    private static final ThreadLocal<WebDriver> DRIVER_HOLDER = new ThreadLocal<>();

    /**
     * 工具类不允许被实例化，所以将构造函数声明为 private。
     */
    private DriverManager() {
        // no-op
    }

    /**
     * 为当前线程绑定一个 WebDriver 实例。
     * 通常在测试前置（例如 BaseTest#setUp）中调用。
     *
     * @param driver 已初始化好的 WebDriver 对象
     */
    public static void setDriver(WebDriver driver) {
        DRIVER_HOLDER.set(driver);
    }

    /**
     * 获取当前线程绑定的 WebDriver 实例。
     *
     * @return 当前线程对应的 WebDriver；如果尚未设置，返回 null
     */
    public static WebDriver getDriver() {
        return DRIVER_HOLDER.get();
    }

    /**
     * 关闭并清理当前线程的 WebDriver。
     * 通常在测试后置（例如 BaseTest#tearDown）中调用。
     * 会调用 WebDriver.quit() 并移除 ThreadLocal 中的引用。
     */
    public static void quitDriver() {
        WebDriver driver = DRIVER_HOLDER.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                // 无论 quit 是否抛出异常，都要确保清理 ThreadLocal，避免内存泄漏
                DRIVER_HOLDER.remove();
            }
        }
    }
}

