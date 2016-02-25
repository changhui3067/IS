package com.company.Employee.controller;

import com.company.Employee.DAO.Employee;
import com.company.Employee.Service.EmployeeService;

/**
 * Created by root on 2/23/16.
 */
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(){
        employeeService = new EmployeeService();
    }

    public void addEmployee(int operatorId, Employee addInfo){
        //Employee operator = queryEmployee(operatorId);
        //if(operator.hasPermission()){
            employeeService.addEmployee(addInfo);
        //}
    }

    public void deleteEmployee(int operatorId, Employee addInfo){

    }

    public void editEmployee(int operatorId, Employee addInfo){

    }

    public Employee queryEmployee(int empId){
        return employeeService.getEmployee(empId);
    }
}
