package org.gms.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class NumberUtil {

    /**
     * 按视觉宽度填充字符串（中文/全角字符计2宽度，英文/数字计1宽度）
     * @param fillChar 填充字符（建议用半角空格' '，占1宽度）
     * @param targetVisualLength 目标总视觉宽度
     * @param content 待处理内容
     * @return 填充后的字符串（总视觉宽度为targetVisualLength）
     */
    public static String format(char fillChar, int targetVisualLength, String content) {
        if (content == null) {
            content = "";
        }

        // 计算内容的视觉宽度（中文2，英文1）
        int contentVisualWidth = calculateVisualWidth(content);

        // 内容视觉宽度超过目标，直接返回原内容
        if (contentVisualWidth > targetVisualLength) {
            return content;
        }

        // 计算需要填充的视觉宽度
        int needFillWidth = targetVisualLength - contentVisualWidth;
        if (needFillWidth <= 0) {
            return content;
        }

        // 生成填充字符串（假设填充字符为1宽度，如半角空格）
        // 若填充字符是2宽度（如中文），需调整填充数量：needFillWidth / 2（有余数则加1）
        String fillStr = generateFillString(fillChar, needFillWidth);

        return content + fillStr;
    }

    /**
     * 计算字符串的视觉宽度（中文/全角字符计2，其他计1）
     */
    private static int calculateVisualWidth(String str) {
        int width = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 中文及全角字符（Unicode范围）
            if (c >= '\u4e00' && c <= '\u9fa5' ||  // 常用汉字
                    c >= '\uff00' && c <= '\uffef') {   // 全角符号
                width += 2;
            } else {
                width += 1;  // 英文、数字、半角符号等
            }
        }
        return width;
    }

    /**
     * 生成填充字符串（根据填充字符的视觉宽度计算数量）
     */
    private static String generateFillString(char fillChar, int needFillWidth) {
        // 计算填充字符的视觉宽度
        int fillCharWidth = (fillChar >= '\u4e00' && fillChar <= '\u9fa5') ||
                (fillChar >= '\uff00' && fillChar <= '\uffef') ? 2 : 1;

        // 计算需要填充的字符数量
        int fillCount;
        if (fillCharWidth == 1) {
            fillCount = needFillWidth;  // 1宽度字符，直接等于需要的宽度
        } else {
            fillCount = (needFillWidth + 1) / 2;  // 2宽度字符，向上取整
        }

        return String.valueOf(fillChar).repeat(fillCount);
    }

    /**
     * 将int类型数字转换为以"万"为单位的字符串
     * @param number 待转换的整数
     * @return 转换后的字符串（如：1234 → "1234"，12345 → "1.2万"）
     */
    public static String toWan(int number) {
        return toWan((long) number);
    }

    /**
     * 将long类型数字转换为以"万"为单位的字符串
     * @param number 待转换的长整数
     * @return 转换后的字符串（如：9999 → "9999"，10000 → "1万"，15600 → "1.6万"）
     */
    public static String toWan(long number) {
        if (number < 10000) {
            return String.valueOf(number);
        }
        // 除以1万并保留一位小数
        double value = number / 10000.0;
        // 处理整数情况（如2.0万 → 2万）
        if (value == (long) value) {
            return (long) value + "万";
        }
        // 四舍五入保留一位小数
        return String.format("%.f万", value);
    }

    /**
     * 将double类型数字转换为以"万"为单位的字符串
     * @param number 待转换的浮点数
     * @param decimalPlaces 保留的小数位数
     * @return 转换后的字符串（如：12345.6 → "1.2万"，25600.33 → "2.6万"）
     */
    public static String toWan(double number, int decimalPlaces) {
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("小数位数不能为负数");
        }
        if (number < 10000) {
            return String.format("%." + decimalPlaces + "f", number);
        }
        double value = number / 10000.0;
        // 格式化输出指定小数位数
        return String.format("%." + decimalPlaces + "f万", value);
    }

    /**
     * 重载方法，默认保留一位小数
     * @param number 待转换的浮点数
     * @return 转换后的字符串
     */
    public static String toWan(double number) {
        return toWan(number, 1);
    }
}
