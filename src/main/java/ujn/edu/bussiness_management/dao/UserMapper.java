package ujn.edu.bussiness_management.dao;

import org.apache.ibatis.annotations.Mapper;
import ujn.edu.bussiness_management.model.Customer;
import ujn.edu.bussiness_management.model.User;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    int insert(User record);
    int insertSelective(User record);
    User findByUsername(String userName);
    User findUserLogin(Map<String,Object> map);
    List<Customer> selectUserInOne(Long id);
}