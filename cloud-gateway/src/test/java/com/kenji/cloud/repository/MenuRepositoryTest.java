package com.kenji.cloud.repository;

import com.kenji.cloud.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * create table `MENU` (
 * 	`ID` bigint(20) not null auto_increment,
 * 	`MENUFID` bigint(20) not null comment '上级菜单ID',
 * 	`NAME` varchar(200) not null comment '菜单名称',
 * 	`ICON` varchar(200),
 * 	`PATH` varchar(200) comment '路径',
 * 	`REDIRECT` varchar(200) comment '重定向路径',
 * 	`COMPONENT` varchar(200) comment '组件路径,Ant Design中使用',
 * 	`ROUTES` varchar(200) comment '路由,Ant Design中使用',
 * 	`AUTHORITY` varchar(200) comment '权限,Ant Design中使用',
 * 	`HIDEINMENU` varchar(200) comment '是否隐藏,Ant Design中使用',
 * 	`CREATEDATE` timestamp not null default current_timestamp comment '创建时间',
 * 	`DESCRIPTION` varchar(200) comment '描述',
 * 	primary key (`ID`),
 * 	constraint `MENU_MENUFID` foreign key (`MENUFID`) references `MENU` (`ID`)
 * )ENGINE=InnoDB default CHARSET=utf8 comment '菜单表';
 * @Author: Cjmmy
 * @Date: 2018/12/27 10:37 AM
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuRepositoryTest {
    @Autowired
    private MenuRepository repository;
    @Test
    public void testSave(){
        Menu menu = new Menu();
        menu.setId(3l);
        menu.setName("部门管理");
        menu.setMenu(menu);
        repository.save(menu);
    }

    @Test
    public void testFindById(){
        Menu menu = repository.findById(3l).get();
        System.out.println(menu);
    }
}