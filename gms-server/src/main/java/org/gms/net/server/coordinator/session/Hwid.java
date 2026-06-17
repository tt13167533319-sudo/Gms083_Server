package org.gms.net.server.coordinator.session;

import java.util.regex.Pattern;

public record Hwid(String hwid) {
    private static final int HWID_LENGTH = 8;
    // First part is a mac address (without dashes), second part is the hwid
    private static final Pattern VALID_HOST_STRING_PATTERN = Pattern.compile("[0-9A-F]{12}_[0-9A-F]{8}");

    private static boolean isValidHostString(String hostString) {
        return VALID_HOST_STRING_PATTERN.matcher(hostString).matches();
    }

    public static Hwid fromHostString(String hostString) throws IllegalArgumentException {
        if (hostString == null || !isValidHostString(hostString)) {
            throw new IllegalArgumentException("hostString has invalid format");
        }

        final String[] split = hostString.split("_");
        if (split.length != 2 || split[1].length() != HWID_LENGTH) {
            throw new IllegalArgumentException("Hwid validation failed for hwid: " + hostString);
        }

        return new Hwid(split[1]);
    }

    public static String formatHwid(String originalHwid) {
        // 检查输入是否为空或格式异常
        if (originalHwid == null || originalHwid.isEmpty()) {
            return "";
        }

        // 定位 "hwid=" 的位置
        int startIndex = originalHwid.indexOf("hwid=");
        if (startIndex == -1) { // 不包含 "hwid=" 标识，返回原始值或空
            return originalHwid;
        }

        // 跳过 "hwid=" 本身（长度为5），得到目标内容的起始位置
        startIndex += "hwid=".length();

        // 定位结束符 "]" 的位置（从起始位置开始查找）
        int endIndex = originalHwid.indexOf("]", startIndex);
        if (endIndex == -1) { // 没有找到结束符，返回从起始位置到结尾的内容
            return originalHwid.substring(startIndex);
        }

        // 截取 "hwid=" 之后、"]" 之前的内容
        return originalHwid.substring(startIndex, endIndex);
    }
}
