package com.course.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.course.entity.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseRepository extends BaseMapper<Course> {
}
