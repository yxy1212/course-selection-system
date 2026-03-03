package com.course.controller;

import com.course.common.Result;
import com.course.entity.Course;
import com.course.service.SelectionService;
import jakarta.servlet.http.HttpServletRequest;
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
            HttpServletRequest request) {
        Long studentId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        if (studentId == null) {
            return Result.error("请先登录");
        }
        if (!"STUDENT".equals(role)) {
            return Result.error("只有学生可以选课");
        }
        return selectionService.selectCourse(courseId, studentId);
    }
    
    @DeleteMapping("/{courseId}")
    public Result<Void> cancelCourse(
            @PathVariable Long courseId,
            HttpServletRequest request) {
        Long studentId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        if (studentId == null) {
            return Result.error("请先登录");
        }
        if (!"STUDENT".equals(role)) {
            return Result.error("只有学生可以退课");
        }
        return selectionService.cancelCourse(courseId, studentId);
    }
    
    @GetMapping("/my")
    public Result<List<Course>> getMyCourses(HttpServletRequest request) {
        Long studentId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        if (studentId == null) {
            return Result.error("请先登录");
        }
        if (!"STUDENT".equals(role)) {
            return Result.error("无权限访问");
        }
        return selectionService.getMyCourses(studentId);
    }
    
    @GetMapping("/ids")
    public Result<List<Long>> getSelectedIds(HttpServletRequest request) {
        Long studentId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        if (studentId == null) {
            return Result.error("请先登录");
        }
        if (!"STUDENT".equals(role)) {
            return Result.error("无权限访问");
        }
        return selectionService.getSelectedCourseIds(studentId);
    }
}
