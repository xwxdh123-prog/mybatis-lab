package com.lab.mapper;

import com.lab.entity.Emp;

import java.util.List;

public interface EmpMapper {
    List<Emp> findAll();

    Emp findById(Integer empId);
}
