package com.woniu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("k15_teacher")
public class Teacher {
    private Integer id;
    private String name;
    private String education;
    private String career;
    private String isFamous;
    private String avatar;
    private int subjectId;
    private String status;
}
