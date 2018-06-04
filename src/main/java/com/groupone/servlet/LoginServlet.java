package com.groupone.servlet;

import com.groupone.DAO.AdminDAO;
import com.groupone.DAO.LabDAO;
import com.groupone.DAO.UserDAO;
import com.groupone.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        AdminDAO adminDAO = new AdminDAO();
        LabDAO labDAO = new LabDAO();
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String result="";
        AdminEntity admin = adminDAO.getAdminById(username);
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
                        result = "{\"state\":2}";
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
