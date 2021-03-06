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
    public List<User> findByProperties(UserSearchVo conditions) {
        Long[] ids = conditions.getRoleIds();
        StringBuilder sb = new StringBuilder("select u.id,u.username,dept.deptname,u.sex,u.birthday,u.mobile," +
                "u.officetel,group_concat(role.name) as roles from ");
        if (ids != null && ids.length > 0) {
            StringBuilder sd1 = new StringBuilder("(select userid FROM user_role where ROLEID in (");
            for (Long id : ids) {
                sd1.append(id).append(",");
            }
            sd1.deleteCharAt(sd1.length() - 1);
            sd1.append(") GROUP BY userid HAVING COUNT(ROLEID)>=").append(ids.length).append(") us" +
                    ",user u,user_role ur,role,dept where us.userid=u.id " +
                    "and u.id=ur.userid and ur.roleid=role.id and u.deptid=dept.id");
            sb.append(sd1);
        } else {
            sb.append("user u,user_role ur,role,dept where u.id=ur.userid and ur.roleid=role.id and u.deptid=dept.id");
        }
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
        sb.append(" group by u.id");
        String hql = sb.toString();
        System.out.println(hql);
        List rows= entityManager.createNativeQuery(hql).getResultList();
        return rows;
    }
}
