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
        String hql = "from ComputerEntity ";
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
        session.delete(computer);
        return true;
    }

    public synchronized List<Object[]> searchComputer(int location, String labName) {
        Session session = MyListener.sessionFactory.getCurrentSession();
        List<Object[]> result;
        if (location == -1) {
            String hql = "select ComputerEntity ,LabEntity .labName " +
                    "from ComputerEntity left join LabEntity on ComputerEntity .labId=LabEntity .labId " +
                    "where LabEntity .labName like :labName";
            Query query = session.createQuery(hql);
            query.setParameter("labName", "%" + labName + "%");
            result = query.list();
        } else {
            String hql = "select ComputerEntity ,LabEntity .labName " +
                    "from ComputerEntity left join LabEntity on ComputerEntity .labId=LabEntity .labId " +
                    "where LabEntity .labName like :labName and ComputerEntity .location=:location";
            Query query = session.createQuery(hql);
            query.setParameter("labName", "%" + labName + "%");
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

}
