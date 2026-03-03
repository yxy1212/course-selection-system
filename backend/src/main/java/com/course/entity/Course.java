package com.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("course")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    private String description;
    private Long teacherId;
    private String teacherName;
    private Integer credits;
    private Integer capacity;
    private Integer selectedCount;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
