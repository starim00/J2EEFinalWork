package com.groupone.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Cookie cookie=null;
        for(int i = 0;i<cookies.length;i++){
            if(cookies[i].getName().equals("loginCookie")){
                cookie=cookies[i];
                break;
            }
        }
        if(cookie==null){
            response.sendRedirect("/index.html");
        }
        response.addCookie(new Cookie("loginCookie",cookie.getValue()));
        response.sendRedirect("/home.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
class userType{
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
