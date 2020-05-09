/*
 Navicat Premium Data Transfer

 Source Server         : JustryDeng
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 06/05/2020 11:27:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `age` tinyint(1) UNSIGNED DEFAULT NULL COMMENT '年龄',
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `motto` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '座右铭',
  `birthday` datetime(0) DEFAULT NULL COMMENT '生日',
  `hobby` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '爱好',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, 'JustryDeng', 1, '男', '废言0', '1994-02-05 00:00:00', '玩游戏0');
INSERT INTO `employee` VALUES (2, 'a', 2, '男', '废言1', '1994-02-05 00:00:00', '玩游戏1');
INSERT INTO `employee` VALUES (3, 'l', 3, '女', '废言2', '1994-02-05 00:00:00', '玩游戏2');
INSERT INTO `employee` VALUES (4, 'JustryDeng', 6, '男', '废言3', '1994-02-05 00:00:00', '玩游戏3');
INSERT INTO `employee` VALUES (5, 'l', 12, '男', '废言4', '1994-02-05 00:00:00', '玩游戏4');
INSERT INTO `employee` VALUES (6, 'u', 9, '女', '废言5', '1994-02-05 00:00:00', '玩游戏5');

SET FOREIGN_KEY_CHECKS = 1;
