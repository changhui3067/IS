package Employee.DAO;

/**
 * Created by freyjachang on 3/15/16.
 */
public class Employee {
    private int _userId;
    private String _companyId;
    private String _employeeName;
    private String _password;

    public Employee(){
        this._userId = 1;
        this._companyId = "aaa";
        this._employeeName = "bbb";
        this._password = "ccc";
        System.out.println("Employee create: "+ this._employeeName);
    }
    public String getPassword(){
        return this._password;
    }

    public int getUserId(){
        return this._userId;
    }
}
