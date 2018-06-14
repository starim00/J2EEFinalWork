package com.groupOne.aspectj;

import com.groupOne.model.AdminEntity;
import com.groupOne.servlet.MyListener;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminAspectj {
    @Pointcut(value = "execution(* com.groupOne.DAO.AdminDAO.*(..))")
    private void PointCut() {}
    @Pointcut(value = "execution(* com.groupOne.DAO.UserDAO.*(..))")
    private void userCut(){}
    @Pointcut(value = "execution(* com.groupOne.DAO.LabDAO.insertLab(..))")
    private void labCut(){}

    @Around("(PointCut()||userCut()||labCut()||execution(* com.groupOne.DAO.LabDAO.deleteLab(..)))&&args(username,*)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint,String username)throws Throwable{
        Object object;
        Session session = MyListener.sessionFactory.openSession();
        AdminEntity temp = session.get(AdminEntity.class,username);
        if(temp!=null){
            object=proceedingJoinPoint.proceed();
        }
        else{
            throw new Exception("权限不足");
        }
        return object;
    }
}
