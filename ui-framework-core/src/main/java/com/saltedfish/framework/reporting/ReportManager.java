package com.saltedfish.framework.reporting;

import com.saltedfish.framework.utils.LogUtil;

/**
 * ReportManager 用于封装与报告相关的通用逻辑。
 *
 * 当前实现较为简单，只是对日志做了一层封装。
 * 真正的可视化报告主要交给 Allure（在 TestNG 适配层与 TestListener 中处理）。
 *
 * 如果未来需要存储自定义的步骤信息到数据库 / 文件，可以在这里扩展。
 */
public final class ReportManager {

    private ReportManager() {
        // 工具类禁止实例化
    }

    /**
     * 记录一个普通步骤信息。
     *
     * @param message 步骤描述
     */
    public static void logStep(String message) {
        LogUtil.info("[STEP] " + message);
    }

    /**
     * 记录一个警告步骤信息。
     *
     * @param message 步骤描述
     */
    public static void logWarning(String message) {
        LogUtil.warn("[STEP][WARN] " + message);
    }
}

