package com.groupone.model;

import javax.persistence.*;

@Entity
@Table(name = "computer", schema = "j2ee", catalog = "")
public class ComputerEntity {
    private int computerId;
    private String ipAddress;
    private int location;
    private int labId;

    @Id
    @Column(name = "computerID", nullable = false)
    public int getComputerId() {
        return computerId;
    }

    public void setComputerId(int computerId) {
        this.computerId = computerId;
    }

    @Basic
    @Column(name = "IPAddress", nullable = true, length = 255)
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
    @Column(name = "labID", nullable = false)
    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComputerEntity that = (ComputerEntity) o;

        if (computerId != that.computerId) return false;
        if (location != that.location) return false;
        if (labId != that.labId) return false;
        if (ipAddress != null ? !ipAddress.equals(that.ipAddress) : that.ipAddress != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = computerId;
        result = 31 * result + (ipAddress != null ? ipAddress.hashCode() : 0);
        result = 31 * result + location;
        result = 31 * result + labId;
        return result;
    }
}
