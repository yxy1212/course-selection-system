package com.course;

import com.course.dto.LoginRequest;
import com.course.dto.LoginResponse;
import com.course.common.Result;
import com.course.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthServiceTest {
    
    @Autowired
    private AuthService authService;
    
    @Test
    public void testLoginSuccess() {
        LoginRequest request = new LoginRequest();
        request.setUsername("student1");
        request.setPassword("123456");
        
        Result<LoginResponse> result = authService.login(request);
        
        assertEquals(200, result.getCode());
        assertNotNull(result.getData());
        assertNotNull(result.getData().getToken());
        assertEquals("student1", result.getData().getUsername());
        assertEquals("STUDENT", result.getData().getRole());
        System.out.println("✓ 登录测试通过");
    }
    
    @Test
    public void testLoginWrongPassword() {
        LoginRequest request = new LoginRequest();
        request.setUsername("student1");
        request.setPassword("wrong");
        
        Result<LoginResponse> result = authService.login(request);
        
        assertEquals(500, result.getCode());
        assertNull(result.getData());
        System.out.println("✓ 错误密码测试通过");
    }
    
    @Test
    public void testLoginUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setUsername("notexist");
        request.setPassword("123456");
        
        Result<LoginResponse> result = authService.login(request);
        
        assertEquals(500, result.getCode());
        System.out.println("✓ 用户不存在测试通过");
    }
    
    @Test
    public void testTeacherLogin() {
        LoginRequest request = new LoginRequest();
        request.setUsername("teacher1");
        request.setPassword("123456");
        
        Result<LoginResponse> result = authService.login(request);
        
        assertEquals(200, result.getCode());
        assertEquals("TEACHER", result.getData().getRole());
        System.out.println("✓ 教师登录测试通过");
    }
    
    @Test
    public void testAdminLogin() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");
        
        Result<LoginResponse> result = authService.login(request);
        
        assertEquals(200, result.getCode());
        assertEquals("ADMIN", result.getData().getRole());
        System.out.println("✓ 管理员登录测试通过");
    }
}
