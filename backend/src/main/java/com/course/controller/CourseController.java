package com.course.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.course.common.Result;
import com.course.entity.Course;
import com.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            @RequestHeader("X-User-Id") Long teacherId,
            @RequestHeader("X-User-Role") String role) {
        if (!"TEACHER".equals(role) && !"ADMIN".equals(role)) {
            return Result.error("无权限创建课程");
        }
        return courseService.createCourse(course, teacherId);
    }
    
    @PutMapping("/{id}")
    public Result<Course> updateCourse(
            @PathVariable Long id,
            @RequestBody Course course,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role) {
        course.setId(id);
        return courseService.updateCourse(course, userId);
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteCourse(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role) {
        return courseService.deleteCourse(id, userId, role);
    }
}
