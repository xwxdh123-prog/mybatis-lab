CREATE DATABASE IF NOT EXISTS ssm_emp
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE ssm_emp;

-- 自定义员工表：保留后续 MyBatis CRUD 所需核心字段，
-- 另外增加联系方式、创建时间、数据约束和查询索引。
DROP TABLE IF EXISTS emp;

CREATE TABLE emp (
    emp_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '员工编号',
    emp_name VARCHAR(50) NOT NULL COMMENT '员工姓名',
    gender CHAR(1) NOT NULL DEFAULT '未' COMMENT '性别：男/女/未',
    dept VARCHAR(50) NOT NULL COMMENT '所属部门',
    post VARCHAR(50) NOT NULL COMMENT '岗位名称',
    salary DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '月薪',
    hire_date DATE NOT NULL COMMENT '入职日期',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1在职 0离职',
    email VARCHAR(100) DEFAULT NULL COMMENT '工作邮箱',
    phone VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    CONSTRAINT uk_emp_email UNIQUE (email),
    CONSTRAINT chk_emp_salary CHECK (salary >= 0),
    CONSTRAINT chk_emp_status CHECK (status IN (0, 1)),
    INDEX idx_emp_dept_status (dept, status),
    INDEX idx_emp_hire_date (hire_date)
) ENGINE=InnoDB COMMENT='MyBatis 实验自定义员工信息表';

INSERT INTO emp
    (emp_name, gender, dept, post, salary, hire_date, status, email, phone)
VALUES
    ('周晨', '女', '技术部', 'Java开发工程师', 13500.00, '2025-03-10', 1,
     'zhouchen@lab.example', '13900001001'),
    ('林浩', '男', '产品部', '产品助理', 9500.00, '2024-11-18', 1,
     'linhao@lab.example', '13900001002'),
    ('何雨', '女', '财务部', '成本会计', 8800.00, '2023-09-05', 1,
     'heyu@lab.example', '13900001003'),
    ('孙磊', '男', '运维部', '系统运维工程师', 11000.00, '2022-04-22', 0,
     'sunlei@lab.example', '13900001004'),
    ('顾雪', '女', '测试部', '自动化测试工程师', 12000.00, '2025-01-06', 1,
     'guxue@lab.example', '13900001005');

SELECT
    emp_id, emp_name, gender, dept, post, salary,
    hire_date, status, email, phone, created_at
FROM emp
ORDER BY emp_id;
