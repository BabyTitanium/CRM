package ujn.edu.bussiness_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujn.edu.bussiness_management.dao.UserInfoMapper;
import ujn.edu.bussiness_management.model.UserInfo;
@Service
public class UserInfoService implements IUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    public UserInfo findUserInfoByUid(Long userId){
        return userInfoMapper.findByUid(userId);
    }
}
