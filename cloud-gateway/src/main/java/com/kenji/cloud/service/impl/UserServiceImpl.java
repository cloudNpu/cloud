package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.Dept;
import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserRole;
import com.kenji.cloud.repository.*;
import com.kenji.cloud.service.UserRoleService;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.RoleReturnVo;
import com.kenji.cloud.vo.SaveUserVo;
import com.kenji.cloud.vo.UserReturnVo;
import com.kenji.cloud.vo.UserSearchVo;
import org.springframework.beans.BeanUtils;
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
        User userInDB = userRepository.findByUsername(saveUser.getUsername());
        if (userInDB != null)
            return null;
        Long deptId = saveUser.getDept().getId();
        Dept dept = deptRepository.findById(deptId).get();
        saveUser.setDept(dept);
        saveUser.setCreateDate(new Date());

        //5月28日增加了下面这条语句
        saveUser.setLastPasswordResetDate(new Date());

        User user = userRepository.save(saveUser);
        Long userId = user.getId();
        Long operatorId = saveUserVo.getOperatorId();
        Long[] roleIds = saveUserVo.getRoleIds();
        userRoleService.saveAll(userId, operatorId, roleIds);
        return user;
    }

    @Override
    public User updateUser(User user) {
        User userOldInfo = userRepository.findByUserId(user.getId());
        user.setCreateDate(userOldInfo.getCreateDate());
        if (user.getOperator() == null)
            user.setOperator(userOldInfo.getOperator());
        if (user.getPassword() == null)
            user.setPassword(userOldInfo.getPassword());
        if (user.getDept() == null)
            user.setDept(userOldInfo.getDept());
        user.setLastPasswordResetDate(userOldInfo.getLastPasswordResetDate());  //以后可能要修改
        return userRepository.save(user);
    }


    @Override
    public UserReturnVo findById(Long id) {
        UserReturnVo userReturnVo = new UserReturnVo();
        User user = userRepository.findByUserId(id);
        if (user != null){
            userReturnVo = new UserReturnVo();
        List<RoleReturnVo> roles = userRoleService.getRolesByUserId(id);
        userReturnVo.setId(user.getId().toString());
        userReturnVo.setUsername(user.getUsername());
        userReturnVo.setPassword(user.getPassword());
        userReturnVo.setSex(user.getSex());
        userReturnVo.setDeptName(user.getDept().getDeptName());
        userReturnVo.setBirthday(user.getBirthday().toString());
        userReturnVo.setOfficeTel(user.getOfficeTel());
        userReturnVo.setMobile(user.getMobile());
        userReturnVo.setRoleList(roles);
        }
        else
            return null;
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

    @Override
    public List<UserReturnVo> findAll(){
        List<UserReturnVo> userReturnVos = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for(User user:users){
            UserReturnVo userReturnVo = new UserReturnVo();
            List<RoleReturnVo> roleList = new ArrayList<>();
            String roles = "";

            userReturnVo.setId(user.getId().toString());
            userReturnVo.setUsername(user.getUsername());
            userReturnVo.setPassword(user.getPassword());
            userReturnVo.setIconUrl(user.getIconurl());
            userReturnVo.setDeptName(user.getDept().getDeptName());
            userReturnVo.setMobile(user.getMobile());
            userReturnVo.setOfficeTel(user.getOfficeTel());
            userReturnVo.setSex(user.getSex());
            userReturnVo.setBirthday(user.getBirthday().toString());

            List<UserRole> userRoles = user.getUserRoles();
            for(UserRole userRole:userRoles){
                RoleReturnVo roleReturnVo = new RoleReturnVo();
                BeanUtils.copyProperties(userRole.getRole(), roleReturnVo);
                roleList.add(roleReturnVo);
                roles = roles + userRole.getRole().getName() + ",";
            }
            if (!roles.isEmpty())
                roles = roles.substring(0, roles.length()-1);
            userReturnVo.setRoles(roles);
            userReturnVo.setRoleList(roleList);

            userReturnVos.add(userReturnVo);
        }
        return userReturnVos;
    }
}
