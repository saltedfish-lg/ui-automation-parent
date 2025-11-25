package com.saltedfish.demo.flows;

import com.saltedfish.demo.pages.HomePage;
import com.saltedfish.demo.pages.LoginPage;
import io.qameta.allure.Step;

/**
 * LoginFlow 封装“登录系统”这一完整业务流程。
 *
 * 设计目的一句话：
 *  - 让测试用例只写一行 loginFlow.loginAs("user", "pwd")，
 *    而不用关心登录页面中要点击哪些按钮、输入哪些字段。
 */
public class LoginFlow {

    private LoginPage loginPage;
    private HomePage homePage;

    public LoginFlow() {
        this.loginPage = new LoginPage();
    }

    /**
     * 使用给定的用户名和密码执行一次登录流程。
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录后首页对象，方便链式调用或断言
     */
    @Step("使用账号 {username} 登录系统")
    public HomePage loginAs(String username, String password) {
        openLoginPage();
        inputUsername(username);
        inputPassword(password);
        acceptAgreementIfNeeded();
        clickLoginButton();
        this.homePage = new HomePage();
        return homePage;
    }


    @Step("打开登录页面入口")
    protected void openLoginPage() {
        // 这里根据你的页面设计，有可能是：
        //  1）直接 driver.get(baseUrl)，或
        //  2）在首页点击“登录”按钮进入登录页面
        loginPage.enterLoginEntry();
    }

    @Step("输入用户名 [{username}]")
    protected void inputUsername(String username) {
        loginPage.enterUsername(username);
    }

    @Step("输入密码")
    protected void inputPassword(String password) {
        loginPage.enterPassword(password);
    }

    @Step("勾选用户协议（如果需要）")
    protected void acceptAgreementIfNeeded() {
        loginPage.clickIsAgree();
    }

    @Step("点击登录按钮")
    protected void clickLoginButton() {
        loginPage.clickLogin();
    }
}


