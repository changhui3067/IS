package Employee.service;

import Employee.DAO.Employee;
import Employee.DAO.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by freyjachang on 3/15/16.
 */
@Service
public class EmployeeService {
    @Autowired(required = false)
    private EmployeeMapper empMapper;
    Employee employee;
    public String getEmployeeInfo(){
        return "emp service";
    }
}
