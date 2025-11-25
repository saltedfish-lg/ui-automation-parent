package com.saltedfish.framework.testng.assertion;

import com.saltedfish.framework.utils.LogUtil;
import org.testng.Assert;

/**
 * AssertHelper 统一封装 TestNG 断言，增加日志输出，
 * 避免在测试代码中到处直接调用 Assert，方便后续统一扩展。
 */
public final class AssertHelper {

    private AssertHelper() {
        // 工具类禁止实例化
    }

    /**
     * 断言条件为 true。
     *
     * @param condition 实际条件
     * @param message   断言失败时的提示信息
     */
    public static void assertTrue(boolean condition, String message) {
        LogUtil.info("断言 [TRUE]，期望为 true，描述：" + message);
        Assert.assertTrue(condition, message);
    }

    /**
     * 断言条件为 false。
     *
     * @param condition 实际条件
     * @param message   断言失败时的提示信息
     */
    public static void assertFalse(boolean condition, String message) {
        LogUtil.info("断言 [FALSE]，期望为 false，描述：" + message);
        Assert.assertFalse(condition, message);
    }

    /**
     * 断言两个对象相等。
     *
     * @param actual   实际值
     * @param expected 期望值
     * @param message  断言失败时提示信息
     */
    public static void assertEquals(Object actual, Object expected, String message) {
        LogUtil.info("断言 [EQUALS]，期望值：" + expected + "，实际值：" + actual + "，描述：" + message);
        Assert.assertEquals(actual, expected, message);
    }

    /**
     * 断言对象不为 null。
     *
     * @param object  实际对象
     * @param message 断言失败时的提示信息
     */
    public static void assertNotNull(Object object, String message) {
        LogUtil.info("断言 [NOT NULL]，期望对象不为 null，描述：" + message);
        Assert.assertNotNull(object, message);
    }

    /**
     * 断言对象为 null。
     *
     * @param object  实际对象
     * @param message 断言失败时的提示信息
     */
    public static void assertNull(Object object, String message) {
        LogUtil.info("断言 [NULL]，期望对象为 null，描述：" + message);
        Assert.assertNull(object, message);
    }

    /**
     * 断言字符串 actual 中包含子串 expectedSubstring。
     *
     * @param actual           实际字符串
     * @param expectedSubstring 期望包含的子串
     * @param message          断言失败时的提示信息
     */
    public static void assertContains(String actual, String expectedSubstring, String message) {
        LogUtil.info("断言 [CONTAINS]，期望字符串包含子串。实际值：" + actual + "，子串：" + expectedSubstring + "，描述：" + message);
        if (actual == null || expectedSubstring == null) {
            Assert.fail("进行包含断言时存在 null 值，actual=" + actual + ", expectedSubstring=" + expectedSubstring + "，描述：" + message);
        }
        Assert.assertTrue(actual.contains(expectedSubstring),
                "期望字符串 [" + actual + "] 包含子串 [" + expectedSubstring + "]，描述：" + message);
    }
}
