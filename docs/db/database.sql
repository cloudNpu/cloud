SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `DEPT`;
create table `DEPT` (
	`ID` bigint(20) not null auto_increment,
	`DEPTNAME` varchar(200) not null comment '部门名称',
	`DESCRIPTION` varchar(200) comment '描述',
	`OPERDATE` timestamp not null default current_timestamp comment '操作时间',
	primary key (`ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '部门表';


DROP TABLE IF EXISTS `USER`;
create table `USER` (
	`ID` bigint(20) not null auto_increment,
	`USERNAME` varchar(200) not null,
	`PASSWORD` varchar(200) not null,
	`SEX` varchar(10) not null comment '男或女',
	`BIRTHDAY` datetime not null,
	`ICONURL` varchar(100),
	`MOBILE` varchar(20) comment '移动电话',
	`OFFICETEL` varchar(20) comment '办公电话',
	`DEPTID` bigint(20) not null comment '部门ID',
	`OPERATORID` bigint(20) comment '操作人',
	`CREATEDATE` timestamp not null default current_timestamp comment '创建时间',
	`LASTPASSWORDRESETDATE` timestamp comment '上次密码重置时间',
	primary key (`ID`),
	constraint `USER_DEPTID` foreign key (`DEPTID`) references `DEPT` (`ID`),
	constraint `USER_OPERATORID` foreign key (`OPERATORID`) references `USER` (`ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '用户表';

DROP TABLE IF EXISTS `ROLE`;
create table `ROLE` (
	`ID` bigint(20) not null auto_increment,
	`NAME` varchar(60) not null comment '角色名称',
	`VALUE` varchar(60) not null comment '角色名称',
	`DESCRIPTION` varchar(200) comment '角色描述',
	`CREATEDATE` timestamp not null default current_timestamp comment '创建时间',
	primary key (`ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '角色表';

DROP TABLE IF EXISTS `USER_ROLE`;
create table `USER_ROLE` (
	`ID` bigint(20) not null auto_increment,
	`USERID` bigint(20) not null comment '用户ID',
	`ROLEID` bigint(20) not null comment '角色ID',
	`OPERATORID` bigint(20) not null comment '操作人',
	`CREATEDATE` timestamp not null default current_timestamp comment '授权时间',
	primary key (`ID`),
	constraint `USER_ROLE_USERID` foreign key (`USERID`) references `USER` (`ID`),
	constraint `USER_ROLE_ROLEID` foreign key (`ROLEID`) references `ROLE` (`ID`),
	constraint `USER_ROLE_OPERATORID` foreign key (`OPERATORID`) references `USER` (`ID`)
)ENGINE=InnoDB default CHARSET=utf8 comment '用户角色分配表';

DROP TABLE IF EXISTS `MENU`;
create table `MENU` (
	`ID` bigint(20) not null auto_increment,
	`MENUFID` bigint(20) not null comment '上级菜单ID',
	`NAME` varchar(200) not null comment '菜单名称',
	`ICON` varchar(200),
	`PATH` varchar(200) comment '路径',
	`REDIRECT` varchar(200) comment '重定向路径',
	`COMPONENT` varchar(200) comment '组件路径,Ant Design中使用',
	`ROUTES` varchar(200) comment '路由,Ant Design中使用',
	`AUTHORITY` varchar(200) comment '权限,Ant Design中使用',
	`HIDEINMENU` varchar(200) comment '是否隐藏,Ant Design中使用',
	`CREATEDATE` timestamp not null default current_timestamp comment '创建时间',
	`DESCRIPTION` varchar(200) comment '描述',
	primary key (`ID`),
	constraint `MENU_MENUFID` foreign key (`MENUFID`) references `MENU` (`ID`)
)ENGINE=InnoDB default CHARSET=utf8 comment '菜单表';

DROP TABLE IF EXISTS `ROLE_MENU`;
create table `ROLE_MENU` (
	`ID` bigint(20) not null auto_increment,
	`ROLEID` bigint(20) not null comment '角色ID',
	`MENUID` bigint(20) not null comment '菜单ID',
	`CREATEDATE` timestamp not null default current_timestamp comment '创建时间',
	primary key (`ID`),
	constraint `ROLE_MENU_ROLEID` foreign key (`ROLEID`) references `ROLE` (`ID`),
	constraint `ROLE_MENU_MENUID` foreign key (`MENUID`) references `MENU` (`ID`)
)ENGINE=InnoDB default CHARSET=utf8 comment '角色菜单分配表';

DROP TABLE IF EXISTS `LEASEINFO`;
create table `LEASEINFO` (
	`ID` bigint(20) not null auto_increment,
	`RENEWALINTERVALINSECS` bigint(10) not null default 30,
	`DURATIONINSECS` bigint(10) not null default 90,
	`REGISTRATIONTIMESTAMP` datetime ,
	`LASTRENEWALTIMESTAMP` datetime,
	`EVICTIONTIMESTAMP` datetime,
	`SERVICEUPTIMESTAMP` datetime,
	primary key (`ID`)
)ENGINE=InnoDB default CHARSET=utf8 comment '租约信息表';

DROP TABLE IF EXISTS `INSTANCEINFO`;
create table `INSTANCEINFO` (
	`ID` bigint(20) not null auto_increment,
	`USERID` bigint(20) not null comment '用户ID',
	`LEASEINFOID` bigint(20) not null comment 'LeaseInfoID',
	`INSTANCEID` varchar(200) not null comment '服务实例ID',
	`APPNAME` varchar(200) not null comment '服务名',
	`APPGROUPNAME` varchar(200) comment '服务组名',
	`IPADDR` varchar(200) not null comment 'IP地址',
	`SID` varchar(200) comment 'SID',
	`PORT` bigint(6) not null comment '端口号',
	`SECUREPORT` bigint(6) comment '安全端口',
	`HOMEPAGEURL` varchar(200) comment '主页URL',
	`STATUSPAGEURL` varchar(200) comment '状态URL',
	`HEALTHCHECKURL` varchar(200) comment '健康检查URL',
	`SECUREHEALTHCHECKURL` varchar(200) comment 'SECUREHEALTHCHECKURL',
	`VIPADDRESS` varchar(200) comment 'VIPADDRESS',
	`SECUREVIPADDRESS` varchar(200) comment 'secureVipAddress',
	`STATUSPAGERELATIVEURL` varchar(200) comment 'statusPageRelativeUrl',
	`STATUSPAGEEXPLICITURL` varchar(200) comment 'statusPageExplicitUrl',
	`HEALTHCHECKRELATIVEURL` varchar(200) comment 'healthCheckRelativeUrl',
	`HEALTHCHECKSECUREEXPLICITURL` varchar(200) comment 'healthCheckSecureExplicitUrl',
	`VIPADDRESSUNRESOLVED` varchar(200) comment 'vipAddressUnresolved',
	`SECUREVIPADDRESSUNRESOLVED` varchar(200) comment 'secureVipAddressUnresolved',
	`HEALTHCHECKEXPLICITURL` varchar(200) comment 'healthCheckExplicitUrl',
	`COUNTRYID` bigint(20) comment 'countryId',
	`ISSECUREPORTENABLED` varchar(10) default 'false' comment 'isSecurePortEnabled',
	`ISUNSECUREPORTENABLED` varchar(10) default 'true' comment 'isUnsecurePortEnabled',
	`DATACENTERINFO` varchar(20) comment 'DataCenterInfo',
	`HOSTNAME` varchar(200) comment 'hostName',
	`STATUS` varchar(20) comment 'status',
	`OVERRIDDENSTATUS` varchar(20) comment 'overriddenStatus',
	`ISINSTANCEINFODIRTY` varchar(10) default 'false' comment 'isInstanceInfoDirty',
	`ISCOORDINATINGDISCOVERYSERVER` varchar(10) default '0' comment 'isCoordinatingDiscoveryServer',
	`METADATA` varchar(100) comment 'metadata',
	`LASTUPDATEDTIMESTAMP` timestamp default current_timestamp comment 'lastUpdatedTimestamp',
	`LASTDIRTYTIMESTAMP` datetime comment 'lastDirtyTimestamp',
	`ACTIONTYPE` varchar(20) comment 'actionType',
	`ASGNAME` varchar(100) comment 'asgName',
	`VERSION` varchar(200) default 'VERSION_UNKNOWN' comment 'version',
	`VISIBLE` varchar(20) comment '是否可见',
	`INPUTPARAMS` varchar(100) comment 'inputParams输入参数',
	`OUTPUTPARAMS` varchar(100) comment 'outputParams输出参数',
	`COMPLEXTYPE` varchar(200) comment 'complexType自定义类型',
	`METHOD` varchar(20) not null default 'POST' comment 'complexType自定义类型',
	`INVOKECOUNT` bigint(20) comment '调用次数',
	primary key (`ID`),
	constraint `INSTANCEINFO_USERID` foreign key (`USERID`) references `USER` (`ID`),
	constraint `INSTANCEINFO_LEASEINFOID` foreign key (`LEASEINFOID`) references `LEASEINFO` (`ID`)
)ENGINE=InnoDB default CHARSET=utf8 comment '服务实例信息表';

DROP TABLE IF EXISTS `USER_APP`;
create table `USER_APP` (
	`ID` bigint(20) not null auto_increment,
	`APPNAME` varchar(200) comment '服务名称',
	`USERID` bigint(20) not null comment '用户ID',
	`OPERATORID` bigint(20) not null comment '操作人',
	`CREATEDATE` timestamp not null default current_timestamp comment '授权时间',
	`COMMENT` varchar(100) comment '备注',
	primary key (`ID`),
	constraint `USER_APP_OPERATORID` foreign key (`OPERATORID`) references `USER` (`ID`),
	constraint `USER_APP_USERID` foreign key (`USERID`) references `USER` (`ID`)
)ENGINE=InnoDB default CHARSET=utf8 comment '用户服务授权表';

DROP TABLE IF EXISTS `APPLOG`;
create table `APPLOG` (
	`ID` bigint(20) not null auto_increment,
	`INSTANCEINFOID` bigint(20) comment '实例ID',
	`APPNAME` varchar(200) comment '服务名',
	`OPERATION`  varchar(200) comment '操作',
	`USERID` bigint(20) not null comment '操作人',
	`CREATEDATE` timestamp not null default current_timestamp comment '日志时间',
	`COMMENT` varchar(100) comment '备注',
	primary key (`ID`),
	constraint `APPLOG_INSTANCEINFOID` foreign key (`INSTANCEINFOID`) references `INSTANCEINFO` (`ID`),
	constraint `APPLOG_USERID` foreign key (`USERID`) references `USER` (`ID`)
)ENGINE=InnoDB default CHARSET=utf8 comment '服务日志表';

DROP TABLE IF EXISTS `SYSLOG`;
create table `SYSLOG` (
	`ID` bigint(20) not null auto_increment,
	`OPERATION`  varchar(200) comment '操作',
	`USERID` bigint(20) not null comment '操作人',
	`CREATEDATE` timestamp not null default current_timestamp comment '日志时间',
	`COMMENT` varchar(100) comment '备注',
	primary key (`ID`),
	constraint `SYSLOG_USERID` foreign key (`USERID`) references `USER` (`ID`)
)ENGINE=InnoDB default CHARSET=utf8 comment '系统日志表';


BEGIN;
INSERT INTO `DEPT` VALUES (1, '研发部', NULL, '2018-12-20 11:17:34');
COMMIT;


BEGIN;
INSERT INTO `USER` VALUES (1, 'admin', 'admin', '1', '2018-12-20 11:18:57', NULL, NULL, NULL, 1,1, '2018-12-20 11:19:02', NULL);
COMMIT;

BEGIN;
INSERT INTO `ROLE` VALUES (1, '管理员', 'ROLE_ADMIN', '管理员', '2018-12-20 11:19:56');
COMMIT;

BEGIN;
INSERT INTO `USER_ROLE` VALUES (1, 1, 1, 1, '2018-12-20 18:52:08');
COMMIT;


SET FOREIGN_KEY_CHECKS = 1;
