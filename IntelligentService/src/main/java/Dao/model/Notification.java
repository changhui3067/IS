package Dao.model;

import java.util.Date;

public class Notification {
    private Integer notiId;

    private String notiSub;

    private String notiDesc;

    private String notiType;

    private Date notyExpireTime;

    public Integer getNotiId() {
        return notiId;
    }

    public void setNotiId(Integer notiId) {
        this.notiId = notiId;
    }

    public String getNotiSub() {
        return notiSub;
    }

    public void setNotiSub(String notiSub) {
        this.notiSub = notiSub == null ? null : notiSub.trim();
    }

    public String getNotiDesc() {
        return notiDesc;
    }

    public void setNotiDesc(String notiDesc) {
        this.notiDesc = notiDesc == null ? null : notiDesc.trim();
    }

    public String getNotiType() {
        return notiType;
    }

    public void setNotiType(String notiType) {
        this.notiType = notiType == null ? null : notiType.trim();
    }

    public Date getNotyExpireTime() {
        return notyExpireTime;
    }

    public void setNotyExpireTime(Date notyExpireTime) {
        this.notyExpireTime = notyExpireTime;
    }
}