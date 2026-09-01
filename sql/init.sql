CREATE DATABASE IF NOT EXISTS ssm_emp
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE ssm_emp;

CREATE TABLE IF NOT EXISTS emp (
    emp_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '员工编号',
    emp_name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender CHAR(1) DEFAULT '男' COMMENT '性别',
    dept VARCHAR(50) COMMENT '部门',
    post VARCHAR(50) COMMENT '岗位',
    salary DECIMAL(10, 2) COMMENT '薪资',
    hire_date DATE COMMENT '入职时间',
    status TINYINT DEFAULT 1 COMMENT '状态：1在职 0离职'
);

INSERT INTO emp (emp_name, gender, dept, post, salary, hire_date, status)
SELECT '张伟', '男', '研发部', 'Java工程师', 12000.00, '2024-07-01', 1
WHERE NOT EXISTS (SELECT 1 FROM emp WHERE emp_name = '张伟' AND hire_date = '2024-07-01');

INSERT INTO emp (emp_name, gender, dept, post, salary, hire_date, status)
SELECT '李娜', '女', '研发部', '前端工程师', 10000.00, '2024-08-15', 1
WHERE NOT EXISTS (SELECT 1 FROM emp WHERE emp_name = '李娜' AND hire_date = '2024-08-15');

INSERT INTO emp (emp_name, gender, dept, post, salary, hire_date, status)
SELECT '王强', '男', '市场部', '市场专员', 8000.00, '2023-03-10', 1
WHERE NOT EXISTS (SELECT 1 FROM emp WHERE emp_name = '王强' AND hire_date = '2023-03-10');

INSERT INTO emp (emp_name, gender, dept, post, salary, hire_date, status)
SELECT '赵敏', '女', '人事部', '人事专员', 7500.00, '2022-06-20', 0
WHERE NOT EXISTS (SELECT 1 FROM emp WHERE emp_name = '赵敏' AND hire_date = '2022-06-20');

SELECT emp_id, emp_name, gender, dept, post, salary, hire_date, status
FROM emp
ORDER BY emp_id;
