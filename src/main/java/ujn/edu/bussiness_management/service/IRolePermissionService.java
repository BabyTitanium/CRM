package ujn.edu.bussiness_management.service;

import ujn.edu.bussiness_management.model.RolePermission;

import java.util.List;

public interface IRolePermissionService {
    List<RolePermission> findByRid(Long id);
}
