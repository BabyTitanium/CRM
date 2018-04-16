package ujn.edu.bussiness_management.dao;

import org.apache.ibatis.annotations.Mapper;
import ujn.edu.bussiness_management.model.Contact;
import ujn.edu.bussiness_management.model.User;
import ujn.edu.bussiness_management.model.UserCon;

@Mapper
public interface ContactMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Contact record);

    int insertSelective(Contact record);

    Contact selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Contact record);

    int updateByPrimaryKey(Contact record);
    void createUserCon(UserCon userCon);
    Contact selectCustomer(Long id);
}