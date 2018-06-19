package com.groupOne.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "lab", schema = "j2ee")
public class LabEntity {
    private int labId;
    private String labName;
    private String labLeader;
    private int location;
    private int safeLevel;
    private Collection<ComputerEntity> computersByLabId;
    private UserEntity userByLabLeader;

    @Id
    @Column(name = "labID", nullable = false)
    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    @Basic
    @Column(name = "labName", nullable = false, length = 255)
    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    @Basic
    @Column(name = "labLeader", nullable = true, length = 255)
    public String getLabLeader() {
        return labLeader;
    }

    public void setLabLeader(String labLeader) {
        this.labLeader = labLeader;
    }

    @Basic
    @Column(name = "location", nullable = false)
    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    @Basic
    @Column(name = "safeLevel", nullable = false)
    public int getSafeLevel() {
        return safeLevel;
    }

    public void setSafeLevel(int safeLevel) {
        this.safeLevel = safeLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabEntity labEntity = (LabEntity) o;

        if (labId != labEntity.labId) return false;
        if (location != labEntity.location) return false;
        if (safeLevel != labEntity.safeLevel) return false;
        if (labName != null ? !labName.equals(labEntity.labName) : labEntity.labName != null) return false;
        if (labLeader != null ? !labLeader.equals(labEntity.labLeader) : labEntity.labLeader != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = labId;
        result = 31 * result + (labName != null ? labName.hashCode() : 0);
        result = 31 * result + (labLeader != null ? labLeader.hashCode() : 0);
        result = 31 * result + location;
        result = 31 * result + safeLevel;
        return result;
    }

    @OneToMany(mappedBy = "labByLabId")
    public Collection<ComputerEntity> getComputersByLabId() {
        return computersByLabId;
    }

    public void setComputersByLabId(Collection<ComputerEntity> computersByLabId) {
        this.computersByLabId = computersByLabId;
    }

    @ManyToOne
    @JoinColumn(name = "labLeader", referencedColumnName = "userID")
    public UserEntity getUserByLabLeader() {
        return userByLabLeader;
    }

    public void setUserByLabLeader(UserEntity userByLabLeader) {
        this.userByLabLeader = userByLabLeader;
    }
}
