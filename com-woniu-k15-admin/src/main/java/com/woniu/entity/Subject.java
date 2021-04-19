package com.woniu.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("k15_subject")
public class Subject {
    private Integer id;
    private String name;
    private Integer sort;
    private Integer courseNum;
}
