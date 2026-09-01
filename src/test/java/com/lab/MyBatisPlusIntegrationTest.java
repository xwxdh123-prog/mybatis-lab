package com.lab;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MyBatisPlusIntegrationTest {
    @Test
    public void baseMapperCompletesCrudWithoutCustomSql() {
        assumeDatabasePasswordIsAvailable();

        try (SqlSession session = MyBatisUtil.openSession()) {
            try {
                EmpMapper mapper = session.getMapper(EmpMapper.class);
                Emp employee = createEmployee();

                assertEquals(1, mapper.insert(employee));
                assertNotNull(employee.getEmpId());

                Emp inserted = mapper.selectById(employee.getEmpId());
                assertEquals("许澄", inserted.getEmpName());

                inserted.setPost("资深交互设计师");
                inserted.setSalary(new BigDecimal("14600.00"));
                assertEquals(1, mapper.updateById(inserted));

                Emp updated = mapper.selectById(employee.getEmpId());
                assertEquals("资深交互设计师", updated.getPost());
                assertEquals(0, new BigDecimal("14600.00").compareTo(updated.getSalary()));

                assertEquals(1, mapper.deleteById(employee.getEmpId()));
                assertNull(mapper.selectById(employee.getEmpId()));
            } finally {
                session.rollback();
            }
        }
    }

    @Test
    public void wrappersFilterAndSortEmployees() {
        assumeDatabasePasswordIsAvailable();

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            QueryWrapper<Emp> wrapper = new QueryWrapper<>();
            wrapper.eq("status", 1)
                    .ge("salary", new BigDecimal("10000.00"))
                    .orderByDesc("salary");
            List<Emp> results = mapper.selectList(wrapper);
            assertEquals(2, results.size());
            assertEquals("周晨", results.get(0).getEmpName());

            LambdaQueryWrapper<Emp> lambdaWrapper = new LambdaQueryWrapper<>();
            lambdaWrapper.like(Emp::getEmpName, "雪")
                    .eq(Emp::getStatus, 1)
                    .orderByDesc(Emp::getSalary);
            List<Emp> lambdaResults = mapper.selectList(lambdaWrapper);
            assertEquals(1, lambdaResults.size());
            assertEquals("顾雪", lambdaResults.get(0).getEmpName());
        }
    }

    @Test
    public void paginationReturnsThreeRecordsOnFirstPage() {
        assumeDatabasePasswordIsAvailable();

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            LambdaQueryWrapper<Emp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Emp::getStatus, 1).orderByDesc(Emp::getSalary);

            Page<Emp> page = new Page<>(1, 3);
            Page<Emp> result = mapper.selectPage(page, wrapper);

            assertEquals(4, result.getTotal());
            assertEquals(2, result.getPages());
            assertEquals(3, result.getRecords().size());
            assertTrue(result.hasNext());
            assertEquals("周晨", result.getRecords().get(0).getEmpName());
        }
    }

    private static Emp createEmployee() {
        Emp employee = new Emp();
        employee.setEmpName("许澄");
        employee.setGender("女");
        employee.setDept("设计部");
        employee.setPost("交互设计师");
        employee.setSalary(new BigDecimal("13200.00"));
        employee.setHireDate(LocalDate.of(2026, 8, 18));
        employee.setStatus(1);
        employee.setEmail("xucheng.task4@lab.example");
        employee.setPhone("13900004001");
        return employee;
    }

    private static void assumeDatabasePasswordIsAvailable() {
        String password = System.getenv("MYBATIS_DB_PASSWORD");
        Assume.assumeTrue("Set MYBATIS_DB_PASSWORD to run the database test",
                password != null && !password.trim().isEmpty());
    }
}
