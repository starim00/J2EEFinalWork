package com.groupOne.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "j2ee")
public class UserEntity {
    private String userId;
    private String userName;
    private String passwd;
    private Integer tel;
    private Integer userType;
    private Collection<LabEntity> labsByUserId;
    private Collection<LoginEntity> loginsByUserId;

    @Id
    @Column(name = "userID", nullable = false, length = 255)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "userName", nullable = false, length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "passwd", nullable = false, length = 255)
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Basic
    @Column(name = "tel", nullable = true)
    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "userType", nullable = true)
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (passwd != null ? !passwd.equals(that.passwd) : that.passwd != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (userType != null ? !userType.equals(that.userType) : that.userType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByLabLeader")
    public Collection<LabEntity> getLabsByUserId() {
        return labsByUserId;
    }

    public void setLabsByUserId(Collection<LabEntity> labsByUserId) {
        this.labsByUserId = labsByUserId;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<LoginEntity> getLoginsByUserId() {
        return loginsByUserId;
    }

    public void setLoginsByUserId(Collection<LoginEntity> loginsByUserId) {
        this.loginsByUserId = loginsByUserId;
    }
}
