package com.woniu.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("rbac_user")
public class User {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private boolean status;
    private String salt;



    /* 查询用户时，获取用户列表每个用户初始的权限*/
   /* @TableField(exist = false)
    private List<UserPerm> perms;
    public void setPerms(List<UserPerm> perms) {
        this.perms = perms;
    }
    public List<UserPerm> getPerms() {
        return perms;
    }*/
    public Integer getId() {
        return id;
    }


    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
