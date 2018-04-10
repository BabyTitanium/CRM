package ujn.edu.bussiness_management.service;

import org.springframework.stereotype.Service;
import ujn.edu.bussiness_management.model.User;

import java.util.Map;

public interface IUserService {
    public User findByUsername(String userName);
    public  User findUserLogin(Map<String,Object> map);
}
