package com.course.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.course.common.Result;
import com.course.dto.LoginRequest;
import com.course.dto.LoginResponse;
import com.course.entity.User;
import com.course.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    
    public Result<LoginResponse> login(LoginRequest request) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername());
        User user = userRepository.selectOne(wrapper);
        
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return Result.error("用户名或密码错误");
        }
        
        String token = generateToken(user);
        
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setRealName(user.getRealName());
        
        return Result.success(response);
    }
    
    public Result<User> register(LoginRequest request) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername());
        User existUser = userRepository.selectOne(wrapper);
        
        if (existUser != null) {
            return Result.error("用户名已存在");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRealName(request.getUsername());
        user.setRole("STUDENT");
        
        userRepository.insert(user);
        return Result.success(user);
    }
    
    private String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public User getUserById(Long userId) {
        return userRepository.selectById(userId);
    }
}
