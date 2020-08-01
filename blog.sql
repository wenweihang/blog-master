/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 01/08/2020 22:02:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_article
-- ----------------------------
DROP TABLE IF EXISTS `b_article`;
CREATE TABLE `b_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(100) NOT NULL COMMENT '文章标题',
  `content` mediumtext NOT NULL COMMENT '文章内容',
  `status` char(1) DEFAULT '1' COMMENT '文章状态：1已发布，0草稿',
  `views` int(11) DEFAULT '0' COMMENT '文章访问量',
  `allow_comment` int(11) DEFAULT '1' COMMENT '是否允许评论：1允许 0禁止',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `last_update_time` bigint(20) DEFAULT NULL COMMENT '最后一次更新时间',
  `sort` int(11) DEFAULT '1' COMMENT '文章显示顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of b_article
-- ----------------------------
BEGIN;
INSERT INTO `b_article` VALUES (16, 'js 日期工具类封装', '&lt;h3&gt;日期工具类封装&lt;/h3&gt;&lt;pre class=\"ql-syntax\" spellcheck=\"false\"&gt;// date.js - 日期格式化{yyyy-MM-dd hh:mm:ss}\n\n\nexport function formatDate(timestamp) {\n  if (timestamp == null) return \'N/A\'\n  const dt = new Date(timestamp)\n  const year = dt.getFullYear()\n  const month = padLeftZero((dt.getMonth() + 1).toString())\n  const day = padLeftZero(dt.getDate().toString())\n  const hour = padLeftZero(dt.getHours().toString())\n  const minute = padLeftZero(dt.getMinutes().toString())\n  const second = padLeftZero(dt.getSeconds().toString())\n\n\n  return `${year}-${month}-${day} ${hour}:${minute}:${second}`\n}\n\n\nfunction padLeftZero(str) {\n  return str.padStart(2, 0)\n}\n\n\nexport function convertDate(timestamp) {\n  if (timestamp == null) return \'N/A\'\n  const now = new Date()\n  const nowYear = now.getFullYear()\n  const nowMonth = now.getMonth() + 1\n  const nowDay = now.getDate()\n  const nowHour = now.getHours()\n  const nowMinute = now.getMinutes()\n  const nowSecond = now.getSeconds()\n\n\n  // 转化成几分钟前 几天前等格式\n  const dt = new Date(timestamp)\n  const year = dt.getFullYear()\n  const month = dt.getMonth() + 1\n  const monthPadZero = padLeftZero(month.toString())\n  const day = dt.getDate()\n  const dayPadZero = padLeftZero(day.toString())\n  const hour = dt.getHours()\n  const hourPadZero = padLeftZero(hour.toString())\n  const minute = dt.getMinutes()\n  const minutePadZero = padLeftZero(minute.toString())\n  const second = dt.getSeconds()\n  const secondPadZero = padLeftZero(second.toString())\n\n\n  if (nowYear != year) return `${year}-${monthPadZero}-${dayPadZero} ${hourPadZero}:${minutePadZero}:${secondPadZero}`\n\n\n  if (nowMonth == month &amp;&amp; nowDay - day == 1) {\n    return \'1天前\'\n  } else if (nowMonth == month &amp;&amp; nowDay - day == 0) {\n    if (nowHour - hour &lt; 1) {\n      if (nowMinute - minute &lt; 1) {\n        return `${second}秒前`\n      }\n      return `${nowMinute - minute}分钟前`\n    } else {\n      return `今天 ${hourPadZero}:${minutePadZero}:${secondPadZero}`\n    }\n  } else {\n    return `${monthPadZero}-${dayPadZero} ${hourPadZero}:${minutePadZero}:${secondPadZero}`\n  }\n}\n&lt;/pre&gt;&lt;p&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;以上。&lt;/p&gt;', '1', 10, 1, 1596203546117, 1596204407896, 1);
INSERT INTO `b_article` VALUES (17, 'Springboot 获取IP地址工具类', '&lt;h3&gt;获取IP地址工具类封装&lt;/h3&gt;&lt;pre class=\"ql-syntax\" spellcheck=\"false\"&gt;package com.zoe.blog.utils;\n\nimport org.springframework.web.context.request.RequestContextHolder;\nimport org.springframework.web.context.request.ServletRequestAttributes;\n\nimport javax.servlet.http.HttpServletRequest;\n\n/**\n * @author liuxuelian\n * @date 2020-07-18 18:45\n */\npublic class IPUtil {\n    /**\n     * 获取IP地址\n     *\n     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址\n     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址\n     */\n    public static String getIp() {\n        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())\n                .getRequest();\n\n        String ip = request.getHeader(\"x-forwarded-for\");\n        if (ip == null || ip.length() == 0 || \"unknown\".equalsIgnoreCase(ip)) {\n            ip = request.getHeader(\"Proxy-Client-IP\");\n        }\n        if (ip == null || ip.length() == 0 || \"unknown\".equalsIgnoreCase(ip)) {\n            ip = request.getHeader(\"WL-Proxy-Client-IP\");\n        }\n        if (ip == null || ip.length() == 0 || \"unknown\".equalsIgnoreCase(ip)) {\n            ip = request.getRemoteAddr();\n        }\n        return \"0:0:0:0:0:0:0:1\".equals(ip) ? \"127.0.0.1\" : ip;\n    }\n}\n&lt;/pre&gt;&lt;p&gt;&lt;br&gt;&lt;/p&gt;&lt;blockquote&gt;通过封装工具类来更好的获取用户的IP地址来作为日志的Ip地址字段写入数据库。&lt;/blockquote&gt;&lt;p&gt;&lt;br&gt;&lt;/p&gt;&lt;p&gt;以上。&lt;/p&gt;', '1', 15, 1, 1596204638003, 1596288557903, 1);
INSERT INTO `b_article` VALUES (18, '用markdown编写', '&lt;p&gt;输入文章内容&lt;/p&gt;', '1', 2, 1, 1596276841464, 1596276841464, 1);
COMMIT;

-- ----------------------------
-- Table structure for b_article_category
-- ----------------------------
DROP TABLE IF EXISTS `b_article_category`;
CREATE TABLE `b_article_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章分类关联表id',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `category_id` int(11) NOT NULL COMMENT '文章分类id',
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `b_article_category_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `b_article` (`id`),
  CONSTRAINT `b_article_category_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `b_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of b_article_category
-- ----------------------------
BEGIN;
INSERT INTO `b_article_category` VALUES (24, 16, 3);
INSERT INTO `b_article_category` VALUES (25, 17, 5);
INSERT INTO `b_article_category` VALUES (26, 18, 1);
COMMIT;

-- ----------------------------
-- Table structure for b_category
-- ----------------------------
DROP TABLE IF EXISTS `b_category`;
CREATE TABLE `b_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类专栏id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `sort` int(11) DEFAULT '1' COMMENT '分类显示顺序',
  `create_time` bigint(20) DEFAULT NULL COMMENT '分类创建时间',
  `enabled` char(1) DEFAULT '1' COMMENT '是否显示分类：1显示 0不显示',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of b_category
