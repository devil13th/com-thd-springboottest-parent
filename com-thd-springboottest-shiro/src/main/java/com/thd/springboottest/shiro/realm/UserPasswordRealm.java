package com.thd.springboottest.shiro.realm;

import com.thd.springboottest.shiro.bean.ShiroPermissions;
import com.thd.springboottest.shiro.bean.ShiroRole;
import com.thd.springboottest.shiro.bean.ShiroUser;
import com.thd.springboottest.shiro.service.ShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
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
public class UserPasswordRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserPasswordRealm(){
        this.setAuthenticationTokenClass(UsernamePasswordToken.class);
    }
    @Override
    /**
     * 授权
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录用户
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

        // =============================== 使用MD5盐值加密 =============================

        logger.info("[UserPasswordRealm] doGetAuthenticationInfo");

        //1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;

        //2. 从 UsernamePasswordToken 中来获取 username
        String username = upToken.getUsername();

        //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录

        ShiroUser user = shiroService.loadUserByAccount(username);
        logger.info("从数据库中获取 username: " + username + " 所对应的用户信息.");

        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
        if(null == user){
            throw new UnknownAccountException("用户不存在!");
        }

        //5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常.
        if(user.isLocked()){
            throw new LockedAccountException("用户被锁定");
        }

        //6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        //以下信息是从数据库中获取的.
        //1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象.
        //Object principal = shiroService.loadUserByAccount(username);
        //2). credentials: 密码 - 模拟数据库中保存的密码 - 对原始密码加密后的字符串
        Object credentials = null;

        //模拟 MD5 盐值加密后的密码
//        if("admin".equals(username)){
//            credentials = "038bdaf98f2037b31f1e75b5b4c9b26e"; // 使用"admin" 盐值加密后的密码
//        }else if("user".equals(username)){
//            credentials = "098d2c478e9c11555ce2823231e02ec1"; // 使用"user" 盐值加密后的密码
//        }

        // 加密次数
        int iteratorCount = 1024;

        // 模拟数据库查询用户
        //ShiroUser u = loginService.getUserByName(username);


        // 原始密码 - 数据库中的密码
        String pwd = user.getCredential();
        // 盐值是用户名
        ByteSource salt = ByteSource.Util.bytes(username);

        // 下面是模拟数据库中保存的密码 - 对原始密码加密后的字符串

        //  MD5 盐值加密后的密码 盐值是用户名
        // credentials = new SimpleHash("MD5",pwd,salt,iteratorCount);

        //  SHA256 盐值加密后的密码 盐值是用户名
        //  credentials = new SimpleHash("sha-256",pwd,salt,iteratorCount);

        //  SHA512 盐值加密后的密码 盐值是用户名
        credentials = new SimpleHash("sha-512",pwd,salt,iteratorCount);

        // 对于上面三种方式也可以用SimpleHash子类构建  它们的构造最终还是调用的new SimpleHash(...)
//        credentials = new Md5Hash(pwd,salt,iteratorCount);
//        credentials = new Sha256Hash(pwd,salt,iteratorCount);
//        credentials = new Sha512Hash(pwd,salt,iteratorCount);



        //3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
        String realmName = getName();

        //4). 盐值。使用username作为盐值，盐值必须是唯一的
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);

        //SimpleAuthenticationInfo还有其他构造方法，比如密码加密算法等，感兴趣可以自己看
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                uId,                      //表示凭证，可以随便设，跟token里的一致就行
//                user.getEmail(),   //表示密钥如密码，你可以自己随便设，跟token里的一致就行
//                getName()
//        );
        //authenticationInfo信息交个shiro，调用login的时候会自动比较这里的token和authenticationInfo



        //将盐值作为参数传入，以便 shiro 在比对密码时使用
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, credentials, credentialsSalt, realmName);
        return info;

    }



    // MD5 盐值加密
    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        // 原始密码
        Object credentials = "123456";
        //盐值
        Object salt = ByteSource.Util.bytes("user");

        System.out.println("salt:" + salt);
        // 加密次数
        int hashIterations = 1024;

        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }

    /**
     * 用来判断是否使用当前的 realm
     * @param var1 传入的token
     * @return true就使用，false就不使用
     */
    public boolean supports(AuthenticationToken var1) {
        System.out.println("supports");
        return var1 instanceof UsernamePasswordToken;
    }
}
