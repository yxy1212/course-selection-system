package com.course;

import com.course.entity.User;
import com.course.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testFindByUsername() {
        User user = userRepository.selectById(1L);
        assertNotNull(user);
        System.out.println("用户查询成功: " + user.getUsername());
    }
    
    @Test
    public void testUserExists() {
        User student = userRepository.selectById(3L);
        assertNotNull(student);
        assertEquals("STUDENT", student.getRole());
        
        User teacher = userRepository.selectById(2L);
        assertNotNull(teacher);
        assertEquals("TEACHER", teacher.getRole());
    }
}
