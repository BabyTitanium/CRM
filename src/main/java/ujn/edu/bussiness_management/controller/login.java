package ujn.edu.bussiness_management.controller;

import com.google.code.kaptcha.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ujn.edu.bussiness_management.config.ShiroRealm;
import ujn.edu.bussiness_management.dao.MenuMapper;
import ujn.edu.bussiness_management.dao.RoleMapper;
import ujn.edu.bussiness_management.dao.RoleMenuMapper;
import ujn.edu.bussiness_management.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import ujn.edu.bussiness_management.service.IUserInfoService;
import ujn.edu.bussiness_management.service.IUserRoleService;
import ujn.edu.bussiness_management.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class login {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private RoleMapper RoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;
    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    @RequiresPermissions("index")
    @RequestMapping("/index")
    public  String index(){
        return("index");
    }
    @RequestMapping("/login")
    public String login() {
        return ("login");
    }

    @RequestMapping(value = "/userlogin")
    public  String userlogin(String username, String password, HttpServletRequest request, HttpServletResponse response,Model model){
        HttpSession session = request.getSession();
        String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println("******************验证码是: " + code + "******************");
        String parameter = request.getParameter("yzm");
        if(!parameter.equals(code)){
            model.addAttribute("message","验证码错误！");
            return "login";
        }
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);   //完成登录
            //将用户信息存放在shiro的session中
            User  user =(User)subject.getSession().getAttribute("user");
            UserInfo userInfo=userInfoService.findUserInfoByUid(user.getId());
            subject.getSession().setAttribute("realName",userInfo.getRealName());
            UserRole ur=userRoleService.findByUid(user.getId());
            Role r= RoleMapper.findByRoleId(ur.getRoleId());
            subject.getSession().setAttribute("roleName",r.getrname());
            //动态获取侧边栏
            List<RoleMenu> roleMenuList=roleMenuMapper.findByRoleId(ur.getRoleId());
            List<Menu> menuList=new ArrayList();
            for(RoleMenu roleMenu:roleMenuList){
                Long menuId=roleMenu.getMenuId();
                Menu menu=menuMapper.findMenuById(menuId);
                menuList.add(menu);
            }
            model.addAttribute("menuList",menuList);
            subject.getSession().setAttribute("menuList",menuList);
             logger.info("login完成");
            return "index";
        } catch(Exception e) {
            logger.info(e.getMessage());
            model.addAttribute("message","登录失败，请检查账号密码！");
            return "login";//返回登录页面
        }
    }

    @RequestMapping("/userlogout")
    public String logout(RedirectAttributes redirectAttributes) {
        logger.info("开始退出");
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        logger.info("安全退出");
        return "redirect:login";
    }

}
