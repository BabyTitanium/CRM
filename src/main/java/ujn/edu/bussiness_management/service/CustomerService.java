package ujn.edu.bussiness_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ujn.edu.bussiness_management.dao.CustomerMapper;
import ujn.edu.bussiness_management.dao.UserCusMapper;
import ujn.edu.bussiness_management.dao.UserMapper;
import ujn.edu.bussiness_management.model.CusShare;
import ujn.edu.bussiness_management.model.Customer;
import ujn.edu.bussiness_management.model.UserCus;

import java.util.HashMap;
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
    public List<Map<String,Object>> loadAllCustomer(Long id,Integer page,Integer limit) {
        Integer first;
        Integer last;
        first=(page-1)*limit;
        last=page*limit;
        Map<String,Object> map=new HashMap();
        map.put("id",id);
        map.put("first",first);
        map.put("last",last);
        return userCusMapper.selectAllCustomer(map);
    }
    @Override
    public int countLoadAllCustomer(Long id) {

        return userCusMapper.selectCountAllCustomer(id);
    }
    @Override
    public  Customer loadCustomer( Long cusId ){
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
    @Override
    public void subCusShare(CusShare cusShare){
        customerMapper.createCusShare(cusShare);
    }
    @Override
    public  void  setNextTime(Map<String,Object> map){
        userCusMapper.updateNextTime(map);
    }
    @Override
    public  void deleteCus(Long id){
        userCusMapper.deleteUserCus(id);
    }
    @Override
    public  List<Map<String,Object>> loadSharedCustomer(Long id,Integer page,Integer limit){
        Integer first;
        Integer last;
        first=(page-1)*limit;
        last=page*limit;
        Map<String,Object> map=new HashMap();
        map.put("id",id);
        map.put("first",first);
        map.put("last",last);
        return  userCusMapper.selectSharedCustomer(map);
    }
    @Override
    public Integer  loadCountSharedCustomer(Long id){
        return userCusMapper.selectCountSharedCustomer(id);
    }
    @Override
    public List<Map<String,Object>> loadAllCustomers(Long id){
        return userCusMapper.selectCustomers(id);
    }
}
