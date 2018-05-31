package com.groupone.model;

import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
        return user;
    }
}
