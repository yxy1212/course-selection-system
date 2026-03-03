package com.course.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.course.common.Result;
import com.course.entity.Course;
import com.course.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping
    public Result<IPage<Course>> getCourseList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        return courseService.getCourseList(page, size, keyword);
    }
    
    @GetMapping("/{id}")
    public Result<Course> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }
    
    @PostMapping
    public Result<Course> createCourse(
            @RequestBody Course course,
            HttpServletRequest request) {
        Long teacherId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        if (teacherId == null) {
            return Result.error("请先登录");
        }
        if (!"TEACHER".equals(role) && !"ADMIN".equals(role)) {
            return Result.error("无权限创建课程，只有教师或管理员可以创建");
        }
        return courseService.createCourse(course, teacherId);
    }
    
    @PutMapping("/{id}")
    public Result<Course> updateCourse(
            @PathVariable Long id,
            @RequestBody Course course,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        course.setId(id);
        return courseService.updateCourse(course, userId);
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteCourse(
            @PathVariable Long id,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return courseService.deleteCourse(id, userId, role);
    }
}
