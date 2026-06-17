package org.gms.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 时间计算工具类（对应 JS 中的时间方法）
 */
public class TimeCalculateUtil {
    // 时区（默认上海，可根据服务器配置调整）
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
    // 时间格式化器（对应 JS sh.formatDate）
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取距离次日7点的时间对象（对应 JS getMillisecondsUntilTomorrow7AM）
     */
    public static LocalDateTime getTomorrow7AM() {
        LocalDateTime now = LocalDateTime.now(ZONE_ID);
        LocalDateTime tomorrow7AM = now.plusDays(now.getHour() < 7 ? 0 : 1)
                .withHour(7)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return tomorrow7AM;
    }

    /**
     * 获取距离下周五7点的时间对象（对应 JS getMillisecondsUntilFriday7AM）
     */
    public static LocalDateTime getNextFriday7AM() {
        LocalDateTime now = LocalDateTime.now(ZONE_ID);
        DayOfWeek currentDay = now.getDayOfWeek();
        int currentHour = now.getHour();

        // 计算距离下周五的天数（Java DayOfWeek：MONDAY=1，FRIDAY=5，SUNDAY=7）
        int daysUntilFriday;
        if (currentDay.getValue() < 5) {
            daysUntilFriday = 5 - currentDay.getValue();
        } else if (currentDay.getValue() == 5) {
            // 当天是周五：若当前时间 <7 点则当天，否则下周五
            daysUntilFriday = currentHour < 7 ? 0 : 7;
        } else {
            daysUntilFriday = 5 + (7 - currentDay.getValue());
        }

        // 构建下周五7点时间
        LocalDateTime nextFriday7AM = now.plusDays(daysUntilFriday)
                .withHour(7)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return nextFriday7AM;
    }

    /**
     * 给指定时间增加小时数（对应 JS addEventValueByHour 中的时间计算）
     */
    public static LocalDateTime addHours(LocalDateTime time, int addHours) {
        return time.plusHours(addHours);
    }

    /**
     * 时间对象转字符串（对应 JS sh.formatDate）
     */
    public static String format(LocalDateTime time) {
        return time.format(DATE_TIME_FORMATTER);
    }
}