-- ----------------------------
BEGIN;
INSERT INTO `b_category` VALUES (1, 'html5', 1, 1595929036757, '1');
INSERT INTO `b_category` VALUES (2, 'css3', 1, 1595929040861, '1');
INSERT INTO `b_category` VALUES (3, 'js', 1, 1595929643270, '1');
INSERT INTO `b_category` VALUES (5, 'java', 1, 1596204486040, '1');
COMMIT;

-- ----------------------------
-- Table structure for b_comment
-- ----------------------------
DROP TABLE IF EXISTS `b_comment`;
CREATE TABLE `b_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `create_time` bigint(20) DEFAULT NULL COMMENT '评论时间',
  `enabled` char(1) DEFAULT '1' COMMENT '评论是否显示：1显示 0不显示',
  `article_id` int(11) NOT NULL COMMENT '评论文章id',
  `visitor_nickname` varchar(20) NOT NULL COMMENT '评论的访客名称',
  `visitor_email` varchar(30) NOT NULL COMMENT '评论的访客邮箱',
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `b_comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `b_article` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of b_comment
-- ----------------------------
BEGIN;
INSERT INTO `b_comment` VALUES (7, '不错，但是应该还可以再优化优化...', 1596276529555, '1', 16, '天马', 'tiamma@gmail.com');
COMMIT;

