package com.saltedfish.demo.tests.login;

import com.saltedfish.framework.testng.retry.RetryAnalyzer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.saltedfish.demo.flows.LoginFlow;
import com.saltedfish.demo.pages.HomePage;
import com.saltedfish.framework.testng.assertion.AssertHelper;
import com.saltedfish.framework.testng.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

/**
 * LoginTest 演示一个最基础的登录用例。
 *
 * 特点：
 *  - 继承 BaseTest，自动获得浏览器生命周期管理；
 *  - 使用 LoginFlow 封装业务步骤；
 *  - 利用 Allure 注解标记模块 / 功能 / 场景；
 *  - 使用 AssertHelper 做断言，统一日志输出；
 *  - 使用 TestNG groups，将用例标记为 smoke / regression。
 */
@Epic("账号体系")
@Feature("登录功能")
public class LoginTest extends BaseTest {

    /**
     * 登录业务流程封装对象。
     * 不在字段定义处直接初始化，而是在 @BeforeMethod 中创建，
     * 确保此时 BaseTest.setUp() 已经执行完毕，driver 已经就绪。
     */
    private LoginFlow loginFlow;

    /**
     * 在每个用例执行前初始化业务流程对象。
     *
     * 执行顺序：
     *  1. 先执行父类 BaseTest.setUp()（创建并注册 WebDriver）；
     *  2. 再执行当前类的 initFlows()，此时可以安全构造依赖 Page 的 Flow。
     */
    @BeforeMethod(alwaysRun = true)
    public void initFlows() {
        // 此时 DriverManager.getDriver() 已经有值，
        // LoginFlow 内部 new 的 Page 对象在调用 BasePage 构造方法时，
        // 能拿到正确的 WebDriver，并通过 PageFactory.initElements() 初始化元素。
        this.loginFlow = new LoginFlow();
    }



    /**
     * 正常登录场景：
     *  1. 使用有效账号密码；
     *  2. 期望登录成功并跳转到首页。
     *
     * groups:
     *  - smoke：冒烟用例；
     *  - regression：也可以参与回归。
     */
    @Test(
            description = "正常账号登录成功",
            groups = {"smoke", "regression"},
            retryAnalyzer = RetryAnalyzer.class
    )
    @Story("正常登录场景")
    @Description("使用有效账号密码登录系统，期望登录成功并跳转到首页。")
    public void testLoginSuccess() {
        // 执行登录流程
        HomePage homePage = loginFlow.loginAs("a_saltedfish@163.com", "XXX110120XXX");

        // 使用 AssertHelper 做断言，日志和结果更清晰
        AssertHelper.assertTrue(homePage.isLoaded(), "登录成功后首页应该加载成功。");
    }
}
