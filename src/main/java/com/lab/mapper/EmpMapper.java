package com.lab.mapper;

import com.lab.entity.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {
    List<Emp> selectAll();

    Emp selectById(Integer empId);

    int insert(Emp emp);

    int update(Emp emp);

    int deleteById(Integer empId);

    List<Emp> selectByCondition(Emp condition);

    int updateDynamic(Emp emp);

    int insertBatch(@Param("list") List<Emp> employees);

    int deleteBatch(@Param("ids") List<Integer> ids);

    List<Emp> selectByPriority(@Param("empName") String empName,
                               @Param("dept") String dept);
}
