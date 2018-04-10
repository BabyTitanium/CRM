package ujn.edu.bussiness_management.config;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ujn.edu.bussiness_management.dao.PermissionMapper;
import ujn.edu.bussiness_management.dao.RoleMapper;
import ujn.edu.bussiness_management.model.*;
import ujn.edu.bussiness_management.service.IRolePermissionService;
import ujn.edu.bussiness_management.service.IUserRoleService;
import ujn.edu.bussiness_management.service.IUserService;

import java.util.ArrayList;
import java.util.List;

//实现AuthorizingRealm接口用户用户认证
public class ShiroRealm extends AuthorizingRealm {

    //用于用户查询
    @Autowired
    private IUserService loginService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private RoleMapper RoleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    //角色权限和对应权限添加
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name= (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        User user = loginService.findByUsername(name);
        Long id=user.getId();
        logger.info("用户："+user.getUsername());
        UserRole userRole=userRoleService.findByUid(id);
        logger.info("角色id:"+userRole.getRoleId());
        Role role= RoleMapper.findByRoleId(userRole.getRoleId());
        logger.info("角色："+role.getrname());
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //添加角色
        simpleAuthorizationInfo.addRole(role.getrname());
        logger.info("获取角色权限");
        List<RolePermission> list=new ArrayList<>();

        try {
            list = rolePermissionService.findByRid(userRole.getRoleId());
        }catch (Exception e){

            logger.info("权限获取失败"+e.getMessage());
        }

        if(list.size()>0){
            logger.info(list.get(0).getPermissionId().toString());
        for (RolePermission rolePermission:list) {
            logger.info("权限"+rolePermission.getPermissionId().toString());
            Long pid=rolePermission.getPermissionId();
            Permission permission=permissionMapper.findByPid(pid);
                //添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
                logger.info("权限："+permission.getPermission());

        }

        }else{
            logger.info("没有找到权限");
        }
        return simpleAuthorizationInfo;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        logger.info("验证当前Subject时获取到token为：" + authenticationToken.toString());
        UsernamePasswordToken upToken = (UsernamePasswordToken)authenticationToken;
        String name = authenticationToken.getPrincipal().toString();
        try {
            User user = loginService.findByUsername(name);
            logger.info(user.getUsername());
            if (user == null) {
                //这里返回后会报出对应异常
                return null;
            } else {
                //这里验证authenticationToken和simpleAuthenticationInfo的信息
                SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getpassword(), getName());
                Session session = SecurityUtils.getSubject().getSession();
                //     User user=(User)SecurityUtils.getSubject().getPrincipal();
                session.setAttribute("userName",user.getUsername());
                session.setAttribute("user", user);//成功则放入session
                session.setAttribute("userId",user.getId());
                session.setAttribute("userName",user.getUsername());
                return simpleAuthenticationInfo;
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

}