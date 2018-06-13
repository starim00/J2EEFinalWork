package com.groupone.DAO;

import com.groupone.model.ComputerEntity;
import java.util.List;

public interface IComputerDAO {
    public void getSession();
    public List<ComputerEntity> loadAllComputer();
    public boolean deleteComputer(String username,int computerId) throws Exception;
    public List<Object[]> searchComputer(int location,String labName);
    public boolean modifyComputer(String username,ComputerEntity computerEntity) throws Exception;
    public boolean insertComputer(String username,ComputerEntity computerEntity);
}
