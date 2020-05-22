package com.thd.springboottest.shiro.configuration;

import com.thd.springboottest.shiro.dao.MySessionDao;
import com.thd.springboottest.shiro.filter.MyAnonFilter;
import com.thd.springboottest.shiro.filter.MyAuthcFilter;
import com.thd.springboottest.shiro.filter.MyPermsFilter;
import com.thd.springboottest.shiro.listener.MyListener;
import com.thd.springboottest.shiro.service.MySessionManager;
import com.thd.springboottest.shiro.service.UserPasswordRealm;
import com.thd.springboottest.shiro.service.PhoneMessageRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * com.thd.springboottest.shiro.configuration.ShiroConfig
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:41
 * Description: No Description
 */
@Configuration
public class ShiroConfig {
//    //不加这个注解不生效，具体不详
//    @Bean
//    @ConditionalOnMissingBean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
//        defaultAAP.setProxyTargetClass(true);
//        return defaultAAP;
//    }
//
//    //将自己的验证方式加入容器
//    @Bean
//    public CustomRealm myShiroRealm() {
//        CustomRealm customRealm = new CustomRealm();
//        return customRealm;
//    }
//
//    //权限管理，配置主要是Realm的管理认证
//    @Bean
//    public SecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(myShiroRealm());
//        return securityManager;
//    }
//
//    //Filter工厂，设置对应的过滤条件和跳转条件
//    @Bean
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        Map<String, String> map = new HashMap<>();
//        //登出
//        map.put("/logout", "logout");
//        //对所有用户认证
//        map.put("/**", "authc");
//        //登录
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        //首页
//        shiroFilterFactoryBean.setSuccessUrl("/index");
//        //错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
//        return shiroFilterFactoryBean;
//    }
//
//    //加入注解的使用，不加入这个注解不生效
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }


    @Bean
    @ConditionalOnMissingBean
    /**
     * 开启shiro 注解模式 原理是使用beanpostprocess进行代理
     * 可以在controller中的方法前加上注解
     * 如 @RequiresPermissions("userInfo:add")
     * @param securityManager
     * @return
     */
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }



    @Bean
    /**
     * 开启shiro 注解模式 原理是使用beanpostprocess进行代理
     * 可以在controller中的方法前加上注解
     * 如 @RequiresPermissions("userInfo:add")
     * @param securityManager
     * @return
     */
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor
                = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    UserPasswordRealm userPasswordRealm() {
        UserPasswordRealm realm= new UserPasswordRealm();
        realm.setCredentialsMatcher(matcher());
        return realm;
    }

    @Bean
    PhoneMessageRealm phoneMessageRealm() {
        PhoneMessageRealm realm= new PhoneMessageRealm();
        return realm;
    }

    @Bean
    /**
     * 密码匹配器
     */
    CredentialsMatcher matcher(){


        // 使用MD5加密
        //HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("MD5");

        // 使用SHA256加密
        //HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("sha-256");

        // 使用SHA512加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("sha-512");

        // 或者使用子类


        // 加密1024次 迭代
        matcher.setHashIterations(1024);

        return matcher;
    }


    /**
     * cookie对象;会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能,rememberMe管理器
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.getDecoder().decode("4AvVhmFLUs0KTA3Kprsdag=="));

        return cookieRememberMeManager;
    }


    /**
     * shiro缓存管理器;
     * 需要添加到securityManager中
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());

        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);

        //redis中针对不同用户缓存
//        redisCacheManager.setPrincipalIdFieldName("username");
        // 设置principal 的标识字段 - 要有setter getter
        redisCacheManager.setPrincipalIdFieldName("id");
        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1");
        redisManager.setPort(6379);
        //redisManager.setPassword("123456");
        return redisManager;
    }

    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        //session在redis中的保存时间,最好大于session会话超时时间
//        redisSessionDAO.setExpire(12000);
//        return redisSessionDAO;

        MySessionDao sessionDao = new MySessionDao();
        return sessionDao;
    }


    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("myshiro.thdsession");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    /**
     * 配置会话管理器，设定会话超时及保存
     * @return
     */
    @Bean("sessionManager")
    public SessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        DefaultWebSessionManager sessionManager = new MySessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setCacheManager(cacheManager());

        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        sessionManager.setGlobalSessionTimeout(1800000);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
        sessionManager.setSessionValidationInterval(3600000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;

    }

    @Bean
    public SessionListener sessionListener(){
        return new MyListener();
    }

    /**
     * 配置session监听
     * @return
     */
