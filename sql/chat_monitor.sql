/*
Navicat MySQL Data Transfer

Source Server         : 10.1.1.253(MAC Mini)
Source Server Version : 50712
Source Host           : 10.1.1.253:3306
Source Database       : chat_monitor

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2018-09-03 16:06:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_game
-- ----------------------------
DROP TABLE IF EXISTS `admin_game`;
CREATE TABLE `admin_game` (
  `game_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `game_name` varchar(255) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_game
-- ----------------------------
INSERT INTO `admin_game` VALUES ('1000', '焚天', '1');
INSERT INTO `admin_game` VALUES ('1001', '勇者大作战', '2');
INSERT INTO `admin_game` VALUES ('1002', '绝地兽人', '3');
INSERT INTO `admin_game` VALUES ('1003', 'whq1121', '1');
INSERT INTO `admin_game` VALUES ('1004', '请问企鹅', '1');
INSERT INTO `admin_game` VALUES ('1005', '纤维球', '1');
INSERT INTO `admin_game` VALUES ('1006', 'asd12', '1');
INSERT INTO `admin_game` VALUES ('1007', '1', '1');
INSERT INTO `admin_game` VALUES ('1008', '测试1', '1');

-- ----------------------------
-- Table structure for admin_group
-- ----------------------------
DROP TABLE IF EXISTS `admin_group`;
CREATE TABLE `admin_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_group
-- ----------------------------
INSERT INTO `admin_group` VALUES ('1', '焚天');
INSERT INTO `admin_group` VALUES ('2', '妖怪正传');
INSERT INTO `admin_group` VALUES ('3', '勇者大作战');
INSERT INTO `admin_group` VALUES ('4', 'fix-undefined');
INSERT INTO `admin_group` VALUES ('5', 'asd12');

-- ----------------------------
-- Table structure for admin_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_log`;
CREATE TABLE `admin_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志标识',
  `account` varchar(50) NOT NULL,
  `method` varchar(200) NOT NULL,
  `result` text,
  `op_data` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `extend1` varchar(255) DEFAULT NULL,
  `extend2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_log
-- ----------------------------
INSERT INTO `admin_log` VALUES ('1', 'mohua', 'loginByAccount', '{\"accessToken\":\"de68f103fe8f734c59c836addc052d09.1538538966780\",\"account\":\"mohua\",\"email\":\"mohua@hoolai.com\",\"groupId\":1,\"phone\":\"15811017452\",\"uid\":1000}', '2018-09-03 11:56:07', 'extend1', 'extend2');
INSERT INTO `admin_log` VALUES ('2', 'mohua', 'loginByAccount', '{\"accessToken\":\"52c28f0fe1b613ff019d3ce95626f3dd.1538539650792\",\"account\":\"mohua\",\"email\":\"mohua@hoolai.com\",\"groupId\":1,\"phone\":\"15811017452\",\"uid\":1000}', '2018-09-03 12:07:31', 'extend1', 'extend2');
INSERT INTO `admin_log` VALUES ('3', 'mohua', 'loginByAccount', '{\"accessToken\":\"281c36f934605277502f9ffef5d69bb8.1538540143154\",\"account\":\"mohua\",\"email\":\"mohua@hoolai.com\",\"groupId\":1,\"phone\":\"15811017452\",\"uid\":1000}', '2018-09-03 12:15:43', 'extend1', 'extend2');
INSERT INTO `admin_log` VALUES ('4', 'mohua', 'loginByAccount', '{\"accessToken\":\"3a276a1fe75f917c16a3195b9d3021b4.1538540143184\",\"account\":\"mohua\",\"email\":\"mohua@hoolai.com\",\"groupId\":1,\"phone\":\"15811017452\",\"uid\":1000}', '2018-09-03 12:15:43', 'extend1', 'extend2');
INSERT INTO `admin_log` VALUES ('5', 'mohua', 'loginByAccount', '{\"accessToken\":\"79abf1567b4a4480c037f50ba28dc99d.1538540152030\",\"account\":\"mohua\",\"email\":\"mohua@hoolai.com\",\"groupId\":1,\"phone\":\"15811017452\",\"uid\":1000}', '2018-09-03 12:15:52', 'extend1', 'extend2');
INSERT INTO `admin_log` VALUES ('6', 'mohua', 'listDetail', '[{\"createTime\":\"2018-08-30 14:53:03\",\"gameId\":1001,\"gameName\":\"勇者大作战\",\"id\":2,\"illegalWords\":\"\",\"msg\":\"aqwe可疑\",\"statusDes\":\"可疑(待审核)\",\"uid\":222},{\"createTime\":\"2018-08-24 16:45:17\",\"gameId\":1002,\"gameName\":\"绝地兽人\",\"id\":6,\"msg\":\"aq123213可疑1\",\"statusDes\":\"可疑(待审核)\",\"uid\":201},{\"createTime\":\"2018-08-24 16:45:13\",\"gameId\":1002,\"gameName\":\"绝地兽人\",\"id\":7,\"msg\":\"aq123213可疑22\",\"statusDes\":\"可疑(待审核)\",\"uid\":222},{\"createTime\":\"2018-08-24 16:45:04\",\"gameId\":1001,\"gameName\":\"勇者大作战\",\"id\":5,\"msg\":\"aq123213可疑1\",\"statusDes\":\"可疑(待审核)\",\"uid\":222},{\"createTime\":\"2018-08-24 16:45:00\",\"gameId\":1000,\"gameName\":\"焚天\",\"id\":4,\"msg\":\"aq123213可疑\",\"statusDes\":\"可疑(待审核)\",\"uid\":201},{\"createTime\":\"2018-08-24 16:44:59\",\"gameId\":1000,\"gameName\":\"焚天\",\"id\":3,\"illegalWords\":\"1\",\"msg\":\"aqwe可疑\",\"opAccount\":\"mohua\",\"opUid\":1000,\"status\":-1,\"statusDes\":\"违规(已审核)\",\"uid\":222},{\"createTime\":\"2018-08-30 14:52:59\",\"gameId\":1000,\"gameName\":\"焚天\",\"gameUid\":\"\",\"id\":1,\"illegalWords\":\"\",\"msg\":\"aqwe可疑\",\"opAccount\":\"mohua\",\"opUid\":1000,\"status\":1,\"statusDes\":\"正常(已审核)\",\"uid\":1000}]', '2018-09-03 12:17:09', 'extend1', 'extend2');
INSERT INTO `admin_log` VALUES ('7', 'mohua', 'loginByAccount', '{\"accessToken\":\"1501a1bd3c9f45a0cf2b940b155504d4.1538540328036\",\"account\":\"mohua\",\"email\":\"mohua@hoolai.com\",\"groupId\":1,\"phone\":\"15811017452\",\"uid\":1000}', '2018-09-03 12:18:48', 'extend1', 'extend2');
INSERT INTO `admin_log` VALUES ('8', 'mohua', 'loginByAccount', '{\"accessToken\":\"548acaa56cf0808e118c6904e5232dd3.1538540812974\",\"account\":\"mohua\",\"email\":\"mohua@hoolai.com\",\"groupId\":1,\"phone\":\"15811017452\",\"uid\":1000}', '2018-09-03 12:26:53', 'extend1', 'extend2');
INSERT INTO `admin_log` VALUES ('9', 'mohua', 'loginByAccount', '{\"accessToken\":\"a4c98cdc444138292709e1927285de3d.1538540823664\",\"account\":\"mohua\",\"email\":\"mohua@hoolai.com\",\"groupId\":1,\"phone\":\"15811017452\",\"uid\":1000}', '2018-09-03 12:27:04', 'extend1', 'extend2');

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `uid` bigint(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `account_index` (`account`) USING BTREE,
  UNIQUE KEY `email_index` (`email`) USING BTREE,
  UNIQUE KEY `phone_index` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('1000', 'mohua', 'mohua@hoolai.com', '15811017452', '14e1b600b1fd579f47433b88e8d85291', '1');
INSERT INTO `admin_user` VALUES ('1001', 'liyongxiang', 'liyongxiang@hoolai.com', null, '14e1b600b1fd579f47433b88e8d85291', '2');
INSERT INTO `admin_user` VALUES ('1002', 'wanghaiqi', 'wanghaiqi@hoolai.com', '18317893457', '14e1b600b1fd579f47433b88e8d85291', '1');
INSERT INTO `admin_user` VALUES ('1003', 'admin', 'access_platform@hoolai.com', '18500059268', '9db06bcff9248837f86d1a6bcf41c9e7', null);
INSERT INTO `admin_user` VALUES ('1004', 'account', 'undefined', '18711112222', '9db06bcff9248837f86d1a6bcf41c9e7', '1');
INSERT INTO `admin_user` VALUES ('1005', 'account2', null, '18722221111', '9db06bcff9248837f86d1a6bcf41c9e7', '1');

-- ----------------------------
-- Table structure for m_suspicious
-- ----------------------------
DROP TABLE IF EXISTS `m_suspicious`;
CREATE TABLE `m_suspicious` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL,
  `game_id` bigint(11) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT NULL COMMENT '可疑：null；   illegal：-1；  正常：1',
  `illegal_words` varchar(255) DEFAULT NULL,
  `op_uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_suspicious
-- ----------------------------
INSERT INTO `m_suspicious` VALUES ('1', '1000', '1000', 'aqwe可疑', '2018-08-30 14:52:59', '1', '', '1000');
INSERT INTO `m_suspicious` VALUES ('2', '222', '1001', 'aqwe可疑', '2018-08-30 14:53:03', null, '', null);
INSERT INTO `m_suspicious` VALUES ('3', '222', '1000', 'aqwe可疑', '2018-08-24 16:44:59', '-1', '1', '1000');
INSERT INTO `m_suspicious` VALUES ('4', '201', '1000', 'aq123213可疑', '2018-08-24 16:45:00', null, null, null);
INSERT INTO `m_suspicious` VALUES ('5', '222', '1001', 'aq123213可疑1', '2018-08-24 16:45:04', null, null, null);
INSERT INTO `m_suspicious` VALUES ('6', '201', '1002', 'aq123213可疑1', '2018-08-24 16:45:17', null, null, null);
INSERT INTO `m_suspicious` VALUES ('7', '222', '1002', 'aq123213可疑22', '2018-08-24 16:45:13', null, null, null);

-- ----------------------------
-- Table structure for m_words
-- ----------------------------
DROP TABLE IF EXISTS `m_words`;
CREATE TABLE `m_words` (
  `id` bigint(20) NOT NULL,
  `wrod` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_words
-- ----------------------------

-- ----------------------------
-- Table structure for u_freeze
-- ----------------------------
DROP TABLE IF EXISTS `u_freeze`;
CREATE TABLE `u_freeze` (
  `uid` bigint(20) NOT NULL,
  `freezed` tinyint(1) DEFAULT NULL,
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_freeze
-- ----------------------------
INSERT INTO `u_freeze` VALUES ('3', '1', '2018-08-17 16:59:55');
INSERT INTO `u_freeze` VALUES ('10', '1', '2018-08-17 17:06:27');
INSERT INTO `u_freeze` VALUES ('11', '1', '2018-08-17 17:07:23');
INSERT INTO `u_freeze` VALUES ('12', '1', '2018-08-17 18:21:59');
INSERT INTO `u_freeze` VALUES ('222', '1', '2018-08-30 11:02:27');
INSERT INTO `u_freeze` VALUES ('1000', '1', '2018-08-30 12:49:05');
INSERT INTO `u_freeze` VALUES ('1001', '1', '2018-09-03 11:15:56');

-- ----------------------------
-- Table structure for u_login_info
-- ----------------------------
DROP TABLE IF EXISTS `u_login_info`;
CREATE TABLE `u_login_info` (
  `uid` bigint(11) NOT NULL AUTO_INCREMENT,
  `game_id` bigint(255) DEFAULT NULL,
  `game_uid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `gameid_gameuid_index` (`game_id`,`game_uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of u_login_info
-- ----------------------------
INSERT INTO `u_login_info` VALUES ('1000', '2000', '', '2018-08-20 19:09:16');
INSERT INTO `u_login_info` VALUES ('1001', '1', '2000', '2018-09-03 10:59:44');
