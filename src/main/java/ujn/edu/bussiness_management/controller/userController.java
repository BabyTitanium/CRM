package ujn.edu.bussiness_management.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ujn.edu.bussiness_management.dao.CustomerMapper;
import ujn.edu.bussiness_management.dao.UserCusMapper;
import ujn.edu.bussiness_management.dao.UserInfoMapper;
import ujn.edu.bussiness_management.model.Customer;
import ujn.edu.bussiness_management.model.User;
import ujn.edu.bussiness_management.model.UserCus;
import ujn.edu.bussiness_management.model.UserInfo;
import ujn.edu.bussiness_management.service.ICustomerService;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class userController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @RequestMapping("/customer")
    public String customer(){
        return ("customer");
    }
    @RequestMapping(value = "/addCustomer",method = RequestMethod.POST)
    @Transactional
    public@ResponseBody Map<String, Object> addCustomer(Customer customer){
        Map<String,Object> data=new HashMap();
        try{
            String creator= String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userName"));
            customer.setCreator(creator);
            customer.setCreateDate(new Date());
            //先创建用户
            Integer id=customerService.addCustomer(customer);
            UserCus userCus=new UserCus();
            userCus.setUserId(Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId"))));
            userCus.setCusId(customer.getId());
            userCus.setGiveDate(new Date());
            //创建用户与客户关系
            customerService.createUserCus(userCus);
            System.out.println(customer.getId());
            data.put("result","SUCCESS");
            return  data;
        }catch (Exception e){
            System.out.println(e);
            return data;
        }
    }
    @RequestMapping("loadAllCustomer")
    public @ResponseBody Map<String,Object> loadAllCustomer(){
        Long userId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
        try {
            List<Map<String,Object>> customers = customerService.loadAllCustomer(userId);
            //转换时间格式
            for(Map<String,Object> m:customers){
                if(m.get("giveDate")!=null){

                    Date gDate=(Date)m.get("giveDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.format(gDate);
                    String str=String.valueOf(gDate);
                    m.put("giveDate",str);
                }
                if(m.get("nextDate")!=null){
                    Date gDate=(Date)m.get("nextDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.format(gDate);
                    m.put("nextDate",gDate);
                }
            }
            Map<String,Object> map=new HashMap();
            map.put("code",0);
            map.put("msg","");
            map.put("count",1000);
            map.put("data",customers);
            return map;
        }catch (Exception e){
            return  null;
        }
    }
    @RequestMapping("/contact")
    public  String contact(){
        return "contact";
    }
    @RequestMapping("/cusEdit")
    public  String cusEdit( Long cusId,Model model){
        try {
            Customer customer=customerService.loadCustomer(cusId);
            model.addAttribute("customer",customer);
            return "cusEdit";
        }catch (Exception e){
            return null;
        }

    }
    @RequestMapping("/editCustomer")
    public @ResponseBody Map<String,Object> editCustomer(Customer customer){
        Map<String,Object> data=new HashMap();
        try{
            customerService.editCustomer(customer);
            data.put("result","SUCCESS");
            return  data;
        }catch (Exception e){
            System.out.println(e);
            return data;
        }
    }
    @RequestMapping("/cusShare")
    public String cusShare(Integer cusId,Model model){
        Long userId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
        UserInfo userInfo=userInfoMapper.findByUid(userId);
        Long leaderId=userInfo.getLeaderId();
        model.addAttribute("cusId",cusId);
        List<UserInfo> userInfos=userInfoMapper.findByLid(leaderId);
        model.addAttribute("userInfos",userInfos);
        return "cusShare";
    }
    @RequestMapping("/conext")
    public String conext(Integer cusId){

        return "conext";
    }
    @RequestMapping("/editCustomer")
    public  @ResponseBody editCustomer(Long cusId,Long userId){
        Map<String,Object> data=new HashMap();
        try{
            Long userId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
            customerService.editCustomer(customer);
            data.put("result","SUCCESS");
            return  data;
        }catch (Exception e){
            System.out.println(e);
            return data;
        }
           return;
    }
}
