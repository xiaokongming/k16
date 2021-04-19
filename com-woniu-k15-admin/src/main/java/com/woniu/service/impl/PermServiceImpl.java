package com.woniu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.PermDao;
import com.woniu.dao.SubjectDao;
import com.woniu.dao.UserDao;
import com.woniu.entity.Perm;
import com.woniu.entity.Subject;
import com.woniu.service.PermService;
import com.woniu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermServiceImpl extends ServiceImpl<PermDao,Perm> implements PermService {

    @Autowired
    private PermDao permDao;
    @Override
    public List<Perm> getMenuByUserName(String userName) {
        return permDao.getMenuByUserName(userName);
    }

    @Override
    public List<Perm> getPermsByUserName(String userName) {
        return permDao.getPermsByUserName(userName);
    }

    @Override
    public void deletePermsByUserId(Integer userId) {
        permDao.deletePermsByUserId(userId);
    }

    @Override
    public void addUserPerms(Map map) {
        permDao.addUserPerms(map);
    }
}
