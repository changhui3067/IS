package com.company.Employee.DAO;

import com.company.EventCenter.Events.AddEmpEvent;
import com.company.EventCenter.Bus.Bus;
import com.company.EventCenter.Bus.BusReceiver;

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
        _registerBus();
        System.out.println("new emp: "+ getEmpInfo());
        //_unregister();
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

    private void _registerBus(){
        Bus.getDefault().register(this);
    }

    private void _unregister(){
        Bus.getDefault().unregister(this);
    }
}
