package org.gms.util;

public class TimeUtil {
    /**
     * 将总秒数转换为"X天X小时X分钟X秒"格式的字符串
     * @param totalSeconds 总秒数（非负整数）
     * @return 格式化后的时间字符串
     */
    public static String formatTime(int totalSeconds) {
        // 处理负数情况（默认转为0秒）
        if (totalSeconds < 0) {
            totalSeconds = 0;
        }

        // 计算各时间单位（1天=86400秒，1小时=3600秒，1分钟=60秒）
        int days = totalSeconds / 86400;
        int remainingSeconds = totalSeconds % 86400;

        int hours = remainingSeconds / 3600;
        remainingSeconds %= 3600;

        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;

        // 拼接结果（仅保留非零部分，通过I18nUtil支持国际化）
        StringBuilder timeStr = new StringBuilder();

        if (days > 0) {
            timeStr.append(I18nUtil.getMessage("TimeFormat.days", days));
        }
        if (hours > 0) {
            appendSeparator(timeStr);
            timeStr.append(I18nUtil.getMessage("TimeFormat.hours", hours));
        }
        if (minutes > 0) {
            appendSeparator(timeStr);
            timeStr.append(I18nUtil.getMessage("TimeFormat.minutes", minutes));
        }
        // 若前面无任何单位（如总秒数<60），或秒数>0，则显示秒
        if (seconds > 0 || timeStr.length() == 0) {
            appendSeparator(timeStr);
            timeStr.append(I18nUtil.getMessage("TimeFormat.seconds", seconds));
        }

        return timeStr.toString();
    }

    /**
     * 为非空字符串添加分隔符（空格）
     */
    private static void appendSeparator(StringBuilder sb) {
        if (sb.length() > 0) {
            sb.append(" ");
        }
    }
}
