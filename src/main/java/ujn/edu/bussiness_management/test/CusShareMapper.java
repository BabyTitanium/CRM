package ujn.edu.bussiness_management.test;

import ujn.edu.bussiness_management.test.CusShare;

public interface CusShareMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CusShare record);

    int insertSelective(CusShare record);

    CusShare selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CusShare record);

    int updateByPrimaryKey(CusShare record);
}