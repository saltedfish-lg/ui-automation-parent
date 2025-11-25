package com.saltedfish.framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saltedfish.framework.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * ConfigManager 负责加载和缓存框架配置。
 *
 * 特点：
 *  1. 支持多环境配置文件：
 *     - 优先按 JVM 参数 env 或 framework.env 加载，例如：
 *       -Denv=dev -> 尝试加载 framework-config-dev.json
 *     - 如果对应环境文件不存在，则回退到默认的 framework-config.json；
 *  2. 首次加载后缓存为单例，避免重复 IO；
 *  3. 如果加载失败，则使用一份内置默认配置。
 */
public final class ConfigManager {

    /**
     * 默认配置文件名称。
     */
    private static final String DEFAULT_CONFIG_FILE_NAME = "framework-config.json";

    /**
     * 配置单例缓存。
     */
    private static FrameworkConfig CONFIG_INSTANCE;

    private ConfigManager() {
        // 工具类不允许实例化
    }

    /**
     * 获取框架配置对象。
     * 线程安全：通过 synchronized 保证只初始化一次。
     *
     * @return FrameworkConfig 实例（不会为 null）
     */
    public static synchronized FrameworkConfig getConfig() {
        if (CONFIG_INSTANCE == null) {
            CONFIG_INSTANCE = loadConfigWithEnvSupport();
        }
        return CONFIG_INSTANCE;
    }

    /**
     * 带多环境支持的配置加载逻辑。
     *
     * 步骤：
     *  1. 尝试从 JVM 参数 env 或 framework.env 读取环境名，如 dev / test / prod；
     *  2. 如果存在 env，则优先尝试加载 framework-config-{env}.json；
     *  3. 如果找不到或解析失败，则回退到默认 framework-config.json；
     *  4. 如果仍失败，使用默认配置。
     *
     * @return 加载完成的配置对象
     */
    private static FrameworkConfig loadConfigWithEnvSupport() {
        ObjectMapper mapper = new ObjectMapper();

        // 1）读取环境标识（可以通过 -Denv=dev 或 -Dframework.env=dev 传入）
        String env = System.getProperty("env");
        if (env == null || env.isBlank()) {
            env = System.getProperty("framework.env");
        }

        // 2）如果指定了环境，尝试加载对应的配置文件，例如 framework-config-dev.json
        if (env != null && !env.isBlank()) {
            String envTrimmed = env.trim();
            String envFileName = "framework-config-" + envTrimmed + ".json";
            LogUtil.info("检测到环境参数 env=" + envTrimmed + "，尝试加载配置文件：" + envFileName);

            FrameworkConfig envConfig = loadConfigFromFile(mapper, envFileName);
            if (envConfig != null) {
                return envConfig;
            }

            LogUtil.warn("环境配置文件 " + envFileName + " 未找到或解析失败，将回退加载默认配置文件。");
        }

        // 3）加载默认配置文件
        FrameworkConfig defaultConfig = loadConfigFromFile(mapper, DEFAULT_CONFIG_FILE_NAME);
        if (defaultConfig != null) {
            return defaultConfig;
        }

        // 4）最终兜底：使用内置默认配置
        LogUtil.warn("未能从任何配置文件加载配置，将使用内置默认配置。");
        return defaultConfigObject();
    }

    /**
     * 从 classpath 根路径加载指定 JSON 配置文件。
     *
     * @param mapper   ObjectMapper 实例
     * @param fileName 配置文件名称
     * @return FrameworkConfig，如果文件不存在或解析失败则返回 null
     */
    private static FrameworkConfig loadConfigFromFile(ObjectMapper mapper, String fileName) {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName)) {

            if (is == null) {
                LogUtil.warn("未在 classpath 中找到配置文件：" + fileName);
                return null;
            }

            FrameworkConfig config = mapper.readValue(is, FrameworkConfig.class);
            LogUtil.info("成功加载配置文件：" + fileName);
            return config;

        } catch (IOException e) {
            LogUtil.error("解析配置文件失败：" + fileName, e);
            return null;
        }
    }

    /**
     * 构造一份内置默认配置。
     * 用于在无法加载任何配置文件时兜底。
     *
     * @return 默认配置对象
     */
    private static FrameworkConfig defaultConfigObject() {
        FrameworkConfig config = new FrameworkConfig();
        config.setBaseUrl("");
        config.setBrowser("CHROME");
        config.setHeadless(false);
        // 默认等待与超时设置
        config.setExplicitWaitSec(10);
        config.setImplicitWaitSec(2);
        config.setPageLoadTimeoutSec(30);
        config.setWeComWebhookUrl(null);
        config.setDingTalkWebhookUrl(null);
        return config;
    }
}
