package com.groupone.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDAO {
    private static SessionFactory sessionFactory = (SessionFactory)new ClassPathXmlApplicationContext("application.xml").getBean("sessionFactory");
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
