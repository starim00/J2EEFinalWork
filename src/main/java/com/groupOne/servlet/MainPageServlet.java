package com.groupOne.servlet;

import com.google.gson.Gson;
import com.groupOne.DAO.*;
import com.groupOne.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainPageServlet extends HttpServlet {
    private ILabDAO labDAO = (ILabDAO)MyListener.applicationContext.getBean("labDAO");
    private IUserDAO userDAO = (IUserDAO)MyListener.applicationContext.getBean("userDAO");
    private IComputerDAO computerDAO = (IComputerDAO)MyListener.applicationContext.getBean("computerDAO");
    private ILoginDAO loginDAO = (ILoginDAO)MyListener.applicationContext.getBean("loginDAO");
    private IAdminDAO adminDAO = (IAdminDAO)MyListener.applicationContext.getBean("adminDAO");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setCharacterEncoding("UTF-8");
        String method = request.getParameter("method");
        String result = "";
        if(method==null){
            method="";
        }
        if(method.equals("load")){
            result = this.load();
        }
        if(method.equals("delLab")){
            result = delLab(request.getParameter("username"),Integer.parseInt(request.getParameter("labId")));
        }
        if(method.equals("delCom")){
            result = delCom(request.getParameter("username"),Integer.parseInt(request.getParameter("comId")));
        }
        if(method.equals("delUser")){
            result = delUser(request.getParameter("username"),Integer.parseInt(request.getParameter("userId")));
        }
        if(method.equals("lookLab")){
            result = lookLab(Integer.parseInt(request.getParameter("labId")));
        }
        if(method.equals("lookCom")){
            result = lookCom(Integer.parseInt(request.getParameter("computerId")));
        }
        if(method.equals("lookUser")){
            result = lookUser(request.getParameter("userId"));
        }
        if(method.equals("modifyLab")){
            try{
                LabEntity lab = new LabEntity();
                lab.setLabId(Integer.parseInt(request.getParameter("labId")));
                lab.setLabName(request.getParameter("labName"));
                lab.setLabLeader(request.getParameter("labLeader"));
                lab.setLocation(Integer.parseInt(request.getParameter("location")));
                lab.setSafeLevel(Integer.parseInt(request.getParameter("safeLevel")));
                result = modifyLab(request.getParameter("username"),lab);
            }catch (Exception e){
                result = "{\"success\":0}";
            }
        }
        if(method.equals("modifyCom")){
            try{
                ComputerEntity computer = new ComputerEntity();
                computer.setComputerId(Integer.parseInt(request.getParameter("computerId")));
                computer.setIpAddress(request.getParameter("ipAddress"));
                computer.setLocation(Integer.parseInt(request.getParameter("location")));
                computer.setLabId(Integer.parseInt(request.getParameter("labId")));
                result = modifyCom(request.getParameter("username"),computer);
            }catch (Exception e ){
                result = "{\"success\":0}";
            }
        }
        if(method.equals("modifyUser")){
            try {
                String username = request.getParameter("username");
                if(adminDAO.getAdminById(username,username)==null){
                    throw new Exception("权限不足");
                }
                UserEntity user = new UserEntity();
                user.setUserId(request.getParameter("userId"));
                user.setUserName(request.getParameter("acc_name"));
                user.setPasswd(request.getParameter("acc_psd"));
                result = modifyUser(user);
            }catch (Exception e){
                result = "{\"success\":0}";
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
    private String load(){
        String result = "";
        LoadMainJson load = new LoadMainJson();
        List<LabEntity> templab = labDAO.loadAllLab();
        List<labJson> listLab = new ArrayList<labJson>();
        for(LabEntity lab:templab){
            labJson labJson = new labJson();
            labJson.setLabId(lab.getLabId());
            labJson.setLabName(lab.getLabName());
            labJson.setLabLeader(lab.getLabLeader());
            labJson.setLocation(lab.getLocation());
            labJson.setSafeLevel(lab.getSafeLevel());
            listLab.add(labJson);
        }
        load.setLab(listLab);
        List<ComputerEntity> tempCom = computerDAO.loadAllComputer();
        List<comJson> listCom = new ArrayList<comJson>();
        for(ComputerEntity com:tempCom){
            comJson comJson = new comJson();
            comJson.setComputerId(com.getComputerId());
            comJson.setIpAddress(com.getIpAddress());
            comJson.setLocation(com.getLocation());
            comJson.setLabName(labDAO.getlabById(com.getLabId()).getLabName());
            listCom.add(comJson);
        }
        load.setCom(listCom);
        List<UserEntity> tempUser = userDAO.loadAllUser();
        List<userJson> listUser = new ArrayList<userJson>();
        for(UserEntity user:tempUser){
            userJson userJson = new userJson();
            userJson.setUserId(user.getUserId());
            userJson.setUserName(user.getUserName());
            userJson.setTel(user.getTel());
            listUser.add(userJson);
        }
        load.setUser(listUser);
        Gson gson = new Gson();
        result = gson.toJson(load,LoadMainJson.class);
        return result;
    }
    private String delLab(String username,int labId){
        String result;
        try {
            labDAO.deleteLab(username,labId);
            result = "{\"success\":1}";
        } catch (Exception e) {
            result = "{\"success\":0}";
        }
        return result;
    }
    private String delCom(String username,int comId){
        String result;
        try {
            computerDAO.deleteComputer(username,comId);
            result = "{\"success\":1}";
        } catch (Exception e) {
            result = "{\"success\":0}";
        }
        return result;
    }
    private String delUser(String username,int userId){
        String result;
        try {
            userDAO.deleteUser(username,userId);
            result = "{\"success\":1}";
        } catch (Exception e) {
            result = "{\"success\":0}";
        }
        return result;
    }
    private String lookLab(int labId){
        String result;
        LabEntity lab = labDAO.getlabById(labId);
        List<ComputerEntity> comList = computerDAO.searchComputer(-1,lab.getLabId());
        List<comUseJson> useList = new ArrayList<comUseJson>();
        for(ComputerEntity com:comList){
            comUseJson temp = new comUseJson();
            temp.setLocation(com.getLocation());
            List<LoginEntity> loginList = loginDAO.searchLoginByComputerId(com.getComputerId());
            int time=0;
            for(LoginEntity login:loginList){
                if(login.getOutTime()!=0){
                    time += (login.getOutTime()-login.getInTime())/3600000;
                }
            }
            temp.setUseTime(time);
            temp.setUseCount(loginList.size());
            useList.add(temp);
        }
        Gson gson = new Gson();
        result = gson.toJson(useList);
        return result;
    }
    private String lookCom(int computerId){
        String result;
        loginJson loginJson = new loginJson();
        ComputerEntity com = computerDAO.getComputerById(computerId);
        comUseJson comUse = new comUseJson();
        comUse.setLocation(com.getLocation());
        List<LoginEntity> loginList = loginDAO.searchLoginByComputerId(com.getComputerId());
        List<login> temp = new ArrayList<login>();
        int time=0;
        for(LoginEntity login:loginList){
            if(login.getOutTime()!=0){
                time += (login.getOutTime()-login.getInTime())/3600000;
            }
            login loginTemp = new login();
            loginTemp.setUserId(login.getUserId());
            loginTemp.setInTime(new Date(login.getInTime()));
            loginTemp.setOutTime(new Date(login.getOutTime()));
            temp.add(loginTemp);
        }
        comUse.setUseTime(time);
        comUse.setUseCount(loginList.size());
        loginJson.setComUse(comUse);
        loginJson.setLogin(temp);
        Gson gson = new Gson();
        result = gson.toJson(loginJson);
        return result;
    }
    private String lookUser(String userId){
        String result;
        userLookJson userLook = new userLookJson();
        List<LoginEntity> loginList = loginDAO.searchLoginByUserId(userId);
        List<useDetail> useDetails = new ArrayList<useDetail>();
        int time = 0;
        for(LoginEntity login:loginList) {
            useDetail useDetail = new useDetail();
            if (login.getOutTime() != 0) {
                time += (login.getOutTime() - login.getInTime()) / 3600000;
            }
            ComputerEntity com = computerDAO.getComputerById(login.getComputerId());
            LabEntity lab = labDAO.getlabById(com.getLabId());
            useDetail.setLocation(com.getLocation());
            useDetail.setLabName(lab.getLabName());
            useDetail.setInTime(new Date(login.getInTime()));
            useDetail.setOutTime(new Date(login.getOutTime()));
            useDetails.add(useDetail);
        }
        comUseJson comUse = new comUseJson();
        comUse.setUseTime(time);
        comUse.setUseCount(loginList.size());
        userLook.setComUse(comUse);
        userLook.setUseDetail(useDetails);
        Gson gson = new Gson();
        result = gson.toJson(userLook);
        return result;
    }
    private String modifyLab(String username,LabEntity lab){
        String result;
        try {
            labDAO.modifyLab(username,lab);
            result = "{\"success\":1}";
        } catch (Exception e) {
            result = "{\"success\":0}";
        }
        return result;
    }
    private String modifyCom(String username,ComputerEntity computer){
        String result;
        try {
            computerDAO.modifyComputer(username,computer);
            result = "{\"success\":1}";
        } catch (Exception e) {
            result = "{\"success\":0}";
        }
        return result;
    }
    private String modifyUser(UserEntity user){
        String result;
        UserEntity oldUser = userDAO.getUserByUserId(user.getUserId());
        if(user.getPasswd()==null){
            user.setPasswd(oldUser.getPasswd());
        }
        try {
            userDAO.modifyUser(user);
            result = "{\"success\":1}";
        }catch (Exception e){
            result = "{\"success\":0}";
        }
        return result;
    }
}
class LoadMainJson{
    private List<labJson> lab;
    private List<comJson> com;
    private List<userJson> user;

    public List<labJson> getLab() {
        return lab;
    }

    public void setLab(List<labJson> lab) {
        this.lab = lab;
    }

    public List<comJson> getCom() {
        return com;
    }

    public void setCom(List<comJson> com) {
        this.com = com;
    }

    public List<userJson> getUser() {
        return user;
    }

    public void setUser(List<userJson> user) {
        this.user = user;
    }
}
class labJson{
    private int labId;
    private String labName;
    private String labLeader;
    private int location;
    private int safeLevel;

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getLabLeader() {
        return labLeader;
    }

    public void setLabLeader(String labLeader) {
        this.labLeader = labLeader;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getSafeLevel() {
        return safeLevel;
    }

    public void setSafeLevel(int safeLevel) {
        this.safeLevel = safeLevel;
    }
}
class comJson{
    private int computerId;
    private String ipAddress;
    private int location;
    private String labName;

    public int getComputerId() {
        return computerId;
    }

    public void setComputerId(int computerId) {
        this.computerId = computerId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }
}
class userJson{
    private String userId;
    private String userName;
    private String passwd;
    private Integer tel;
    private Integer userType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
class comUseJson{
    private int location;
    private int useTime;
    private int useCount;

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }
}
class loginJson{
    private comUseJson comUse;
    private List<login> login;

    public comUseJson getComUse() {
        return comUse;
    }

    public void setComUse(comUseJson comUse) {
        this.comUse = comUse;
    }

    public List<login> getLogin() {
        return login;
    }

    public void setLogin(List<login> login) {
        this.login = login;
    }
}
class login{
    private String userId;
    private Date inTime;
    private Date outTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }
}
class userLookJson{
    private comUseJson comUse;
    List<useDetail> useDetail;

    public comUseJson getComUse() {
        return comUse;
    }

    public void setComUse(comUseJson comUse) {
        this.comUse = comUse;
    }

    public List<com.groupOne.servlet.useDetail> getUseDetail() {
        return useDetail;
    }

    public void setUseDetail(List<com.groupOne.servlet.useDetail> useDetail) {
        this.useDetail = useDetail;
    }
}
class useDetail{
    private String labName;
    private int location;
    private Date inTime;
    private Date outTime;

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

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }
}