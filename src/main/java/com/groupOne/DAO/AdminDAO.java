package com.groupOne.DAO;

import com.groupOne.model.AdminEntity;
import com.groupOne.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository("adminDAO")
public class AdminDAO implements IAdminDAO {

    public synchronized AdminEntity getAdminById(String username,String adminId) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        AdminEntity user = session.get(AdminEntity.class, adminId);
        return user;
    }

    public synchronized List<AdminEntity> loadAllAdmin(String username) {
        String hql = "from AdminEntity ";
        Session session = MyListener.sessionFactory.getCurrentSession();
        List<AdminEntity> result = session.createQuery(hql).list();
        return result;
    }

    public synchronized boolean deleteAdmin(String username,int adminId) throws Exception {
        Session session = MyListener.sessionFactory.getCurrentSession();
        AdminEntity admin = session.get(AdminEntity.class, adminId);
        if (admin == null) {
            throw new Exception("实验室不存在");
        }
        session.delete(admin);
        return true;
    }

    public synchronized boolean modifyAdmin(String username,AdminEntity adminEntity) throws Exception {
        Session session = MyListener.sessionFactory.getCurrentSession();
        AdminEntity lab = session.get(AdminEntity.class, adminEntity.getAdminId());
        if (lab == null) {
            throw new Exception("用户不存在");
        }
        session.evict(lab);
        session.update(adminEntity);
        return true;
    }

    public synchronized boolean insertAdmin(String username,AdminEntity adminEntity) throws Exception {
        Session session = MyListener.sessionFactory.getCurrentSession();
        Transaction transaction = null;
        transaction = session.getTransaction();
        session.save(adminEntity);
        transaction.commit();
        return true;
    }
}
