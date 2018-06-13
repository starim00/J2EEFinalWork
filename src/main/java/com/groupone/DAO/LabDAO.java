package com.groupone.DAO;

import com.groupone.model.LabEntity;
import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("labDAO")
public class LabDAO implements ILabDAO {
    private Session session;

    public void getSession() {
        if (session == null) {
            session = MyListener.sessionFactory.openSession();
        }
    }

    public synchronized List<LabEntity> loadAllLab() {
        String hql = "from LabEntity ";
        getSession();
        List<LabEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean deleteLab(String username,int labId) throws Exception {
        getSession();
        LabEntity lab = session.get(LabEntity.class, labId);
        if (lab == null) {
            throw new Exception("实验室不存在");
        }
        session.delete(lab);
        session.close();
        return true;
    }

    public synchronized List<LabEntity> searchLab(LabEntity labEntity) {
        String hql;
        getSession();
        Query query;
        if (labEntity.getSafeLevel() != -1 && labEntity.getLocation() != -1) {
            hql = "from LabEntity where labName like :labName and labLeader like :labLeader and location = :location and safeLevel = :safeLevel";
            query = session.createQuery(hql);
            query.setParameter("labName", "%" + labEntity.getLabName() + "%");
            query.setParameter("labLeader", "%" + labEntity.getLabLeader() + "%");
            query.setParameter("location", labEntity.getLocation());
            query.setParameter("safeLevel", labEntity.getSafeLevel());
        } else if (labEntity.getLocation() != -1) {
            hql = "from LabEntity where labName like :labName and labLeader like :labLeader and location = :location";
            query = session.createQuery(hql);
            query.setParameter("labName", "%" + labEntity.getLabName() + "%");
            query.setParameter("labLeader", "%" + labEntity.getLabLeader() + "%");
            query.setParameter("location", labEntity.getLocation());
        } else if (labEntity.getSafeLevel() != -1) {
            hql = "from LabEntity where labName like :labName and labLeader like :labLeader and safeLevel = :safeLevel";
            query = session.createQuery(hql);
            query.setParameter("labName", "%" + labEntity.getLabName() + "%");
            query.setParameter("labLeader", "%" + labEntity.getLabLeader() + "%");
            query.setParameter("safeLevel", labEntity.getSafeLevel());
        } else {
            hql = "from LabEntity where labName like :labName and labLeader like :labLeader ";
            query = session.createQuery(hql);
            query.setParameter("labName", "%" + labEntity.getLabName() + "%");
            query.setParameter("labLeader", "%" + labEntity.getLabLeader() + "%");

        }
        List<LabEntity> result = query.list();
        session.close();
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean modifyLab(String username,LabEntity labEntity) throws Exception {
        getSession();
        LabEntity lab = session.get(LabEntity.class, labEntity.getLabId());
        if (lab == null) {
            throw new Exception("实验室不存在");
        }
        session.update(labEntity);
        session.close();
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean insertLab(String username,LabEntity labEntity) {
        getSession();
        Transaction transaction = null;
        transaction = session.getTransaction();
        session.save(labEntity);
        transaction.commit();
        session.close();
        return true;
    }
}
