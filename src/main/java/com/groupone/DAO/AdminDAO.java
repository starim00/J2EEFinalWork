package com.groupone.DAO;

import com.groupone.model.AdminEntity;
import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AdminDAO {
    private Session session;


    public void getSession(){
        if(session == null){
            this.session=MyListener.sessionFactory.openSession();
        }
    }
    public synchronized AdminEntity getAdminById(String adminId){
        getSession();
        AdminEntity user = session.get(AdminEntity.class,adminId);
        session.close();
        return user;
    }
    public synchronized List<AdminEntity> loadAllAdmin(){
        String hql = "from AdminEntity ";
        getSession();
        List<AdminEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }
    public synchronized boolean deleteAdmin(int adminId) throws Exception {
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            AdminEntity admin = session.get(AdminEntity.class,adminId);
            if(admin == null){
                throw new Exception("实验室不存在");
            }
            session.delete(admin);
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
    public synchronized boolean modifyAdmin(AdminEntity adminEntity){
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            AdminEntity lab = session.get(AdminEntity.class,adminEntity.getAdminId());
            if(lab==null){
                throw new Exception("用户不存在");
            }
            session.update(adminEntity);
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
    public synchronized boolean insertAdmin(AdminEntity adminEntity){
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            session.save(adminEntity);
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
}
