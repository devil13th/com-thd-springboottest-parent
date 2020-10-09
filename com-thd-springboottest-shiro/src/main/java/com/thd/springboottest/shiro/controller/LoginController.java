package com.thd.springboottest.shiro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thd.springboottest.shiro.bean.Message;
import com.thd.springboottest.shiro.bean.ShiroUser;
import com.thd.springboottest.shiro.service.ShiroService;
import com.thd.springboottest.shiro.token.PhoneMessageToken;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * com.thd.springboottest.shiro.controller.LoginController
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:43
 * Description: No Description
 */
@RestController
public class LoginController {

    @Autowired
    private ShiroService shiroService;
    @Autowired
    private RedisTemplate redisTemplate;


    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    public Message success(){
        return Message.success();
    }

    public Message success(String msg){
        return Message.success(msg);
    }

    public Message success(Object result){
        return Message.success(result);
    }

    public Message error(){
        return Message.error();
    }

    public Message error(String msg){
        return Message.error(msg);
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @RequestMapping("/login")
    // url : http://127.0.0.1:8899/thd/login?userName=wsl&credential=123456
    // url : http://127.0.0.1:8899/thd/login?userName=zhangsan&credential=123456
    public String login(ShiroUser user) {

        this.getLogger().info("login()");
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();

        ShiroUser realUser = shiroService.loadUserByAccount(user.getUserName());
        if(realUser == null){
            return "用户未找到";
        }


        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getCredential()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);

/*
            this.getLogger().info(" ------------------ 认证 ---------------------");
            String sessionId =  SecurityUtils.getSubject().getSession().getId().toString();
            SecurityUtils.getSubject().getSession().setAttribute("userName",user.getUserName());
            this.getLogger().info("session id:" + sessionId);
            this.getLogger().info("isAuthenticated:" + SecurityUtils.getSubject().isAuthenticated());
            this.getLogger().info("Principal:" + SecurityUtils.getSubject().getPrincipal());
            this.getLogger().info("Principal Json:" + JSON.toJSONString(SecurityUtils.getSubject().getPrincipal()));
            SecurityUtils.getSubject().getSession().setAttribute("user",user);
            SecurityUtils.getSubject().getSession().setAttribute("username",user.getUserName());
            this.getLogger().info("User Json:" + JSON.toJSONString(user));
            this.getLogger().info(SecurityUtils.getSubject().getSession().getAttribute("user").toString());

           // this.getLogger().info(SecurityUtils.getSecurityManager().getSession("myShiroshiro_redis_session:" + sessionId));


            this.getLogger().info("------------------- 权限 ---------------------");
            System.out.println(subject.hasRole("admin"));
            System.out.println(subject.hasRole("user"));
//            subject.checkPermissions("query");
//            subject.checkPermissions("add");

            System.out.println(subject.isPermitted("query"));
            System.out.println(subject.isPermitted("add"));





//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");

 */
            List perms = new ArrayList();
            perms.add("新增");
            perms.add("保存");
            perms.add("删除");
            SecurityUtils.getSubject().getSession().setAttribute("sessionPerms",perms);
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
        this.getLogger().info("isAuthenticated:" + SecurityUtils.getSubject().isAuthenticated());
        SecurityUtils.getSubject().logout();


        this.getLogger().info(" ------------------ 认证 ---------------------");
        String sessionId =  SecurityUtils.getSubject().getSession().getId().toString();
        this.getLogger().info("isAuthenticated:" + SecurityUtils.getSubject().isAuthenticated());
        this.getLogger().info("session id:" + sessionId);
        this.getLogger().info("Principal:" + SecurityUtils.getSubject().getPrincipal());


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



    @RequestMapping("/validatecode")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/validatecode
    public String validatecode(){
        // 生成6位随机数
        //String code =  UUID.randomUUID().toString().replace("-","").substring(0,6);
        // 保存到session中
        //SecurityUtils.getSubject().getSession().setAttribute("validateCode",code);



        Object code =  SecurityUtils.getSubject().getSession().getAttribute("validateCode");
        if(code == null){
            throw new RuntimeException("未生成验证码");
        }
        String validateCode = code.toString();

        return validateCode;
    }

    @RequestMapping("/getPhoneLoginCode")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/getPhoneLoginCode
    public String getPhoneLoginCode(){
        // 生成6位随机数
        String code =  UUID.randomUUID().toString().replace("-","").substring(0,6);
        // 保存到session中
        // 这里是保存到session中，实际上可以将该验证码发送至登录人的手机号，然后将手机号和验证码保存到Redis中，在Realm中可以根据手机号(pricipal)获取验证码(Credentials)就可以了
        SecurityUtils.getSubject().getSession().setAttribute("validateCode",code);

        return code;
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



        if(code != null && !code.trim().equals("")){  // 模拟验证 手机验证码
            Subject subject = SecurityUtils.getSubject();
            System.out.println(subject.isAuthenticated());
            System.out.println(subject.getPrincipal());
            ShiroUser realUser = shiroService.loadUserByPhone(phone);
            if(realUser == null){
               return "用户未找到";
            }


            PhoneMessageToken token = new PhoneMessageToken(phone,code);


            try {

                subject.login(token);
                System.out.println(subject.isAuthenticated());
                System.out.println(subject.getPrincipal());

                // 多realm 认证时当多个realm都通过时 会返回成功认证的身份 - 多个身份
                PrincipalCollection principalCollection = subject.getPrincipals();

                Session session = subject.getSession();
                System.out.println(session.getId());

                this.getLogger().info("------------------- 权限 ---------------------");
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



            this.getLogger().info("登陆成功");

            Object user = subject.getPrincipal();
            return "login success";

        }else{
            return "validate code failed";
        }
    }

    @RequestMapping("/perm/add")
//    @RequiresPermissions(value={"add"})
    @RequiresPermissions("add")
    // url : http://127.0.0.1:8899/thd/perm/add
    public String perm() {
        SecurityUtils.getSubject().getSession().touch();
        SessionKey sk = new DefaultSessionKey(SecurityUtils.getSubject().getSession().getId());
        Session session = SecurityUtils.getSecurityManager().getSession(sk);
        this.getLogger().info("attribute username:" + session.getAttribute("userName"));
        return "perm/add";
    }
    @RequestMapping("/perm/query")
    @RequiresPermissions("query")
    // url : http://127.0.0.1:8899/thd/perm/query
    public String query() {
        SecurityUtils.getSubject().getSession().touch();
        SessionKey sk = new DefaultSessionKey(SecurityUtils.getSubject().getSession().getId());
        Session session = SecurityUtils.getSecurityManager().getSession(sk);
        this.getLogger().info("attribute username:" + session.getAttribute("userName"));
        return "perm/query";
    }

    @RequiresRoles("admin")
    @RequestMapping("/role/add")
    // url : http://127.0.0.1:8899/thd/role/add
    public String roleAdd() {
        SecurityUtils.getSubject().getSession().touch();
        SessionKey sk = new DefaultSessionKey(SecurityUtils.getSubject().getSession().getId());
        Session session = SecurityUtils.getSecurityManager().getSession(sk);
        this.getLogger().info("attribute username:" + session.getAttribute("userName"));
        return "role/add";
    }




    @RequestMapping("/dynamicPerm")
    @RequiresPermissions(value={"dynamicPerm"})
    // url : http://127.0.0.1:8899/thd/dynamicPerm
    public String dynamicPerm() {
        SecurityUtils.getSubject().getSession().touch();
        SessionKey sk = new DefaultSessionKey(SecurityUtils.getSubject().getSession().getId());
        Session session = SecurityUtils.getSecurityManager().getSession(sk);
        this.getLogger().info("attribute username:" + session.getAttribute("userName"));
        return "perm/query";
    }

    @RequestMapping("/list")
    // url : http://127.0.0.1:8899/thd/list
    public String list() {
        SecurityUtils.getSubject().getSession().touch();
        SessionKey sk = new DefaultSessionKey(SecurityUtils.getSubject().getSession().getId());
        Session session = SecurityUtils.getSecurityManager().getSession(sk);
        this.getLogger().info("attribute username:" + session.getAttribute("userName"));
        return "list!";
    }


    @ResponseBody
    @RequestMapping("/unauthorizedurl")
    // url : http://127.0.0.1:8899/thd/unauthorizedurl
    public Map unauthorizedurl() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未授权,请授权!!");
        return map;
    }
    @ResponseBody
    @RequestMapping("/unlogin")
    // url : http://127.0.0.1:8899/thd/unlogin
    public Map unlogin() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录,请登录 please login!!");
        return map;
    }



