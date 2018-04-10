package ujn.edu.bussiness_management.dao;

import org.apache.ibatis.annotations.Mapper;
import ujn.edu.bussiness_management.model.Menu;
@Mapper
public interface MenuMapper {
    Menu findMenuById(Long id);
}
