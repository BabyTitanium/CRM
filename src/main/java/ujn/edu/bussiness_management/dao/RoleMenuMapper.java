package ujn.edu.bussiness_management.dao;

import org.apache.ibatis.annotations.Mapper;
import ujn.edu.bussiness_management.model.RoleMenu;

import java.util.List;
@Mapper
public interface RoleMenuMapper {
List<RoleMenu> findByRoleId(Long roleId);
}
