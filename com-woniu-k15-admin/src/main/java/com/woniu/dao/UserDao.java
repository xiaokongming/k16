package com.woniu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.entity.Perm;
import com.woniu.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao extends BaseMapper<User> {

    @Select("select * from rbac_perms\n" +
            "where  id in (select permid from rbac_user_perm where userid  = \n" +
            "  (select id from rbac_user where username = #{username}))")
    public List<Perm> getPermissions(String username);
}
