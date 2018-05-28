package com.groupone.model;

import javax.persistence.*;

@Entity
@Table(name = "login", schema = "j2ee")
public class LoginEntity {
    private int loginId;
    private long inTime;
    private long outTime;

    @Id
    @Column(name = "loginID")
    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    @Basic
    @Column(name = "inTime")
    public long getInTime() {
        return inTime;
    }

    public void setInTime(long inTime) {
        this.inTime = inTime;
    }

    @Basic
    @Column(name = "outTime")
    public long getOutTime() {
        return outTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginEntity that = (LoginEntity) o;

        if (loginId != that.loginId) return false;
        if (inTime != that.inTime) return false;
        if (outTime != that.outTime) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = loginId;
        result = 31 * result + (int) (inTime ^ (inTime >>> 32));
        result = 31 * result + (int) (outTime ^ (outTime >>> 32));
        return result;
    }
}
