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
import ujn.edu.bussiness_management.model.*;
import ujn.edu.bussiness_management.service.ICustomerService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    public @ResponseBody Map<String,Object> loadAllCustomer(int page,int limit){
        Long userId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
        try {
            List<Map<String,Object>> customers = customerService.loadAllCustomer(userId,page,limit);
            int count=customerService.countLoadAllCustomer(userId);
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
                    String str=String.valueOf(gDate);
                    m.put("nextDate",str);
                }

            }
            Map<String,Object> map=new HashMap();
            map.put("code",0);
            map.put("msg","");
            map.put("count",count);
            map.put("data",customers);
            return map;
        }catch (Exception e){
            return  null;
        }
    }
    @RequestMapping("/contact")
    public  String contact( Model model){
        Long userId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
        System.out.println(userId);
        List<Map<String,Object>> cust=customerService.loadAllCustomers(userId);
        model.addAttribute("cust",cust);
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
    public String conext(Integer cusId,Model model){
        model.addAttribute("cusId",cusId);
        return "conext";
    }
    @RequestMapping("/subCusShare")
    public  @ResponseBody Map<String, Object> subCusShare(Long cusId,Long us){
        Map<String,Object> data=new HashMap();
        try{
            //先记录下分享表
            Long uId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
            CusShare cusShare=new CusShare();
            cusShare.setCusId(cusId);
            cusShare.setRecUserId(us);
            cusShare.setShareUserId(uId);
            cusShare.setTime(new Date());
            customerService.subCusShare(cusShare);
            //然后在将客户分享给用户
            UserCus userCus=new UserCus();
            userCus.setUserId(us);
            userCus.setCusId(cusId);
            userCus.setGiveDate(new Date());
            customerService.createUserCus(userCus);
            data.put("result","SUCCESS");
            return  data;
        }catch (Exception e){
            System.out.println(e);
            return data;
        }
    }
    @RequestMapping("/subTime")
    public @ResponseBody Map<String,Object> subTime(Long cusId,String date){
        Map<String,Object> data=new HashMap<>();
        try{
            Long uId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
            Map<String,Object> temp=new HashMap<>();
            temp.put("cusId",cusId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(date);
        //    Date s=new Date( date);
            LocalDate s= LocalDate.parse(date);
            System.out.println(s);
            temp.put("nextTime",s);
            temp.put("userId",uId);
            customerService.setNextTime(temp);
            data.put("result","SUCCESS");

            return data;
        }catch (Exception e){
            return  null;
        }
    }
    @RequestMapping("/deleteCus")
    public  @ResponseBody  Map<String,Object> deleteCus(Long uId){
        System.out.println(uId);
        Map<String,Object> data=new HashMap<>();
        try{
            customerService.deleteCus(uId);

            data.put("result","SUCCESS");
            return  data;
        }catch (Exception e){
            return  null;
        }
    }
    @RequestMapping("/loadSharedCustomer")
    public  @ResponseBody Map<String,Object> loadSharedCustomer(int page,int limit){
        List<Map<String,Object>> data=new ArrayList<>();
        try{
            Long uId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
            data=customerService.loadSharedCustomer(uId,page,limit);
            for(Map<String,Object> m:data){
                if(m.get("sharedate")!=null){

                    Date gDate=(Date)m.get("sharedate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.format(gDate);
                    String str=String.valueOf(gDate);
                    m.put("sharedate",str);
                }

            }
            Integer count=customerService.loadCountSharedCustomer(uId);
            System.out.println(data);
            Map<String,Object> map=new HashMap();
            map.put("code",0);
            map.put("msg","");
            map.put("count",count);
            map.put("data",data);
            return map;
        }catch (Exception E){
            return  null;
        }
    }

}
