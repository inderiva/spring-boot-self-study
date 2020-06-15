package com.meethong.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //ShiroFileterFatoryBean:3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /*
            anon:无需认证就可以访问
            authc:必须认证才能访问
            user:必须拥有记住我功能
            perms:拥有对某个资源的权限
            role:拥有某个角色

         */
        Map<String,String> fileterMap=new LinkedHashMap<>();
        fileterMap.put("/user/add","perms[liaoh]");
        fileterMap.put("/user/update","perms[root]");
        fileterMap.put("/user/*","authc");

        //设置登陆的请求
        bean.setLoginUrl("/login");
        //未授权页面
        bean.setUnauthorizedUrl("/nologin");
        bean.setFilterChainDefinitionMap(fileterMap);
        return  bean;
    }

    // DefaultWebSecurityManager 2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联userRealm对象
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    //创建Realm对象 1
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
    //整合thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return  new ShiroDialect();
    }
}
