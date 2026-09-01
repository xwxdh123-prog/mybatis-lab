package com.lab;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assume;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DynamicSqlIntegrationTest {
    @Test
    public void selectByOptionalConditionsAndPriority() {
        assumeDatabasePasswordIsAvailable();

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            Emp condition = new Emp();
            condition.setDept("技术部");
            condition.setStatus(1);
            List<Emp> activeTechnicalEmployees = mapper.selectByCondition(condition);
            assertEquals(1, activeTechnicalEmployees.size());
            assertEquals("周晨", activeTechnicalEmployees.get(0).getEmpName());

            List<Emp> nameFirst = mapper.selectByPriority("顾", "技术部");
            assertEquals(1, nameFirst.size());
            assertEquals("顾雪", nameFirst.get(0).getEmpName());

            List<Emp> departmentFallback = mapper.selectByPriority("", "技术部");
            assertEquals(1, departmentFallback.size());
            assertEquals("周晨", departmentFallback.get(0).getEmpName());

            assertEquals(5, mapper.selectByPriority(null, null).size());
        }
    }

    @Test
    public void updateOnlyProvidedFields() {
        assumeDatabasePasswordIsAvailable();

        try (SqlSession session = MyBatisUtil.openSession()) {
            try {
                EmpMapper mapper = session.getMapper(EmpMapper.class);
                Emp patch = new Emp();
                patch.setEmpId(1);
                patch.setPost("高级Java开发工程师");
                patch.setSalary(new BigDecimal("14800.00"));

                assertEquals(1, mapper.updateDynamic(patch));
                Emp updated = mapper.selectByIdCustom(1);
                assertEquals("高级Java开发工程师", updated.getPost());
                assertEquals(0, new BigDecimal("14800.00").compareTo(updated.getSalary()));
                assertEquals("周晨", updated.getEmpName());
            } finally {
                session.rollback();
            }
        }
    }

    @Test
    public void insertAndDeleteEmployeesInBatch() {
        assumeDatabasePasswordIsAvailable();

        try (SqlSession session = MyBatisUtil.openSession()) {
            try {
                EmpMapper mapper = session.getMapper(EmpMapper.class);
                List<Emp> employees = Arrays.asList(
                        createEmployee("陆遥", "luyao.task3@lab.example", "13900003001"),
                        createEmployee("江晚", "jiangwan.task3@lab.example", "13900003002")
                );

                assertEquals(2, mapper.insertBatch(employees));

                Emp condition = new Emp();
                condition.setDept("创新实验室");
                List<Emp> inserted = mapper.selectByCondition(condition);
                assertEquals(2, inserted.size());

                List<Integer> ids = new ArrayList<>();
                for (Emp employee : inserted) {
                    ids.add(employee.getEmpId());
                }
                assertEquals(2, mapper.deleteBatch(ids));
                assertEquals(0, mapper.selectByCondition(condition).size());
            } finally {
                session.rollback();
            }
        }
    }

    private static Emp createEmployee(String name, String email, String phone) {
        Emp employee = new Emp();
        employee.setEmpName(name);
        employee.setGender("未");
        employee.setDept("创新实验室");
        employee.setPost("研发助理");
        employee.setSalary(new BigDecimal("9800.00"));
        employee.setHireDate(LocalDate.of(2026, 9, 1));
        employee.setStatus(1);
        employee.setEmail(email);
        employee.setPhone(phone);
        return employee;
    }

    private static void assumeDatabasePasswordIsAvailable() {
        String password = System.getenv("MYBATIS_DB_PASSWORD");
        Assume.assumeTrue("Set MYBATIS_DB_PASSWORD to run the database test",
                password != null && !password.trim().isEmpty());
    }
}
