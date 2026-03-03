-- 选课管理系统数据库初始化

CREATE DATABASE IF NOT EXISTS course_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE course_system;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(50),
    role ENUM('STUDENT', 'TEACHER', 'ADMIN') DEFAULT 'STUDENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 课程表
CREATE TABLE IF NOT EXISTS course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    teacher_id BIGINT NOT NULL,
    teacher_name VARCHAR(50),
    credits INT DEFAULT 0,
    capacity INT DEFAULT 50,
    selected_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (teacher_id) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 选课记录表
CREATE TABLE IF NOT EXISTS course_selection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    selected_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES user(id),
    FOREIGN KEY (course_id) REFERENCES course(id),
    UNIQUE KEY uk_student_course (student_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 插入测试数据
-- 管理员
INSERT INTO user (username, password, real_name, role) VALUES ('admin', '123456', '系统管理员', 'ADMIN');
-- 教师
INSERT INTO user (username, password, real_name, role) VALUES ('teacher1', '123456', '张老师', 'TEACHER');
INSERT INTO user (username, password, real_name, role) VALUES ('teacher2', '123456', '李老师', 'TEACHER');
-- 学生
INSERT INTO user (username, password, real_name, role) VALUES ('student1', '123456', '王小明', 'STUDENT');
INSERT INTO user (username, password, real_name, role) VALUES ('student2', '123456', '李小红', 'STUDENT');

-- 课程
INSERT INTO course (name, description, teacher_id, teacher_name, credits, capacity, selected_count) 
VALUES ('Java程序设计', 'Java编程基础课程', 2, '张老师', 3, 50, 0);
INSERT INTO course (name, description, teacher_id, teacher_name, credits, capacity, selected_count) 
VALUES ('数据结构', '数据结构与算法基础', 2, '张老师', 4, 40, 0);
INSERT INTO course (name, description, teacher_id, teacher_name, credits, capacity, selected_count) 
VALUES ('数据库原理', 'MySQL数据库课程', 3, '李老师', 3, 45, 0);
INSERT INTO course (name, description, teacher_id, teacher_name, credits, capacity, selected_count) 
VALUES ('Web前端开发', 'HTML/CSS/JavaScript', 3, '李老师', 2, 50, 0);
