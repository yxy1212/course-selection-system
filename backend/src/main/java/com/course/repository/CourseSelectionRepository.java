package com.course.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.course.entity.CourseSelection;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseSelectionRepository extends BaseMapper<CourseSelection> {
}
