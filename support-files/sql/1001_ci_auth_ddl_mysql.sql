USE devops_ci_auth;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for T_LOG_INDICES
-- ----------------------------

CREATE TABLE IF NOT EXISTS `T_AUTH_GROUP_INFO` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主健ID',
  `GROUP_NAME` VARCHAR(32) NOT NULL DEFAULT '""' COMMENT '用户组名称',
  `GROUP_CODE` VARCHAR(32) NOT NULL COMMENT '用户组标识 默认用户组标识一致',
  `GROUP_TYPE` BIT(1) NOT NULL COMMENT '用户组类型 0默认分组',
  `PROJECT_CODE` VARCHAR(64) NOT NULL DEFAULT '""' COMMENT '用户组所属项目',
  `IS_DELETE` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0 可用 1删除',
  `CREATE_USER` VARCHAR(64) NOT NULL DEFAULT '""' COMMENT '添加人',
  `UPDATE_USER` VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  `CREATE_TIME` DATETIME(3) NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` DATETIME(3) DEFAULT NULL COMMENT '修改时间',
  `DISPLAY_NAME` VARCHAR(32) DEFAULT NULL COMMENT '用户组别名',
  `RELATION_ID` VARCHAR(32) DEFAULT NULL COMMENT '关联系统ID',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `GROUP_NAME+PROJECT_CODE` (`GROUP_NAME`,`PROJECT_CODE`),
  KEY `PROJECT_CODE` (`PROJECT_CODE`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户组信息表';

CREATE TABLE IF NOT EXISTS `T_AUTH_GROUP_PERSSION` (
  `ID` varchar(64) NOT NULL COMMENT '主健ID',
  `AUTH_ACTION` varchar(64) NOT NULL DEFAULT '""' COMMENT '权限动作',
  `GROUP_CODE` varchar(64) NOT NULL DEFAULT '""' COMMENT '用户组编号 默认7个内置组编号固定 自定义组编码随机',
  `CREATE_USER` varchar(64) NOT NULL DEFAULT '""' COMMENT '创建人',
  `UPDATE_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
  `CREATE_TIME` datetime(3) NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime(3) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';

