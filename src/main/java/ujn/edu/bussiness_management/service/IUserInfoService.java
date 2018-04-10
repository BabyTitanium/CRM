package ujn.edu.bussiness_management.service;

import ujn.edu.bussiness_management.dao.UserInfoMapper;
import ujn.edu.bussiness_management.model.UserInfo;

public interface IUserInfoService {
    UserInfo findUserInfoByUid(Long userId);
}
