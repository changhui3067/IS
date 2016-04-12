package Dao.dao.model;

import java.util.Date;

public class Position {
    private Integer positionId;

    private String positionTitle;

    private String positionDesc;

    private String positionBenefit;

    private String positionRequire;

    private Integer positionHeadcount;

    private Integer positionContact;

    private Date positionExpireTime;

    private byte[] positionIsopen;

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle == null ? null : positionTitle.trim();
    }

    public String getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc == null ? null : positionDesc.trim();
    }

    public String getPositionBenefit() {
        return positionBenefit;
    }

    public void setPositionBenefit(String positionBenefit) {
        this.positionBenefit = positionBenefit == null ? null : positionBenefit.trim();
    }

    public String getPositionRequire() {
        return positionRequire;
    }

    public void setPositionRequire(String positionRequire) {
        this.positionRequire = positionRequire == null ? null : positionRequire.trim();
    }

    public Integer getPositionHeadcount() {
        return positionHeadcount;
    }

    public void setPositionHeadcount(Integer positionHeadcount) {
        this.positionHeadcount = positionHeadcount;
    }

    public Integer getPositionContact() {
        return positionContact;
    }

    public void setPositionContact(Integer positionContact) {
        this.positionContact = positionContact;
    }

    public Date getPositionExpireTime() {
        return positionExpireTime;
    }

    public void setPositionExpireTime(Date positionExpireTime) {
        this.positionExpireTime = positionExpireTime;
    }

    public byte[] getPositionIsopen() {
        return positionIsopen;
    }

    public void setPositionIsopen(byte[] positionIsopen) {
        this.positionIsopen = positionIsopen;
    }
}