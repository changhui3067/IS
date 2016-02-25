package com.company.Employee.Service;

import com.company.Employee.DAO.Employee;
import com.company.Employee.EmployeeEvent.AddEmpEvent;
import com.company.EventCenter.Bus;

/**
 * Created by root on 2/23/16.
 */
public class EmployeeService {
    public void addEmployee(Employee emp){
        //after add the data into database
        Bus.getDefault().post(new AddEmpEvent(emp));
    }

    public Employee getEmployee(int empId){
        Employee emp1 = new Employee(1, "emp1", 2, 1000.00);
        Employee emp2 = new Employee(2, "emp2", 0, 2000.00);

        if(empId == 1) {
            return emp1;
        } else{
            return emp2;
        }
    }
}
