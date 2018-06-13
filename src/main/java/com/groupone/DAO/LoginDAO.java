package com.groupone.DAO;

import com.groupone.model.LoginEntity;
import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("loginDAO")
public class LoginDAO implements ILoginDAO {
    private Session session;

    public void getSession() {
        if (session == null) {
            session = MyListener.sessionFactory.openSession();
        }
    }

    public synchronized List<LoginEntity> loadAllLogin() {
        String hql = "from LoginEntity ";
        getSession();
        List<LoginEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean deleteLogin(int loginId) throws Exception {
        getSession();
        LoginEntity login = session.get(LoginEntity.class, loginId);
        if (login == null) {
            throw new Exception("记录不存在");
        }
        session.delete(login);
        session.close();
        return true;
    }

    public synchronized List<LoginEntity> searchLoginByComputerId(int computerId) {
        getSession();
        List<LoginEntity> result;
        String hql = "from LoginEntity where computerId  = :computerId";
        Query query = session.createQuery(hql);
        query.setParameter("computerId", computerId);
        result = query.list();
        return result;
    }

    public synchronized List<LoginEntity> searchLoginByUserId(int userId) {
        getSession();
        List<LoginEntity> result;
        String hql = "from LoginEntity where userId  = :userId";
        Query query = session.createQuery(hql);
        query.setParameter("userId", userId);
        result = query.list();
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean insertLogin(LoginEntity loginEntity) {
        getSession();
        Transaction transaction = null;
        transaction = session.getTransaction();
        session.save(loginEntity);
        transaction.commit();
        session.close();
        return true;
    }


}
