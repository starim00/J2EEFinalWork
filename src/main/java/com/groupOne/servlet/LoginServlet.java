package com.groupOne.servlet;

import com.groupOne.DAO.IAdminDAO;
import com.groupOne.DAO.ILabDAO;
import com.groupOne.DAO.IUserDAO;
import com.groupOne.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginServlet extends HttpServlet {
    private IUserDAO userDAO = (IUserDAO)MyListener.applicationContext.getBean("userDAO");
    private IAdminDAO adminDAO = (IAdminDAO)MyListener.applicationContext.getBean("adminDAO");
    private ILabDAO labDAO = (ILabDAO)MyListener.applicationContext.getBean("labDAO");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String result="";
        AdminEntity admin = null;
        try{
            admin = adminDAO.getAdminById(username,username);
        }catch (Exception e){
            e.printStackTrace();
            admin = null;
        }
        if(admin!=null&&admin.getPassword().equals(password)){
            result="{\"state\":3}";
        }
        else {
            LabEntity labEntity = new LabEntity();
            labEntity.setLabLeader(username);
            labEntity.setLocation(-1);
            labEntity.setSafeLevel(-1);
            List<LabEntity> temp = labDAO.searchLab(labEntity);
            if(!temp.isEmpty()) {
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).getLabLeader().equals(username)) {
                        result = "{\"state\":1}";
                        break;
                    }
                }
            }
            else{
                UserEntity user = userDAO.getUserByUserId(username);

                if(user==null){
                    result="{\"state\":0}";
                }
                else if(!user.getPasswd().equals(password)){
                    result="{\"state\":0}";
                }
                else{
                    result="{\"state\":1}";
                }
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
