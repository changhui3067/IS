package Dao.dao.model;

public class Employee {
    private Integer sysEmpId;

    private String empName;

    private String empPwd;

    private String empPosition;

    private Integer empLevel;

    private Integer empManagerId;

    private String photo;

    private String payment;

    public Integer getSysEmpId() {
        return sysEmpId;
    }

    public void setSysEmpId(Integer sysEmpId) {
        this.sysEmpId = sysEmpId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getEmpPwd() {
        return empPwd;
    }

    public void setEmpPwd(String empPwd) {
        this.empPwd = empPwd == null ? null : empPwd.trim();
    }

    public String getEmpPosition() {
        return empPosition;
    }

    public void setEmpPosition(String empPosition) {
        this.empPosition = empPosition == null ? null : empPosition.trim();
    }

    public Integer getEmpLevel() {
        return empLevel;
    }

    public void setEmpLevel(Integer empLevel) {
        this.empLevel = empLevel;
    }

    public Integer getEmpManagerId() {
        return empManagerId;
    }

    public void setEmpManagerId(Integer empManagerId) {
        this.empManagerId = empManagerId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }
}