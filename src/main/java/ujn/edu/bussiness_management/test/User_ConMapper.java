package ujn.edu.bussiness_management.test;

import ujn.edu.bussiness_management.test.User_Con;

public interface User_ConMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User_Con record);

    int insertSelective(User_Con record);

    User_Con selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User_Con record);

    int updateByPrimaryKey(User_Con record);
}