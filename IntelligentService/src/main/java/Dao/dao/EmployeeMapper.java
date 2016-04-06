package Dao.dao;

import Dao.model.Employee;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer sysEmpId);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer sysEmpId);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}