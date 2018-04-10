package ujn.edu.bussiness_management.test;

import ujn.edu.bussiness_management.test.UserCus;

public interface UserCusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCus record);

    int insertSelective(UserCus record);

    UserCus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCus record);

    int updateByPrimaryKey(UserCus record);
}