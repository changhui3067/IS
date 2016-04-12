package Dao.dao.model;

import java.util.Date;

public class CourseRecord {
    private Integer resordId;

    private Integer courseId;

    private Integer empId;

    private Date expireTime;

    private byte[] isFinished;

    public Integer getResordId() {
        return resordId;
    }

    public void setResordId(Integer resordId) {
        this.resordId = resordId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public byte[] getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(byte[] isFinished) {
        this.isFinished = isFinished;
    }
}