package com.woniu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.entity.Perm;
import com.woniu.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PermDao extends BaseMapper<Perm> {

    @Select("SELECT r.* FROM  rbac_perms  r  \n" +
            "inner join rbac_user_perm pu on r.id = pu.permid\n" +
            "inner join rbac_user u on pu.userid = u.id\n" +
            "where u.username =  #{username} and r.type = 'm'")
    public List<Perm> getMenuByUserName(String username);

    @Select("SELECT r.* FROM  rbac_perms  r  \n" +
            "inner join rbac_user_perm pu on r.id = pu.permid\n" +
            "inner join rbac_user u on pu.userid = u.id\n" +
            "where u.username =  #{username}")
    public List<Perm> getPermsByUserName(String username);

    //删除某个用户的所有权限
    @Delete("delete from rbac_user_perm where userId = #{userId}")
    public void deletePermsByUserId(Integer userId);

    @Insert("insert into rbac_user_perm(userid,permid) values(#{uid},#{pid})")
    public void addUserPerms(Map map);
}
