package com.yc.sandfactory.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * TODO hsun 完成注释
 *
 * @author hsun
 * @version 1.0
 * @since 2018/5/19 上午11:39
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "customRealm")
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager ();
        securityManager.setRealm(customRealm);
        return securityManager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login.html");
        Map<String, String> filterChainDefinitions = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        filterChainDefinitions.put("/login", "anon");
        filterChainDefinitions.put("/index.html", "authc");
//    anon：匿名权限，无需认证和授权直接可以访问
//    authc：需要认证才可以访问
//    authc, perms[maintain:*]：需要认证，且需要特定的权限授权才可以访问
//    authc,roles[admin]：需要认证，且需要特定的角色授权才可以访问
        filterChainDefinitions.put("/admin/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
        return shiroFilterFactoryBean;
    }
}
