package com.shiro;

import com.entity.Login;
import com.service.LoginService;
import com.service.UserPermService;
import com.service.UserRoleService;
import com.util.JustPhone;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserPermService userPermsService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private LoginService loginService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        Subject subject= SecurityUtils.getSubject();
        Login login =(Login) subject.getPrincipal();
        Integer permId = userRoleService.LookUserRoleId(login.getUserid());
        List<String> userPerms=userPermsService.LookPermsByUserid(permId);
        info.addStringPermissions(userPerms);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        Login login = new Login();
        if (!JustPhone.justPhone(token.getUsername())) {
            login.setUsername(token.getUsername());
        }else {
            login.setMobilephone(token.getUsername());
        }
        Login Login1=loginService.userLogin(login);
        if(Login1==null){

            return null;
        }
        return new SimpleAuthenticationInfo(Login1,Login1.getPassword(),"");
    }
}
