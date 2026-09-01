package com.lab;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assume;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyBatisIntegrationTest {
    @Test
    public void queryCustomEmployeeTable() throws Exception {
        String password = System.getenv("MYBATIS_DB_PASSWORD");
        Assume.assumeTrue("Set MYBATIS_DB_PASSWORD to run the database test",
                password != null && !password.isEmpty());

        Properties runtimeProperties = new Properties();
        runtimeProperties.setProperty("jdbc.password", password);

        try (InputStream input = Resources.getResourceAsStream("mybatis-config.xml")) {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder()
                    .build(input, null, runtimeProperties);

            try (SqlSession session = factory.openSession()) {
                EmpMapper mapper = session.getMapper(EmpMapper.class);
                List<Emp> employees = mapper.findAll();

                assertEquals(5, employees.size());
                assertEquals("周晨", employees.get(0).getEmpName());
                assertEquals("技术部", employees.get(0).getDept());

                Emp employee = mapper.findById(5);
                assertNotNull(employee);
                assertEquals("顾雪", employee.getEmpName());
                assertEquals("guxue@lab.example", employee.getEmail());

                employees.forEach(System.out::println);
            }
        }
    }
}
