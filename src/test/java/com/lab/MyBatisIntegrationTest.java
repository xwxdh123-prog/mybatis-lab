package com.lab;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assume;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MyBatisIntegrationTest {
    @Test
    public void queryCustomEmployeeTable() {
        assumeDatabasePasswordIsAvailable();

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            List<Emp> employees = mapper.selectAllCustom();

            assertEquals(5, employees.size());
            assertEquals("周晨", employees.get(0).getEmpName());
            assertEquals("技术部", employees.get(0).getDept());

            Emp employee = mapper.selectByIdCustom(5);
            assertNotNull(employee);
            assertEquals("顾雪", employee.getEmpName());
            assertEquals("guxue@lab.example", employee.getEmail());

            employees.forEach(System.out::println);
        }
    }

    @Test
    public void insertUpdateAndDeleteEmployeeWithGeneratedKey() {
        assumeDatabasePasswordIsAvailable();

        try (SqlSession session = MyBatisUtil.openSession()) {
            try {
                EmpMapper mapper = session.getMapper(EmpMapper.class);
                Emp employee = createTaskTwoEmployee();

                assertEquals(1, mapper.insertCustom(employee));
                assertNotNull(employee.getEmpId());
                assertTrue(employee.getEmpId() > 0);

                Emp inserted = mapper.selectByIdCustom(employee.getEmpId());
                assertNotNull(inserted);
                assertEquals("唐宁", inserted.getEmpName());
                assertEquals("数据部", inserted.getDept());
                assertNotNull(inserted.getCreatedAt());

                inserted.setPost("数据开发工程师");
                inserted.setSalary(new BigDecimal("15200.00"));
                inserted.setPhone("13900002002");
                assertEquals(1, mapper.updateCustom(inserted));

                Emp updated = mapper.selectByIdCustom(employee.getEmpId());
                assertEquals("数据开发工程师", updated.getPost());
                assertEquals(0, new BigDecimal("15200.00").compareTo(updated.getSalary()));
                assertEquals("13900002002", updated.getPhone());

                assertEquals(1, mapper.deleteCustomById(employee.getEmpId()));
                assertNull(mapper.selectByIdCustom(employee.getEmpId()));
            } finally {
                session.rollback();
            }
        }
    }

    private static Emp createTaskTwoEmployee() {
        Emp employee = new Emp();
        employee.setEmpName("唐宁");
        employee.setGender("女");
        employee.setDept("数据部");
        employee.setPost("数据分析师");
        employee.setSalary(new BigDecimal("13800.00"));
        employee.setHireDate(LocalDate.of(2026, 9, 1));
        employee.setStatus(1);
        employee.setEmail("tangning.task2@lab.example");
        employee.setPhone("13900002001");
        return employee;
    }

    private static void assumeDatabasePasswordIsAvailable() {
        String password = System.getenv("MYBATIS_DB_PASSWORD");
        Assume.assumeTrue("Set MYBATIS_DB_PASSWORD to run the database test",
                password != null && !password.trim().isEmpty());
    }
}
