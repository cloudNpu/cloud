package com.kenji.cloud.security;

import com.kenji.cloud.repository.MenuRoleRepository;
import com.kenji.cloud.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SHI Jing
 * @date 2018/12/31 16:38
 */
public class CloudFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private FilterInvocationSecurityMetadataSource  superMetadataSource;

    public CloudFilterInvocationSecurityMetadataSource(FilterInvocationSecurityMetadataSource superMetadataSource) {
        this.superMetadataSource = superMetadataSource;
   //     urlRoleMap = menuRoleService.findAll();
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
//
//    private FilterInvocationSecurityMetadataSource  superMetadataSource;
//


    @Bean("testBean")
    //@Lazy
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        System.out.println("111111111111111111111111111111111111111111111111111111111111111111111111111111111");
//
//        FilterInvocation fi = (FilterInvocation) object;
//        String url = fi.getRequestUrl();
//        if(url.contains("/auth") || url.contains("/eureka/apps")){
//            return superMetadataSource.getAttributes(object);
//        }
//        else{
//            urlRoleMap = getMap();
//            for(Map.Entry<String,String> entry:urlRoleMap.entrySet()){
//                if(antPathMatcher.match(entry.getKey(),url)){
//                    //String[] roles = entry.getValue().split(",");
//                    return SecurityConfig.createList(entry.getValue());
//                }
//            }
//
//        }
        //  返回代码定义的默认配置
        return superMetadataSource.getAttributes(object);

    }

    @Autowired
    private MenuRoleRepository menuRepository;

    private Map<String, String> getMap() {
        MenuRoleRepository menuRoleRepository = (MenuRoleRepository)SpringContextUtils.getBean("getMenuRepository");
        List<Map> resultList = menuRoleRepository.findMap();

        Map<String,String> map = new HashMap();
        for (int i = 0 ; i <resultList.size() ; i++){
            String path = (String) resultList.get(i).get("path");
            String value = (String)resultList.get(i).get("rolevalue");

            if(map.containsKey(path)){
                value = value + ","+map.get(path);
            }
            map.put(path,value);
        }
        System.out.println(map);
        return map;
        //return null;
    }


    //
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    public CloudFilterInvocationSecurityMetadataSource(FilterInvocationSecurityMetadataSource expressionBasedFilterInvocationSecurityMetadataSource){
//        this.superMetadataSource = expressionBasedFilterInvocationSecurityMetadataSource;
//
//        // TODO 从数据库加载权限配置
//    }
      private final AntPathMatcher antPathMatcher = new AntPathMatcher();

     // 这里的需要从DB加载
      private Map<String,String> urlRoleMap;
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return FilterInvocation.class.isAssignableFrom(clazz);
//    }
}
