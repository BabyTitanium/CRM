package ujn.edu.bussiness_management.service;

import ujn.edu.bussiness_management.model.Contact;
import ujn.edu.bussiness_management.model.UserCon;

import java.util.List;
import java.util.Map;

public interface IContactService {
    Integer addContact(Contact contact);
    void  createUserCon(UserCon userCon);
    List<Map<String,Object>> loadAllContact(Long id, Integer page, Integer limit);
    int countLoadAllContact(Long id);
    Contact loadContact(Long id);
    Integer editContact(Contact contact);
}
