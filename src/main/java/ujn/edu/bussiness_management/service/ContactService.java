package ujn.edu.bussiness_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujn.edu.bussiness_management.dao.ContactMapper;
import ujn.edu.bussiness_management.dao.UserConMapper;
import ujn.edu.bussiness_management.model.Contact;
import ujn.edu.bussiness_management.model.UserCon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactService implements IContactService {
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private UserConMapper userConMapper;
    @Override
    public Integer addContact(Contact contact){
        return  contactMapper.insertSelective(contact);
    }
    @Override
    public  void createUserCon(UserCon userCon){
        userConMapper.insertSelective(userCon);
    }

    @Override
    public List<Map<String,Object>> loadAllContact(Long id, Integer page, Integer limit) {
        Integer first;
        Integer last;
        first=(page-1)*limit;
        last=page*limit;
        Map<String,Object> map=new HashMap();
        map.put("id",id);
        map.put("first",first);
        map.put("last",last);
        return userConMapper.selectAllContact(map);
    }
    @Override
    public int countLoadAllContact(Long id) {

        return userConMapper.selectCountAllContact(id);
    }
    @Override
    public  Contact loadContact( Long conId ){
        return contactMapper.selectByPrimaryKey(conId);
    }
    @Override
    public  Integer editContact(Contact contact){
        return  contactMapper.updateByPrimaryKey(contact);
    }
}
