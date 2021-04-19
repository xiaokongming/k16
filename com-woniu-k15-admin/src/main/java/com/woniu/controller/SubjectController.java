package com.woniu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.entity.Subject;
import com.woniu.entity.Teacher;
import com.woniu.service.SubjectService;
import com.woniu.service.TeacherService;
import com.woniu.util.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService ;

    @RequestMapping("list")
    public ResponseResult<List<Subject>> subjectList(@RequestHeader("Authorization") String token){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        List<Subject> lists = subjectService.list();
        return new ResponseResult(lists,200);
    }
}
