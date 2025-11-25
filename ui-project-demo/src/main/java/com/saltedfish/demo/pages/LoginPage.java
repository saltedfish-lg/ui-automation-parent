package com.saltedfish.demo.pages;

import com.saltedfish.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * LoginPage 表示被测系统的登录页面。
 *
 * 职责：
 *  1. 维护本页面上的元素定位；
 *  2. 提供与本页面相关的操作方法（输入用户名、密码、点击登录等）。
 *
 * 注意：
 *  - 元素定位写在这里；
 *  - 等待与 driver 操作在 BasePage 中统一处理。
 */
public class LoginPage extends BasePage {

    /**
     * 登录入口。
     * 说明：id 值需要根据实际系统调整。
     */
    @FindBy(id = "s-top-loginbtn")
    private WebElement loginEntryButton;

    /**
     * 用户名输入框。
     * 说明：id 值需要根据实际系统调整。
     */
    @FindBy(id = "TANGRAM__PSP_11__userName")
    private WebElement usernameInput;

    /**
     * 密码输入框。
     */
    @FindBy(id = "TANGRAM__PSP_11__password")
    private WebElement passwordInput;

    /**
     * 勾选协议。
     */
    @FindBy(id = "TANGRAM__PSP_11__isAgree")
    private WebElement isAgreeBox;

    /**
     * 登录按钮。
     */
    @FindBy(id = "TANGRAM__PSP_11__submit")
    private WebElement loginButton;

    /**
     * 构造方法中调用 PageFactory.initElements，将 @FindBy 标注的字段与实际元素绑定。
     */
    public LoginPage() {
        PageFactory.initElements(getDriver(), this);
    }

    /**
     * 进入登录界面。
     *
     * 点击登录进入登录界面
     */
    public void enterLoginEntry() {
        click(loginEntryButton);
    }

    /**
     * 在用户名输入框中输入用户名。
     *
     * @param username 用户名字符串
     */
    public void enterUsername(String username) {
        type(usernameInput, username);
    }

    /**
     * 在密码输入框中输入密码。
     *
     * @param password 密码字符串
     */
    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    /**
     * 点击登录按钮进入系统。
     */

    public void clickIsAgree() {
        click(isAgreeBox);
    }
    /**
     * 点击登录按钮进入系统。
     */
    public void clickLogin() {
        click(loginButton);
    }
}
