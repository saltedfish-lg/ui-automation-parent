package com.saltedfish.framework.testng.listeners;

import com.saltedfish.framework.config.ConfigManager;
import com.saltedfish.framework.config.FrameworkConfig;
import com.saltedfish.framework.notification.DingTalkNotifier;
import com.saltedfish.framework.notification.Notifier;
import com.saltedfish.framework.notification.WeComNotifier;
import com.saltedfish.framework.utils.LogUtil;
import org.testng.ISuite;
import org.testng.ISuiteListener;

/**
 * SuiteListener 用于监听整个测试套件的执行情况。
 *
 * 核心功能：
 *  1. 在套件执行结束时统计通过、失败、跳过用例数量；
 *  2. 将结果通过企业微信 / 钉钉机器人发送到对应群；
 *  3. 可根据需要扩展更多统计指标（用例执行时长等）。
 */
public class SuiteListener implements ISuiteListener {

    /**
     * 当整个 suite 执行完成时触发。
     *
     * @param suite 当前执行完成的套件对象
     */
    @Override
    public void onFinish(ISuite suite) {
        LogUtil.info("测试套件执行结束，开始统计结果并发送通知。");

        // 从 TestNG 的结果结构中统计通过/失败/跳过的用例数量
        int passed = suite.getResults().values().stream()
                .mapToInt(r -> r.getTestContext().getPassedTests().size())
                .sum();

        int failed = suite.getResults().values().stream()
                .mapToInt(r -> r.getTestContext().getFailedTests().size())
                .sum();

        int skipped = suite.getResults().values().stream()
                .mapToInt(r -> r.getTestContext().getSkippedTests().size())
                .sum();

        String suiteName = suite.getName();

        String title = "UI 自动化回归结果";
        String content = """
                套件名称：%s
                通过用例数：%d
                失败用例数：%d
                跳过用例数：%d
                """.formatted(suiteName, passed, failed, skipped);

        // 读取配置决定发送到企业微信、钉钉或两者都发
        FrameworkConfig config = ConfigManager.getConfig();

        // 企业微信通知
        if (config.getWeComWebhookUrl() != null && !config.getWeComWebhookUrl().isBlank()) {
            Notifier notifier = new WeComNotifier(config.getWeComWebhookUrl());
            notifier.sendText(title, content);
        } else {
            LogUtil.info("未配置企业微信 WebHook，跳过企业微信通知。");
        }

        // 钉钉通知
        if (config.getDingTalkWebhookUrl() != null && !config.getDingTalkWebhookUrl().isBlank()) {
            Notifier notifier = new DingTalkNotifier(config.getDingTalkWebhookUrl());
            notifier.sendText(title, content);
        } else {
            LogUtil.info("未配置钉钉 WebHook，跳过钉钉通知。");
        }
    }
}
