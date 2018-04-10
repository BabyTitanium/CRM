package ujn.edu.bussiness_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujn.edu.bussiness_management.dao.CustomerMapper;
import ujn.edu.bussiness_management.dao.UserCusMapper;
import ujn.edu.bussiness_management.dao.UserMapper;
import ujn.edu.bussiness_management.model.Customer;
import ujn.edu.bussiness_management.model.UserCus;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    UserCusMapper userCusMapper;
    @Autowired
    UserMapper userMapper;
    public Integer addCustomer(Customer customer){
        if(customer!=null) {
            return customerMapper.addCustomer(customer);
        }
        return null;
    }
    public void createUserCus(UserCus userCus){
        if(userCus!=null)
            userCusMapper.insertSelective(userCus);
    }

    @Override
    public List<Map<String,Object>> loadAllCustomer(Long id) {
        return userCusMapper.selectAllCustomer(id);
    }
    @Override
    public  Customer loadCustomer( Long cusId){
        return customerMapper.selectCustomer(cusId);
    }
    @Override
    public  Integer editCustomer(Customer customer){
        return  customerMapper.updateCustomer(customer);
    }
    @Override
    public  List<Customer> findCusInOne(Long id){
        return userMapper.selectUserInOne(id);
    }
}
