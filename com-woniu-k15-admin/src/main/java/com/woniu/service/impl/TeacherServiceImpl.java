package com.woniu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.dao.TeacherDao;
import com.woniu.dao.UserDao;
import com.woniu.entity.Teacher;
import com.woniu.entity.User;
import com.woniu.service.TeacherService;
import com.woniu.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherDao,Teacher> implements TeacherService {

}
