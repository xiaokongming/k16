package com.woniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.entity.Perm;
import com.woniu.entity.User;

import java.util.List;
import java.util.Map;

public interface PermService extends IService<Perm> {
    public List<Perm> getMenuByUserName(String userName);

    public List<Perm> getPermsByUserName(String userName);


    public void deletePermsByUserId(Integer userId);
    public void addUserPerms(Map map);
}
