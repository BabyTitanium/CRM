package ujn.edu.bussiness_management.test;

import ujn.edu.bussiness_management.test.UserCon;

public interface UserConMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCon record);

    int insertSelective(UserCon record);

    UserCon selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCon record);

    int updateByPrimaryKey(UserCon record);
}