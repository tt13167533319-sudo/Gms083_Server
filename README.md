本项目基于北斗083进行大量魔改和个人修改，BeiDou地址：https://github.com/BeiDouMS/BeiDou-Server   

# BeiDou由来
北斗卫星导航系统（Beidou Navigation Satellite System，简称：BDS，又称为：COMPASS，中文音译名称：BeiDou）是中国自行研制的全球卫星导航系统，也是继GPS、GLONASS之后的第三个成熟的卫星导航系统。北斗卫星导航系统（BDS）和美国GPS、俄罗斯GLONASS、欧盟GALILEO，是联合国卫星导航委员会已认定的供应商。  
北斗卫星导航系统由空间段、地面段和用户段三部分组成，可在全球范围内全天候、全天时为各类用户提供高精度、高可靠定位、导航、授时服务，并且具备短报文通信能力。经过多年发展，北斗系统已成为面向全球用户提供全天候、全天时、高精度定位、导航与授时服务的重要新型基础设施。北斗系统定位导航授时服务，通过30颗卫星，免费向全球用户提供服务，全球范围水平定位精度优于9米、垂直定位精度优于10米，测速精度优于0.2米/秒、授时精度优于20纳秒。  
北斗这一词对于中国来说，有着特殊的意义。北斗，是中国的一个卫星导航系统，也是中国自主研制的第一个卫星导航系统。既然小伙伴说这个项目也要整个天体的名字，想了半天，就叫北斗好了！这也意味着我们要做的比HeavenMS和Cosmic更加优秀和强大！  

