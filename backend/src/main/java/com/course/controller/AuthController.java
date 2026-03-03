package com.course.controller;

import com.course.common.Result;
import com.course.dto.LoginRequest;
import com.course.dto.LoginResponse;
import com.course.entity.User;
import com.course.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    
    @PostMapping("/register")
    public Result<User> register(@RequestBody LoginRequest request) {
        return authService.register(request);
    }
    
    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("请先登录");
        }
        return Result.success(authService.getUserById(userId));
    }
}
