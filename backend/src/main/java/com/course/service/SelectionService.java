package com.course.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.course.common.Result;
import com.course.entity.Course;
import com.course.entity.CourseSelection;
import com.course.repository.CourseRepository;
import com.course.repository.CourseSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SelectionService {
    
    @Autowired
    private CourseSelectionRepository selectionRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Transactional
    public Result<Void> selectCourse(Long courseId, Long studentId) {
        // 检查课程是否存在
        Course course = courseRepository.selectById(courseId);
        if (course == null) {
            return Result.error("课程不存在");
        }
        
        // 检查是否已选
        LambdaQueryWrapper<CourseSelection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseSelection::getStudentId, studentId)
               .eq(CourseSelection::getCourseId, courseId);
        CourseSelection exist = selectionRepository.selectOne(wrapper);
        
        if (exist != null) {
            return Result.error("已选择该课程");
        }
        
        // 检查容量
        if (course.getSelectedCount() >= course.getCapacity()) {
            return Result.error("课程已满");
        }
        
        // 创建选课记录
        CourseSelection selection = new CourseSelection();
        selection.setStudentId(studentId);
        selection.setCourseId(courseId);
        selection.setSelectedAt(LocalDateTime.now());
        selectionRepository.insert(selection);
        
        // 更新已选人数
        course.setSelectedCount(course.getSelectedCount() + 1);
        courseRepository.updateById(course);
        
        return Result.success(null);
    }
    
    @Transactional
    public Result<Void> cancelCourse(Long courseId, Long studentId) {
        LambdaQueryWrapper<CourseSelection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseSelection::getStudentId, studentId)
               .eq(CourseSelection::getCourseId, courseId);
        CourseSelection selection = selectionRepository.selectOne(wrapper);
        
        if (selection == null) {
            return Result.error("未选择该课程");
        }
        
        selectionRepository.deleteById(selection.getId());
        
        // 更新已选人数
        Course course = courseRepository.selectById(courseId);
        if (course != null && course.getSelectedCount() > 0) {
            course.setSelectedCount(course.getSelectedCount() - 1);
            courseRepository.updateById(course);
        }
        
        return Result.success(null);
    }
    
    public Result<List<Course>> getMyCourses(Long studentId) {
        LambdaQueryWrapper<CourseSelection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseSelection::getStudentId, studentId);
        List<CourseSelection> selections = selectionRepository.selectList(wrapper);
        
        List<Course> courses = selections.stream()
                .map(s -> courseRepository.selectById(s.getCourseId()))
                .toList();
        
        return Result.success(courses);
    }
    
    public Result<List<Long>> getSelectedCourseIds(Long studentId) {
        LambdaQueryWrapper<CourseSelection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseSelection::getStudentId, studentId);
        List<CourseSelection> selections = selectionRepository.selectList(wrapper);
        
        List<Long> courseIds = selections.stream()
                .map(CourseSelection::getCourseId)
                .toList();
        
        return Result.success(courseIds);
    }
}
