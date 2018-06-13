package com.groupone.DAO;

import com.groupone.model.LabEntity;

import java.util.List;

public interface ILabDAO {
    public void getSession();
    public List<LabEntity> loadAllLab();
    public boolean deleteLab(String username,int labId) throws Exception;
    public List<LabEntity> searchLab(LabEntity labEntity);
    public boolean modifyLab(String username,LabEntity labEntity) throws Exception;
    public boolean insertLab(String username,LabEntity labEntity);
}
