package com.groupone.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.groupone.model.UserDAO;
import com.groupone.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("123123");
        System.out.println(username);
        System.out.println(password);
        String result;
        UserEntity user = userDAO.getUserByUserId(username);

        if(user==null){
            result="{state:0}";
            System.out.println("1");
        }
        else if(!user.getPasswd().equals(password)){
            result="{state:0}";
            System.out.println("2");
        }
        else{
            result="{\"state\":1}";
            System.out.println("3");
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
