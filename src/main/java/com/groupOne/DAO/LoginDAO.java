package com.groupOne.DAO;

import com.groupOne.model.LoginEntity;
import com.groupOne.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository("loginDAO")
public class LoginDAO implements ILoginDAO {

    public synchronized List<LoginEntity> loadAllLogin() {
        String hql = "from LoginEntity ";
        Session session = MyListener.sessionFactory.getCurrentSession();
        List<LoginEntity> result = session.createQuery(hql).list();
        return result;
    }

    public synchronized boolean deleteLogin(int loginId) throws Exception {
        Session session = MyListener.sessionFactory.getCurrentSession();
        LoginEntity login = session.get(LoginEntity.class, loginId);
        if (login == null) {
            throw new Exception("记录不存在");
        }
        session.delete(login);
        return true;
    }

    public synchronized List<LoginEntity> searchLoginByComputerId(int computerId) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        List<LoginEntity> result;
        String hql = "from LoginEntity where computerId  = :computerId";
        Query query = session.createQuery(hql);
        query.setParameter("computerId", computerId);
        result = query.list();
        return result;
    }

    public synchronized List<LoginEntity> searchLoginByUserId(int userId) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        List<LoginEntity> result;
        String hql = "from LoginEntity where userId  = :userId";
        Query query = session.createQuery(hql);
        query.setParameter("userId", userId);
        result = query.list();
        return result;
    }

    public synchronized boolean insertLogin(LoginEntity loginEntity) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        session.save(loginEntity);
        return true;
    }


}
