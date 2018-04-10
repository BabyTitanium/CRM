package ujn.edu.bussiness_management.dao;

import org.apache.ibatis.annotations.Mapper;
import ujn.edu.bussiness_management.model.Customer;
import ujn.edu.bussiness_management.model.UserCus;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserCusMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCus record);

    int insertSelective(UserCus record);

    UserCus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCus record);

    int updateByPrimaryKey(UserCus record);
    List<Map<String,Object>> selectAllCustomer(Long userId);
}