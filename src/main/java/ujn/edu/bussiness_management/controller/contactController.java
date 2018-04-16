package ujn.edu.bussiness_management.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ujn.edu.bussiness_management.dao.UserInfoMapper;
import ujn.edu.bussiness_management.model.*;
import ujn.edu.bussiness_management.service.IContactService;
import ujn.edu.bussiness_management.service.ICustomerService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/contact")
public class contactController {
    @Autowired
    private IContactService contactService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @RequestMapping("/loadAllContact")
    public @ResponseBody
    Map<String,Object> loadAllContact(int page,int limit){
        Long userId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
        try {
            List<Map<String,Object>> contacts = contactService.loadAllContact(userId,page,limit);
            System.out.println(contacts.size());
            int count=contactService.countLoadAllContact(userId);
            System.out.println(count);
            //转换时间格式
            for(Map<String,Object> c:contacts){
                System.out.println(c);
                if(c.get("giveDate")!=null){
                    Date gDate=(Date)c.get("giveDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.format(gDate);
                    String str=String.valueOf(gDate);
                    c.put("giveDate",str);
                }
                if(c.get("nextDate")!=null){
                    Date gDate=(Date)c.get("nextDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.format(gDate);
                    String str=String.valueOf(gDate);
                    c.put("nextDate",str);
                }
                if(c.get("belongCus")!=null){
                    Long id=Long.valueOf(String.valueOf(c.get("belongCus")));
                    Customer customer=customerService.loadCustomer(id);
                    c.put("cusName",customer.getCname());
                }
            }
            Map<String,Object> map=new HashMap();
            map.put("code",0);
            map.put("msg","");
            map.put("count",count);
            map.put("data",contacts);
            return map;
        }catch (Exception e){
            return  null;
        }
    }
    @RequestMapping("/addContact")
    public @ResponseBody Map<String,Object> addContact(Contact contact){
        Map<String,Object> data=new HashMap();
        try{
            String creator= String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userName"));
            contact.setCreator(creator);
            contact.setCreatorDate(new Date());
            //先创建用户
            Integer id=contactService.addContact(contact);
            System.out.println(contact.getId());
            UserCon userCon=new UserCon();
            userCon.setUserId(Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId"))));
            userCon.setConId(contact.getId());
            userCon.setGiveDate(new Date());
            System.out.println(userCon.getUserId()+" "+userCon.getConId()+" "+userCon.getGiveDate());
            //创建用户与客户关系
            contactService.createUserCon(userCon);
            data.put("result","SUCCESS");

            return  data;
        }catch (Exception e){
            System.out.println(e);
            return data;
        }

    }
    @RequestMapping("/conEdit")
    public  String conEdit( Long conId,Model model){
        Long userId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
        try {
            Contact contact=contactService.loadContact(conId);
            List<Map<String,Object>> cust=customerService.loadAllCustomers(userId);
            model.addAttribute("cust",cust);
            model.addAttribute("contact",contact);
            return "conEdit";
        }catch (Exception e){
            return null;
        }

    }

    @RequestMapping("/editContact")
    public @ResponseBody Map<String,Object> editContatct(Contact contact){
        Map<String,Object> data=new HashMap();
        try{
            contactService.editContact(contact);
            data.put("result","SUCCESS");
            return  data;
        }catch (Exception e){
            System.out.println(e);
            return data;
        }
    }
    @RequestMapping("/conShare")
    public String cusShare(Integer conId,Model model){
        Long userId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
        UserInfo userInfo=userInfoMapper.findByUid(userId);
        Long leaderId=userInfo.getLeaderId();
        model.addAttribute("cusId",conId);
        List<UserInfo> userInfos=userInfoMapper.findByLid(leaderId);
        model.addAttribute("userInfos",userInfos);
        return "conShare";
    }
    @RequestMapping("/subConShare")
    public  @ResponseBody Map<String, Object> subConShare(Long cusId,Long us){
        Map<String,Object> data=new HashMap();
        try{
            //先记录下分享表
            Long uId=Long.valueOf(String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("userId")));
            ConShare cusShare=new CusShare();
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
}
