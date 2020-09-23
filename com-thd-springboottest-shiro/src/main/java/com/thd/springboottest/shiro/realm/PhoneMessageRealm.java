package com.thd.springboottest.shiro.realm;

import com.thd.springboottest.shiro.bean.ShiroPermissions;
import com.thd.springboottest.shiro.bean.ShiroRole;
import com.thd.springboottest.shiro.bean.ShiroUser;
import com.thd.springboottest.shiro.service.ShiroService;
import com.thd.springboottest.shiro.token.PhoneMessageToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * com.thd.springboottest.shiro.service.CustomRealm
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:27
 * Description: No Description
 */
public class PhoneMessageRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public PhoneMessageRealm(){
        this.setAuthenticationTokenClass(PhoneMessageToken.class);
    }
    @Override
    /**
     * 授权
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户信息
        ShiroUser user = (ShiroUser)principalCollection.getPrimaryPrincipal();
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (ShiroRole role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (ShiroPermissions permissions : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    /**
     * 认证
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        logger.info("[PhoneMessageRealm] doGetAuthenticationInfo");
        PhoneMessageToken token = null;

        // 如果是PhoneToken，则强转，获取phone；否则不处理。
        // 如果该类添加了 supports 方法则不用判断(模板模式中，已经事先调用supports进行了判断，如果符合的token才会进入到本代码方法)
        if(authenticationToken instanceof PhoneMessageToken){
            token = (PhoneMessageToken) authenticationToken;
        }else{
            return null;
        }

        // 手机验证码
        String validateCode = (String) token.getCredentials();

        // 通过手机号获取用户
        ShiroUser user = shiroService.loadUserByPhone(token.getPrincipal().toString());

        if (user == null) {
            throw new UnknownAccountException("手机号不存在!");
        }

        // 从session中获取 手机验证码
        String code = SecurityUtils.getSubject().getSession().getAttribute("validateCode").toString();

        return new SimpleAuthenticationInfo(user, code, this.getName());

    }

    /**
     * 用来判断是否使用当前的 realm
     * @param var1 传入的token
     * @return true就使用，false就不使用
     */
    public boolean supports(AuthenticationToken var1) {
        System.out.println("supports");
        return var1 instanceof PhoneMessageToken;
    }


}
