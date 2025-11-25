package com.saltedfish.framework.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LogUtil 对 java.util.logging 进行简单封装。
 *
 * 说明：
 *  - 为了避免额外引入日志框架（如 slf4j / logback），此处直接使用 JDK 自带日志；
 *  - 后续如果项目统一使用 slf4j，可以在本类中替换实现，而不改动业务代码。
 */
public final class LogUtil {

    /**
     * 使用类名作为 logger 名称，便于定位日志来源。
     */
    private static final Logger LOGGER = Logger.getLogger("UI-AUTOMATION-FRAMEWORK");

    private LogUtil() {
        // 工具类禁止实例化
    }

    /**
     * 记录 INFO 级别日志。
     *
     * @param message 日志内容
     */
    public static void info(String message) {
        LOGGER.log(Level.INFO, message);
    }

    /**
     * 记录 WARN 级别日志。
     *
     * @param message 日志内容
     */
    public static void warn(String message) {
        LOGGER.log(Level.WARNING, message);
    }

    /**
     * 记录 ERROR 级别日志。
     *
     * @param message 日志内容
     * @param t       异常对象
     */
    public static void error(String message, Throwable t) {
        LOGGER.log(Level.SEVERE, message, t);
    }
}

