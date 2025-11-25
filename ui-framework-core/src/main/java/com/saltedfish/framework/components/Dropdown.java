package com.saltedfish.framework.components;

import com.saltedfish.framework.wait.WaitFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Dropdown 组件封装：
 *  - 用于处理标准 HTML <select> 下拉框；
 *  - 对常见操作（按文本/值/索引选择）进行统一封装；
 *  - 内置显式等待，避免元素尚未可见时操作失败。
 *
 * 使用方式通常是：
 *  1. 在 Page 中通过 @FindBy 或 By 定位到 <select> 元素；
 *  2. 包装成 Dropdown 实例；
 *  3. 调用 selectByVisibleText 等方法完成操作。
 */
public class Dropdown {

    /**
     * 下拉框对应的根元素（通常是 <select> 标签）。
     */
    private final WebElement rootElement;

    /**
     * 构造函数通过 WebElement 注入。
     *
     * @param rootElement 下拉框根元素
     */
    public Dropdown(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    /**
     * 构造函数通过 By 定位器注入。
     * 内部会使用 WaitFactory 等待元素可见。
     *
     * @param locator 下拉框定位器
     */
    public Dropdown(By locator) {
        this.rootElement = WaitFactory.waitUntilVisible(locator);
    }

    /**
     * 根据可见文本选择下拉选项。
     *
     * @param text 选项的可见文本，例如 "启用"
     */
    public void selectByVisibleText(String text) {
        Select select = new Select(rootElement);
        select.selectByVisibleText(text);
    }

    /**
     * 根据 value 属性选择下拉选项。
     *
     * @param value 选项的 value 值
     */
    public void selectByValue(String value) {
        Select select = new Select(rootElement);
        select.selectByValue(value);
    }

    /**
     * 根据索引选择下拉选项。
     *
     * @param index 选项索引（从 0 开始）
     */
    public void selectByIndex(int index) {
        Select select = new Select(rootElement);
        select.selectByIndex(index);
    }

    /**
     * 获取当前选中选项的可见文本。
     *
     * @return 当前选中项的文本
     */
    public String getSelectedText() {
        Select select = new Select(rootElement);
        return select.getFirstSelectedOption().getText();
    }
}

