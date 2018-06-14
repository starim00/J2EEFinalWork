package com.groupOne.DAO;

import com.groupOne.model.UserEntity;
import com.groupOne.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository("userDAO")
public class UserDAO implements IUserDAO {

    public synchronized UserEntity getUserByUserId(String userId) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        UserEntity user = session.get(UserEntity.class, userId);
        return user;
    }

    public synchronized List<UserEntity> loadAllUser() {
        Session session = MyListener.sessionFactory.getCurrentSession();
        String hql = "from UserEntity ";
        List<UserEntity> result = session.createQuery(hql).list();
        return result;
    }

    public synchronized boolean deleteUser(String username,int userId) throws Exception {
        Session session = MyListener.sessionFactory.getCurrentSession();
        UserEntity user = session.get(UserEntity.class, userId);
        if (user == null) {
            throw new Exception("实验室不存在");
        }
        session.delete(user);
        return true;
    }

    public synchronized boolean modifyUser(UserEntity userEntity) throws Exception {
        Session session = MyListener.sessionFactory.getCurrentSession();
        UserEntity user = session.get(UserEntity.class, userEntity.getUserId());
        if (user == null) {
            throw new Exception("用户不存在");
        }
        session.evict(user);
        session.update(userEntity);
        return true;
    }

    public synchronized boolean insertUser(String username,UserEntity userEntity) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        session.save(userEntity);
        return true;
    }

    public synchronized List<UserEntity> searchUser(UserEntity userEntity) {
        String hql = "";
        Session session = MyListener.sessionFactory.getCurrentSession();
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
        return result;
    }
}
