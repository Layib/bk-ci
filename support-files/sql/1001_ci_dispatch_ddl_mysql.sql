USE devops_ci_dispatch;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for T_DISPATCH_PIPELINE_BUILD
-- ----------------------------

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_BUILD` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PROJECT_ID` varchar(32) NOT NULL COMMENT '项目ID',
  `PIPELINE_ID` varchar(34) NOT NULL COMMENT '流水线ID',
  `BUILD_ID` varchar(34) NOT NULL COMMENT '构建ID',
  `VM_SEQ_ID` varchar(34) NOT NULL DEFAULT '' COMMENT '构建序列号',
  `VM_ID` bigint(20) NOT NULL COMMENT '虚拟机ID',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime NOT NULL COMMENT '更新时间',
  `STATUS` int(11) NOT NULL COMMENT '状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';

-- ----------------------------
-- Table structure for T_DISPATCH_PIPELINE_DOCKER_BUILD
-- ----------------------------

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_DOCKER_BUILD` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `BUILD_ID` varchar(64) NOT NULL COMMENT '构建ID',
  `VM_SEQ_ID` int(20) NOT NULL COMMENT '构建序列号',
  `SECRET_KEY` varchar(64) NOT NULL DEFAULT '' COMMENT '密钥',
  `STATUS` int(11) NOT NULL COMMENT '状态',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime NOT NULL COMMENT '更新时间',
  `ZONE` varchar(128) DEFAULT NULL COMMENT '构建机地域',
  `PROJECT_ID` varchar(34) DEFAULT '' COMMENT '项目ID',
  `PIPELINE_ID` varchar(34) DEFAULT '' COMMENT '流水线ID',
  `DISPATCH_MESSAGE` varchar(4096) DEFAULT '' COMMENT '发送信息',
  `STARTUP_MESSAGE` text COMMENT '启动信息',
  `ROUTE_KEY` varchar(64) DEFAULT '' COMMENT '消息队列的路由KEY',
  `DOCKER_INST_ID` bigint(20) DEFAULT NULL COMMENT '',
  `VERSION_ID` int(20) DEFAULT NULL COMMENT '版本ID',
  `TEMPLATE_ID` int(20) DEFAULT NULL COMMENT '模板ID',
  `NAMESPACE_ID` bigint(20) DEFAULT NULL COMMENT '命名空间ID',
  `DOCKER_IP` VARCHAR(64) DEFAULT '' COMMENT '构建机IP',
  `CONTAINER_ID` VARCHAR(128) DEFAULT '' COMMENT '构建容器ID',
  `POOL_NO` INT(11) DEFAULT 0 COMMENT '构建容器池序号',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BUILD_ID` (`BUILD_ID`,`VM_SEQ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';

-- ----------------------------
-- Table structure for T_DISPATCH_PIPELINE_DOCKER_HOST_ZONE
-- ----------------------------

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_DOCKER_HOST_ZONE` (
  `HOST_IP` varchar(128) NOT NULL COMMENT '主机ip',
  `ZONE` varchar(128) NOT NULL COMMENT '构建机地域',
  `ENABLE` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `REMARK` varchar(1024) DEFAULT NULL COMMENT '评论',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime NOT NULL COMMENT '更新时间',
  `TYPE` int(11) NOT NULL DEFAULT '0' COMMENT '类型',
  `ROUTE_KEY` varchar(45) DEFAULT NULL COMMENT '消息队列的路由KEY',
  PRIMARY KEY (`HOST_IP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';

-- ----------------------------
-- Table structure for T_DISPATCH_PIPELINE_DOCKER_TASK
-- ----------------------------

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_DOCKER_TASK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PROJECT_ID` varchar(64) NOT NULL COMMENT '项目ID',
  `AGENT_ID` varchar(32) NOT NULL COMMENT '构建机ID',
  `PIPELINE_ID` varchar(34) NOT NULL DEFAULT '' COMMENT '流水线ID',
  `BUILD_ID` varchar(34) NOT NULL COMMENT '构建ID',
  `VM_SEQ_ID` int(20) NOT NULL COMMENT '构建序列号',
  `STATUS` int(11) NOT NULL COMMENT '状态',
  `SECRET_KEY` varchar(128) NOT NULL COMMENT '密钥',
  `IMAGE_NAME` varchar(1024) NOT NULL COMMENT '镜像名称',
  `CHANNEL_CODE` varchar(128) DEFAULT NULL COMMENT '渠道号，默认为DS',
  `HOST_TAG` varchar(128) DEFAULT NULL COMMENT '主机标签',
  `CONTAINER_ID` varchar(128) DEFAULT NULL COMMENT '构建容器ID',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime NOT NULL COMMENT '更新时间',
  `ZONE`          varchar(128) DEFAULT NULL COMMENT '构建机地域',
  `REGISTRY_USER` varchar(128) DEFAULT NULL COMMENT '注册用户名',
  `REGISTRY_PWD`  varchar(128) DEFAULT NULL COMMENT '注册用户密码',
  `IMAGE_TYPE`    varchar(128) DEFAULT NULL COMMENT '镜像类型',
  `CONTAINER_HASH_ID` varchar(128) DEFAULT NULL COMMENT '构建Job唯一标识',
  `IMAGE_PUBLIC_FLAG` bit(1) DEFAULT NULL COMMENT '镜像是否为公共镜像：0否1是',
  `IMAGE_RD_TYPE` tinyint(1) DEFAULT NULL COMMENT '镜像研发来源：0自研1第三方',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BUILD_ID` (`BUILD_ID`,`VM_SEQ_ID`),
  KEY `UPDATED_TIME` (`UPDATED_TIME`),
  KEY `STATUS` (`STATUS`),
  KEY `HOST_TAG` (`HOST_TAG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';

-- ----------------------------
-- Table structure for T_DISPATCH_THIRDPARTY_AGENT_BUILD
-- ----------------------------

CREATE TABLE IF NOT EXISTS `T_DISPATCH_THIRDPARTY_AGENT_BUILD` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PROJECT_ID` varchar(64) NOT NULL COMMENT '项目ID',
  `AGENT_ID` varchar(32) NOT NULL COMMENT '构建机ID',
  `PIPELINE_ID` varchar(34) NOT NULL DEFAULT '' COMMENT '流水线ID',
  `BUILD_ID` varchar(34) NOT NULL COMMENT '构建ID',
  `VM_SEQ_ID` varchar(34) NOT NULL COMMENT '构建序列号',
  `STATUS` int(11) NOT NULL COMMENT '状态',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime NOT NULL COMMENT '更新时间',
  `WORKSPACE` varchar(4096) DEFAULT NULL COMMENT '工作空间',
  `BUILD_NUM` int(20) DEFAULT '0' COMMENT '构建次数',
  `PIPELINE_NAME` varchar(255) DEFAULT '' COMMENT '流水线名称',
  `TASK_NAME` varchar(255) DEFAULT '' COMMENT '任务名称',
  `AGENT_IP` varchar(128) DEFAULT '' COMMENT '节点IP',
  `NODE_ID` bigint(20) DEFAULT 0 COMMENT '第三方构建机NODE_ID',
  `DOCKER_INFO` json NULL COMMENT '第三方构建机docker构建信息',
  `EXECUTE_COUNT` int(11) NULL COMMENT '流水线执行次数',
  `CONTAINER_HASH_ID` varchar(128) NULL COMMENT '容器ID日志使用',
  `ENV_ID` bigint(20) NULL COMMENT '第三方构建所属环境',
  `IGNORE_ENV_AGENT_IDS` json NULL COMMENT '这次调度被排除的agent节点',
  `JOB_ID` VARCHAR(128) NULL COMMENT '当前构建所属jobid',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BUILD_ID` (`BUILD_ID`,`VM_SEQ_ID`),
  KEY `idx_agent_id` (`AGENT_ID`),
  KEY `idx_pipeline_id`(`PIPELINE_ID`),
  KEY `idx_status`(`STATUS`),
  KEY `IDX_PROJECT_PIPELINE_SEQ_STATUS_TIME`(`PROJECT_ID`, `PIPELINE_ID`, `VM_SEQ_ID`, `STATUS`, `CREATED_TIME`),
  KEY `IDX_AGENTID_STATUS_UPDATE`(`AGENT_ID`, `STATUS`, `UPDATED_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_DOCKER_DEBUG`
(
    `ID`            bigint(20)       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `PROJECT_ID`    varchar(64)   NOT NULL COMMENT '项目ID',
    `PIPELINE_ID`   varchar(34)   NOT NULL DEFAULT '' COMMENT '流水线ID',
    `VM_SEQ_ID`     varchar(34)   NOT NULL COMMENT '构建序列号',
    `POOL_NO`       int(11)       NOT NULL DEFAULT 0 COMMENT '构建池序号',
    `STATUS`        int(11)       NOT NULL COMMENT '状态',
    `TOKEN`         varchar(128)           DEFAULT NULL COMMENT 'TOKEN',
    `IMAGE_NAME`    varchar(1024) NOT NULL COMMENT '镜像名称',
    `HOST_TAG`      varchar(128)           DEFAULT NULL COMMENT '主机标签',
    `CONTAINER_ID`  varchar(128)           DEFAULT NULL COMMENT '构建容器ID',
    `CREATED_TIME`  datetime      NOT NULL COMMENT '创建时间',
    `UPDATED_TIME`  datetime      NOT NULL COMMENT '修改时间',
    `ZONE`          varchar(128)           DEFAULT NULL COMMENT '构建机地域',
    `BUILD_ENV`     varchar(4096)          DEFAULT NULL COMMENT '构建机环境变量',
    `REGISTRY_USER` varchar(128)           DEFAULT NULL COMMENT '注册用户名',
    `REGISTRY_PWD`  varchar(128)           DEFAULT NULL COMMENT '注册用户密码',
    `IMAGE_TYPE`    varchar(128)           DEFAULT NULL COMMENT '镜像类型',
    `IMAGE_PUBLIC_FLAG` bit(1) DEFAULT NULL COMMENT '镜像是否为公共镜像：0否1是',
    `IMAGE_RD_TYPE` tinyint(1) DEFAULT NULL COMMENT '镜像研发来源：0自研1第三方',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `PIPELINE_ID` (`PIPELINE_ID`, `VM_SEQ_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT='';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_DOCKER_ENABLE`
(
    `PIPELINE_ID` varchar(64) NOT NULL COMMENT '流水线ID',
    `ENABLE`      tinyint(1)  NOT NULL DEFAULT '0' COMMENT '是否启用',
    `VM_SEQ_ID`   int(20)     NOT NULL DEFAULT '-1' COMMENT '构建序列号',
    PRIMARY KEY (`PIPELINE_ID`, `VM_SEQ_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT='';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_DOCKER_HOST`
(
    `PROJECT_CODE` varchar(128) NOT NULL COMMENT '用户组所属项目',
    `HOST_IP`      varchar(128) NOT NULL COMMENT '主机ip',
    `REMARK`       varchar(1024)         DEFAULT NULL COMMENT '评论',
    `CREATED_TIME` datetime     NOT NULL COMMENT '创建时间',
    `UPDATED_TIME` datetime     NOT NULL COMMENT '更新时间',
    `TYPE`         int(11)      NOT NULL DEFAULT '0' COMMENT '类型',
    `ROUTE_KEY`    varchar(45)           DEFAULT NULL COMMENT '消息队列的路由KEY',
    PRIMARY KEY (`PROJECT_CODE`, `HOST_IP`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT='';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_DOCKER_TASK_SIMPLE` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PIPELINE_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '流水线ID',
    `VM_SEQ` varchar(64) NOT NULL DEFAULT '' COMMENT '构建机序号',
    `DOCKER_IP` varchar(64) NOT NULL DEFAULT '' COMMENT '构建容器IP',
    `DOCKER_RESOURCE_OPTION` int(11) NOT NULL DEFAULT 0 COMMENT '构建资源配置',
    `GMT_CREATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `GMT_MODIFIED` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UNI_BUILD_SEQ` (`PIPELINE_ID`,`VM_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='DOCKER构建任务表';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_DOCKER_TASK_DRIFT` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PIPELINE_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '流水线ID',
    `BUILD_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '构建ID',
    `VM_SEQ` varchar(64) NOT NULL DEFAULT '' COMMENT '构建机序号',
    `OLD_DOCKER_IP` varchar(64) NOT NULL DEFAULT '' COMMENT '旧构建容器IP',
    `NEW_DOCKER_IP` varchar(64) NOT NULL DEFAULT '' COMMENT '新构建容器IP',
    `OLD_DOCKER_IP_INFO` varchar(1024) NOT NULL DEFAULT '' COMMENT '旧容器IP负载',
    `GMT_CREATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `GMT_MODIFIED` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`ID`),
    INDEX `IDX_P_B`(`PIPELINE_ID`, `BUILD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='DOCKER构建任务漂移记录表';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_DOCKER_POOL` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `PIPELINE_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '流水线ID',
    `VM_SEQ` varchar(64) NOT NULL DEFAULT '' COMMENT '构建机序号',
    `POOL_NO` int(11) NOT NULL DEFAULT 0 COMMENT '构建池序号',
    `STATUS` int(11) NOT NULL DEFAULT 0 COMMENT '构建池状态',
    `GMT_CREATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `GMT_MODIFIED` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UNI_BUILD_SEQ` (`PIPELINE_ID`,`VM_SEQ`, `POOL_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='DOCKER并发构建池状态表';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PIPELINE_DOCKER_IP_INFO` (
    `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `DOCKER_IP` varchar(64) NOT NULL DEFAULT '' COMMENT 'DOCKER IP',
    `DOCKER_HOST_PORT` int(11) NOT NULL DEFAULT 80 COMMENT 'DOCKER PORT',
    `CAPACITY` int(11) NOT NULL DEFAULT 0 COMMENT '节点容器总容量',
    `USED_NUM` int(11) NOT NULL DEFAULT 0 COMMENT '节点容器已使用容量',
    `CPU_LOAD` int(11) NOT NULL DEFAULT 0 COMMENT '节点容器CPU负载',
    `MEM_LOAD` int(11) NOT NULL DEFAULT 0 COMMENT '节点容器MEM负载',
    `DISK_LOAD` int(11) NOT NULL DEFAULT 0 COMMENT '节点容器DISK负载',
    `DISK_IO_LOAD` int(11) NOT NULL DEFAULT 0 COMMENT '节点容器DISK IO负载',
    `ENABLE` bit(1) DEFAULT 0 COMMENT '节点是否可用',
    `SPECIAL_ON` bit(1) DEFAULT 0 COMMENT '节点是否作为专用机',
    `GRAY_ENV` bit(1) DEFAULT 0 COMMENT '是否为灰度节点',
    `CLUSTER_NAME` varchar (64) DEFAULT 'COMMON' COMMENT '构建集群类型',
    `GMT_CREATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `GMT_MODIFIED` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UNI_IP` (`DOCKER_IP`),
    INDEX `idx_1` (`ENABLE`, `GRAY_ENV`, `CPU_LOAD`, `MEM_LOAD`, `DISK_LOAD`, `DISK_IO_LOAD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='DOCKER构建机负载表';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_QUOTA_SYSTEM` (
  `VM_TYPE` varchar(128) NOT NULL COMMENT '构建机类型',
  `RUNNING_JOBS_MAX_SYSTEM` int(10) NOT NULL COMMENT '蓝盾系统最大并发JOB数',
  `RUNNING_JOBS_MAX_PROJECT` int(10) NOT NULL COMMENT '单项目默认最大并发JOB数',
	`RUNNING_TIME_JOB_MAX` int(10) NOT NULL COMMENT '系统默认所有单个JOB最大执行时间',
  `RUNNING_TIME_JOB_MAX_PROJECT` int(10) NOT NULL COMMENT '默认单项目所有JOB最大执行时间',
	`RUNNING_JOBS_MAX_GITCI_SYSTEM` int(10) NOT NULL COMMENT '工蜂CI系统总最大并发JOB数量',
  `RUNNING_JOBS_MAX_GITCI_PROJECT` int(10) NOT NULL COMMENT '工蜂CI单项目最大并发JOB数量',
	`RUNNING_TIME_JOB_MAX_GITCI` int(10) NOT NULL COMMENT '工蜂CI单JOB最大执行时间',
  `RUNNING_TIME_JOB_MAX_PROJECT_GITCI` int(10) NOT NULL COMMENT '工蜂CI单项目最大执行时间',
	`PROJECT_RUNNING_JOB_THRESHOLD` int(10) NOT NULL COMMENT '项目执行job数量告警阈值',
	`PROJECT_RUNNING_TIME_THRESHOLD` int(10) NOT NULL COMMENT '项目执行job时间告警阈值',
	`SYSTEM_RUNNING_JOB_THRESHOLD` int(10) NOT NULL COMMENT '系统执行job数量告警阈值',
	`CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime NOT NULL COMMENT '更新时间',
	`OPERATOR` varchar(128) NOT NULL COMMENT '操作人',
  PRIMARY KEY (`VM_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配额';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_QUOTA_PROJECT` (
  `PROJECT_ID` varchar(128) NOT NULL COMMENT '项目ID',
  `VM_TYPE` varchar(128) NOT NULL COMMENT 'VM 类型',
  `CHANNEL_CODE` varchar(128) NOT NULL DEFAULT 'BS' COMMENT '构建来源，包含：BS,CODECC,AM,GIT等',
  `RUNNING_JOBS_MAX` int(10) NOT NULL COMMENT '项目最大并发JOB数',
	`RUNNING_TIME_JOB_MAX` int(10) NOT NULL COMMENT '项目单JOB最大执行时间',
  `RUNNING_TIME_PROJECT_MAX` int(10) NOT NULL COMMENT '项目所有JOB最大执行时间',
	`CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime NOT NULL COMMENT '更新时间',
	`OPERATOR` varchar(128) NOT NULL COMMENT '操作人',
  PRIMARY KEY (`PROJECT_ID`, `VM_TYPE`, `CHANNEL_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目配额';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_RUNNING_JOBS` (
	`ID` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PROJECT_ID` varchar(128) NOT NULL COMMENT '项目ID',
  `VM_TYPE` varchar(128) NOT NULL COMMENT 'VM 类型',
    `CHANNEL_CODE` varchar(128) NOT NULL DEFAULT 'BS' COMMENT '构建来源，包含：BS,CODECC,AM,GIT等',
	`BUILD_ID` varchar(128) NOT NULL COMMENT '构建ID',
	`VM_SEQ_ID` varchar(128) NOT NULL COMMENT '构建序列号',
	`EXECUTE_COUNT` int(11) NOT NULL COMMENT '执行次数',
	`CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
	`AGENT_START_TIME` datetime NULL COMMENT '构建机启动时间',
  PRIMARY KEY (`ID`),
	KEY `inx_project_id` (`PROJECT_ID`, `VM_TYPE`, `CHANNEL_CODE`),
  KEY `inx_vm_type` (`VM_TYPE`),
  KEY `inx_build_id` (`BUILD_ID`),
	KEY `inx_vm_seq_id` (`VM_SEQ_ID`),
	KEY `inx_create_time` (`CREATED_TIME`),
	KEY `inx_agent_start_time` (`AGENT_START_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='运行中的JOB';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_PROJECT_RUN_TIME` (
  `PROJECT_ID` varchar(128) NOT NULL COMMENT '项目ID',
  `VM_TYPE` varchar(128) NOT NULL COMMENT 'VM 类型',
  `RUN_TIME` BIGINT NOT NULL COMMENT '运行时长',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`PROJECT_ID`, `VM_TYPE`),
  KEY `inx_project_id` (`PROJECT_ID`),
  KEY `inx_vm_type` (`VM_TYPE`),
  KEY `inx_create_time` (`UPDATE_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目当月已使用额度';

CREATE TABLE IF NOT EXISTS `T_DOCKER_RESOURCE_OPTIONS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CPU_PERIOD` int NOT NULL DEFAULT 10000 COMMENT 'CPU配置',
  `CPU_QUOTA` int NOT NULL DEFAULT 160000 COMMENT 'CPU配置',
  `MEMORY_LIMIT_BYTES` bigint NOT NULL DEFAULT 34359738368 COMMENT '内存：32G',
  `DISK` int NOT NULL DEFAULT 100 COMMENT '磁盘：100G',
  `BLKIO_DEVICE_WRITE_BPS` bigint NOT NULL DEFAULT 125829120 COMMENT '磁盘写入速率，120m/s',
  `BLKIO_DEVICE_READ_BPS` bigint NOT NULL DEFAULT 125829120 COMMENT '磁盘读入速率，120m/s',
  `DESCRIPTION` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '描述',
  `GMT_CREATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `GMT_MODIFIED` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='docker基础配额表';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_KUBERNETES_BUILD_CONTAINER_POOL_NO` (
  `BUILD_ID` varchar(64) NOT NULL COMMENT '构建ID',
  `VM_SEQ_ID` varchar(64) NOT NULL COMMENT 'VmSeqID',
  `CONTAINER_NAME` varchar(128) DEFAULT NULL COMMENT '容器名称',
  `POOL_NO` varchar(128) DEFAULT NULL COMMENT '构建机池编号',
  `CREATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `EXECUTE_COUNT` int(11) NOT NULL DEFAULT '1' COMMENT '流水线重试次数',
  PRIMARY KEY (`BUILD_ID`,`VM_SEQ_ID`,`EXECUTE_COUNT`),
  KEY `inx_tpi_create_time` (`CREATE_TIME`),
  KEY `inx_tpi_build_id` (`BUILD_ID`),
  KEY `inx_tpi_vm_seq_id` (`VM_SEQ_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='buildId和containerName,poolNo的映射关系';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_KUBERNETES_BUILD` (
  `PIPELINE_ID` varchar(34) NOT NULL,
  `VM_SEQ_ID` varchar(34) NOT NULL,
  `POOL_NO` int(11) NOT NULL,
  `PROJECT_ID` varchar(64) NOT NULL,
  `CONTAINER_NAME` varchar(128) NOT NULL,
  `IMAGES` varchar(1024) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `CREATED_TIME` timestamp NULL DEFAULT NULL,
  `UPDATE_TIME` timestamp NULL DEFAULT NULL,
  `USER_ID` varchar(34) NOT NULL,
  `DEBUG_STATUS` bit(1) DEFAULT b'0' COMMENT '是否处于debug状态',
  `DEBUG_TIME` timestamp NULL DEFAULT NULL COMMENT 'debug时间',
  `CPU` int(11) DEFAULT '16' COMMENT 'CPU',
  `MEMORY` varchar(64) DEFAULT '32768M' COMMENT '内存',
  `DISK` varchar(64) DEFAULT '100G' COMMENT '磁盘',
  PRIMARY KEY (`PIPELINE_ID`,`VM_SEQ_ID`,`POOL_NO`),
  KEY `idx_1` (`STATUS`,`DEBUG_STATUS`,`DEBUG_TIME`),
  KEY `idx_pipeline_vm_name` (`PIPELINE_ID`,`VM_SEQ_ID`,`CONTAINER_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='dispatch-kubernetes流水线构建机信息';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_KUBERNETES_BUILD_HIS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PIPELINE_ID` varchar(64) NOT NULL DEFAULT '' COMMENT 'pipeline id',
  `BUIDLD_ID` varchar(64) NOT NULL DEFAULT '' COMMENT 'build id',
  `VM_SEQ_ID` varchar(64) NOT NULL DEFAULT '' COMMENT 'vm seq id',
  `CONTAINER_NAME` varchar(128) DEFAULT '' COMMENT '容器名称',
  `GMT_CREATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `GMT_MODIFIED` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `CPU` int(11) DEFAULT '16' COMMENT 'CPU',
  `MEMORY` varchar(64) DEFAULT '32768M' COMMENT '内存',
  `DISK` varchar(64) DEFAULT '100G' COMMENT '磁盘',
  `SECRET_KEY` varchar(64) DEFAULT '' COMMENT '构建密钥',
  `POOL_NO` varchar(64) DEFAULT '' COMMENT '并发构建池',
  `EXECUTE_COUNT` int(11) DEFAULT '1' COMMENT '流水线重试次数',
  PRIMARY KEY (`ID`),
  KEY `idx_1` (`BUIDLD_ID`,`PIPELINE_ID`,`VM_SEQ_ID`),
  KEY `idx_pipeline_vm_modify` (`PIPELINE_ID`,`VM_SEQ_ID`,`GMT_CREATE`),
  KEY `IDX_BUILD_VM_COUNT` (`BUIDLD_ID`,`VM_SEQ_ID`,`EXECUTE_COUNT`)
) ENGINE=InnoDB AUTO_INCREMENT=75421050 DEFAULT CHARSET=utf8 COMMENT='dispatch kubernetes 构建历史记录';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_THIRDPARTY_AGENT_DOCKER_DEBUG`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `AGENT_ID` varchar(32) NOT NULL COMMENT '构建机ID',
  `PROJECT_ID` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '项目ID',
  `PIPELINE_ID` varchar(34) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '流水线ID',
  `BUILD_ID` varchar(34) NOT NULL COMMENT '构建ID',
  `VM_SEQ_ID` varchar(34) CHARACTER SET utf8mb4 NOT NULL COMMENT '构建序列号',
  `USER_ID` varchar(34) CHARACTER SET utf8mb4 NOT NULL COMMENT '调试用户',
  `STATUS` int(11) NOT NULL COMMENT '状态',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime NOT NULL COMMENT '修改时间',
  `WORKSPACE` varchar(4096) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '工作空间',
  `DOCKER_INFO` json NULL COMMENT '第三方构建机docker构建信息',
  `ERR_MSG` text CHARACTER SET utf8mb4 NULL COMMENT '启动构建时的错误信息',
  `DEBUG_URL` varchar(4096) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT 'debug链接',
  PRIMARY KEY (`ID`),
  KEY `BUILD_ID` (`BUILD_ID`,`VM_SEQ_ID`),
  KEY `IDX_AGENTID_STATUS_UPDATE`(`AGENT_ID`, `STATUS`, `UPDATED_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方构建机Docker登录调试';

CREATE TABLE IF NOT EXISTS `T_DISPATCH_QUOTA_JOB_SYSTEM`
(
    `ID`                             bigint(11)   NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `VM_TYPE`                        varchar(64)  NOT NULL DEFAULT '' COMMENT '构建机类型',
    `CHANNEL_CODE`                   varchar(128) NOT NULL DEFAULT '' COMMENT '构建来源，包含：BS,CODECC,AM,GIT等',
    `RUNNING_JOBS_MAX_SYSTEM`        int(11)      NOT NULL DEFAULT '1000' COMMENT '系统最大并发JOB数',
    `RUNNING_JOBS_MAX_PROJECT`       int(11)      NOT NULL DEFAULT '100' COMMENT '单项目默认最大并发JOB数',
    `RUNNING_TIME_JOB_MAX`           int(11)      NOT NULL DEFAULT '24' COMMENT '系统默认所有单个JOB最大执行时间',
    `RUNNING_TIME_JOB_MAX_PROJECT`   int(11)      NOT NULL DEFAULT '1000' COMMENT '默认单项目所有JOB最大执行时间',
    `PROJECT_RUNNING_JOB_THRESHOLD`  int(11)      NOT NULL DEFAULT '80' COMMENT '项目执行job数量告警阈值',
    `PROJECT_RUNNING_TIME_THRESHOLD` int(11)      NOT NULL DEFAULT '80' COMMENT '项目执行job时间告警阈值',
    `SYSTEM_RUNNING_JOB_THRESHOLD`   int(11)      NOT NULL DEFAULT '80' COMMENT '系统执行job数量告警阈值',
    `OPERATOR`                       varchar(128) NOT NULL DEFAULT '' COMMENT '操作人',
    `CREATE_TIME`                    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `UPDATE_TIME`                    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`ID`),
    UNIQUE INDEX `UNI_KEY` (`VM_TYPE`, `CHANNEL_CODE`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '流水线JOB配额系统表';

SET FOREIGN_KEY_CHECKS = 1;