    @RequestMapping("/showInfo")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/showInfo
    public Map showInfo() {
        SessionKey sk = new DefaultSessionKey(SecurityUtils.getSubject().getSession().getId());
        Session session = SecurityUtils.getSecurityManager().getSession(sk);



        this.getLogger().info("==================== Information ====================");
//        this.getLogger().info("Subject:" + JSON.toJSONString(SecurityUtils.getSubject()));
        this.getLogger().info("Session:" + JSON.toJSONString(SecurityUtils.getSubject().getSession()));
        this.getLogger().info("Principal:" + JSON.toJSONString(SecurityUtils.getSubject().getPrincipal()));
        this.getLogger().info("session keys:" + session.getAttributeKeys());

        if(null != SecurityUtils.getSubject().getPrincipal()){
            this.getLogger().info("principal:" + ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
        }
        this.getLogger().info("isAuthenticated:" + SecurityUtils.getSubject().isAuthenticated());
        this.getLogger().info("session perms:" + session.getAttribute("userName"));

        Map m = new HashMap();
        m.put("principal",JSON.toJSONString(SecurityUtils.getSubject().getPrincipal()));
        m.put("Session",JSON.toJSONString(SecurityUtils.getSubject().getSession()));
        return m;
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
        ShiroUser u = this.shiroService.loadUserByAccount(username);
        this.redisTemplate.opsForValue().set(username,u);


        String jsonStr = JSON.toJSONString(u, SerializerFeature.WriteClassName);
        this.getLogger().info(jsonStr);

        try{
            ShiroUser u2 = JSON.parseObject(jsonStr,ShiroUser.class);
            System.out.println(u2);
        }catch(Exception e){
            e.printStackTrace();
        }



        String str = this.redisTemplate.opsForValue().get(username).toString();
        this.getLogger().info(str);

        //User ustr = JSON.parseObject(str,User.class);

        try{
            ShiroUser u3 = (ShiroUser)this.redisTemplate.opsForValue().get(username);
            System.out.println(u3);
        }catch(Exception e){
            e.printStackTrace();
        }

        return "SUCCESS";
    }

    /**
     * 在shiroConfig中使用 map.put("/testRole","prems"); 配置可访问此URL的权限
     * @return
     */
    @RequestMapping("/testPerm")
    public String testPerm(){
        this.getLogger().info("principal:" + ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
        return "testPerm";
    }

    /**
     * 在shiroConfig中使用 map.put("/testRoleAdmin","prems") 配置可访问此URL的权限
     * @return
     */
    @RequestMapping("/testPermAdd")
    public String testPermAdd(){
        this.getLogger().info("principal:" + ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
        return "testPermAdd";
    }
    /**
     * 不在shiroConfig中配置可访问此URL的权限,而是在该方法中加@RequiresPermissions注释
     * @return
     */
    @RequiresPermissions("add")
    @RequestMapping("/testPermAdd2")
    public String testPermAdd2(){
        this.getLogger().info("principal:" + ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
        return "testPermAdd2";
    }

    /**
     * 在shiroConfig中使用 map.put("/testPerm","prems") 配置 可访问此URL的角色
     * @return
     */
    @RequestMapping("/testRole")
    public String testRole(){
        this.getLogger().info("principal:" + ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
        return "testRole";
    }

    /**
     * 在shiroConfig中使用 map.put("/testRoleAdmin","roles[admin]") 可访问此URL的角色
     * @return
     */
    @RequestMapping("/testRoleAdmin")
    public String testRoleAdmin(){
        this.getLogger().info("principal:" + ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
        return "testRoleAdmin";
    }

    /**
     * 不在shiroConfig中配置可访问此URL的角色,而是在该方法中加@RequiresRoles注释
     * 通过注释进行角色验证的过程使用AOP完成的 ， 而不是Filter，验证不通过可以通过定义全局异常处理器(参见ShiroExceptionHandlerController(@RestControllerAdvice注释的Controller))进行处理
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping("/testRoleAdmin2")
    public String testRoleAdmin2(){
        this.getLogger().info("principal:" + ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserName());
        return "testRoleAdmin2";
    }






}
