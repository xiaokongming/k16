package com.woniu.config;

import com.woniu.entity.Perm;
import com.woniu.service.UserService;
import jwt.JwtTokenUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JwtRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String jwtToken = (String)authenticationToken.getPrincipal();
        try {
             JwtTokenUtil.checkSign(jwtToken);
             return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(),
                                authenticationToken.getCredentials(),JwtRealm.class.getName());
        } catch (RuntimeException e) {
            System.out.println("jwt 解密失败。jwt 已过期，或被篡改。");
            throw new IncorrectCredentialsException(e);
        }
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {


        String token = (String)principalCollection.getPrimaryPrincipal() ;
        //解析token
        String userName = JwtTokenUtil.getUserId(token);

        List<Perm> permissions = userService.getPermissions(userName);
        Set<String> permissionSet = new HashSet<>();
        for(Perm perm:permissions){
            permissionSet.add(perm.getPerCode());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionSet);

        return info;

    }
}
