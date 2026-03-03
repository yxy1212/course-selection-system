package com.course.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.common.Result;
import com.course.entity.Course;
import com.course.entity.User;
import com.course.repository.CourseRepository;
import com.course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Result<IPage<Course>> getCourseList(int page, int size, String keyword) {
        Page<Course> pageParam = new Page<>(page, size);
        
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Course::getName, keyword);
        }
        wrapper.orderByDesc(Course::getCreatedAt);
        
        IPage<Course> result = courseRepository.selectPage(pageParam, wrapper);
        return Result.success(result);
    }
    
    public Result<Course> getCourseById(Long id) {
        Course course = courseRepository.selectById(id);
        if (course == null) {
            return Result.error("课程不存在");
        }
        
        // 获取教师姓名
        User teacher = userRepository.selectById(course.getTeacherId());
        if (teacher != null) {
            course.setTeacherName(teacher.getRealName());
        }
        
        return Result.success(course);
    }
    
    public Result<Course> createCourse(Course course, Long teacherId) {
        course.setTeacherId(teacherId);
        course.setSelectedCount(0);
        
        // 获取教师姓名
        User teacher = userRepository.selectById(teacherId);
        if (teacher != null) {
            course.setTeacherName(teacher.getRealName());
        }
        
        courseRepository.insert(course);
        return Result.success(course);
    }
    
    public Result<Course> updateCourse(Course course, Long teacherId) {
        Course existCourse = courseRepository.selectById(course.getId());
        if (existCourse == null) {
            return Result.error("课程不存在");
        }
        
        if (!existCourse.getTeacherId().equals(teacherId)) {
            return Result.error("无权限修改该课程");
        }
        
        courseRepository.updateById(course);
        return Result.success(course);
    }
    
    public Result<Void> deleteCourse(Long courseId, Long userId, String role) {
        Course course = courseRepository.selectById(courseId);
        if (course == null) {
            return Result.error("课程不存在");
        }
        
        if (!course.getTeacherId().equals(userId) && !"ADMIN".equals(role)) {
            return Result.error("无权限删除该课程");
        }
        
        courseRepository.deleteById(courseId);
        return Result.success(null);
    }
}
