package com.groupone.DAO;

import com.groupone.model.ComputerEntity;
import com.groupone.servlet.MyListener;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("computerDAO")
public class ComputerDAO implements IComputerDAO {
    private Session session;

    public void getSession() {
        if (session == null) {
            session = MyListener.sessionFactory.openSession();
        }
    }

    public synchronized List<ComputerEntity> loadAllComputer() {
        String hql = "from ComputerEntity ";
        getSession();
        List<ComputerEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean deleteComputer(String username,int computerId) throws Exception {
        getSession();
        ComputerEntity computer = session.get(ComputerEntity.class, computerId);
        if (computer == null) {
            throw new Exception("计算机不存在");
        }
        session.delete(computer);
        session.close();
        return true;
    }

    public synchronized List<Object[]> searchComputer(int location, String labName) {
        getSession();
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

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean modifyComputer(String username,ComputerEntity computerEntity) throws Exception {
        getSession();
        ComputerEntity com = session.get(ComputerEntity.class, computerEntity.getComputerId());
        if (com == null) {
            throw new Exception("计算机不存在");
        }
        session.update(computerEntity);
        session.close();
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean insertComputer(String username,ComputerEntity computerEntity) {
        getSession();
        session.save(computerEntity);
        session.close();
        return true;
    }

}
