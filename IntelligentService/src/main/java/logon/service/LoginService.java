package logon.service;

import Dao.dao.EmployeeMapper;
import Dao.model.Employee;
import Dao.model.EmployeeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* Created by Administrator on 2015/3/17.
*/
@Service
public class LoginService {
    @Autowired(required = false)
    private EmployeeKey employeeKey;
    @Autowired(required = false)
    private EmployeeMapper employeeMapper;
    @Autowired(required =  false)
    Employee employee;

    @Autowired(required = false)
    public int getLoginInfo(String company, String userName,String password){
        // Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        try {
            employeeKey = new EmployeeKey();
            employeeKey.setCompanyname(company);
            employeeKey.setEmpName(userName);

            employee = employeeMapper.selectByPrimaryKey(employeeKey);
            //employee = new Employee();//just a test, will use mapper to get the real data
            if(employee.getEmpPwd().equals(password)){
                System.out.println("LoginService.getLoginInfo equal");
                return employee.getSysEmpId();
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
