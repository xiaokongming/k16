package com.woniu.config;


import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //创建realm对象  需要自定义realm类
    @Bean
    public JwtRealm userRealm(){
        return new JwtRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(JwtRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //登录失败 跳转请求
        shiroFilterFactoryBean.setLoginUrl("/user/unauthenticated");
        //授权失败  跳转的请求
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauthorized");

        //拦截之后，跳到登录页面  设置登录的请求
        //shiroFilterFactoryBean.setLoginUrl("/login");

        /*
         * 定义Filter链
         */
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("anon", new AnonymousFilter());
        filters.put("jwtAuthentication", new JwtAuthenticationFilter()); //必须要new  不能装配
        shiroFilterFactoryBean.setFilters(filters);

        /*
         * 拦截规则
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/unauthenticated", "anon");
        filterChainDefinitionMap.put("/unauthorized", "anon");

        // 【认证】靠 jwt-filter，【鉴权】靠注解。
        filterChainDefinitionMap.put("/**", "jwtAuthentication");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     *  ShiroFilterChainDefinition 的配置仍然需要。
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        return new DefaultShiroFilterChainDefinition();
    }
}
