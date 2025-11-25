package com.saltedfish.framework.driver;


/**
 * 浏览器类型的枚举定义。
 * 通过枚举而不是字符串，避免魔法字符串导致的拼写错误。
 * 未来要扩展新浏览器类型，只需在此新增枚举常量，并在 DriverFactory 中补充创建逻辑。
 */
public enum DriverType {

    /**
     * 谷歌浏览器。
     */
    CHROME,

    /**
     * 微软 Edge 浏览器。
     */
    EDGE,

    /**
     * 火狐浏览器。
     */
    FIREFOX;

    /**
     * 从字符串安全转换为 BrowserType 枚举。
     *
     * @param value 配置中的字符串，例如 "chrome" / "CHROME" / "edge"
     * @return 对应的 BrowserType，如果无法识别则默认返回 CHROME
     */
    public static DriverType fromString(String value) {
        if (value == null || value.isBlank()) {
            return CHROME;
        }
        String normalized = value.trim().toUpperCase();
        return switch (normalized) {
            case "EDGE" -> EDGE;
            case "FIREFOX", "FF" -> FIREFOX;
            default -> CHROME;
        };
    }
}
