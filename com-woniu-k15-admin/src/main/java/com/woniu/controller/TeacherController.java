package com.woniu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.entity.Teacher;
import com.woniu.service.TeacherService;
import com.woniu.util.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("list")
    @RequiresPermissions("teacher:list")
    public ResponseResult<Page<Teacher>> teacherList(@RequestHeader("Authorization") String token,
                   String query, Integer pagenum, Integer pagesize, Integer subjectId){
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(query)){
            queryWrapper.like("name",query);
        }
        if (subjectId != null && subjectId != 0) {
            queryWrapper.eq("subjectId", subjectId);
        }
        Page teacherPage = new Page( pagenum, pagesize);
        return new ResponseResult(teacherService.page(teacherPage, queryWrapper),200);
    }


    @RequestMapping("add")
    @RequiresPermissions("teacher:add")
    public ResponseResult<Void> addTeacher(@RequestBody Teacher teacher){

        teacherService.save(teacher);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("delete")
    @RequiresPermissions("teacher:delete")
    public ResponseResult<Void> deleteTeacherById(Integer id){
        teacherService.removeById(id);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("update")
    @RequiresPermissions("teacher:update")
    public ResponseResult<Void> updateTeacher(@RequestBody Teacher teacher){

        teacherService.updateById(teacher);
        return ResponseResult.SUCCESS;
    }
}
