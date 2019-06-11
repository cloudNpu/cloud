package com.kenji.cloud.security;

import com.kenji.cloud.CloudGateway;
import com.kenji.cloud.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;


import java.util.*;

/**
 * 获取请求url所需要的权限value
 * @author SHI Jing
 * @date 2018/12/31 16:38
 */
@Configurable(preConstruction = true)
public class CloudFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private FilterInvocationSecurityMetadataSource  superMetadataSource;

    @Autowired
    private MenuRoleRepository menuRoleRepository;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public CloudFilterInvocationSecurityMetadataSource(FilterInvocationSecurityMetadataSource superMetadataSource) {
        this.superMetadataSource = superMetadataSource;
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


    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //System.out.println("111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        ArrayList<ConfigAttribute> roleValueCfgs = new ArrayList<>();
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();    //获取传入的uri
        String method = fi.getRequest().getMethod();

        if (url.contains("?")){
            url = url.substring(0, url.indexOf('?'));
        }
        //若为登陆和心跳检测等则返回白名单
        if(url.contains("/auth")){
            return superMetadataSource.getAttributes(object);
        }
        String urlPattern = "/cloud/apps/*/*";
        if (antPathMatcher.match(urlPattern, url) && method.equals("PUT"))
            return superMetadataSource.getAttributes(object);
        List<Map<String, String>> menu_role = getMap();
        for (Map<String, String> map: menu_role){
            String pattern = map.get("path") + "/**";
            if (!antPathMatcher.isPattern(pattern)){
                pattern = map.get("path");
            }
            if (antPathMatcher.match(pattern,url)){
                ConfigAttribute configAttribute = new SecurityConfig(map.get("value"));
                roleValueCfgs.add(configAttribute);
            }
        }
        if (roleValueCfgs.isEmpty()){
            ConfigAttribute configAttribute = new SecurityConfig("AccessDenied");
            roleValueCfgs.add(configAttribute);
        }
        return roleValueCfgs;
    }



     /**
     * 从数据库加载roleMenu Map
     * @return List<Map<String, String>>
     */
    private List<Map<String, String>> getMap() {
        if (this.menuRoleRepository == null)
            this.menuRoleRepository = (MenuRoleRepository) CloudGateway.getBean("menuRoleRepository");
        List<Map<String, String>> result = menuRoleRepository.findRoleMenu();
        return result;
    }

}
