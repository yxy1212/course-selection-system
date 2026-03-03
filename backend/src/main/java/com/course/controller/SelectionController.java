package com.course.controller;

import com.course.common.Result;
import com.course.entity.Course;
import com.course.service.SelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/selections")
public class SelectionController {
    
    @Autowired
    private SelectionService selectionService;
    
    @PostMapping("/{courseId}")
    public Result<Void> selectCourse(
            @PathVariable Long courseId,
            @RequestHeader("X-User-Id") Long studentId,
            @RequestHeader("X-User-Role") String role) {
        if (!"STUDENT".equals(role)) {
            return Result.error("只有学生可以选课");
        }
        return selectionService.selectCourse(courseId, studentId);
    }
    
    @DeleteMapping("/{courseId}")
    public Result<Void> cancelCourse(
            @PathVariable Long courseId,
            @RequestHeader("X-User-Id") Long studentId,
            @RequestHeader("X-User-Role") String role) {
        if (!"STUDENT".equals(role)) {
            return Result.error("只有学生可以退课");
        }
        return selectionService.cancelCourse(courseId, studentId);
    }
    
    @GetMapping("/my")
    public Result<List<Course>> getMyCourses(
            @RequestHeader("X-User-Id") Long studentId,
            @RequestHeader("X-User-Role") String role) {
        if (!"STUDENT".equals(role)) {
            return Result.error("无权限访问");
        }
        return selectionService.getMyCourses(studentId);
    }
    
    @GetMapping("/ids")
    public Result<List<Long>> getSelectedIds(
            @RequestHeader("X-User-Id") Long studentId,
            @RequestHeader("X-User-Role") String role) {
        if (!"STUDENT".equals(role)) {
            return Result.error("无权限访问");
        }
        return selectionService.getSelectedCourseIds(studentId);
    }
}
