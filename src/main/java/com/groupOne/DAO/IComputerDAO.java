package com.groupOne.DAO;

import com.groupOne.model.ComputerEntity;

import java.util.List;

public interface IComputerDAO {
    public List<ComputerEntity> loadAllComputer();
    public boolean deleteComputer(String username,int computerId) throws Exception;
    public List<ComputerEntity> searchComputer(int location,int labId);
    public boolean modifyComputer(String username,ComputerEntity computerEntity) throws Exception;
    public boolean insertComputer(String username,ComputerEntity computerEntity);
    public ComputerEntity getComputerById(int computerId);
}
