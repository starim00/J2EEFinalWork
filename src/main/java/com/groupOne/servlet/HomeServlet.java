package com.groupOne.servlet;

import com.google.gson.Gson;
import com.groupOne.DAO.IAdminDAO;
import com.groupOne.DAO.ILabDAO;
import com.groupOne.DAO.IUserDAO;
import com.groupOne.model.AdminEntity;
import com.groupOne.model.LabEntity;
import com.groupOne.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeServlet extends HttpServlet {
    private ILabDAO labDAO = (ILabDAO) MyListener.applicationContext.getBean("labDAO");
    private IUserDAO userDAO = (IUserDAO) MyListener.applicationContext.getBean("userDAO");
    private IAdminDAO adminDAO = (IAdminDAO) MyListener.applicationContext.getBean("adminDAO");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setCharacterEncoding("UTF-8");
        String method = request.getParameter("method");
        String result = "";
        if (method == null)
            method = "";
        if (method.equals("load")) {
            result = this.load();
        }
        if (method.equals("modify")) {
            result = this.modify(request.getParameter("username"), request.getParameter("password"),request.getParameter("password_new"));
        }

        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    private String load() {
        String result = "";
        List<LabEntity> list = labDAO.loadAllLab();
        List<LoadHomeJson> temp = new ArrayList<LoadHomeJson>();
        UserEntity leader;
        for (int i = 0; i < list.size(); i++) {
            leader = userDAO.getUserByUserId(list.get(i).getLabLeader());
            LoadHomeJson ins = new LoadHomeJson();
            ins.setLabLeader(list.get(i).getLabLeader());
            ins.setLabName(list.get(i).getLabName());
            ins.setLocation(list.get(i).getLocation());
            ins.setTel(leader.getTel());
            temp.add(ins);
        }
        Gson gson = new Gson();
        result = gson.toJson(temp);
        return result;
    }

    private String modify(String username, String password,String password_new) {
        AdminEntity admin;
        admin = adminDAO.getAdminById(username, username);
        String pa = "PasswordError";
        boolean ps = false;
        if (admin != null) {
            if (admin.getPassword().equals(password)) {
                admin.setPassword(password_new);
                try {
                    adminDAO.modifyAdmin(username, admin);
                    return "true";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ps = true;
            }
        }
        UserEntity user = userDAO.getUserByUserId(username);
        if (user != null) {
            if (user.getPasswd().equals(password)) {
                user.setPasswd(password_new);
                try {
                    userDAO.modifyUser(user);
                    return "true";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "false";
                }
            } else {
                return pa;
            }
        } else {
            if (ps) {
                return pa;
            } else {
                return "false";
            }
        }
    }
}
class LoadHomeJson {
    private String labName;
    private int location;
    private String labLeader;
    private Integer tel;

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getLabLeader() {
        return labLeader;
    }

    public void setLabLeader(String labLeader) {
        this.labLeader = labLeader;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }
}