//    @Bean("sessionListener")
//    public ShiroSessionListener sessionListener(){
//        ShiroSessionListener sessionListener = new ShiroSessionListener();
//        return sessionListener;
//    }

    @Bean
    SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用单一realm
        //manager.setRealm(myRealm());

        // 使用多realm
        List realms = new ArrayList();
        realms.add(phoneMessageRealm());
        realms.add(userPasswordRealm());
        manager.setRealms(realms);


        //配置记住我
        manager.setRememberMeManager(rememberMeManager());

        //配置redis缓存
        manager.setCacheManager(cacheManager());
        //配置自定义session管理，使用redis
        manager.setSessionManager(sessionManager());

        return manager;
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        // 可以添加自己定义的过滤器
        /*
        简写(加粗为常用)		名称			优先级(1为最高)	说明	对应Java类
        anon				匿名拦截器		1				不需要登录就能访问,一般用于静态资源,或者移动端接口	org.apache.shiro.web.filter.authc.AnonymousFilter
        authc				登录拦截器		2				需要登录认证才能访问的资源	org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        authcBasic			Http拦截器		3				Http身份验证拦截器,非常用类型,不太了解	org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
        logout				登出拦截器		4				用户登出拦截器,主要属性:redirectURL退出登录后重定向的地址	org.apache.shiro.web.filter.authc.LogoutFilter
        noSessionCreation	不创建会话拦截器	5				调用 subject.getSession(false) 不会有什么问题，但是如果 subject.getSession(true) 将抛出 DisabledSessionException 异常	org.apache.shiro.web.filter.authc.NoSessionCreationFilter
        prems				权限拦截器		6				验证用户是否拥有资源权限	org.apache.shiro.web.filter.authc.PermissionsAuthorizationFilter
        port				端口拦截器		7				其主要属性: port(80) 如果用户访问该页面是非 80，将自动将请求端口改为 80 并重定向到该 80 端口	org.apache.shiro.web.filter.authc.PortFilter
        rest				rest风格拦截器	8				rest 风格拦截器，自动根据请求方法构建权限字符串构建权限字符串；非常用类型拦截器	org.apache.shiro.web.filter.authc.HttpMethodPermissionFilter
        roles				角色拦截器		9				验证用户是否拥有资源角色	org.apache.shiro.web.filter.authc.RolesAuthorizationFilter
        ssl					SSL拦截器		10				只有请求协议是https才能通过,否则你会自动跳转到https端口(443)	org.apache.shiro.web.filter.authc.SslFilter
        user				用户拦截器		11				用户拦截器，用户已经身份验证 / 记住我登录的都可；	org.apache.shiro.web.filter.authc.UserFilter
        */
        Map<String, Filter> filters = bean.getFilters();
        filters.put("anon", new MyAnonFilter());
        filters.put("authc", new MyAuthcFilter());
        filters.put("prems", new MyPermsFilter());
        bean.setFilters(filters);


        bean.setSecurityManager(securityManager());
        //注意过滤器配置顺序 不能颠倒

        // 未登录时跳转到的地址
        bean.setLoginUrl("/unlogin");

        //登录成功后的跳转地址
        bean.setSuccessUrl("/index");
        // 未授权时跳转到的地址
        bean.setUnauthorizedUrl("/unauthorizedurl");


        Map<String, String> map = new LinkedHashMap<>();

        //配置不会被拦截的地址 顺序判断，在 ShiroConfiguration 中的 shiroFilter 处配置了 /ajaxLogin=anon，意味着可以不需要认证也可以访问

        // 配置url对应的拦截器(与上面定义的拦截器名称对应)

        //map.put("/list", "anon");
        map.put("/logout", "anon");
        map.put("/login", "anon");
        map.put("/doLogin", "anon");
        map.put("/simpleHash","anon");
        map.put("/pLogin","anon");
        map.put("/showInfo","anon");
        map.put("/unauthorizedurl","anon");

        map.put("/testRedis/*","anon");
        map.put("/perm/*","anon,authc,prems[admin,sss]");
        map.put("/dynamicPerm","prems");
        map.put("/**", "authc");


        bean.setFilterChainDefinitionMap(map);


        return bean;
    }
}
