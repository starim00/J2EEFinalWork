package com.groupOne.DAO;

import com.groupOne.model.AdminEntity;
import java.util.List;

public interface IAdminDAO {
        public AdminEntity getAdminById(String username,String adminId);
        public List<AdminEntity> loadAllAdmin(String username);
        public boolean deleteAdmin(String username,int adminId) throws Exception ;
        public boolean modifyAdmin(String username,AdminEntity adminEntity) throws Exception;
        public boolean insertAdmin(String username,AdminEntity adminEntity) throws Exception;

}
