package com.groupOne.model;

import javax.persistence.*;

@Entity
@Table(name = "login", schema = "j2ee")
public class LoginEntity {
    private int loginId;
    private String userId;
    private long inTime;
    private long outTime;
    private int computerId;

    @Id
    @Column(name = "loginID", nullable = false)
    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    @Basic
    @Column(name = "userID", nullable = false, length = 255)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "inTime", nullable = false)
    public long getInTime() {
        return inTime;
    }

    public void setInTime(long inTime) {
        this.inTime = inTime;
    }

    @Basic
    @Column(name = "outTime", nullable = false)
    public long getOutTime() {
        return outTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    @Basic
    @Column(name = "computerID",nullable = false)
    public int getComputerId(){return computerId;}

    public void setComputerId(int computerId){this.computerId = computerId;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginEntity that = (LoginEntity) o;

        if (loginId != that.loginId) return false;
        if (inTime != that.inTime) return false;
        if (outTime != that.outTime) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = loginId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (int) (inTime ^ (inTime >>> 32));
        result = 31 * result + (int) (outTime ^ (outTime >>> 32));
        return result;
    }

}