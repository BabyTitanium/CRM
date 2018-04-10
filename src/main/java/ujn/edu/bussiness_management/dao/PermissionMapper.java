package ujn.edu.bussiness_management.dao;

import org.apache.ibatis.annotations.Mapper;
import ujn.edu.bussiness_management.model.Permission;
@Mapper
public interface PermissionMapper {
    int insert(Permission record);

    int insertSelective(Permission record);
    Permission findByPid(Long id);
}