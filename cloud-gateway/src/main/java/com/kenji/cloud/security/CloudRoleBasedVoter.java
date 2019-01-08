package com.kenji.cloud.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author SHI Jing
 * @date 2019/1/2 11:44
 */
public class CloudRoleBasedVoter implements AccessDecisionVoter<Object> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        /*
        * Authentication中是用户及用户权限信息，
        * attributes是访问资源需要的权限，然后循环判断用户是否有访问资源需要的权限，如果有就返回ACCESS_GRANTED，通俗的说就是有权限。
         */
        int result = ACCESS_ABSTAIN;
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);

        for (ConfigAttribute attribute : attributes) {

            if(attribute.getAttribute()==null){
                continue;
            }
            String[] attr = attribute.getAttribute().split(",");


                if (this.supports(attribute)) {
                    result = ACCESS_DENIED;

                    // Attempt to find a matching granted authority
                    for (GrantedAuthority authority : authorities) {
                        for (String attrAtr : attr){
                            if (attrAtr.equals(authority.getAuthority())) {
                                return ACCESS_GRANTED;
                            }
                        }

                    }
                }

        }

        return result;
       // return 0;
    }

    Collection<? extends GrantedAuthority> extractAuthorities(
            Authentication authentication) {
        return authentication.getAuthorities();
    }
}
