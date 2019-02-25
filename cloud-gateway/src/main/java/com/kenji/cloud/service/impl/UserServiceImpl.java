package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Dept;
import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.repository.*;
import com.kenji.cloud.service.UserRoleService;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.RoleReturnVo;
import com.kenji.cloud.vo.SaveUserVo;
import com.kenji.cloud.vo.UserReturnVo;
import com.kenji.cloud.vo.UserSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserRoleService userRoleService;
    private UserRoleRepository userRoleRepository;
    private UserAppRepository userAppRepository;
    private SysLogRepository sysLogRepository;
    private AppLogRepository appLogRepository;
    private InstanceInfoRepository instanceInfoRepository;
    private DeptRepository deptRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, UserRoleRepository userRoleRepository, UserAppRepository userAppRepository, SysLogRepository sysLogRepository, AppLogRepository appLogRepository, InstanceInfoRepository instanceInfoRepository, DeptRepository deptRepository) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.userRoleRepository = userRoleRepository;
        this.userAppRepository = userAppRepository;
        this.sysLogRepository = sysLogRepository;
        this.appLogRepository = appLogRepository;
        this.instanceInfoRepository = instanceInfoRepository;
        this.deptRepository = deptRepository;
    }


    @Transactional
    @Override
    public User saveUser(SaveUserVo saveUserVo) {
        User saveUser = saveUserVo.getUser();
        Long deptId = saveUser.getDept().getId();
        Dept dept = deptRepository.findById(deptId).get();
        saveUser.setDept(dept);
        saveUser.setCreateDate(new Date());
        User user = userRepository.save(saveUser);
        Long userId = user.getId();
        Long operatorId = saveUserVo.getOperatorId();
        Long[] roleIds = saveUserVo.getRoleIds();
        userRoleService.saveAll(userId, operatorId, roleIds);
        return user;
    }

    @Override
    public User updateUser(User user) {
        Dept dept =deptRepository.findById(user.getDept().getId()).get();
        user.setDept(dept);
        return userRepository.save(user);
    }


    @Override
    public UserReturnVo findById(Long id) {
        User user = userRepository.findByUserId(id);
        List<RoleReturnVo> roles = userRoleService.getRolesByUserId(id);
        UserReturnVo userReturnVo = new UserReturnVo();
        userReturnVo.setId(user.getId().toString());
        userReturnVo.setUsername(user.getUsername());
        userReturnVo.setPassword(user.getPassword());
        userReturnVo.setSex(user.getSex());
        userReturnVo.setDeptName(user.getDept().getDeptName());
        userReturnVo.setBirthday(user.getBirthday().toString());
        userReturnVo.setOfficeTel(user.getOfficeTel());
        userReturnVo.setMobile(user.getMobile());
        userReturnVo.setRoleList(roles);
        return userReturnVo;
    }

    @Override
    public List<UserReturnVo> findSearch(UserSearchVo conditions) {
        List rows =userRepository.findByProperties(conditions);
        List<UserReturnVo> list = new ArrayList<>();
        for (Object row : rows) {
            Object[] cells = (Object[]) row;
            UserReturnVo ret = new UserReturnVo();
            ret.setId(cells[0].toString());
            ret.setUsername(cells[1].toString());
            ret.setDeptName(cells[2].toString());
            ret.setSex(cells[3].toString());
            ret.setBirthday(cells[4].toString());
            if (cells[5]!=null) ret.setMobile(cells[5].toString());
            if (cells[6]!=null) ret.setOfficeTel(cells[6].toString());
            if (cells[7]!=null) ret.setRoles(cells[7].toString());
            list.add(ret);
        }
        return list;
    }

    @Override
    @Transactional
    public void deleteUsers(Long[] ids) {
        if (ids.length==0) return;
        for (Long id : ids) {
            //1.将所有operatorId是该用户id的记录都设为null
            //update user set operatorid=null where operatorid=id;
            //update user_app set operatorid=null where operatorid=id;(不能为null，所以被我删了）
            //update user_role set operatorid=null where operatorid=id;(不能为null，所以被我删了）
            userRepository.updateOperatorIdToNull(id);
            userAppRepository.deleteByOperatorId(id);
            userRoleRepository.deleteByOperatorId(id);

            //2.删除子表中userid是该id的记录
            //delete from user_role where userid=id;
            //delete from user_app where userid=id;
            //delete from syslog where userid=id;
            //delete from instanceinfo where userid=id;
            //delete from applog where userid=id;
            userRoleRepository.deleteByUserId(id);
            userAppRepository.deleteByUserId(id);
            sysLogRepository.deleteByUserId(id);
            instanceInfoRepository.deleteByUserId(id);
            appLogRepository.deleteByUserId(id);

            //3.删除主表用户记录
            //delete from user where id=id;
            userRepository.deleteUserById(id);
        }
    }

    @Override//张书玮自用
    public User getUser(Long id) {
      return userRepository.findById(id).get();
    }
}
