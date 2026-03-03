package com.course;

import com.course.entity.Course;
import com.course.service.CourseService;
import com.course.common.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CourseServiceTest {
    
    @Autowired
    private CourseService courseService;
    
    @Test
    public void testGetCourseList() {
        Result<IPage<Course>> result = courseService.getCourseList(1, 10, null);
        
        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
        assertTrue(result.getData().getRecords().size() > 0);
        System.out.println("✓ 课程列表测试通过，共 " + result.getData().getRecords().size() + " 门课程");
    }
    
    @Test
    public void testGetCourseById() {
        Result<Course> result = courseService.getCourseById(1L);
        
        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
        assertEquals("Java程序设计", result.getData().getName());
        System.out.println("✓ 课程详情测试通过: " + result.getData().getName());
    }
    
    @Test
    public void testGetCourseNotFound() {
        Result<Course> result = courseService.getCourseById(9999L);
        
        assertEquals(500, result.getCode());
        System.out.println("✓ 课程不存在测试通过");
    }
    
    @Test
    public void testSearchCourse() {
        Result<IPage<Course>> result = courseService.getCourseList(1, 10, "Java");
        
        assertEquals(200, result.getCode());
        assertTrue(result.getData().getRecords().size() > 0);
        System.out.println("✓ 课程搜索测试通过");
    }
}
