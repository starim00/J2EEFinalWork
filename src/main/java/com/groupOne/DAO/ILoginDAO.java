package com.groupOne.DAO;

import com.groupOne.model.LoginEntity;

import java.util.List;

public interface ILoginDAO {
    public List<LoginEntity> loadAllLogin();
    public boolean deleteLogin(int loginId) throws Exception ;
    public List<LoginEntity> searchLoginByComputerId(int computerId);
    public List<LoginEntity> searchLoginByUserId(String userId);
    public boolean insertLogin(LoginEntity loginEntity);
}
