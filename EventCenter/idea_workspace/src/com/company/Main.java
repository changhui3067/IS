package com.company;

import com.company.Employee.DAO.Employee;
import com.company.Employee.EmployeeEvent.AddEmpEvent;
import com.company.Employee.controller.EmployeeController;
import com.company.EventCenter.Bus;
import com.company.EventCenter.BusReceiver;

public class Main {

    public static void main(String[] args) {
        //new Main().run();
        Employee emp1 = new Employee(1, "emp1", 2, 1000.00);
        Employee emp2 = new Employee(2, "emp2", 0, 2000.00);
        Employee emp3 = new Employee(3, "emp3", 2, 2000.00);

        EmployeeController ec = new EmployeeController();
        ec.addEmployee(emp2.getEmpId(), emp1);

        Employee emp4 = new Employee(4, "emp4", 2, 2000.00);
    }

//    public void run() {
//        Bus.getDefault().register(this);
//        Bus.getDefault().post(new StringBuilder("a stringbuilder"));
//        Bus.getDefault().unregister(this);
//    }
//
//    @BusReceiver
//    public void onStringEvent(String event){
//        System.out.println("onStringEvent event= " + event);
//    }
//
//    @BusReceiver
//    public void onObjectEvent(Object event)
//    {
//        System.out.println("onObjectEvent event = " + event);
//    }
//
//    @BusReceiver
//    public void onCharSequenceEvent(CharSequence event)
//    {
//        System.out.println("onCharSequenceEvent event = " + event);
//    }
//
//    @BusReceiver
//    public void onExceptionEvent(Exception event){
//        System.out.println("onExceptionEvent event = " + event);
//    }
//
//    @BusReceiver
//    public void onNewEmpAdd(AddEmpEvent event){
//        System.out.println("on main get event: "+ event);
//    }
}
