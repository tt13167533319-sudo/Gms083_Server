/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80039
Source Host           : localhost:3306
Source Database       : beidou

Target Server Type    : MYSQL
Target Server Version : 80039
File Encoding         : 65001

Date: 2025-09-05 23:49:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area_boss_kill_log
-- ----------------------------
DROP TABLE IF EXISTS `area_boss_kill_log`;
CREATE TABLE `area_boss_kill_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL COMMENT '归属玩家角色ID（-1表示无归属玩家）',
  `player_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '归属玩家角色名称（"未知玩家"表示无归属）',
  `boss_id` int NOT NULL COMMENT '被击杀BOSS的怪物ID',
  `boss_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '被击杀BOSS的名称',
  `kill_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'BOSS被击杀的时间（默认当前时间）',
  `channel_id` tinyint NOT NULL COMMENT '击杀所在频道ID（0表示未知频道）',
  `map_id` int NOT NULL COMMENT '击杀所在地图ID（0表示未知地图）',
  PRIMARY KEY (`id`),
  KEY `idx_player` (`player_id`) USING BTREE,
  KEY `idx_boss` (`boss_id`) USING BTREE,
  KEY `idx_kill_time` (`kill_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='野外BOSS击杀记录表';
