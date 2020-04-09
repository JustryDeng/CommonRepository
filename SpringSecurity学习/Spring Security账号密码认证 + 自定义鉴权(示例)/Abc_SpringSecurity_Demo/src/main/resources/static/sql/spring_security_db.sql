/*
 Navicat Premium Data Transfer

 Source Server         : JustryDeng
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : spring_security_db

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 09/04/2020 15:43:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for api_resource
-- ----------------------------
DROP TABLE IF EXISTS `api_resource`;
CREATE TABLE `api_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` int(11) NOT NULL DEFAULT 0 COMMENT '父资源id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名',
  `path` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源路径',
  `request_method` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求该资源所需要的方法(多个之间使用逗号分割)',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资源描述',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `p_id_idx`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'api资源表(注：不在这个表中的资源，也不会被要求鉴权)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of api_resource
-- ----------------------------
INSERT INTO `api_resource` VALUES (1, 0, '招呼页', '/hello', 'GET', NULL);
INSERT INTO `api_resource` VALUES (3, 0, '首页', '/index', 'GET,POST', NULL);
INSERT INTO `api_resource` VALUES (5, 0, '登录失败页', '/login/failed', 'GET', NULL);
INSERT INTO `api_resource` VALUES (7, 0, '登出成功页', '/logout/success', 'GET', NULL);
INSERT INTO `api_resource` VALUES (8, 0, '鉴权失败页', '/403', 'GET', NULL);
INSERT INTO `api_resource` VALUES (10, 0, '普通用户页', '/user', 'GET', NULL);
INSERT INTO `api_resource` VALUES (12, 0, '数据库DBA页', '/dba', 'GET', NULL);
INSERT INTO `api_resource` VALUES (14, 0, '超级管理员页', '/admin', 'GET', NULL);

-- ----------------------------
-- Table structure for mid_role_api_resource
-- ----------------------------
DROP TABLE IF EXISTS `mid_role_api_resource`;
CREATE TABLE `mid_role_api_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `api_resource_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'api资源id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id_idx`(`role_id`) USING BTREE,
  INDEX `api_resource_id_idx`(`api_resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色&api资源 中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mid_role_api_resource
-- ----------------------------
INSERT INTO `mid_role_api_resource` VALUES (7, 1, '10');
INSERT INTO `mid_role_api_resource` VALUES (13, 2, '12');
INSERT INTO `mid_role_api_resource` VALUES (14, 2, '10');
INSERT INTO `mid_role_api_resource` VALUES (15, 3, '10');
INSERT INTO `mid_role_api_resource` VALUES (16, 3, '12');
INSERT INTO `mid_role_api_resource` VALUES (19, 3, '14');
INSERT INTO `mid_role_api_resource` VALUES (20, 5, '1');
INSERT INTO `mid_role_api_resource` VALUES (21, 5, '3');
INSERT INTO `mid_role_api_resource` VALUES (22, 5, '5');
INSERT INTO `mid_role_api_resource` VALUES (23, 5, '7');
INSERT INTO `mid_role_api_resource` VALUES (24, 5, '8');

-- ----------------------------
-- Table structure for mid_user_role
-- ----------------------------
DROP TABLE IF EXISTS `mid_user_role`;
CREATE TABLE `mid_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) NOT NULL DEFAULT 0 COMMENT '角色id',
  `user_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id_idx`(`role_id`) USING BTREE,
  INDEX `user_id_idx`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户&角色 中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mid_user_role
-- ----------------------------
INSERT INTO `mid_user_role` VALUES (1, 1, '1');
INSERT INTO `mid_user_role` VALUES (2, 2, '2');
INSERT INTO `mid_user_role` VALUES (4, 3, '3');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_unique`(`name`) USING BTREE COMMENT '角色唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'USER', '普通用户');
INSERT INTO `role` VALUES (2, 'DBA', '数据库管理员');
INSERT INTO `role` VALUES (3, 'ADMIN', '超级管理员');
INSERT INTO `role` VALUES (5, 'ANY', '任何人(每一个人都是ANY)');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account_no` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户登录账号',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户登录密码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account_no_unique`(`account_no`) USING BTREE COMMENT '账号唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zhangsan', 'zhangsan123', '张三');
INSERT INTO `user` VALUES (2, 'lisi', 'lisi123', '李四');
INSERT INTO `user` VALUES (3, 'wangwu', 'wangwu123', '王五');

SET FOREIGN_KEY_CHECKS = 1;
