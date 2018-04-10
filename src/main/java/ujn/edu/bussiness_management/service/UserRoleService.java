package ujn.edu.bussiness_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujn.edu.bussiness_management.dao.UserRoleMapper;
import ujn.edu.bussiness_management.model.UserRole;
@Service
public class UserRoleService implements IUserRoleService {
    @Autowired
    private UserRoleMapper UserRoleMapper;
    public UserRole findByUid(Long uid){
        UserRole userRole= UserRoleMapper.findByUid(uid);
        return  userRole;
    }
}
