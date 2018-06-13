package com.groupone.DAO;

import com.groupone.model.LabEntity;
import com.groupone.model.UserEntity;
import com.groupone.servlet.MyListener;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public interface IUserDAO {
    public void getSession();
    public UserEntity getUserByUserId(String userId);
    public List<UserEntity> loadAllUser();
    public boolean deleteUser(String username,int userId) throws Exception;
    public boolean modifyUser(String username,UserEntity userEntity) throws Exception;
    public boolean insertUser(String username,UserEntity userEntity);
    public List<UserEntity> searchUser(UserEntity userEntity);
}
