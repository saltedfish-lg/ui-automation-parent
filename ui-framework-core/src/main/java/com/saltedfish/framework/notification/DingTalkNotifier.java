package com.saltedfish.framework.notification;

import com.saltedfish.framework.utils.LogUtil;
import okhttp3.*;

import java.io.IOException;

/**
 * DingTalkNotifier 用于向钉钉自定义机器人发送文本消息。
 *
 * 使用前提：
 *  1. 在钉钉群中添加“自定义机器人”，获得 WebHook 地址；
 *  2. 将 WebHook 地址配置到 FrameworkConfig 中；
 *  3. 由 SuiteListener 或其他调用方构造本类并调用 sendText。
 */
public class DingTalkNotifier implements Notifier {

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();

    /**
     * 钉钉机器人 WebHook 地址。
     */
    private final String webhookUrl;

    public DingTalkNotifier(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    @Override
    public void sendText(String title, String content) {
        if (webhookUrl == null || webhookUrl.isBlank()) {
            LogUtil.warn("钉钉 WebHook 地址为空，跳过消息发送。");
            return;
        }

        // 钉钉文本消息 JSON 格式
        String jsonBody = """
                {
                  "msgtype": "text",
                  "text": {
                    "content": "%s\\n%s"
                  }
                }
                """.formatted(title, content);

        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            LogUtil.info("钉钉通知发送完成，响应码: " + response.code());
        } catch (IOException e) {
            LogUtil.error("钉钉通知发送失败。", e);
        }
    }
}