CREATE TABLE IF NOT EXISTS `T_AUTH_GROUP_USER` (
  `ID` varchar(64) NOT NULL COMMENT '主键ID',
  `USER_ID` varchar(64) NOT NULL DEFAULT '""' COMMENT '用户ID',
  `GROUP_ID` varchar(64) NOT NULL DEFAULT '""' COMMENT '用户组ID',
  `CREATE_USER` varchar(64) NOT NULL DEFAULT '""' COMMENT '添加用户',
  `CREATE_TIME` datetime(3) NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';

CREATE TABLE IF NOT EXISTS `T_AUTH_STRATEGY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '策略主键ID',
  `STRATEGY_NAME` varchar(32) NOT NULL COMMENT '策略名称',
  `STRATEGY_BODY` varchar(2000) NOT NULL COMMENT '策略内容',
  `IS_DELETE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1删除',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATE_USER` varchar(32) NOT NULL COMMENT '创建人',
  `UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='权限策略表';

CREATE TABLE IF NOT EXISTS `T_AUTH_MANAGER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `NAME` varchar(32) NOT NULL COMMENT '名称',
  `ORGANIZATION_ID` int(11) NOT NULL COMMENT '组织ID',
  `LEVEL` int(11) NOT NULL COMMENT '层级ID',
  `STRATEGYID` int(11) NOT NULL COMMENT '权限策略ID',
  `IS_DELETE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `CREATE_USER` varchar(11) NOT NULL DEFAULT '""' COMMENT '创建用户',
  `UPDATE_USER` varchar(11) DEFAULT '""' COMMENT '修改用户',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='管理员策略表';

CREATE TABLE IF NOT EXISTS `T_AUTH_MANAGER_USER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `USER_ID` varchar(64) NOT NULL COMMENT '用户ID',
  `MANAGER_ID` int(11) NOT NULL COMMENT '管理员权限ID',
  `START_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '权限生效起始时间',
  `END_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '权限生效结束时间',
  `CREATE_USER` varchar(64) NOT NULL COMMENT '创建用户',
  `UPDATE_USER` varchar(64) DEFAULT NULL COMMENT '修改用户',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_ID+MANGER_ID` (`USER_ID`,`MANAGER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='管理员用户表(只存有效期内的用户)';

CREATE TABLE IF NOT EXISTS `T_AUTH_MANAGER_USER_HISTORY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `USER_ID` varchar(64) NOT NULL COMMENT '用户ID',
  `MANAGER_ID` int(11) NOT NULL COMMENT '管理员权限ID',
  `START_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '权限生效起始时间',
  `END_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '权限生效结束时间',
  `CREATE_USER` varchar(64) NOT NULL COMMENT '创建用户',
  `UPDATE_USER` varchar(64) DEFAULT NULL COMMENT '修改用户',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  KEY `MANGER_ID` (`MANAGER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='管理员用户历史表';

CREATE TABLE IF NOT EXISTS `T_AUTH_MANAGER_WHITELIST` (
   `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   `MANAGER_ID` int(11) NOT NULL COMMENT '管理策略ID',
   `USER_ID` varchar(64) NOT NULL COMMENT '用户ID',
   PRIMARY KEY (`ID`),
   KEY `idx_manager` (`MANAGER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='管理员自助申请表名单表';

CREATE TABLE IF NOT EXISTS `T_AUTH_IAM_CALLBACK` (
   `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   `GATEWAY` varchar(255) NOT NULL DEFAULT '""' COMMENT '目标服务网关',
   `PATH` varchar(1024) NOT NULL DEFAULT '""' COMMENT '目标接口路径',
   `DELETE_FLAG` bit(1) DEFAULT b'0' COMMENT '是否删除 true-是 false-否',
   `RESOURCE` varchar(32) NOT NULL DEFAULT '""' COMMENT '资源类型',
   `SYSTEM` varchar(32) NOT NULL DEFAULT '""' COMMENT '接入系统',
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IAM回调地址';

CREATE TABLE IF NOT EXISTS `T_AUTH_USER_BLACKLIST` (
   `ID` int(11) NOT NULL AUTO_INCREMENT,
   `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
   `REMARK` varchar(255) NOT NULL COMMENT '拉黑原因',
   `CREATE_TIME` datetime NOT NULL COMMENT '拉黑时间',
   `STATUS` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否生效 1生效 0不生效',
   PRIMARY KEY (`ID`),
   KEY `bk_userId` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `T_AUTH_MANAGER_APPROVAL`
(
    `ID`           int(11)                             NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `USER_ID`      varchar(64)                         NOT NULL COMMENT '用户ID',
    `MANAGER_ID`   int                                 NOT NULL COMMENT '管理员权限ID',
    `EXPIRED_TIME` timestamp                           NOT NULL COMMENT '权限过期时间',
    `START_TIME`   timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '审批单生效时间',
    `END_TIME`     timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '审批单失效时间',
    `STATUS`       int(2)                              NOT NULL COMMENT '发送状态 0-审核流程中 ,1-用户拒绝续期,2-用户同意续期,3-审批人拒绝续期，4-审批人同意续期',
    `CREATE_TIME`  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `UPDATE_TIME`  timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '修改时间',
    INDEX `IDX_USER_ID` (`USER_ID`),
    INDEX `IDX_MANAGER_ID` (`MANAGER_ID`),
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT '蓝盾超级管理员权限续期审核表';

CREATE TABLE IF NOT EXISTS `T_AUTH_USER_INFO`  (
   `ID` int NOT NULL AUTO_INCREMENT,
   `userId` varchar(255) NOT NULL COMMENT '用户ID',
   `email` varchar(255) NULL COMMENT '邮箱',
   `phone` varchar(32) NULL COMMENT '手机号',
   `create_time` datetime NOT NULL COMMENT '注册时间',
   `user_type` int NOT NULL COMMENT '用户类型 0.页面注册 1.GitHub 2.Gitlab',
   `last_login_time` datetime NULL COMMENT '最后登陆时间',
  `user_status` int NOT NULL COMMENT '用户状态,0--正常,1--冻结',
   PRIMARY KEY (`ID`),
   UNIQUE INDEX `bk_user`(`userId`, `user_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号信息表';

CREATE TABLE IF NOT EXISTS `T_AUTH_TEMPORARY_VERIFY_RECORD`
(
    `USER_ID`          varchar(64)                        not null comment '用户ID',
    `PROJECT_CODE`     varchar(64)                        not null comment '项目ID',
    `RESOURCE_TYPE`    varchar(64)                        not null comment '资源类型',
    `RESOURCE_CODE`    varchar(255)                       not null comment '资源ID',
    `ACTION`           varchar(64)                        not null comment '操作ID',
    `VERIFY_RESULT`    bit                                not null comment '鉴权结果',
    `LAST_VERIFY_TIME` datetime default CURRENT_TIMESTAMP not null comment '最后鉴权时间',
    primary key (USER_ID, PROJECT_CODE, RESOURCE_TYPE, RESOURCE_CODE, ACTION)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '迁移-鉴权记录表';

CREATE TABLE IF NOT EXISTS `T_AUTH_ACTION` (
	`ACTION` varchar(64) NOT NULL COMMENT '操作ID',
	`RESOURCE_TYPE` varchar(64) NOT NULL COMMENT '蓝盾-关联资源类型',
	`RELATED_RESOURCE_TYPE` varchar(64) NOT NULL COMMENT 'IAM-关联资源类型',
	`ACTION_NAME` varchar(64) NOT NULL COMMENT '操作名称',
	`ENGLISH_NAME` varchar(64) DEFAULT NULL COMMENT '动作英文名称',
	`CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建者',
	`CREATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`UPDATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
	`DELETE` bit(1) DEFAULT NULL COMMENT '是否删除',
	`ACTION_TYPE` varchar(32) DEFAULT NULL COMMENT '操作类型',
	PRIMARY KEY (`ACTION`),
	UNIQUE INDEX `UNI_INX_RESOURCE_TYPE_ACTION_ID` (`RESOURCE_TYPE`, `ACTION`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '权限操作表';

CREATE TABLE IF NOT EXISTS `T_AUTH_RESOURCE_TYPE` (
    `ID` int(11) NOT NULL COMMENT 'ID',
    `RESOURCE_TYPE` varchar(64) NOT NULL  COMMENT '资源类型',
    `NAME` varchar(64) NOT NULL  COMMENT '资源名称',
    `ENGLISH_NAME` varchar(64) DEFAULT NULL  COMMENT '资源英文名称',
    `DESC` varchar(255) NOT NULL  COMMENT '资源描述',
    `ENGLISH_DESC` varchar(255) DEFAULT NULL  COMMENT '资源英文描述',
    `PARENT` varchar(255) DEFAULT NULL  COMMENT '父类资源',
    `SYSTEM` varchar(255) NOT NULL  COMMENT '所属系统',
    `CREATE_USER` varchar(32) NOT NULL  COMMENT '创建者',
    `CREATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
    `UPDATE_USER` varchar(32) DEFAULT NULL  COMMENT '更新者',
    `UPDATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '修改时间',
    `DELETE` bit(1) DEFAULT NULL  COMMENT '是否删除',
    UNIQUE KEY `ID` (`ID`),
    PRIMARY KEY (`RESOURCE_TYPE`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限资源类型表';

CREATE TABLE IF NOT EXISTS T_AUTH_RESOURCE (
	`ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`PROJECT_CODE` varchar(32) NOT NULL COMMENT '项目ID',
	`RESOURCE_TYPE` varchar(32) NOT NULL COMMENT '资源类型',
	`RESOURCE_CODE` varchar(255) NOT NULL COMMENT '资源ID',
	`RESOURCE_NAME` varchar(255) NOT NULL COMMENT '资源名',
	`IAM_RESOURCE_CODE` varchar(32) NOT NULL COMMENT 'IAM资源ID',
	`ENABLE` bit(1) NOT NULL DEFAULT b'0' COMMENT '开启权限管理,0-不启用,1-启用',
	`RELATION_ID` varchar(32) NOT NULL COMMENT '关联的IAM分级管理员ID',
	`CREATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`UPDATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
	`CREATE_USER` varchar(64) NOT NULL DEFAULT '' COMMENT '创建者',
	`UPDATE_USER` varchar(64) NOT NULL DEFAULT '' COMMENT '修改人',
	PRIMARY KEY (`ID`),
	UNIQUE KEY `IDX_PROJECT_RESOURCE` (`PROJECT_CODE`, `RESOURCE_TYPE`, `RESOURCE_CODE`),
	UNIQUE KEY `IDX_PROJECT_IAM_RESOURCE` (`PROJECT_CODE`, `RESOURCE_TYPE`, `IAM_RESOURCE_CODE`),
    INDEX `RESOURCE_TYPE_UPDATE_TIME_IDX` (`RESOURCE_TYPE`,`UPDATE_TIME`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '资源表';

CREATE TABLE IF NOT EXISTS T_AUTH_RESOURCE_GROUP (
	`ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`PROJECT_CODE` varchar(32) NOT NULL COMMENT '项目ID',
	`RESOURCE_TYPE` varchar(32) NOT NULL COMMENT '资源类型',
	`RESOURCE_CODE` varchar(255) NOT NULL COMMENT '资源ID',
	`RESOURCE_NAME` varchar(255) NOT NULL COMMENT '资源名',
	`IAM_RESOURCE_CODE` varchar(32) NOT NULL COMMENT 'IAM资源ID',
	`GROUP_CODE` varchar(32) NOT NULL COMMENT '用户组标识',
	`GROUP_NAME` varchar(255) NOT NULL COMMENT '用户组名称',
	`DEFAULT_GROUP` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否为默认组,0-非默认组,1-默认组',
	`RELATION_ID` varchar(32) NOT NULL COMMENT '关联的IAM组ID',
	`CREATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`UPDATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`ID`),
	UNIQUE KEY `UNIQ_PROJECT_RESOURCE` (`PROJECT_CODE`, `RESOURCE_TYPE`, `RESOURCE_CODE`, `GROUP_NAME`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '资源关联用户组表';

CREATE TABLE IF NOT EXISTS T_AUTH_RESOURCE_GROUP_CONFIG (
	`ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`RESOURCE_TYPE` varchar(32) NOT NULL COMMENT '资源类型',
	`GROUP_CODE` varchar(32) NOT NULL COMMENT '用户组标识',
	`GROUP_NAME` varchar(32) NOT NULL COMMENT '用户组名称',
	`CREATE_MODE` bit NOT NULL DEFAULT b'0' COMMENT '创建模式,0-开启时创建,1-启用权限管理时创建',
	`DESCRIPTION` text DEFAULT NULL COMMENT '用户组描述',
	`AUTHORIZATION_SCOPES` mediumtext NOT NULL COMMENT '用户组授权范围',
	`ACTIONS` text DEFAULT NULL COMMENT '用户组拥有的资源操作',
	`CREATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`UPDATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`ID`),
	UNIQUE KEY `IDX_RESOURCE_TYPE_GROUP_CODE` (`RESOURCE_TYPE`, `GROUP_CODE`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '资源用户组配置表';

CREATE TABLE IF NOT EXISTS `T_AUTH_ITSM_CALLBACK` (
	`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
	`APPLY_ID` int(11) NOT NULL COMMENT '权限中心申请单ID',
	`SN` varchar(64) NOT NULL COMMENT 'ITSM申请单号',
	`ENGLISH_NAME` varchar(64) NOT NULL COMMENT '项目英文名',
	`CALLBACK_ID` varchar(32) NOT NULL COMMENT '权限中心审批单ID',
	`APPLICANT` varchar(32) NOT NULL COMMENT '申请人',
	`APPROVER` varchar(32) DEFAULT NULL COMMENT '最后审批人',
	`APPROVE_RESULT` bit(1) DEFAULT NULL COMMENT '审批结果，0-审批拒绝，1-审批成功',
	`UPDATE_TIME` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
	`CREATE_TIME` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
	PRIMARY KEY (`ID`),
	UNIQUE KEY `UNIQ_SN` (`SN`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT '权限itsm回调表';

CREATE TABLE IF NOT EXISTS T_AUTH_MIGRATION(
    `PROJECT_CODE` varchar(32) not null comment '项目ID',
    `STATUS` int(10) DEFAULT '0' COMMENT '迁移状态, 0-迁移中,1-迁移成功,2-迁移失败',
    `BEFORE_GROUP_COUNT` int default 0 comment '迁移前用户组数',
    `AFTER_GROUP_COUNT` int default 0 comment '迁移后用户组数',
    `RESOURCE_COUNT` text null comment '迁移后资源数和资源用户组数',
    `START_TIME` datetime NULL COMMENT '开始时间',
    `END_TIME` datetime NULL COMMENT '结束时间',
    `TOTAL_TIME` bigint null comment '总耗时',
    `ERROR_MESSAGE` text DEFAULT NULL COMMENT '错误信息',
    `ROUTER_TAG` varchar(32) DEFAULT NULL COMMENT '迁移项目的网关路由tags',
    `CREATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `UPDATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`PROJECT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限迁移';

CREATE TABLE IF NOT EXISTS `T_AUTH_MONITOR_SPACE` (
    `PROJECT_CODE` varchar(32) not null comment '项目ID',
    `SPACE_BIZ_ID` bigint(20) NOT NULL  COMMENT '监控空间业务ID',
    `SPACE_UID` varchar(64) NOT NULL  COMMENT '监控空间ID',
    `CREATOR` varchar(32) not null comment '创建人',
    `UPDATE_USER` varchar(32) DEFAULT NULL  COMMENT '更新者',
    `UPDATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `CREATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`PROJECT_CODE`,`SPACE_BIZ_ID`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT= '蓝盾监控空间权限表';

CREATE TABLE IF NOT EXISTS `T_AUTH_OAUTH2_CLIENT_DETAILS` (
    `CLIENT_ID` VARCHAR(32) NOT NULL COMMENT '客户端标识',
    `CLIENT_SECRET` VARCHAR(64) NOT NULL COMMENT '客户端秘钥',
    `CLIENT_NAME` VARCHAR(255) NOT NULL COMMENT '客户端名称',
    `SCOPE` MEDIUMTEXT DEFAULT NULL COMMENT '授权操作范围',
    `ICON` MEDIUMTEXT DEFAULT NULL COMMENT '图标',
    `AUTHORIZED_GRANT_TYPES` VARCHAR(64) NOT NULL  COMMENT '授权模式',
    `WEB_SERVER_REDIRECT_URI` MEDIUMTEXT DEFAULT NULL COMMENT '跳转链接',
    `ACCESS_TOKEN_VALIDITY` BIGINT(20)  NOT NULL COMMENT 'access_token有效时间',
    `REFRESH_TOKEN_VALIDITY` BIGINT(20) DEFAULT NULL COMMENT 'refresh_token有效时间',
    `CREATE_USER` VARCHAR(32) NOT NULL DEFAULT '""' COMMENT '创建人',
    `UPDATE_USER` VARCHAR(32) DEFAULT NULL COMMENT '修改人',
    `CREATE_TIME` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `UPDATE_TIME` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`CLIENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户端信息表';

CREATE TABLE IF NOT EXISTS `T_AUTH_OAUTH2_CODE` (
    `CLIENT_ID` VARCHAR(32) NOT NULL COMMENT '客户端标识',
    `CODE` VARCHAR(64)  NOT NULL COMMENT '授权码',
    `USER_NAME` VARCHAR(32) NOT NULL COMMENT '用户名',
    `EXPIRED_TIME` BIGINT(20) NOT NULL COMMENT '过期时间',
    `SCOPE_ID` INT NOT NULL  COMMENT '授权范围ID',
    `CREATE_TIME` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `IDX_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='授权码表';

CREATE TABLE IF NOT EXISTS `T_AUTH_OAUTH2_ACCESS_TOKEN` (
    `ACCESS_TOKEN` VARCHAR(64) NOT NULL COMMENT 'ACCESS_TOKEN',
    `CLIENT_ID` VARCHAR(32) NOT NULL COMMENT '客户端ID',
    `USER_NAME` VARCHAR(32) DEFAULT NULL COMMENT '登录的用户名，客户端模式该值为空',
    `GRANT_TYPE` VARCHAR(32) NOT NULL  COMMENT '授权模式',
    `EXPIRED_TIME` BIGINT(20) NOT NULL COMMENT '过期时间',
    `REFRESH_TOKEN` VARCHAR(64) DEFAULT NULL COMMENT 'REFRESH_TOKEN，客户端模式该值为空',
    `SCOPE_ID` INT NOT NULL  COMMENT '授权范围ID',
    `CREATE_TIME` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `IDX_CLIENT_ID_USER_NAME` (`ACCESS_TOKEN`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='ACCESS_TOKEN表';

CREATE TABLE IF NOT EXISTS `T_AUTH_OAUTH2_REFRESH_TOKEN` (
    `REFRESH_TOKEN` VARCHAR(64) NOT NULL COMMENT 'REFRESH_TOKEN',
    `CLIENT_ID` VARCHAR(32) NOT NULL COMMENT '客户端ID',
    `EXPIRED_TIME` BIGINT(20) NOT NULL COMMENT '过期时间',
    `CREATE_TIME` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX `IDX_REFRESH_TOKEN` (`REFRESH_TOKEN`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='REFRESH_TOKEN表';

CREATE TABLE IF NOT EXISTS `T_AUTH_OAUTH2_SCOPE` (
    `ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主健ID',
    `SCOPE` MEDIUMTEXT NOT NULL  COMMENT '授权范围',
    `CREATE_TIME` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='授权范围表';

CREATE TABLE IF NOT EXISTS `T_AUTH_OAUTH2_SCOPE_OPERATION` (
   `ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主健ID',
   `OPERATION_ID` VARCHAR(64) NOT NULL  COMMENT '授权操作ID',
   `OPERATION_NAME_CN` VARCHAR(64) NOT NULL  COMMENT '授权操作中文名称',
   `OPERATION_NAME_EN` VARCHAR(64) NOT NULL  COMMENT '授权操作英文名称',
   `CREATE_TIME` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='授权操作信息表';

CREATE TABLE IF NOT EXISTS `T_AUTH_USER_DAILY`(
    `PROJECT_ID` VARCHAR(64) not null comment '项目ID',
    `USER_ID`    VARCHAR(64) not null comment '用户ID',
    `THE_DATE`   DATE        not null comment '日期',
    PRIMARY KEY (`PROJECT_ID`, `USER_ID`, `THE_DATE`),
    INDEX IDX_DATE (`THE_DATE`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='每日鉴权用户';

SET FOREIGN_KEY_CHECKS = 1;
