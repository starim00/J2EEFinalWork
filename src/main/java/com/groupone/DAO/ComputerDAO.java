package com.groupone.DAO;

import com.groupone.model.ComputerEntity;
import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ComputerDAO {
    private Session session;
    public void getSession(){
        if(session==null){
            session=MyListener.sessionFactory.openSession();
        }
    }
    public synchronized List<ComputerEntity> loadAllComputer(){
        String hql = "from ComputerEntity ";
        getSession();
        List<ComputerEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }
    public synchronized boolean deleteComputer(int computerId) throws Exception {
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            ComputerEntity computer = session.get(ComputerEntity.class,computerId);
            if(computer == null){
                throw new Exception("计算机不存在");
            }
            session.delete(computer);
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
    public synchronized List<Object[]> searchComputer(int location,String labName){
        getSession();
        List<Object[]> result;
        if(location==-1){
            String hql = "select ComputerEntity ,LabEntity .labName " +
                    "from ComputerEntity left join LabEntity on ComputerEntity .labId=LabEntity .labId " +
                    "where LabEntity .labName like :labName";
            Query query = session.createQuery(hql);
            query.setParameter("labName","%"+labName+"%");
            result = query.list();
        }
        else {
            String hql = "select ComputerEntity ,LabEntity .labName " +
                    "from ComputerEntity left join LabEntity on ComputerEntity .labId=LabEntity .labId " +
                    "where LabEntity .labName like :labName and ComputerEntity .location=:location";
            Query query = session.createQuery(hql);
            query.setParameter("labName","%"+labName+"%");
            query.setParameter("location",location);
            result = query.list();
        }

        return result;
    }
    public synchronized boolean modifyComputer(ComputerEntity computerEntity){
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            ComputerEntity com = session.get(ComputerEntity.class,computerEntity.getComputerId());
            if(com==null){
                throw new Exception("计算机不存在");
            }
            session.update(computerEntity);
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
    public synchronized boolean insertComputer(ComputerEntity computerEntity){
        getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            session.save(computerEntity);
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
