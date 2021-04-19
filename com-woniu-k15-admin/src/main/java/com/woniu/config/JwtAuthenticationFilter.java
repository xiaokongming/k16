package com.woniu.config;

import com.woniu.entity.User;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }


        String jwtToken = request.getHeader("Authorization");
        UsernamePasswordToken token = new UsernamePasswordToken(jwtToken,jwtToken);
        try {
            this.getSubject(servletRequest,servletResponse).login(token);
        }catch(Exception ex){
            ex.printStackTrace();
            HttpServletResponse response =  (HttpServletResponse)servletResponse;
            response.sendRedirect("/user/unauthenticated");
            return false;
        }
        //合法则放行
        return true;
    }

}
