package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.entity.UserRole;
import com.kenji.cloud.repository.RoleRepository;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.repository.UserRoleRepository;
import com.kenji.cloud.service.UserRoleService;
import com.kenji.cloud.service.UserService;
import com.kenji.cloud.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserRoleService userRoleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
    }

    @Transactional
    @Override
    public User saveUser(UserVo userVo) {
        Long operatorId = userVo.getOperatorId();
        User user = userRepository.save(userVo.getUser());
        Long userId = user.getId();
        Long[] roleIds = userVo.getRoleIds();
        userRoleService.saveAll(userId, operatorId, roleIds);
        return user;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).get();
//        System.out.println(user.getDept());
//        Dept dept = new Dept();
//        BeanUtils.copyProperties(user.getDept(),dept);
//        user.setDept(user.getDept());
        return user;
    }

    @Override
    public String findPasswordByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user.getPassword();
    }

    @Override
    public List<User> findSearch(User model) {
        List<User> list = userRepository.findAll((Specification<User>) (root, query, criteriaBuilder) -> {
            List<Predicate> list1 = new ArrayList<>();

            //查询条件示例
            //equal示例
            /*if (!StringUtils.isEmpty(name)){
                Predicate namePredicate = cb.equal(root.get("name"), name);
                predicatesList.add(namePredicate);
            }*/
            if (StringUtils.isNotBlank(model.getUsername())) {
                list1.add(criteriaBuilder.like(root.get("username"), model.getUsername()));
            }
//            if (StringUtils.isNotBlank(model.getDept().getDeptName())) {
//                list1.add(criteriaBuilder.equal(root.get("deptName"), model.getDept().getDeptName()));
//            }
            if (StringUtils.isNotBlank(model.getMobile())) {
                list1.add(criteriaBuilder.like(root.get("mobile"), model.getMobile()));
            }
            if (StringUtils.isNotBlank(model.getOfficeTel())) {
                list1.add(criteriaBuilder.like(root.get("officeTel"), model.getOfficeTel()));
            }
            if (StringUtils.isNotBlank(model.getSex())) {
                list1.add(criteriaBuilder.equal(root.get("sex"), model.getSex()));
            }
            if (StringUtils.isNotBlank(model.getSex())) {
                list1.add(criteriaBuilder.equal(root.get("sex"), model.getSex()));
            }
            /*
             * 还有出生日期，角色，部门
             * */


            /*//like示例
            if (!StringUtils.isEmpty(nickName)){
                Predicate nickNamePredicate = cb.like(root.get("nickName"), '%'+nickName+'%');
                predicatesList.add(nickNamePredicate);
            }*/
            /*//between示例
            if (birthday != null) {
                Predicate birthdayPredicate = cb.between(root.get("birthday"), birthday, new Date());
                predicatesList.add(birthdayPredicate);
            }*/

            /*//关联表查询示例
            if (!StringUtils.isEmpty(courseName)) {
                Join<Student,Teacher> joinTeacher = root.join("teachers",JoinType.LEFT);
                Predicate coursePredicate = cb.equal(joinTeacher.get("courseName"), courseName);
                predicatesList.add(coursePredicate);
            }*/

            /*//复杂条件组合示例
            if (chineseScore!=0 && mathScore!=0 && englishScore!=0 && performancePoints!=0) {
                Join<Student,Examination> joinExam = root.join("exams",JoinType.LEFT);
                Predicate predicateExamChinese = cb.ge(joinExam.get("chineseScore"),chineseScore);
                Predicate predicateExamMath = cb.ge(joinExam.get("mathScore"),mathScore);
                Predicate predicateExamEnglish = cb.ge(joinExam.get("englishScore"),englishScore);
                Predicate predicateExamPerformance = cb.ge(joinExam.get("performancePoints"),performancePoints);
                //组合
                Predicate predicateExam = cb.or(predicateExamChinese,predicateExamEnglish,predicateExamMath);
                Predicate predicateExamAll = cb.and(predicateExamPerformance,predicateExam);
                predicatesList.add(predicateExamAll);
            }*/

//            //排序示例(先根据学号排序，后根据姓名排序)
//            query.orderBy(cb.asc(root.get("studentNumber")),cb.asc(root.get("name")));
            //--------------------------------------------
            //最终将查询条件拼好然后return

            Predicate[] p = new Predicate[list1.size()];
            return criteriaBuilder.and(list1.toArray(p));
        });
        return list;
    }

    @Override
    public void deleteUsers(Long[] ids) {
        for (Long id : ids) {
            
            userRepository.deleteById(id);
        }
    }

}
