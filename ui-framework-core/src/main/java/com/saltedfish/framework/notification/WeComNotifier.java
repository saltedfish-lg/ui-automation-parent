package com.saltedfish.framework.notification;

import com.saltedfish.framework.utils.LogUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * WeComNotifier 通过企业微信机器人 WebHook 发送通知消息。
 *
 * 使用前提：
 *  1. 在企业微信群中添加“自定义机器人”，获得 WebHook 地址；
 *  2. 将 WebHook 地址配置到 FrameworkConfig 中；
 *  3. 由 SuiteListener 或其他调用方构造本类并调用 sendText。
 */
public class WeComNotifier implements Notifier {

    /**
     * 企业微信机器人要求的 JSON Content-Type。
     */
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    /**
     * 复用 OkHttpClient 实例，避免每次创建带来的资源浪费。
     */
    private final OkHttpClient client = new OkHttpClient();

    /**
     * 企业微信机器人 WebHook 地址。
     */
    private final String webhookUrl;

    /**
     * 构造函数通过参数注入 WebHook 地址，避免硬编码。
     *
     * @param webhookUrl 企业微信机器人 WebHook 地址
     */
    public WeComNotifier(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    @Override
    public void sendText(String title, String content) {
        if (webhookUrl == null || webhookUrl.isBlank()) {
            LogUtil.warn("企业微信 WebHook 地址为空，跳过消息发送。");
            return;
        }

        // 企业微信文本消息的 JSON 格式
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
            LogUtil.info("企业微信通知发送完成，响应码: " + response.code());
        } catch (IOException e) {
            LogUtil.error("企业微信通知发送失败。", e);
        }
    }
}
