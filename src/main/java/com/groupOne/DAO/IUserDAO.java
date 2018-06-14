package com.groupOne.DAO;

import com.groupOne.model.UserEntity;

import java.util.List;

public interface IUserDAO {
    public UserEntity getUserByUserId(String userId);
    public List<UserEntity> loadAllUser();
    public boolean deleteUser(String username,int userId) throws Exception;
    public boolean modifyUser(UserEntity userEntity) throws Exception;
    public boolean insertUser(String username,UserEntity userEntity);
    public List<UserEntity> searchUser(UserEntity userEntity);
}
