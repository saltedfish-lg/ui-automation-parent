package com.saltedfish.framework.base;

import com.saltedfish.framework.driver.DriverManager;
import com.saltedfish.framework.wait.WaitFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * BasePage 是所有业务页面对象（Page Object）的父类。
 * 主要职责：
 * 1. 提供 WebDriver 访问入口；
 * 2. 提供常用的高层封装操作（click、type 等），统一加显式等待；
 * 3. 让业务 Page 类只关心“元素 + 业务逻辑”，而不用重复写等待逻辑。
 */
public abstract class BasePage {

    /**
     * 获取当前线程绑定的 WebDriver。
     *
     * @return 当前线程对应的 WebDriver 实例
     */
    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    /**
     * 执行点击操作：
     * 1. 使用 WaitFactory 等待元素可点击；
     * 2. 调用 WebElement.click()。
     *
     * @param element 需要点击的元素
     */
    protected void click(WebElement element) {
        WebElement clickable = WaitFactory.waitUntilClickable(element);
        clickable.click();
    }

    /**
     * 执行文本输入操作：
     * 1. 等待元素可见；
     * 2. 清空原有内容；
     * 3. 输入新的文本内容。
     *
     * @param element 需要输入内容的元素
     * @param text    要输入的文本内容
     */
    protected void type(WebElement element, String text) {
        WebElement visible = WaitFactory.waitUntilVisible(element);
        visible.clear();
        visible.sendKeys(text);
    }
}