-- ----------------------------
-- Table structure for b_log
-- ----------------------------
DROP TABLE IF EXISTS `b_log`;
CREATE TABLE `b_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `username` varchar(20) NOT NULL DEFAULT 'anonymousUser' COMMENT '调用接口用户名',
  `ip` varchar(20) DEFAULT NULL COMMENT 'ip地址',
  `params` varchar(200) DEFAULT NULL COMMENT '参数',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建日期',
  `level` varchar(10) DEFAULT NULL COMMENT '日志等级',
  `thread` varchar(100) DEFAULT NULL COMMENT '进程',
  `location` varchar(200) DEFAULT NULL COMMENT '位置',
  `message` mediumtext COMMENT '消息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of b_log
-- ----------------------------
BEGIN;
INSERT INTO `b_log` VALUES (39, 'anonymousUser', '127.0.0.1', '', 1596285044531, 'INFO', 'http-nio-8081-exec-3', 'com.zoe.blog.controller.web.WebUserDetailController.getUserDetail(WebUserDetailController.java:33)', '访问博客');
INSERT INTO `b_log` VALUES (40, 'anonymousUser', '127.0.0.1', '', 1596288403743, 'INFO', 'http-nio-8081-exec-2', 'com.zoe.blog.controller.web.WebUserDetailController.getUserDetail(WebUserDetailController.java:33)', '访问博客');
COMMIT;

-- ----------------------------
-- Table structure for b_reply
-- ----------------------------
DROP TABLE IF EXISTS `b_reply`;
CREATE TABLE `b_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论回复表',
  `create_time` bigint(20) DEFAULT NULL COMMENT '回复时间',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '回复内容',
  `enabled` char(1) DEFAULT '1' COMMENT '回复是否显示：1显示 0不显示',
  `comment_id` int(11) NOT NULL COMMENT '回复的评论id',
  PRIMARY KEY (`id`),
  KEY `comment_id` (`comment_id`),
  CONSTRAINT `b_reply_ibfk_1` FOREIGN KEY (`comment_id`) REFERENCES `b_comment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for b_role
-- ----------------------------
DROP TABLE IF EXISTS `b_role`;
CREATE TABLE `b_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) NOT NULL COMMENT '英文角色名称',
  `description` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `create_time` bigint(20) DEFAULT NULL COMMENT '角色创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of b_role
-- ----------------------------
BEGIN;
INSERT INTO `b_role` VALUES (1, 'ROLE_ADMIN', '管理员权限', 1595928940006);
INSERT INTO `b_role` VALUES (2, 'ROLE_USER', '普通用户权限', 1595928979204);
COMMIT;

-- ----------------------------
-- Table structure for b_user
-- ----------------------------
DROP TABLE IF EXISTS `b_user`;
CREATE TABLE `b_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '登录用户名（唯一）',
  `password` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录密码',
  `nickname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱地址',
  `intro` varchar(200) DEFAULT NULL COMMENT '简介',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `enabled` char(1) DEFAULT '1' COMMENT '账户是否启用 1开启 0关闭',
  `last_login_time` bigint(20) DEFAULT NULL COMMENT '最后一次登录时间',
  `create_time` bigint(20) DEFAULT NULL COMMENT '账户创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of b_user
-- ----------------------------
BEGIN;
INSERT INTO `b_user` VALUES (1, 'nico', '$2a$10$3akBMObBU5DA3.NeeG71Je7QCAEhJVX0C0a5zSuvGIDANXFRweVu6', 'Nico', 'liuxuelian0929@gmail.com', '一枚程序猿在不断的学习，学习Web前端和Java', 'https://zoe-oss.oss-cn-beijing.aliyuncs.com/blog/images/20200801/u=3270615085,1029522308&fm=26&gp=0.jpg', '1', 1596285620764, 1595928473134);
INSERT INTO `b_user` VALUES (2, 'user', '$2a$10$EzF7EI/gXL.VFsjKMaz4t.obUVolH4u3zxp71GuYe/4sl/ERHTwgG', 'User', '', '普通用户账户', 'https://zoe-oss.oss-cn-beijing.aliyuncs.com/blog/images/20200728/jj.jpg', '1', NULL, 1595929007247);
COMMIT;

-- ----------------------------
-- Table structure for b_user_role
-- ----------------------------
DROP TABLE IF EXISTS `b_user_role`;
CREATE TABLE `b_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户和角色关联表id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `b_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `b_user` (`id`),
  CONSTRAINT `b_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `b_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of b_user_role
-- ----------------------------
BEGIN;
INSERT INTO `b_user_role` VALUES (1, 1, 1);
INSERT INTO `b_user_role` VALUES (2, 2, 2);
COMMIT;

-- ----------------------------
-- Table structure for b_uv
-- ----------------------------
DROP TABLE IF EXISTS `b_uv`;
CREATE TABLE `b_uv` (
  `date` char(8) NOT NULL,
  `date_time` date NOT NULL,
  `uv` int(11) NOT NULL DEFAULT '0',
  `comments` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of b_uv
-- ----------------------------
BEGIN;
INSERT INTO `b_uv` VALUES ('20200731', '2020-07-31', 15, 1);
INSERT INTO `b_uv` VALUES ('20200801', '2020-08-01', 12, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
