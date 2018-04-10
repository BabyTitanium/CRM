package ujn.edu.bussiness_management.dao;

import org.apache.ibatis.annotations.Mapper;
import ujn.edu.bussiness_management.model.Role;
@Mapper
public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);
    Role findByRoleId(Long id);
}