# 开发进展
[开发进展](https://github.com/BeiDouMS/BeiDou-Server/wiki/%E5%BC%80%E5%8F%91%E8%BF%9B%E5%BA%A6)

# gms-server 服务端
- 已实现自动创建数据库，执行初始化sql脚本，只要保证mysql是启动的即可  
- 已开放api端口8686
- 已引入swagger，swagger地址：http://localhost:8686/swagger-ui/index.html
- 接口由版本控制，如：v1 v2 v3。默认的swagger标签为name = ApiConstant.LATEST，默认的RequestMapping为："/" + ApiConstant.LATEST + "/xx"
- 接口如果增加新版本且接口不需要更新，只需要把ApiConstant.LATEST指向新版本即可。如果部分接口不兼容，需要把旧接口的Tag和RequestMapping都改成指定版本，如：ApiConstant.V1。其他的，只需要把ApiConstant.LATEST指向新版本即可。
- 支持多语言，脚本和wz针对多语言会读取不同的路径：wz-zh-CN，wz-en-US，script-zh-CN，script-en-US
- 不支持MySQL8以下的版本

## 开发环境
- OpenJDK 21：https://jdk.java.net/archive/
- Intellij IDEA 2023.3及以上：https://www.jetbrains.com/idea/
- MySQL8：https://github.com/SleepNap/NapMysqlTool/releases/latest 或者 https://downloads.mysql.com/archives/community/
- Maven：https://maven.apache.org/download.cgi
- git：https://git-scm.com/downloads
- DBeaver：https://dbeaver.io/download/ 或者 Navicat Lite：https://www.navicat.com/en/download/navicat-premium-lite

# gms-ui web端

## 开发环境部署

请根据自身实际情况选择性跳过已完成的步骤

**1 安装 NodeJS v20.15.0 （LTS 版）**

下载地址：https://nodejs.org/dist/v20.15.0/node-v20.15.0-x64.msi

**2 安装 Yarn**

```shell
npm install -g yarn
```

> 如提示npm命令不存在，可能是安装NodeJS时，安装程序配置的环境变量还没有生效，小白请使用重启大法

**3 初始化前端开发环境**

在命令行进入 gms-ui 目录，然后执行命令

```shell
yarn install
```

**4 启动开发环境**

```shell
yarn dev
```

## 备注
web中所有的图片均需要联网获取，感谢 https://maplestory.io 提供给的图片接口！  

# 客户端
服务端和客户端已经打包好了在[Release](https://github.com/BeiDouMS/BeiDou-Server/releases)中，大家直接下载即可。  
如果想下载北斗客户端的**早期Beta的版本**，可以[点击这里了解更多](https://github.com/BeiDouMS/BeiDou-Server/wiki/%E5%8C%97%E6%96%97%E5%AE%A2%E6%88%B7%E7%AB%AF%E5%8F%91%E5%B8%83) 

# docker
原服务端中docker相关配置已移除，配置已独立到[新的仓库](https://github.com/BeiDouMS/BeiDou-docker)，且支持[镜像拉取](https://github.com/BeiDouMS/BeiDou-docker/pkgs/container/beidou-server-all)。想参加docker开发，欢迎在新仓库进行pr。  
[了解更多](https://github.com/BeiDouMS/BeiDou-docker)

# Wiki
发现很多同学的问题基本在Wiki中都有答案，欢迎大家去看看。另外如果发现Wiki中没有的问题，欢迎提issue，或直接补充。已将Wiki开放为所有人都可以编辑。  
[Wiki地址](https://github.com/BeiDouMS/BeiDou-Server/wiki)

# 更新记录
2025.8.23 ~ 2025.8.31  
1、增加轮回碑石功能，换图、进商城消失等  
2、增加全屏攻击实时计时函数  
    [Character.java：fullScreenAttack]  
3、增加全屏攻击时长充值函数  
    [Character.java：addFullScreenAttackTime(int addSeconds)]  
4、增加全屏攻击时长查询函数  
    [Character.java：getFullScreenAttackTime()]  
5、增强外挂检测：吸怪、无延迟、MISS、吸物、飞天（待测试）  
6、优化日志功能分文件存储  
7、GameConfig增加轮回功能、外挂检测、GM使用技能无CD等相关配置参数  
8、优化[创建角色时的非法角色名称校验]  
9、新增世界等级参数配置，及升级、经验获取相应拦截处理逻辑，修复levelpro命令在世界等级机制下可能导致无限循环的bug，支持参数管理动态调整  
10、优化only_allow_gm_login为大区配置,支持参数管理动态调整(在GameConfig.java实现)  
11、增加服务端启动时的端口冲突校验，避免端口冲突导致的启动失败  
12、增加大区开启数量参数管理，服务端启动时读取配置，开启对应数量的大区  
    [server.java：final int worldCount = Math.min(GameConstants.WORLD_NAMES.length, GameConfig.getServerByte("world_count"));]  

2025.9.1 ~ 2025.9.7  
1、修复多开限制不生效及处理逻辑漏洞  
2、增加部分登陆失败日志记录  
    [LoginPasswordHandler.java]  
3、增加多开特权设定,可能存在未知问题  
4、新增野外BOSS击杀记录：日志、数据库 [maplemap.java:killmonster]  
5、mobid.java增加野外BOSS类型判断  
6、野外BOSS刷新移植至服务端实现：修改wz,map/life 增加BOSS刷新节点  
7、新增野外BOSS刷新时间随机机制，支持Gameconfig参数配置  
    [area_boss_respawn]  
8、新增野外BOSS通用、特殊刷新时间绑定Gameconfig参数配置  
    [MapFactory.java:loadLifeRaw]  
9、修复换线轮回效果不消失  
10、增加全屏攻击系数及攻击力上限动态参数配置  
11、增加自动封号广播  

2025.9.10  
1、增加多色轮回效果，增加对应gameconfig配置 [samsara_stele_rate_special]，修复同地图存在其他玩家时切换地图轮回可能不消失的bug  
2、优化吸怪检测逻辑，避免误封  

2025.9.13 ~ 2025.9.14  
1、增加近战不挥拳功能  
2、修复黑骑士恶龙附身效果下可能会误封的bug  
3、优化吸物封号的检测强度，避免误封  
4、新增物落脚下功能在独立掉落地图（一般为BOSS远征副本地图）不生效的判定  
5、新增独立掉落机制，通过MapId.java中isSoloDropMap()配置独立掉落地图  
    [MapleMap.java：killMonster / spawnDrop]  todo：修改为配置文件配置  
6、修复独立掉落生效时，新玩家进入目标地图后，可以看到目标地图已生成的非归属掉落  
    [MapleMap.java：getMapObjectsInRange, spawnAndAddRangedMapObject]  
7、修复独立掉落地图，玩家组队时，地图掉落同步处理逻辑  
    [MapleMap.java：updatePlayerItemDropsToParty / updatePartyItemDropsToNewcomer]  
8、增加独立掉落时，金币只执行一次掉落的处理  
    [MapleMap.java：dropItemsFromMonsterOnMap]  
    [Monster.java：//独立掉落辅助方法]  
9、增加释放轮回时，当前地图强制刷新怪物一次至数量上限  
    [MapleMap.java:SummonSamsaraForceRespawn]  
10、增加全屏攻击时自动使用轮回功能  
11、修复非独立掉落时，玩家切换地图后无法正常显示已有掉落的问题  
12、增加“内面耀光”自动BUFF功能，部分职业buffList未完善  
    [Character.java：startAutoUseSkillBuff]  

2025.9.15  
1、补全自动buff功能bufflist  
    [AutoSkillBuffId.java]  
2、修复自动buff功能下线后召唤兽不消失的bug  
    [Character.java：startAutoUseSkillBuff]  
2025.9.17  
1、新增支持客户端通过config.ini静态修改分辨率配置  

2025.9.18  
1、修改角色能力面板为宽屏显示  

2025.9.20  
1、增强鼠标飞天检测逻辑，增加相应Gameconfig参数配置  
    [mouse_fly_check]  
2、增强全屏吸物检测逻辑，，增加相应Gameconfig参数配置  
    [pick_item_faraway_check]  
3、宠物自动吃食品  
    [Character.java: runFullnessSchedule]  
4、挂机时无法对怪物造成伤害  
5、轮回技能改为技能书学习，而不是出生时即有  
6、修复挂机换线不停止的bug  
7、修复GM 净化技能 ，待验证 todo  
    [SpecialMoveHandler.java：132行 p.skip(11) -> p.skip(7)]  
8、新增仅GM登录和多开、异地超限的弹框提示  
    [c.sendPacket(PacketCreator.serverNotice(1,messgae));]  
9、新增自动注册一个IP、HWID只允许注册一个账号  
    [LoginPasswordHandler.java]  
10、新增注册后在accounts表记录ip_reg和hwid_reg，同时登录后记录ip  
11、增加hwid截取方法  
    [Hwid.java：formatHwid]  

2025.9.21  
1、优化samsara_stele_item参数设置，0为不启用  
2、修复挂机宠物不自动吃血蓝的bug  
3、增加地图归属批量使用及相关日志记录的功能  
    [MapleMap.java：claimOwnership() / checkMapOwnerActivity()]  
    [MapOwnerClaimCommand.java]  
4、新增自动挂机时的金币、经验统计  

2025.9.22  
1、优化独立掉落处理逻辑，增加非独立掉落时，掉落物玩家之间是否可见参数配置  
    [seen_area_map_drop]  
    [MapleMap.java：getMapObjectsInRange, spawnAndAddRangedMapObject, updatePlayerItemDropsToParty, updatePartyItemDropsToNewcomer]  

2025.9.23  
1、修复测谎仪，增加测谎仪自定义脚本  
    [UseLieDetectorHandler.java]  
    [测谎仪.js]  
2、新增针对长时间挂机玩家的自动测谎机制  
    [World.java：lieDecetorSchedule]  
    [LieDecetorTask.java]  
    [Character.java：changeMapInternal]  
    [PlayerLoggedinHandler.java]  
    [Client.java：changeChannel]  
3、增加测谎仪对应Gameconfig参数  
    [use_auto_lie_decetor/ auto_lie_decetor_interval]  

2025.9.24  
1、增加独立掉落处理逻辑，仅为怪物为BOSS时为每个玩家单独处理掉落  
    [MapleMap.java：killMonster]  

2025.9.25 - 2025.10.8  
1、防御崩坏突破后增加取消怪物物理免疫、魔法免疫效果  
    [StatEffect.java：applyMonsterBuff]  
    [Monster.java：debuffMob]  
2、葵花宝典突破后不消耗斗气  
    [StatEffect.java：applyBuffEffect]  
3、自动buff：增加冷却技能检测，仅在非冷却时使用；使用葵花宝典时斗气点不足时不应用技能  
    [Character.java：startAutoUseSkillBuff]  
4、客户端增加伤害测试地图  
    [仙人沉睡之处：993184000]  
5、远征事件等增加怪物受到攻击的回调函数 todo：可能存在扎昆手臂不统计的问题，待确认  
    [MapleMap.java：damageMonster]  
    [EventInstanceManager.java：damageMonster]  
6、新增伤害测试功能  
    [MapleMap.java：damageMonster / showTestDamageInfo]  
7、修复释放轮回后BOSS立即刷新的BUG  
8、新增愤怒之火、火眼、敛财术、聚财术对队员不应用，增加相应GameConfig参数  
    [StatEffect.java：isPartyBuff]  
    [GameConfig：fighter_rage_apply_party / sharp_eyes_apply_party / meso_up_apply_party]  
9、新增圣骑士属性攻击不消除BUFF  
    [CloseRangeDamageHandler.java：chr.cancelEffectFromBuffStat(BuffStat.WK_CHARGE)]  
    [GameConfig：charge_blow_cancel_buff]  
10、完成战士、弓箭手职业技改  
11、修复自动封号检测中针对火眼加成计算逻辑，适配神射手和箭神WZ中数值逻辑不同  
    [AbstractDealDamageHandler.java：parseDamage]  
12、指定记录日志  
    [  
    Logger logger = LoggerFactory.getLogger("AreaBoss");  
    <logger name="AreaBoss" level="INFO" additivity="false">  
        <appender-ref ref="bossFile"/> <!-- 定向到 BOSS 专属日志文件 野外BOSS-->  
        <appender-ref ref="Console"/> <!-- 可选：同时输出到控制台，便于调试 -->  
    </logger>  
    ]  
13、增加部分js函数  
    [AbstractPlayerInteraction.java： getSkillName(skillid: number): string]  
    [Character.java： isMyJobSkill(skillid: number): boolean]  
14、新增武器用毒液是否对BOSS生效的判断  
    [Monster.java：applyStatus]  
    [GameConfig：venom_apply_boss]  
15、弓、弩矢、飞镖堆叠数量修改为默认，修复飞镖充值上限不匹配技改问题  
    [ItemInformationProvider.java：isSpecialSlotItem]  
    [ItemInformationProvider.java：getExtraSlotMaxFromPlayer]  
16、修复平衡之怒数量为0时，无限补充下一个飞镖  
17、平衡之怒适配暗器伤人技能  
18、新增敛财术额外触发效果  
    [StatEffect.java：applyTo]  
    [GameConfig：pick_pocket_special_effect]  
19、完成飞侠、海盗技改  
20、新增船长武装技能不损坏设定  
    [Character.java：decreaseBattleshipHp]  
    [GameConfig：battle_ship_destroy_level]  
21、新增冲锋队长 “能量获得”技能能量获取效率处理  
    [Character.java：handleEnergyChargeGain]  
22、修复伤害计算时攻击多个怪物可能造成的叠乘bug  
    [AbstractDealDamageHandler.java：long currentDmgMax = calcDmgMax;]  

2025.10.9  
1、新增GM指令  
    [maxSkillB]  
2025.10.30  
1、完成全职业技改，新增相关突破手册wz/script  

2025.11.9  
1、同步时装道具、装备道具  
2、同步脸型、发型  
3、同步骑宠 todo：骑宠名称、万能马鞍、测试闪退  
4、同步椅子 todo：修复坐标、特效  
5、重置射手村地图 回退  
6、修复快递  
7、优化cashshop手动数据库插入修改数据，无法购买的问题  
    [CashShop.java：getItem()]  

2025.11.28  
1、修改商城上架物品WZ  