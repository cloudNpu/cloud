package com.kenji.cloud.repository.impl;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.repository.UserRepositoryCustom;
import com.kenji.cloud.vo.UserSearchVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kenji
 * @Date: 2019-01-03 19:39
 */
public class UserRepositoryImpl extends SimpleJpaRepository<User, Long> implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        super(User.class,entityManager);
        this.entityManager = entityManager;
    }

    public UserRepositoryImpl(JpaEntityInformation<User, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public UserRepositoryImpl(Class<User> domainClass, EntityManager em) {
        super(domainClass, em);
    }


    @Override
    public List<User> findByProperties(UserSearchVo conditions) {//，u.userRoles
        /*
        * select u.id,u.username,d.DEPTNAME,u.sex,u.BIRTHDAY,u.MOBILE,u.OFFICETEL,GROUP_CONCAT(r.NAME) as roles
        * from user u join dept d on d.ID=u.DEPTID
        * LEFT JOIN user_role ur on u.id=ur.userid
        * LEFT JOIN role r on r.id=ur.roleid
        * where u.username = '123'
         * and exists (select 1 from user_role ur where ur.userid=u.id and ur.roleid = 1)
         * and exists (select 1 from user_role ur where ur.userid=u.id and ur.roleid = 2)
          * GROUP BY u.ID;
        * */
        StringBuffer sb = new StringBuffer("select u.id,u.username,d.DEPTNAME,u.sex,u.BIRTHDAY,u.MOBILE,u.OFFICETEL,GROUP_CONCAT(r.NAME) as roles from user u join dept d on d.ID=u.DEPTID LEFT JOIN user_role ur on u.id=ur.userid LEFT JOIN role r on r.id=ur.roleid where 1=1");

        if (StringUtils.isNotBlank(conditions.getUsername()))
            sb.append(" and u.username like '%").append(conditions.getUsername()).append("%'");
        if (conditions.getDeptId()!=null)
            sb.append(" and u.deptId = '").append(conditions.getDeptId()).append("'");
        if (StringUtils.isNotBlank(conditions.getMobile()))
            sb.append(" and u.mobile = '").append(conditions.getMobile()).append("'");
        if (StringUtils.isNotBlank(conditions.getOfficeTel()))
            sb.append(" and u.officeTel = '").append(conditions.getOfficeTel()).append("'");
        if (StringUtils.isNotBlank(conditions.getSex()))
            sb.append(" and u.sex = '").append(conditions.getSex()).append("'");
        if (conditions.getBirthday()!=null)
            sb.append(" and u.birthday = '").append(conditions.getBirthday()).append("'");
        Long[] ids = conditions.getRoleIds();
        if (ids!=null&&ids.length>0){
            for (Long id : ids) {
                sb.append(" and exists (select 1 from user_role ur where ur.userid=u.id and ur.roleid = ").append(id).append(")");
            }
        }
        sb.append(" group by u.id");
        String hql = sb.toString();
        System.out.println(hql);
//        List<User> users = entityManager.createQuery(hql).getResultList();
        List rows= entityManager.createNativeQuery(hql).getResultList();
        return rows;
    }
}
