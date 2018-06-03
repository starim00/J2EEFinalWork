package com.groupone.model;

import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LabDAO {
    private static SessionFactory sessionFactory = MyListener.sessionFactory;
    private Session session;
    public void getSession(){
        if(session==null){
            session=sessionFactory.openSession();
        }
    }
    public synchronized List<LabEntity> loadAllLab(){
        String hql = "from LabEntity ";
        getSession();
        List<LabEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }
    public synchronized boolean deleteLab(int labId) throws Exception {
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            LabEntity lab = session.get(LabEntity.class,labId);
            if(lab == null){
                throw new Exception("实验室不存在");
            }
            session.delete(lab);
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
    public synchronized List<LabEntity> searchLabByLabName(String labName){
        String hql = "from LabEntity where labName like :labName";
        getSession();
        Query query = session.createQuery(hql);
        query.setParameter("labName","%"+labName+"%");
        List<LabEntity> result = query.list();
        session.close();
        return result;
    }
    public synchronized boolean modifyLab(LabEntity labEntity){
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            LabEntity lab = session.get(LabEntity.class,labEntity.getLabId());
            if(lab==null){
                throw new Exception("实验室不存在");
            }
            session.update(labEntity);
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
    public synchronized boolean insertLab(LabEntity labEntity){
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            session.save(labEntity);
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
