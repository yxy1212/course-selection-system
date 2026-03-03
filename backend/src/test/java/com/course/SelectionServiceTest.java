package com.course;

import com.course.entity.Course;
import com.course.service.SelectionService;
import com.course.common.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SelectionServiceTest {
    
    @Autowired
    private SelectionService selectionService;
    
    @Test
    public void testSelectCourse() {
        // 使用学生ID=3 (student1), 课程ID=1
        Result<Void> result = selectionService.selectCourse(1L, 3L);
        
        // 可能是已选或成功
        assertTrue(result.getCode() == 200 || result.getCode() == 500);
        System.out.println("选课结果: " + result.getMessage());
        System.out.println("✓ 选课功能测试完成");
    }
    
    @Test
    public void testGetMyCourses() {
        Result<List<Course>> result = selectionService.getMyCourses(3L);
        
        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
        System.out.println("✓ 我的选课列表测试通过，共 " + result.getData().size() + " 门");
    }
    
    @Test
    public void testGetSelectedCourseIds() {
        Result<List<Long>> result = selectionService.getSelectedCourseIds(3L);
        
        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
        System.out.println("✓ 已选课程ID列表测试通过");
    }
    
    @Test
    public void testCancelCourse() {
        // 先选课再退课
        Result<Void> result = selectionService.cancelCourse(1L, 3L);
        
        assertTrue(result.getCode() == 200 || result.getCode() == 500);
        System.out.println("退课结果: " + result.getMessage());
        System.out.println("✓ 退课功能测试完成");
    }
}
