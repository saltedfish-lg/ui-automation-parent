package com.saltedfish.framework.testng.listeners;

import com.saltedfish.framework.driver.DriverManager;
import com.saltedfish.framework.utils.FileUtil;
import com.saltedfish.framework.utils.LogUtil;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestListener 监听单个用例的执行过程。
 *
 * 核心功能：
 *  1. 当用例失败时自动截取当前屏幕；
 *  2. 将截图作为附件挂到 Allure 报告中；
 *  3. 可选：将截图写入本地文件，方便离线排查。
 */
public class TestListener implements ITestListener {

    /**
     * Allure @Attachment 注解：
     *  - 标记该方法返回值应当作为附件添加到报告中；
     *  - value 为附件名称；
     *  - type 为附件的 MIME 类型，这里为 image/png。
     *
     * @param screenshotBytes 截图的字节数组
     * @return 传入的截图字节数组（原样返回以便 Allure 处理）
     */
    @Attachment(value = "失败截图", type = "image/png")
    public byte[] attachScreenshot(byte[] screenshotBytes) {
        return screenshotBytes;
    }

    /**
     * 进行截图操作：
     *  1. 从 DriverManager 获取当前线程的 WebDriver；
     *  2. 调用 TakesScreenshot.getScreenshotAs() 截图；
     *  3. 同时返回字节数组，供 Allure 作为附件使用。
     *
     * @return 截图内容，如果失败则返回空数组
     */
    private byte[] takeScreenshotAsBytes() {
        try {
            if (DriverManager.getDriver() instanceof TakesScreenshot ts) {
                return ts.getScreenshotAs(OutputType.BYTES);
            } else {
                LogUtil.warn("当前 WebDriver 不支持截图接口。");
                return new byte[0];
            }
        } catch (Exception e) {
            LogUtil.error("执行截图操作失败。", e);
            return new byte[0];
        }
    }

    /**
     * 当单个测试方法执行失败时，TestNG 会调用本方法。
     *
     * @param result 包含当前用例名称、异常信息等的结果对象
     */
    @Override
    public void onTestFailure(ITestResult result) {
        LogUtil.warn("用例失败，开始截图：" + result.getName());

        // 截图为字节数组
        byte[] screenshotBytes = takeScreenshotAsBytes();

        // 将截图挂到 Allure 报告
        attachScreenshot(screenshotBytes);

        // 额外：将截图写入本地文件，方便离线排查
        if (screenshotBytes.length > 0) {
            String fileName = FileUtil.generateTimestampFileName("screenshot_", ".png");
            String filePath = "target/screenshots/" + fileName;
            FileUtil.writeBytesToFile(screenshotBytes, filePath);
        }
    }
}
