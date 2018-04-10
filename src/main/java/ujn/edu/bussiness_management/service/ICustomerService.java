package ujn.edu.bussiness_management.service;

import ujn.edu.bussiness_management.model.Customer;
import ujn.edu.bussiness_management.model.UserCus;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    Integer addCustomer(Customer customer);
    void  createUserCus(UserCus userCus);
    List<Map<String,Object>> loadAllCustomer(Long id);
    Customer loadCustomer(Long id);
    Integer editCustomer(Customer customer);
    List<Customer> findCusInOne(Long id);
}
