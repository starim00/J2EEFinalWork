package com.groupOne.DAO;

import com.groupOne.model.ComputerEntity;
import com.groupOne.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository("computerDAO")
public class ComputerDAO implements IComputerDAO {

    public synchronized List<ComputerEntity> loadAllComputer() {
        String hql = "from ComputerEntity";
        Session session = MyListener.sessionFactory.getCurrentSession();
        List<ComputerEntity> result = session.createQuery(hql).list();
        return result;
    }

    public synchronized boolean deleteComputer(String username,int computerId) throws Exception {
        Session session = MyListener.sessionFactory.getCurrentSession();
        ComputerEntity computer = session.get(ComputerEntity.class, computerId);
        if (computer == null) {
            throw new Exception("计算机不存在");
        }
        String hql ="delete from LoginEntity where computerId = :computerId";
        Query query = session.createQuery(hql);
        query.setParameter("computerId",computerId);
        query.executeUpdate();
        session.delete(computer);
        return true;
    }

    public synchronized List<ComputerEntity> searchComputer(int location, int labId) {
        Query query;
        List<ComputerEntity> result;
        if (location == -1) {
            String hql = "from ComputerEntity where labId = :labId";
            Session session = MyListener.sessionFactory.getCurrentSession();
            query = session.createQuery(hql);
            query.setParameter("labId", labId);
            result = query.list();
        }
        else {
            String hql = "from ComputerEntity where ComputerEntity.labId = :labId and ComputerEntity .location=:location";
            Session session = MyListener.sessionFactory.getCurrentSession();
            query = session.createQuery(hql);
            query.setParameter("labId", labId);
            query.setParameter("location", location);
            result = query.list();
        }
        return result;
    }

    public synchronized boolean modifyComputer(String username,ComputerEntity computerEntity) throws Exception {
        Session session = MyListener.sessionFactory.getCurrentSession();
        ComputerEntity com = session.get(ComputerEntity.class, computerEntity.getComputerId());
        if (com == null) {
            throw new Exception("计算机不存在");
        }
        session.evict(com);
        session.update(computerEntity);
        return true;
    }

    public synchronized boolean insertComputer(String username,ComputerEntity computerEntity) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        session.save(computerEntity);
        return true;
    }

    public ComputerEntity getComputerById(int computerId) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        return session.get(ComputerEntity.class,computerId);
    }
}
