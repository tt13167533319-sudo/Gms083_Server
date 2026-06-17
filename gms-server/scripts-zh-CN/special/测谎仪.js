let status;
let chr;
let totalOptions; // 选项总数（随机生成）
let correctIndex; // 预定义的正确选项位置（随机生成）
let normalIconId = 2000000; // 普通药水图标
let correctIconId = 2000005; // 正确药水图标
let errorCount = 0;
let correctCount = 0; //正确次数
let MAX_CORRECT = 0; //需要的正确次数
const MAX_ERROR = 3; // 最大错误次数

function start() {
    status = -1;
    chr = cm.getPlayer();
    MAX_CORRECT = Math.floor(Math.random() * 2 + 2);
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode === -1) {
        cm.dispose();
        return;
    }

    // 处理关闭/取消操作
    if (mode === 0 && type > 0) {
        cm.dispose();
        return;
    }

    // 更新状态（前进/后退）
    if (mode === 1) {
        status++;
    } else {
        status--;
    }

    // 状态0：生成随机选项并展示
    if (status === 0) {
        // 1. 完全随机生成选项总数（10-30之间，可调整范围）
        totalOptions = Math.floor(Math.random() * 21) + 10; // 10~30之间的随机数

        // 2. 随机指定正确选项的位置（1~totalOptions之间，确保在选项范围内）
        correctIndex = Math.floor(Math.random() * totalOptions) + 1;

        // 3. 构建选项列表（随机分布正确药水）
        let say = "[ #i2190000# #b#z2190000# ]\r\n\r\n";
        say += "#d从下方选项中选取 #r#i" + correctIconId + "# #z" + correctIconId + "#\r\n";
        say += "#d答对 #r" + correctCount + " / " + MAX_CORRECT + " #d次后通过验证，";
        say += "#d再失败 #r" + (MAX_ERROR - errorCount) + " #d次后您将被传送回城\r\n\r\n";

        // 循环生成选项（每5个选项换行）
        for (let i = 1; i <= totalOptions; i++) {
            // 随机位置放置正确药水（用correctIconId），其他用普通药水（normalIconId）
            const icon = (i === correctIndex) ? correctIconId : normalIconId;
            say += "#L" + i + "#  #i" + icon + "##l  ";

            // 每5个选项换行，优化显示
            if (i % 5 === 0) {
                say += "\r\n\r\n";
            }
        }

        // 发送选项对话框
        cm.sendSimple(say, 3);
    }

    // 状态1：判断用户选择是否正确
    else if (status === 1) {
        const userSelection = selection; // 用户选中的选项序号

        // 核心判断：用户选中的序号是否等于预定义的正确选项位置
        if (userSelection !== correctIndex) {
            // 错误处理
            errorCount++;
            if (errorCount >= MAX_ERROR) {
                // 达到最大错误次数，传送回城
                cm.warp(100000000);
                chr.dropMessage(5, "未通过验证，已将您传送回城");
                cm.dispose();
            } else {
                // 未达最大次数，重新生成选项（回到状态0）
                status = -1;
                action(1, 0, 0);
            }
        } else {
            // 选择正确，验证通过
            correctCount++; 
            if (correctCount >= MAX_CORRECT) {
                chr.setInMapTimeRecord(+new Date());
                chr.dropMessage(5, "通过本次验证，祝您游戏愉快");
                cm.dispose();
            } else {
                // 未达最大次数，重新生成选项（回到状态0）
                status = -1;
                action(1, 0, 0);
            }
        }
    }
}
