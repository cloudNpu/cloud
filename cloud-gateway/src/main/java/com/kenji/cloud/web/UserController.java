package com.kenji.cloud.web;

import com.kenji.cloud.entity.Role;
import com.kenji.cloud.entity.User;
import com.kenji.cloud.service.UserRoleService;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.RoleReturnVo;
import com.kenji.cloud.vo.SaveUserVo;
import com.kenji.cloud.vo.UserReturnVo;
import com.kenji.cloud.vo.UserSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;
    @Autowired
    public UserController(UserService userService,UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    /**
     * @Author fly
     * @Description 增加用户
     * @Date 2019/1/7 9:41
     * @Param [saveUserVo]
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     *
     * @editBy ubeyang
     * @Date 2019/4/18
     * @return 带更新后
     **/
    @PostMapping()
    public ResponseEntity addUser(@RequestBody SaveUserVo saveUserVo) {
        List<UserReturnVo>  userReturnVos = null;
        try {
            User user = userService.saveUser(saveUserVo);
            if (user == null)
                return ResponseEntity.status(400).body("该用户名已被使用");
            UserSearchVo userSearchVo = new UserSearchVo(saveUserVo.getUser().getUsername(),
                                                         saveUserVo.getUser().getDept().getId(),
                                                         saveUserVo.getUser().getMobile(),
                                                         saveUserVo.getUser().getOfficeTel(),
                                                         saveUserVo.getUser().getSex(),
                                                         //saveUserVo.getUser().getBirthday(),
                                                         null,
                                                         saveUserVo.getRoleIds());
            userReturnVos = userService.findSearch(userSearchVo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("创建失败");
        }
        return ResponseEntity.ok(userReturnVos);
    }

    /**
     * @Author fly
     * @Description 批量删除用户
     * @Date 2019/1/7 9:41
     * @Param [params]
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @DeleteMapping()
    public ResponseEntity<String> deleteUsers(@RequestBody Map<String, Long[]> params) {
        try {
            Long[] ids = params.get("ids");
            userService.deleteUsers(ids);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("删除用户失败"+e);
        }
        return ResponseEntity.status(204).body("删除成功!!!!");
    }

    /**
     * @Author fly
     * @Description 修改用户
     * @Date 2019/1/7 9:40
     * @Param [user]
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@RequestBody User user) {
        List<UserReturnVo>  userReturnVos = null;
        try {
            userService.updateUser(user);
            UserSearchVo userSearchVo = new UserSearchVo(user.getUsername(),
                    user.getDept().getId(),
                    user.getMobile(),
                    user.getOfficeTel(),
                    user.getSex(),
                    //saveUserVo.getUser().getBirthday(),
                    null,
                    null);
            userReturnVos = userService.findSearch(userSearchVo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("更新用户失败"+e);
        }
        return ResponseEntity.status(201).body(userReturnVos);
    }

    /**
     * @Author fly
     * @Description 通过用户id查找用户
     * @Date 2019/1/7 9:39
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<com.kenji.cloud.entity.User>
     **/
    @GetMapping("/{id}")
    public ResponseEntity<UserReturnVo> getUserById(@PathVariable Long id) {
        UserReturnVo vo = userService.findById(id);
        return ResponseEntity.status(200).body(vo);
    }

    /**
     * @Author fly
     * @Description 用户的复杂查找
     * @Date 2019/1/7 9:39
     * @Param [userSearchVo]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.kenji.cloud.entity.User>>
     **/
    @GetMapping()
    public ResponseEntity<List<UserReturnVo>> getUsersByCondition(@RequestParam(name = "username", defaultValue = "") String username,
                                                                  @RequestParam(name = "deptId", defaultValue = "") Long deptId,
                                                                  @RequestParam(name = "mobile", defaultValue = "") String mobile,
                                                                  @RequestParam(name = "officeTel", defaultValue = "") String officeTel,
                                                                  @RequestParam(name = "sex", defaultValue = "") String sex,
                                                                  @RequestParam(name = "birthday", defaultValue = "") String birthdayStr,
                                                                  @RequestParam(name = "roleIds", defaultValue = "") Long[] roleIds) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date birthday = null;
        try {
            birthday = format.parse(birthdayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        UserSearchVo userSearchVo = new UserSearchVo();
        //Date birthday = new Date(birthdayStr);
        userSearchVo.setUsername(username);
        userSearchVo.setDeptId(deptId);
        userSearchVo.setMobile(mobile);
        userSearchVo.setOfficeTel(officeTel);
        userSearchVo.setSex(sex);
        userSearchVo.setBirthday(birthday);
        //userSearchVo.setBirthday(null);
        userSearchVo.setRoleIds(roleIds);
        List<UserReturnVo> list = userService.findSearch(userSearchVo);
        return ResponseEntity.status(200).body(list);
    }

    /**
     * @Author fly
     * @Description 批量向多个用户增加角色关系
     * @Date 2019/1/7 9:38
     * @Param [params]
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @PostMapping("/roles")
    public ResponseEntity addUserRoles(@RequestBody Map<String, Long[]> params) {
        List<UserReturnVo> userReturnVos = null;
        try {
            Long[] userIds= params.get("userIds");
            Long[] roleIds= params.get("roleIds");
            Long operatorId = params.get("operatorId")[0];
            userRoleService.updateUserRoles(userIds, roleIds, operatorId);

        } catch (Exception e) {
            return ResponseEntity.status(400).body("编辑失败" + e);
        }
        userReturnVos = userService.findAll();
        return ResponseEntity.status(201).body(userReturnVos);
    }

    /**
     * @Author fly
     * @Description 查找用户对应的角色列表
     * @Date 2019/1/7 9:39
     * @Param [userId]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.kenji.cloud.entity.Role>>
     **/
    @GetMapping("/roles/{id}")
    public ResponseEntity<List<RoleReturnVo>> getRolesByUserId(@PathVariable Long id) {
        List<RoleReturnVo> list;
        try {
            list = userRoleService.getRolesByUserId(id);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(list);
    }
}
