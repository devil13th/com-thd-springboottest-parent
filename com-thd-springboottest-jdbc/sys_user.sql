/*
Navicat MySQL Data Transfer

Source Server         : local-mysql
Source Server Version : 50611
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

Date: 2019-07-16 16:32:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(50) NOT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_sex` int(11) DEFAULT NULL,
  `user_mail` varchar(100) DEFAULT NULL,
  `user_tel` varchar(50) DEFAULT NULL,
  `user_birthday` date DEFAULT NULL,
  `user_status` varchar(10) DEFAULT NULL,
  `org_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
