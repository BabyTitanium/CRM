package ujn.edu.bussiness_management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujn.edu.bussiness_management.config.ShiroRealm;
import ujn.edu.bussiness_management.dao.RolePermissionMapper;
import ujn.edu.bussiness_management.model.RolePermission;

import java.util.List;
@Service
public class RolePermissionService implements IRolePermissionService {
    @Autowired
    private RolePermissionMapper RolePermissionMapper;
    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    public List<RolePermission> findByRid(Long id){
        try {
            logger.info("roleId:"+id);
            List<RolePermission> permissions= RolePermissionMapper.findByRid(id);
            return permissions;
        }catch (Exception e){
            logger.info("查找权限"+e.getMessage());
            return  null;
        }

    }
}
