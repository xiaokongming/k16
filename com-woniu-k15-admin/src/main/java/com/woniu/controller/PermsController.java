package com.woniu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.entity.Perm;
import com.woniu.entity.Subject;
import com.woniu.entity.Teacher;
import com.woniu.entity.User;
import com.woniu.service.PermService;
import com.woniu.service.UserService;
import com.woniu.util.ResponseResult;
import jwt.JwtTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("perm")
@CrossOrigin
public class PermsController {

    @Autowired
    private PermService permService;

    @RequestMapping("list")
    public ResponseResult<List<Perm>> list(){
        QueryWrapper<Perm> queryWrapper = new QueryWrapper<>();
        List<Perm> lists = permService.list();
        return new ResponseResult(lists,200);

    }

    //查询权限 如果是显示在菜单 则查询一级和二级即可，type为1
    //如果给用户分配权限，则查询所有的权限 包括三级，在树型控件中显示
    @RequestMapping("{type}")
    public ResponseResult<List<Perm>> getMenuByUserName(@PathVariable String  type){
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        String username = JwtTokenUtil.getUserId(token);

        List<Perm> oldList = new ArrayList<>();

        List<Perm> resultList = new ArrayList<>();
        if(type.equals("menu")){ //根据用户获取菜单
            //菜单集合权限查询
            oldList = permService.getMenuByUserName(username);
        }else if(type =="person"){
            //获取某个人的所有权限
            oldList = permService.getPermsByUserName(username);
        }else{
            //树型菜单要展示的 所有权限列表
            oldList = permService.list();
        }
        //顶层元素的结合
        for(Perm entity:oldList){
            if(entity.getParentId() == null){
                resultList.add(entity);
            }
        }
        //获取每个顶层元素的子数据的集合
        for(Perm perm : resultList){
            List<Perm> childPerms = getSubList(perm.getId(),oldList);
            perm.setChildren(childPerms);
        }
        return new ResponseResult<>(resultList,200);
    }

    public List<Perm> getSubList(Integer id,List<Perm> menusList){

        //直接子元素
        List<Perm> childList = new ArrayList<>();
        for(Perm perm :menusList ){
            if(perm.getParentId() == id){
                childList.add(perm);
            }
        }
        //子元素的子对象
        for(Perm entity:childList){
            entity.setChildren( getSubList(entity.getId(),menusList)  );
        }
        //递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    //给某个用户授权
    @RequestMapping("assign")  //提交的树节点的 key  和  id 对应
    public ResponseResult<Void> userPermRightsChange(String rids,Integer userId){
         //删除原先的权限
        String[] ids = rids.split(",");
        permService.deletePermsByUserId(userId);
        for(int i = 0;i<ids.length;i++){
            Map<String,Object> maps = new HashMap<String,Object>();
            maps.put("pid",Integer.parseInt(ids[i]));
            maps.put("uid", userId);
            permService.addUserPerms(maps);
        }
        return ResponseResult.SUCCESS;
    }


}
