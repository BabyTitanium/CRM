package ujn.edu.bussiness_management.dao;

import org.apache.ibatis.annotations.Mapper;
import ujn.edu.bussiness_management.model.UserCon;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserConMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCon record);

    int insertSelective(UserCon record);

    UserCon selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCon record);

    int updateByPrimaryKey(UserCon record);
    List<Map<String,Object>> selectAllContact(Map<String,Object>map);
    int selectCountAllContact(Long id);
}