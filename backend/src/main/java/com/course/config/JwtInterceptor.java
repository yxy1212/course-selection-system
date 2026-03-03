package com.course.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            
            try {
                SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                
                String userId = claims.getSubject();
                String role = (String) claims.get("role");
                String username = (String) claims.get("username");
                
                request.setAttribute("userId", Long.parseLong(userId));
                request.setAttribute("role", role);
                request.setAttribute("username", username);
                
                response.setHeader("X-User-Id", userId);
                response.setHeader("X-User-Role", role);
                
            } catch (Exception e) {
                // Token 无效，继续但不设置用户信息
            }
        }
        
        return true;
    }
}
