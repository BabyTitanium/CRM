package ujn.edu.bussiness_management.dao;

import org.apache.ibatis.annotations.Mapper;
import ujn.edu.bussiness_management.model.UserInfo;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    UserInfo findByUid(Long userId);//根据userId查找用户信息
    List<UserInfo> findByLid(Long id);
}
