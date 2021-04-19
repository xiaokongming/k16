package com.woniu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.woniu.entity.User;
import com.woniu.service.UserService;
import com.woniu.util.ResponseResult;
import jwt.JwtTokenUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public ResponseResult<Object> login(@RequestBody User account){
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",account.getUsername());
            User user = userService.getOne(queryWrapper);
            if(user == null){
                return new ResponseResult("用户名错误",400);
            }else{
                if(!account.getPassword().equals(user.getPassword())){
                    return new ResponseResult("密码错误",400);
                }
            }
            //生成token
            String token = JwtTokenUtil.createSign(user.getUsername());
            System.out.println("==="+token);
            //查询用户权限
            //List<Perm> permissions = userService.getPermissions(user.getId());
            //user.setPerms(permissions);
            return new ResponseResult<Object>(token,200);
        }catch(Exception ex){
            return new ResponseResult(400,"签名失败");
        }
    }

    @RequestMapping("unauthenticated")
    public ResponseResult<Object> unauthenticatedFail(){
        return new ResponseResult("token非法",400);
    }

    @RequestMapping("list")
    public ResponseResult<Page<User>> userList(String query, Integer pagenum, Integer pagesize){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(query)){
            queryWrapper.like("username",query);
        }
        Page userPage = new Page( pagenum, pagesize);
        userPage = userService.page(userPage, queryWrapper);
        return new ResponseResult(userPage,200);
    }


    @RequestMapping("add")
    @RequiresPermissions("user:add")
    public ResponseResult<Void> addUser(@RequestBody User user){

        userService.save(user);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("delete")
    @RequiresPermissions("user:delete")
    public ResponseResult<Void> deleteUserById(Integer id){
        userService.removeById(id);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("update")
    @RequiresPermissions("user:update")
    public ResponseResult<Void> updateUser(@RequestBody User user){

        userService.updateById(user);
        return ResponseResult.SUCCESS;
    }


}
