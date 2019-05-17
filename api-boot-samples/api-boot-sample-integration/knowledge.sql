/*
 Navicat MySQL Data Transfer

 Source Server         : 知识库正式
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : rm-2zeet0j72ljt4uz97wo.mysql.rds.aliyuncs.com:3306
 Source Schema         : knowledge

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 17/05/2019 13:44:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clientdetails
-- ----------------------------
DROP TABLE IF EXISTS `clientdetails`;
CREATE TABLE `clientdetails` (
  `appId` varchar(128) NOT NULL,
  `resourceIds` varchar(256) DEFAULT NULL,
  `appSecret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `grantTypes` varchar(256) DEFAULT NULL,
  `redirectUrl` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additionalInformation` varchar(4096) DEFAULT NULL,
  `autoApproveScopes` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kl_article_info
-- ----------------------------
DROP TABLE IF EXISTS `kl_article_info`;
CREATE TABLE `kl_article_info` (
  `AI_ID` varchar(36) NOT NULL COMMENT '主键自增',
  `AI_USER_ID` varchar(36) DEFAULT NULL COMMENT '文章所属用户',
  `AI_TITLE` varchar(200) DEFAULT NULL COMMENT '文章标题',
  `AI_READ_COUNT` int(11) DEFAULT NULL COMMENT '阅读量',
  `AI_LIKE_COUNT` int(11) DEFAULT NULL COMMENT '喜欢数量',
  `AI_COMMENT_COUNT` int(11) DEFAULT NULL COMMENT '评论数量',
  `AI_SHARE_COUNT` int(11) DEFAULT NULL COMMENT '分享数量',
  `AI_CONTENT` longtext COMMENT '文章内容',
  `AI_IS_ORIGINAL` char(1) DEFAULT 'Y' COMMENT '是否为原创文章，Y：原创，N：转载',
  `AI_IS_RELEASE` char(1) DEFAULT NULL COMMENT '文章是否发布，Y：已发布，N：未发布',
  `AI_IS_HOT` char(1) DEFAULT 'N' COMMENT '是否热门,Y：热门，N：非热门',
  `AI_IS_TOP` char(1) DEFAULT 'N' COMMENT '是否置顶，Y：置顶，N：普通',
  `AI_IS_RECOMMEND` char(1) DEFAULT 'N' COMMENT '是否推荐，Y：推荐，N：不推荐',
  `AI_IS_MARKDOWN` char(1) DEFAULT 'Y' COMMENT '是否为markdown语法文章',
  `AI_RELEASE_TIME` datetime DEFAULT NULL COMMENT '发布时间',
  `AI_STATUS` char(3) DEFAULT 'O' COMMENT '文章状态，O：正常，D：已删除',
  `AI_MARK` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `AI_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '文章创建时间',
  PRIMARY KEY (`AI_ID`),
  KEY `kl_article_info_AI_USER_ID_index` (`AI_USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章信息表';

-- ----------------------------
-- Table structure for kl_article_topic_info
-- ----------------------------
DROP TABLE IF EXISTS `kl_article_topic_info`;
CREATE TABLE `kl_article_topic_info` (
  `ATI_ID` varchar(36) NOT NULL COMMENT '专题主键',
  `ATI_NAME` varchar(20) DEFAULT NULL COMMENT '专题名称',
  `ATI_SORT` int(11) DEFAULT NULL COMMENT '排序字段，值越大越靠前',
  `ATI_STATUS` char(1) DEFAULT 'O' COMMENT '专题状态，O：正常，D：已删除',
  `ATI_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ATI_MARK` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`ATI_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章专题信息表';

-- ----------------------------
-- Table structure for kl_article_topic_uni
-- ----------------------------
DROP TABLE IF EXISTS `kl_article_topic_uni`;
CREATE TABLE `kl_article_topic_uni` (
  `ATU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ATU_ARTICLE_ID` varchar(36) DEFAULT NULL COMMENT '文章编号，关联kl_article_info主键',
  `ATU_TOPIC_ID` varchar(36) DEFAULT NULL COMMENT '文章专题，关联kl_article_topic_info主键',
  `ATU_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ATU_ID`),
  KEY `kl_article_topic_uni_ATU_ARTICLE_ID_index` (`ATU_ARTICLE_ID`),
  KEY `kl_article_topic_uni_ATU_TOPIC_ID_index` (`ATU_TOPIC_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COMMENT='文章专题关联信息表';

-- ----------------------------
-- Table structure for kl_banner_info
-- ----------------------------
DROP TABLE IF EXISTS `kl_banner_info`;
CREATE TABLE `kl_banner_info` (
  `BI_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `BI_JUMP_ARTICLE_ID` varchar(36) DEFAULT NULL COMMENT '点击跳转文章编号，关联kl_article_info主键',
  `BI_START_TIME` datetime DEFAULT NULL COMMENT '展示开始时间',
  `BI_END_TIME` datetime DEFAULT NULL COMMENT '展示结束时间',
  `BI_STATUS` char(1) DEFAULT 'O' COMMENT '轮播图状态，O：正常，D：已删除',
  `BI_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `BI_MARK` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`BI_ID`),
  KEY `kl_banner_info_BI_JUMP_ARTICLE_ID_index` (`BI_JUMP_ARTICLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='轮播图信息表';

-- ----------------------------
-- Table structure for kl_comment_info
-- ----------------------------
DROP TABLE IF EXISTS `kl_comment_info`;
CREATE TABLE `kl_comment_info` (
  `CI_ID` varchar(36) NOT NULL COMMENT '留言主键',
  `CI_ARTICLE_ID` varchar(36) DEFAULT NULL COMMENT '文章编号，关联kl_article_info主键',
  `CI_CREATOR_ID` varchar(36) DEFAULT NULL COMMENT '评论创建者，关联kl_user_info主键',
  `CI_COMMENT_ID` varchar(36) DEFAULT NULL COMMENT '上级评论编号，关联本表主键',
  `CI_TARGET_ID` varchar(36) DEFAULT NULL COMMENT '回复目标用户编号，关联kl_user_info主键',
  `CI_CONTENT` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '留言内容',
  `CI_IS_AUTHOR` char(1) DEFAULT NULL COMMENT '是否为作者评论，Y：作者评论，N：非作者',
  `CI_LIKE_COUNT` int(11) DEFAULT NULL COMMENT '评论喜欢数量',
  `CI_COMMENT_COUNT` int(11) DEFAULT NULL COMMENT '评论的评论、回复数量',
  `CI_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '留言时间',
  PRIMARY KEY (`CI_ID`),
  KEY `kl_comment_info_CI_ARTICLE_ID_index` (`CI_ARTICLE_ID`),
  KEY `kl_comment_info_CI_COMMENT_ID_index` (`CI_COMMENT_ID`),
  KEY `kl_comment_info_CI_CREATOR_ID_index` (`CI_CREATOR_ID`),
  KEY `kl_comment_info_CI_TARGET_ID_index` (`CI_TARGET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章评论信息表';

-- ----------------------------
-- Table structure for kl_common_resource
-- ----------------------------
DROP TABLE IF EXISTS `kl_common_resource`;
CREATE TABLE `kl_common_resource` (
  `CR_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `CR_TYPE_ID` varchar(36) DEFAULT NULL COMMENT '资源类型，关联kl_common_resource_type主键',
  `CR_TARGET_ID` varchar(36) DEFAULT NULL COMMENT '资源所属目标编号',
  `CR_URL` text COMMENT '资源路径',
  `CR_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`CR_ID`),
  KEY `kl_common_resource_CR_TARGET_ID_index` (`CR_TARGET_ID`),
  KEY `kl_common_resource_CR_TYPE_ID_index` (`CR_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COMMENT='统一资源';

-- ----------------------------
-- Table structure for kl_common_resource_type
-- ----------------------------
DROP TABLE IF EXISTS `kl_common_resource_type`;
CREATE TABLE `kl_common_resource_type` (
  `CRT_ID` varchar(36) NOT NULL COMMENT '资源编号',
  `CRT_NAME` varchar(30) DEFAULT NULL COMMENT '资源类型名称',
  `CRT_FLAG` varchar(50) DEFAULT NULL COMMENT '资源类型标识',
  `CRT_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `CRT_MARK` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`CRT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统一资源类型';

-- ----------------------------
-- Table structure for kl_feedback
-- ----------------------------
DROP TABLE IF EXISTS `kl_feedback`;
CREATE TABLE `kl_feedback` (
  `KF_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈主键自增',
  `KF_USER_ID` varchar(36) DEFAULT NULL COMMENT '反馈用户编号，关联kl_user_info主键',
  `KF_CONTENT` text COMMENT '反馈内容',
  `KF_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '反馈时间',
  PRIMARY KEY (`KF_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COMMENT='意见反馈信息表';

-- ----------------------------
-- Table structure for kl_user_balance_info
-- ----------------------------
DROP TABLE IF EXISTS `kl_user_balance_info`;
CREATE TABLE `kl_user_balance_info` (
  `UBI_ID` varchar(36) NOT NULL COMMENT '账户主键',
  `UBI_USER_ID` varchar(36) DEFAULT NULL COMMENT '用户编号，关联kl_user_info主键',
  `UBI_TYPE_ID` varchar(36) DEFAULT NULL COMMENT '账户类型编号，关联kl_balance_type主键',
  `UBI_BALANCE` decimal(8,2) DEFAULT NULL COMMENT '账户余额，保留两位小数点',
  `UBI_IS_LOCK` char(1) DEFAULT 'N' COMMENT '账户是否锁定，Y：已锁定，N：未锁定',
  `UBI_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UBI_MARK` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`UBI_ID`),
  KEY `kl_user_balance_info_UBI_USER_ID_index` (`UBI_USER_ID`),
  KEY `kl_user_balance_info_UBI_TYPE_ID_index` (`UBI_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户信息表';

-- ----------------------------
-- Table structure for kl_user_balance_record
-- ----------------------------
DROP TABLE IF EXISTS `kl_user_balance_record`;
CREATE TABLE `kl_user_balance_record` (
  `BR_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BR_BALANCE_ID` varchar(36) DEFAULT NULL COMMENT '所属账户编号，关联kl_balance_info主键',
  `BR_BEFORE` decimal(8,2) DEFAULT NULL COMMENT '变动之前的值',
  `BR_COUNT` decimal(8,2) DEFAULT NULL COMMENT '变动的值',
  `BR_AFTER` decimal(8,2) DEFAULT NULL COMMENT '变动后的值',
  `BR_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `BR_MARK` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`BR_ID`),
  KEY `kl_balance_record_BR_BALANCE_ID_index` (`BR_BALANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户变动记录';

-- ----------------------------
-- Table structure for kl_user_balance_type
-- ----------------------------
DROP TABLE IF EXISTS `kl_user_balance_type`;
CREATE TABLE `kl_user_balance_type` (
  `UBT_ID` varchar(36) NOT NULL COMMENT '账户类型主键',
  `UBT_NAME` varchar(20) DEFAULT NULL COMMENT '账户类型名称',
  `UBT_FLAG` varchar(20) DEFAULT NULL COMMENT '账户类型标识',
  `UBT_MARK` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`UBT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户类型';

-- ----------------------------
-- Table structure for kl_user_info
-- ----------------------------
DROP TABLE IF EXISTS `kl_user_info`;
CREATE TABLE `kl_user_info` (
  `UI_ID` varchar(36) CHARACTER SET utf8 NOT NULL COMMENT '主键自增',
  `UI_NICK_NAME` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `UI_OPEN_ID` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户微信openId',
  `UI_PASSWORD` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户密码',
  `UI_LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '最后登录时间',
  `UI_STATUS` char(1) CHARACTER SET utf8 DEFAULT 'O' COMMENT '用户状态，O：正常，D：已删除',
  `UI_IS_LOCK` char(1) CHARACTER SET utf8 DEFAULT 'N' COMMENT '是否锁定，Y：锁定，N：未锁定',
  `UI_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UI_MARK` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`UI_ID`),
  KEY `kl_user_info_UI_OPEN_ID_index` (`UI_OPEN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户基本信息';

-- ----------------------------
-- Table structure for kl_user_like_record
-- ----------------------------
DROP TABLE IF EXISTS `kl_user_like_record`;
CREATE TABLE `kl_user_like_record` (
  `ULR_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `ULR_USER_ID` varchar(36) DEFAULT NULL COMMENT '用户编号，关联kl_user_info主键',
  `ULR_ARTICLE_ID` varchar(36) DEFAULT NULL COMMENT '文章编号，关联kl_article_info主键',
  `ULR_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `URL_MARK` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`ULR_ID`),
  KEY `kl_user_like_record_ULR_ARTICLE_ID_index` (`ULR_ARTICLE_ID`),
  KEY `kl_user_like_record_ULR_USER_ID_index` (`ULR_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='用户喜欢文章记录';

-- ----------------------------
-- Table structure for kl_user_read_record
-- ----------------------------
DROP TABLE IF EXISTS `kl_user_read_record`;
CREATE TABLE `kl_user_read_record` (
  `URR_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `URR_USER_ID` varchar(36) DEFAULT NULL COMMENT '阅读用户编号，关联kl_user_info主键',
  `URR_ARTICLE_ID` varchar(36) DEFAULT NULL COMMENT '文章编号，关联kl_article_info主键',
  `URR_CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '阅读时间',
  `URR_MARK` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`URR_ID`),
  KEY `kl_article_read_record_ARR_ARTICLE_ID_index` (`URR_ARTICLE_ID`),
  KEY `kl_article_read_record_ARR_USER_ID_index` (`URR_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8 COMMENT='文章阅读记录';

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(128) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` datetime DEFAULT NULL,
  `lastModifiedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(128) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(128) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
