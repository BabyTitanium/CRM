package ujn.edu.bussiness_management.service;

import ujn.edu.bussiness_management.model.CusShare;
import ujn.edu.bussiness_management.model.Customer;
import ujn.edu.bussiness_management.model.UserCus;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    Integer addCustomer(Customer customer);
    void  createUserCus(UserCus userCus);
    List<Map<String,Object>> loadAllCustomer(Long id,Integer page,Integer limit);

    int countLoadAllCustomer(Long id);
    Customer loadCustomer(Long id);
    Integer editCustomer(Customer customer);
    List<Customer> findCusInOne(Long id);
    void subCusShare(CusShare cusShare);
    void  setNextTime(Map<String,Object> map);
    void deleteCus(Long id);
    List<Map<String,Object>> loadSharedCustomer(Long id,Integer page,Integer limit);
    Integer  loadCountSharedCustomer(Long id);
    List<Map<String,Object>> loadAllCustomers(Long id);
}
