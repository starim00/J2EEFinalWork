package com.groupone.model;

import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class UserDAO {
    private static SessionFactory sessionFactory = MyListener.sessionFactory;
    private Session session;


    public void getSession(){
        if(session == null){
            this.session=sessionFactory.openSession();
        }
    }
    public synchronized UserEntity getUserByUserId(String userId){
        getSession();
        UserEntity user = session.get(UserEntity.class,userId);
        session.close();
        return user;
    }
    public synchronized List<UserEntity> loadAllUser(){
        String hql = "from UserEntity ";
        getSession();
        List<UserEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }
    public synchronized boolean deleteUser(int userId) throws Exception {
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            UserEntity user = session.get(UserEntity.class,userId);
            if(user == null){
                throw new Exception("实验室不存在");
            }
            session.delete(user);
            transaction.commit();
        }catch (Exception e){
            if(transaction!=null){
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }
        return true;
    }
    public synchronized boolean modifyUser(UserEntity userEntity){
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            LabEntity lab = session.get(LabEntity.class,userEntity.getUserId());
            if(lab==null){
                throw new Exception("用户不存在");
            }
            session.update(userEntity);
            transaction.commit();
        }
        catch (Exception e){
            if(transaction !=null){
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }
        return true;
    }
    public synchronized boolean insertUser(UserEntity userEntity){
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            session.save(userEntity);
            transaction.commit();
        }
        catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }
        return true;
    }
    public synchronized List<UserEntity> searchUser(UserEntity userEntity){
        String hql = "";
        getSession();
        Query query;
        if(userEntity.getUserType()!=-1){
            hql = "from UserEntity where userId like :userId and userName like :userName and userType = :userType";
            query = session.createQuery(hql);
            query.setParameter("userId","%"+userEntity.getUserId()+"%");
            query.setParameter("userName","%"+userEntity.getUserName()+"%");
            query.setParameter("userType",userEntity.getUserType());
        }
        else{
            hql="from UserEntity where userId like :userId and userName like :userName";
            query = session.createQuery(hql);
            query.setParameter("userId","%"+userEntity.getUserId()+"%");
            query.setParameter("userName","%"+userEntity.getUserName()+"%");

        }
        List<UserEntity> result = query.list();
        session.close();
        return result;
    }
}
