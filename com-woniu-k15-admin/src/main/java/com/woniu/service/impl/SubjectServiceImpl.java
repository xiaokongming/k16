package com.woniu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.dao.SubjectDao;
import com.woniu.dao.TeacherDao;
import com.woniu.entity.Subject;
import com.woniu.entity.Teacher;
import com.woniu.service.SubjectService;
import com.woniu.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubjectServiceImpl extends ServiceImpl<SubjectDao,Subject> implements SubjectService {

}
