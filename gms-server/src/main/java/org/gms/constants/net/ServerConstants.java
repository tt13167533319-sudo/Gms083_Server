package org.gms.constants.net;

public class ServerConstants {

    //Server Version
    public static final short VERSION = 83;

    //Debug Variables
    public static int[] DEBUG_VALUES = new int[10];             // Field designed for packet testing purposes
    //创建角色时的非法角色名称校验
    public static final String[] BLOCKED_NAMES = {"admin", "owner", "moderator", "intern", "donor", "administrator", "FREDRICK", "help", "helper", "alert", "notice", "maplestory", "fuck", "wizet", "fucking", "negro", "fuk", "fuc", "penis", "pussy", "asshole", "gay",
            "nigger", "homo", "suck", "cum", "shit", "shitty", "condom", "security", "official", "rape", "nigga", "sex", "tit", "boner", "orgy", "clit", "asshole", "fatass", "bitch", "support", "gamemaster", "cock", "gaay", "gm",
            "operate", "master", "sysop", "party", "GameMaster", "community", "message", "event", "test", "meso", "Scania", "yata", "AsiaSoft", "henesys"
            ,"GM", "gm", "童心", "大家庭", "管理员", "管理", "所有者", "阴茎", "阴部", "逼", "强奸", "奸", "社区", "消息", "事件", "通知", "通告", "广播", "测试", "巡逻", "挂", "吸怪", "外挂", "操", "烂", "屎", "性", "乳头"};
    public static final String LEVEL_200 = "[祝贺] %s 等级达到了 %d 级! 让我们恭喜TA！";

    public static final String BUILD_VERSION = "2.0";
    public static final String BUILD_TIME = "2025-08-24 23:45:59";
}
