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
    public synchronized List<LabEntity> searchLab(LabEntity labEntity){
        String hql = "";
        getSession();
        Query query;
        if(labEntity.getSafeLevel()!=-1&&labEntity.getLocation()!=-1){
            hql = "from LabEntity where labName like :labName and labLeader like :labLeader and location = :location and safeLevel = :safeLevel";
            query = session.createQuery(hql);
            query.setParameter("labName","%"+labEntity.getLabName()+"%");
            query.setParameter("labLeader","%"+labEntity.getLabLeader()+"%");
            query.setParameter("location",labEntity.getLocation());
            query.setParameter("safeLevel",labEntity.getSafeLevel());
        }
        else if(labEntity.getLocation()!=-1){
            hql = "from LabEntity where labName like :labName and labLeader like :labLeader and location = :location";
            query = session.createQuery(hql);
            query.setParameter("labName","%"+labEntity.getLabName()+"%");
            query.setParameter("labLeader","%"+labEntity.getLabLeader()+"%");
            query.setParameter("location",labEntity.getLocation());
        }
        else if(labEntity.getSafeLevel()!=-1){
            hql = "from LabEntity where labName like :labName and labLeader like :labLeader and safeLevel = :safeLevel";
            query = session.createQuery(hql);
            query.setParameter("labName","%"+labEntity.getLabName()+"%");
            query.setParameter("labLeader","%"+labEntity.getLabLeader()+"%");
            query.setParameter("safeLevel",labEntity.getSafeLevel());
        }
        else{
            hql="from LabEntity where labName like :labName and labLeader like :labLeader ";
            query = session.createQuery(hql);
            query.setParameter("labName","%"+labEntity.getLabName()+"%");
            query.setParameter("labLeader","%"+labEntity.getLabLeader()+"%");

        }
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
