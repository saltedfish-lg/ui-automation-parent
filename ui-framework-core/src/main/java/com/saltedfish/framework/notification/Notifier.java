package com.saltedfish.framework.notification;

/**
 * Notifier 接口定义了统一的“通知”行为抽象。
 *
 * 无论是企业微信、钉钉或其他 IM 工具，
 * 只要实现本接口，就可以被框架统一调用而无需修改上层代码。
 */
public interface Notifier {

    /**
     * 发送一条简单的文本消息。
     *
     * @param title   消息标题，用于概要描述此次通知，例如“UI 自动化执行结果”
     * @param content 消息正文，通常包含通过、失败、跳过等统计信息
     */
    void sendText(String title, String content);
}


