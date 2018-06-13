package com.groupone.DAO;

import com.groupone.model.AdminEntity;
import java.util.List;

public interface IAdminDAO {
        void getSession();
        public AdminEntity getAdminById(String username,String adminId);
        public List<AdminEntity> loadAllAdmin(String username);
        public boolean deleteAdmin(String username,int adminId) throws Exception ;
        public boolean modifyAdmin(String username,AdminEntity adminEntity) throws Exception;
        public boolean insertAdmin(String username,AdminEntity adminEntity) throws Exception;

}
