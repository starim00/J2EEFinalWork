package com.groupone.model;

import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
        return result;
    }
    public synchronized boolean deleteLab(int labId) throws Exception {
        LabEntity lab = session.get(LabEntity.class,labId);
        if(lab == null){
            throw new Exception("实验室不存在");
        }
        session.delete(lab);
        return true;
    }
}
