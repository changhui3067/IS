package Employee.service;

import Employee.DAO.Employee;
import Employee.DAO.EmployeeMaper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * Created by freyjachang on 3/15/16.
 */
@Service
public class EmployeeService {
    @Autowired(required = false)
    private EmployeeMaper empMapper;
    Employee employee;
    public String getEmployeeInfo(){
        return "emp service";
    }
}
