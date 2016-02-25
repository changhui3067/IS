package com.company.Employee.DAO;

import com.company.Employee.EmployeeEvent.AddEmpEvent;
import com.company.EventCenter.Bus;
import com.company.EventCenter.BusReceiver;

/**
 * Created by root on 2/23/16.
 */
public class Employee {
    public final int empId;
    public final String empName;
    public final int managerId;
    public final double empSalary;

    public Employee(int id, String name, int managerId, double empSalary){
        this.empId      = id;
        this.empName    = name;
        this.managerId  = managerId;
        this.empSalary  = empSalary;
        Bus.getDefault().register(this);
        System.out.println("new emp: "+ getEmpInfo());
    }

    public boolean hasPermission(){
        return true;
    }

    @BusReceiver
    public void onNewEmpAdd(AddEmpEvent event){
        System.out.println( getEmpInfo() + " get event: "+ event);
    }

    public String getEmpInfo(){
        return "id:" + empId +", name:" + empName;
    }

    public int getEmpId(){
        return empId;
    }
}
