package com.groupOne.DAO;

import com.groupOne.model.LabEntity;
import com.groupOne.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository("labDAO")
public class LabDAO implements ILabDAO {

    public synchronized List<LabEntity> loadAllLab() {
        String hql = "from LabEntity ";
        Session session = MyListener.sessionFactory.getCurrentSession();
        List<LabEntity> result = session.createQuery(hql).list();
        return result;
    }

    public synchronized boolean deleteLab(String username,int labId) throws Exception {
        Session session = MyListener.sessionFactory.getCurrentSession();
        LabEntity lab = session.get(LabEntity.class, labId);
        if (lab == null) {
            throw new Exception("实验室不存在");
        }
        String hql = "delete from ComputerEntity where labId = :labId";
        Query query = session.createQuery(hql);
        query.setParameter("labId",labId);
        query.executeUpdate();
        session.delete(lab);
        return true;
    }

    public synchronized List<LabEntity> searchLab(LabEntity labEntity) {
        String hql;
        Session session = MyListener.sessionFactory.getCurrentSession();
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
        return result;
    }

    public synchronized boolean modifyLab(String username,LabEntity labEntity) throws Exception {
        Session session = MyListener.sessionFactory.getCurrentSession();
        LabEntity lab = session.get(LabEntity.class, labEntity.getLabId());
        if (lab == null) {
            throw new Exception("实验室不存在");
        }
        session.evict(lab);
        session.update(labEntity);
        return true;
    }

    public synchronized boolean insertLab(String username,LabEntity labEntity) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        session.save(labEntity);
        return true;
    }

    public LabEntity getlabById(int labId) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        return session.get(LabEntity.class,labId);
    }
}
