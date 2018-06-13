package com.groupone.aspectj;

import com.groupone.DAO.LabDAO;
import com.groupone.model.AdminEntity;
import com.groupone.model.LabEntity;
import com.groupone.servlet.MyListener;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class LabLeaderAspectj {
    @Pointcut(value = "execution(* com.groupone.DAO.ComputerDAO.modifyComputer(..))||execution(* com.groupone.DAO.ComputerDAO.deleteComputer(..))||execution(* com.groupone.DAO.ComputerDAO.insertComputer(..))||execution(* com.groupone.DAO.LabDAO.modifyLab(..))")
    private void pointCut(){}

    @Around("pointCut()&&args(username,..)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint,String username) throws Throwable{
        Object object;
        Session session = MyListener.sessionFactory.openSession();
        AdminEntity temp = session.get(AdminEntity.class,username);
        if(temp!=null){
            object=proceedingJoinPoint.proceed();
        }
        else{
            LabDAO labDAO = (LabDAO)MyListener.applicationContext.getBean("labDAO");
            LabEntity labEntity = new LabEntity();
            labEntity.setLabLeader(username);
            labEntity.setLocation(-1);
            labEntity.setSafeLevel(-1);
            List<LabEntity> temp2 = labDAO.searchLab(labEntity);
            if(!temp2.isEmpty()) {
                object = proceedingJoinPoint.proceed();
            }
            else {
                throw new Exception("权限不足");
            }
        }
        return object;
    }
}
