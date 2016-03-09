package com.company.EventCenter.Events;

import com.company.Employee.DAO.Employee;

/**
 * Created by root on 2/23/16.
 */
public class AddEmpEvent {
    public final Employee emp;

    public AddEmpEvent(final Employee emp){
        this.emp = emp;
    }
}
