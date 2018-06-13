package com.groupone.DAO;

import com.groupone.model.AdminEntity;
import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("adminDAO")
public class AdminDAO implements IAdminDAO {
    private Session session;


    public void getSession() {
        if (session == null) {
            this.session = MyListener.sessionFactory.openSession();
        }
    }

    public synchronized AdminEntity getAdminById(String username,String adminId) {
        getSession();
        AdminEntity user = session.get(AdminEntity.class, adminId);
        session.close();
        return user;
    }

    public synchronized List<AdminEntity> loadAllAdmin(String username) {
        String hql = "from AdminEntity ";
        getSession();
        List<AdminEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean deleteAdmin(String username,int adminId) throws Exception {
        getSession();
        AdminEntity admin = session.get(AdminEntity.class, adminId);
        if (admin == null) {
            throw new Exception("实验室不存在");
        }
        session.delete(admin);
        session.close();
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean modifyAdmin(String username,AdminEntity adminEntity) throws Exception {
        getSession();
        AdminEntity lab = session.get(AdminEntity.class, adminEntity.getAdminId());
        if (lab == null) {
            throw new Exception("用户不存在");
        }
        session.update(adminEntity);
        session.close();
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean insertAdmin(String username,AdminEntity adminEntity) throws Exception {
        getSession();
        Transaction transaction = null;
        transaction = session.getTransaction();
        session.save(adminEntity);
        transaction.commit();
        session.close();
        return true;
    }
}
