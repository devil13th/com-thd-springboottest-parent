package com.thd.springboottest.shiro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thd.springboottest.shiro.entity.User;
import com.thd.springboottest.shiro.service.LoginService;
import com.thd.springboottest.shiro.service.PhoneMessageToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * com.thd.springboottest.shiro.controller.LoginController
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:43
 * Description: No Description
 */
@RestController
public class LoginController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("/login")
    // url : http://127.0.0.1:8899/thd/login?userName=wsl&password=123456
    public String login(User user) {

        logger.info("login");
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();

        User realUser = loginService.getUserByName(user.getUserName());
        if(realUser == null){
            return "用户未找到";
        }


        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);


            System.out.println(" ------------------ 认证 ---------------------");
            String sessionId =  SecurityUtils.getSubject().getSession().getId().toString();
            SecurityUtils.getSubject().getSession().setAttribute("userName",user.getUserName());
            System.out.println("session id:" + sessionId);
            System.out.println("isAuthenticated:" + SecurityUtils.getSubject().isAuthenticated());
            System.out.println("Principal:" + SecurityUtils.getSubject().getPrincipal());
            System.out.println("Principal Json:" + JSON.toJSONString(SecurityUtils.getSubject().getPrincipal()));
            SecurityUtils.getSubject().getSession().setAttribute("user",user);
            System.out.println("User Json:" + JSON.toJSONString(user));
            System.out.println(SecurityUtils.getSubject().getSession().getAttribute("user"));

           // System.out.println(SecurityUtils.getSecurityManager().getSession("myShiroshiro_redis_session:" + sessionId));


            System.out.println("------------------- 权限 ---------------------");
            System.out.println(subject.hasRole("admin"));
            System.out.println(subject.hasRole("user"));
//            subject.checkPermissions("query");
//            subject.checkPermissions("add");

            System.out.println(subject.isPermitted("query"));
            System.out.println(subject.isPermitted("add"));





//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return "没有权限";
        }
        return "login success";
    }






    @RequestMapping("/logout")
    // url : http://127.0.0.1:8899/thd/logout
    public String logout() {
        System.out.println("isAuthenticated:" + SecurityUtils.getSubject().isAuthenticated());
        SecurityUtils.getSubject().logout();


        System.out.println(" ------------------ 认证 ---------------------");
        String sessionId =  SecurityUtils.getSubject().getSession().getId().toString();
        System.out.println("isAuthenticated:" + SecurityUtils.getSubject().isAuthenticated());
        System.out.println("session id:" + sessionId);
        System.out.println("Principal:" + SecurityUtils.getSubject().getPrincipal());


        return "logout success";
    }
    //注解验角色和权限
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @RequestMapping("/index")
    // url : http://127.0.0.1:8899/thd/index
    public String index() {
        return "index!";
    }






    // 使用手机号和短信验证码登录
    @RequestMapping("/pLogin")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/pLogin?phone=13800138000&code=123456
    public String pLogin(@RequestParam("phone") String phone, @RequestParam("code") String code){

        // 根据phone从session中取出发送的短信验证码，并与用户输入的验证码比较
        //String messageCode = (String) session.getAttribute(phone);

        // 模拟手机验证码 - 一般会储存在redis中 以phone为key 生成的验证码为 value
        String messageCode ="123456";



        if(code != null && !code.trim().equals("") && messageCode.equals(code)){  // 模拟验证 手机验证码
            Subject subject = SecurityUtils.getSubject();
            System.out.println(subject.isAuthenticated());
            System.out.println(subject.getPrincipal());
            User realUser = loginService.getUserByPhone(phone);
            if(realUser == null){
               return "用户未找到";
            }


            PhoneMessageToken token = new PhoneMessageToken(realUser,phone);


            try {

                subject.login(token);
                System.out.println(subject.isAuthenticated());
                System.out.println(subject.getPrincipal());

                // 多realm 认证时当多个realm都通过时 会返回成功认证的身份 - 多个身份
                PrincipalCollection principalCollection = subject.getPrincipals();

                System.out.println("------------------- 权限 ---------------------");
                System.out.println(subject.hasRole("admin"));
                System.out.println(subject.hasRole("user"));
                System.out.println(subject.isPermitted("query"));
                System.out.println(subject.isPermitted("add"));
            }catch (AuthenticationException e) {
                e.printStackTrace();
                return "账号或密码错误！";
            } catch (AuthorizationException e) {
                e.printStackTrace();
                return "没有权限";
            }



            System.out.println("登陆成功");

            Object user = subject.getPrincipal();
            return "login success";

        }else{
            return "validate code failed";
        }
    }



    @RequestMapping("/list")
    // url : http://127.0.0.1:8899/thd/list
    public String list() {
        SecurityUtils.getSubject().getSession().touch();
        SessionKey sk = new DefaultSessionKey(SecurityUtils.getSubject().getSession().getId());
        Session session = SecurityUtils.getSecurityManager().getSession(sk);
        System.out.println("attribute username:" + session.getAttribute("userName"));
        return "list!";
    }



    @RequestMapping("/showInfo")
    // url : http://127.0.0.1:8899/thd/showInfo
    public String showInfo() {
        SessionKey sk = new DefaultSessionKey(SecurityUtils.getSubject().getSession().getId());
        Session session = SecurityUtils.getSecurityManager().getSession(sk);
        System.out.println("Subject:" + SecurityUtils.getSubject());
        System.out.println("session Id:" + session.getId());
        System.out.println("principal:" + SecurityUtils.getSubject().getPrincipal());
        System.out.println("isAuthenticated:" + SecurityUtils.getSubject().isAuthenticated());
        System.out.println("attribute username:" + session.getAttribute("userName"));
        return "showInfo!";
    }


    @RequestMapping("/simpleHash")
    // url : http://127.0.0.1:8899/thd/simpleHash?pwd=pa$$word&salt=123456
    public String simpleHash(String pwd,String salt){

        int iteratorCount = 1024;
        Object result = new SimpleHash("MD5",pwd,salt,iteratorCount);
        return result.toString();
    }



    @RequestMapping("/testRedis/{username}")
    // url : http://127.0.0.1:8899/thd/testRedis/wsl
    public String testRedis(@PathVariable String username){

        System.out.println(redisTemplate.getKeySerializer());
        System.out.println(redisTemplate.getHashKeySerializer());
        System.out.println(redisTemplate.getValueSerializer());
        System.out.println(redisTemplate.getHashValueSerializer());
        User u = this.loginService.getUserByName(username);
        this.redisTemplate.opsForValue().set(username,u);


        String jsonStr = JSON.toJSONString(u, SerializerFeature.WriteClassName);
        System.out.println(jsonStr);

        try{
            User u2 = JSON.parseObject(jsonStr,User.class);
            System.out.println(u2);
        }catch(Exception e){
            e.printStackTrace();
        }



        String str = this.redisTemplate.opsForValue().get(username).toString();
        System.out.println(str);

        //User ustr = JSON.parseObject(str,User.class);

        try{
            User u3 = (User)this.redisTemplate.opsForValue().get(username);
            System.out.println(u3);
        }catch(Exception e){
            e.printStackTrace();
        }

        return "SUCCESS";
    }
}
