package com.lab.demo;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public final class MyBatisDemo {
    private MyBatisDemo() {
    }

    public static void main(String[] args) {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            List<Emp> employees = mapper.selectAll();
            System.out.println("员工总数：" + employees.size());
            employees.forEach(System.out::println);
        }
    }
}
