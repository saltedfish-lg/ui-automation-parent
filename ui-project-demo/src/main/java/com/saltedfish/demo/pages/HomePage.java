package com.saltedfish.demo.pages;

import com.saltedfish.framework.base.BasePage;
import com.saltedfish.framework.wait.WaitFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * HomePage 表示登录成功后跳转的首页。
 *
 * 职责：
 *  1. 维护首页关键元素；
 *  2. 提供检查首页是否加载成功的方法。
 */
public class HomePage extends BasePage {

    /**
     * 示例：首页上某个标识用户已登录的元素，例如“欢迎你，xxx”文字。
     * 实际定位需要根据你的系统调整。
     */
    @FindBy(id = "s-top-username")
    private WebElement userWelcomeLabel;

    public HomePage() {
        PageFactory.initElements(getDriver(), this);
    }

    /**
     * 判断首页是否加载成功。
     * 做法：等待首页关键元素可见，如果超时则认为未加载成功。
     *
     * @return true 表示首页已加载；false 表示首页未正确加载
     */
    public boolean isLoaded() {
        try {
            // 使用显式等待，避免页面稍慢导致误判
            WaitFactory.waitUntilVisible(userWelcomeLabel);
            return userWelcomeLabel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}

