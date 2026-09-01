package com.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.entity.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper extends BaseMapper<Emp> {
    List<Emp> selectAllCustom();

    Emp selectByIdCustom(Integer empId);

    int insertCustom(Emp emp);

    int updateCustom(Emp emp);

    int deleteCustomById(Integer empId);

    List<Emp> selectByCondition(Emp condition);

    int updateDynamic(Emp emp);

    int insertBatch(@Param("list") List<Emp> employees);

    int deleteBatch(@Param("ids") List<Integer> ids);

    List<Emp> selectByPriority(@Param("empName") String empName,
                               @Param("dept") String dept);
}
