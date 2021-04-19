package com.woniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.entity.Perm;
import com.woniu.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    //根据用户名获取用户的权限
    public List<Perm> getPermissions(String  username);


}
