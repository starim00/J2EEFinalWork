package com.groupone.DAO;

import com.groupone.model.LabEntity;
import com.groupone.model.UserEntity;
import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userDAO")
public class UserDAO implements IUserDAO {
    private Session session;


    public void getSession() {
        if (session == null) {
            this.session = MyListener.sessionFactory.openSession();
        }
    }

    public synchronized UserEntity getUserByUserId(String userId) {
        getSession();
        UserEntity user = session.get(UserEntity.class, userId);
        session.close();
        return user;
    }

    public synchronized List<UserEntity> loadAllUser() {
        String hql = "from UserEntity ";
        getSession();
        List<UserEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean deleteUser(String username,int userId) throws Exception {
        getSession();
        UserEntity user = session.get(UserEntity.class, userId);
        if (user == null) {
            throw new Exception("实验室不存在");
        }
        session.delete(user);
        session.close();
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean modifyUser(String username,UserEntity userEntity) throws Exception {
        getSession();
        LabEntity lab = session.get(LabEntity.class, userEntity.getUserId());
        if (lab == null) {
            throw new Exception("用户不存在");
        }
        session.update(userEntity);
        session.close();
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean insertUser(String username,UserEntity userEntity) {
        getSession();
        session.save(userEntity);
        session.close();
        return true;
    }

    public synchronized List<UserEntity> searchUser(UserEntity userEntity) {
        String hql = "";
        getSession();
        Query query;
        if (userEntity.getUserType() != -1) {
            hql = "from UserEntity where userId like :userId and userName like :userName and userType = :userType";
            query = session.createQuery(hql);
            query.setParameter("userId", "%" + userEntity.getUserId() + "%");
            query.setParameter("userName", "%" + userEntity.getUserName() + "%");
            query.setParameter("userType", userEntity.getUserType());
        } else {
            hql = "from UserEntity where userId like :userId and userName like :userName";
            query = session.createQuery(hql);
            query.setParameter("userId", "%" + userEntity.getUserId() + "%");
            query.setParameter("userName", "%" + userEntity.getUserName() + "%");

        }
        List<UserEntity> result = query.list();
        session.close();
        return result;
    }
}
