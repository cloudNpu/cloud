package com.kenji.cloud.service.impl;

import com.kenji.cloud.service.MenuRoleService;
import com.kenji.cloud.repository.MenuRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SHI Jing
 * @date 2018/12/26 10:28
 */
@Service
public class MenuRoleServiceImpl implements MenuRoleService {
    @Autowired
    private MenuRoleRepository menuRepository;

    @Bean
    public MenuRoleRepository getMenuRepository() {
        return menuRepository;
    }


    public void setMenuRepository(MenuRoleRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    @Override
    public Map<String,String> findAll() {
        List<Map> resultList = menuRepository.findMap();

        Map<String,String> map = new HashMap();
        for (int i = 0 ; i <resultList.size() ; i++){
            String path = (String) resultList.get(i).get("path");
            String value = "'"+(String)resultList.get(i).get("rolevalue")+"'";

            if(map.containsKey(path)){
                value = value + ","+map.get(path);
            }
            map.put(path,value);
        }
        return map;
    }


}
