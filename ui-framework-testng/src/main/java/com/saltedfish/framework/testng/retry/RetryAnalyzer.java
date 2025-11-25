package com.saltedfish.framework.testng.retry;

import com.saltedfish.framework.utils.LogUtil;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * RetryAnalyzer 用于在用例失败时进行有限次数的自动重试。
 *
 * 设计说明：
 *  1. 当前版本仅支持固定最大重试次数（默认 1 次）；
 *  2. 仅在用例执行失败时触发重试；成功则不会重跑；
 *  3. 适用于存在偶发性干扰的 UI 用例（例如偶发弹窗、网络抖动）。
 *
 * 后续如果需要：
 *  - 可以从配置文件读取最大重试次数；
 *  - 可以通过 Listener + IAnnotationTransformer 对指定分组统一应用重试策略。
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    /**
     * 当前已重试次数。
     */
    private int retryCount = 0;

    /**
     * 最大允许重试次数。
     *
     * 例如：
     *  maxRetryCount = 1 表示最多执行 1 + 1 = 2 次（原始执行一次 + 重试一次）。
     */
    private final int maxRetryCount = 1;

    /**
     * 当 TestNG 判断某个测试方法执行结果为失败时，会调用该方法。
     *
     * @param result 当前测试用例的执行结果
     * @return true 表示需要重试该用例；false 表示不再重试
     */
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;

            String methodName = result.getMethod().getMethodName();
            LogUtil.info("用例执行失败，准备进行第 " + retryCount + " 次重试，测试方法：" + methodName);

            return true;
        }

        // 达到最大重试次数，不再重试
        return false;
    }
}
