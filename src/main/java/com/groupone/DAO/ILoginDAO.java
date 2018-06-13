package com.groupone.DAO;

import com.groupone.model.LoginEntity;

import java.util.List;

public interface ILoginDAO {
    public void getSession();
    public List<LoginEntity> loadAllLogin();
    public boolean deleteLogin(int loginId) throws Exception ;
    public List<LoginEntity> searchLoginByComputerId(int computerId);
    public List<LoginEntity> searchLoginByUserId(int userId);
    public boolean insertLogin(LoginEntity loginEntity);
}
