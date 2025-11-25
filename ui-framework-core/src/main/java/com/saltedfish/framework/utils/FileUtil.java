package com.saltedfish.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * FileUtil 封装常见的文件操作工具方法。
 * 当前主要用于：
 *  - 生成带时间戳的文件名；
 *  - 将字节数组写入文件（如截图落盘）。
 */
public final class FileUtil {

    /**
     * 默认的时间戳格式：yyyyMMdd_HHmmss_SSS
     * 示例：20251117_153045_123
     */
    private static final DateTimeFormatter TS_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private FileUtil() {
        // 工具类禁止实例化
    }

    /**
     * 将字节数组写入文件，如果父目录不存在则自动创建。
     *
     * @param data     要写入的字节数据
     * @param filePath 目标文件路径（绝对路径或相对路径）
     */
    public static void writeBytesToFile(byte[] data, String filePath) {
        File file = new File(filePath);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            boolean created = parent.mkdirs();
            if (!created) {
                LogUtil.warn("创建目录失败：" + parent.getAbsolutePath());
            }
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
            fos.flush();
            LogUtil.info("成功写入文件：" + file.getAbsolutePath());
        } catch (IOException e) {
            LogUtil.error("写入文件失败：" + file.getAbsolutePath(), e);
        }
    }

    /**
     * 生成带时间戳的文件名。
     *
     * @param prefix 文件名前缀，例如 "screenshot_"
     * @param suffix 文件名后缀，例如 ".png"
     * @return 形如 "screenshot_20251117_153045_123.png" 的文件名
     */
    public static String generateTimestampFileName(String prefix, String suffix) {
        String ts = LocalDateTime.now().format(TS_FORMATTER);
        return prefix + ts + suffix;
    }
}

