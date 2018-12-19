package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 1:35 PM
 * create table `USER` (
 * 	`ID` bigint(20) not null,
 * 	`DEPTID` bigint(20) not null comment '部门ID',
 * 	`OPERATORID` bigint(20) comment '操作人',
 * 	`USERNAME` varchar(200) not null,
 * 	`SEX` varchar(2) not null comment '男或女',
 * 	`BIRTHDAY` datetime not null,
 * 	`ICONURL` varchar(100),
 * 	`MOBILE` varchar(20) comment '移动电话',
 * 	`OFFICETEL` varchar(20) comment '办公电话',
 * 	`CREATEDATE` timestamp not null default current_timestamp comment '创建时间',
 * 	primary key (`ID`),
 * 	constraint `USER_DEPTID` foreign key (`DEPTID`) references `DEPT` (`ID`),
 * 	constraint `USER_OPERATORID` foreign key (`OPERATORID`) references `USER` (`ID`)
 * )ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '用户表';
 */
@Entity
@Data
public class User {
    @Id
    private Integer id;
    /**
     * 部门ID
     */
    private Integer deptid;
    /**
     * 操作人
     */
    private Integer operatorid;
    private String username;
    private String password;
    private String sex;
    private Date birthday;
    private String iconurl;
    /**
     * 移动电话
     */
    private String mobile;
    /**
     * 办公电话
     */
    private String officetel;
    private Date createdate;
}
