package logon.service;

import Employee.DAO.EmployeeMapper;
import Employee.DAO.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* Created by Administrator on 2015/3/17.
*/
@Service
public class LoginService {
    //@Autowired(required = false)
    //private EmployeeMapper userMapper;
    @Autowired(required =  false)
    Employee employee;

    @Autowired(required = false)
    public int getLoginInfo(String company, String userName,String password){
        // Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        try {
            //employee = userMapper.selectByEmployeeName(company, userName);
            employee = new Employee();//just a test, will use mapper to get the real data
            if(employee.getPassword().equals(password)){
                System.out.println("LoginService.getLoginInfo equal");
                return employee.getUserId();
            }
            else {
                System.out.println("LoginService.getLoginInfo not equal");
                return -1;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return -2;
        }
    }


}
