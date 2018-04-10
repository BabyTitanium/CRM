package ujn.edu.bussiness_management.service;

import ujn.edu.bussiness_management.model.UserRole;

public interface IUserRoleService {
     UserRole findByUid(Long uid);
}
