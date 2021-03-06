package com.groupOne.aspectj;

import com.groupOne.DAO.LabDAO;
import com.groupOne.DAO.UserDAO;
import com.groupOne.model.AdminEntity;
import com.groupOne.model.LabEntity;
import com.groupOne.model.UserEntity;
import com.groupOne.servlet.MyListener;
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
    @Pointcut(value = "execution(* com.groupOne.DAO.ComputerDAO.modifyComputer(..))||execution(* com.groupOne.DAO.ComputerDAO.deleteComputer(..))||execution(* com.groupOne.DAO.ComputerDAO.insertComputer(..))||execution(* com.groupOne.DAO.LabDAO.modifyLab(..))")
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
            Session session1 = MyListener.sessionFactory.openSession();
             UserEntity user = session1.get(UserEntity.class,username);
            if(user.getUserType()==1) {
                object = proceedingJoinPoint.proceed();
            }
            else {
                throw new Exception("权限不足");
            }
        }
        return object;
    }
}
