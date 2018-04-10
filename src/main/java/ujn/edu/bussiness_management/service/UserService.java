package ujn.edu.bussiness_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujn.edu.bussiness_management.dao.UserMapper;
import ujn.edu.bussiness_management.model.User;

import java.util.Map;

@Service
public class UserService implements IUserService{
    @Autowired
    private UserMapper userMapper;
    public User findByUsername(String userName){
        User user=userMapper.findByUsername(userName);
      //  User user=new User();
      //  user.setId(1);
       // user.setUsername("C0001");
       // user.setPassowrd("C0001");
       // System.out.println(user.getPassowrd());
        return  user;
    }
    public  User findUserLogin(Map<String,Object> map){
        System.out.println(map.get("username").toString()+map.get("password").toString());
        User user=userMapper.findUserLogin(map);
        System.out.println("----------------"+map.get("username").toString()+map.get("password").toString());
        return user;
    }
}
