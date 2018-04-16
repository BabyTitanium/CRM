package ujn.edu.bussiness_management.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    List<Map<String,Object>> selectAllCustomer(Map<String,Object>map);
   int selectCountAllCustomer(Long id);
    void updateNextTime(Map<String,Object> map);
   void deleteUserCus(Long  id);
   List<Map<String,Object> > selectSharedCustomer(Map<String,Object> m);
   Integer selectCountSharedCustomer(Long id);
    List<Map<String,Object>> selectCustomers(Long id);

}