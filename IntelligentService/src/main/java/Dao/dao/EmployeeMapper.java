package Dao.dao;

import Dao.model.Employee;
import Dao.model.EmployeeKey;

public interface EmployeeMapper {
    int deleteByPrimaryKey(EmployeeKey key);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(EmployeeKey key);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}