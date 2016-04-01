package Employee.DAO;

/**
 * Created by freyjachang on 3/15/16.
 */
public interface EmployeeMapper {

    public Employee selectByEmployeeName(String companyId, String username);
}
