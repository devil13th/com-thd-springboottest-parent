package com.thd.springboottest.mybatis.controller;

//import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInfo;
import com.thd.springboottest.mybatis.dao.SysUserMapper;
import com.thd.springboottest.mybatis.entity.MyUser;
import com.thd.springboottest.mybatis.entity.SysUser;
import com.thd.springboottest.mybatis.service.MyUserService;
import com.thd.springboottest.mybatis.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/mybatis")
public class MybatisController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MyUserService myUserService;


    @Autowired
    private SysUserMapper sysUserMapper;


    @ResponseBody
    @RequestMapping(value="/index",method= RequestMethod.GET)
    //http://127.0.0.1:8899/thd/mybatis/index
    public String index(){
        this.log.info("index");
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/queryAll")
    //http://127.0.0.1:8899/thd/mybatis/queryAll
    public List queryAll(){
        return sysUserService.getAll();
    }

    @ResponseBody
    @RequestMapping(value="/insert")
    //http://127.0.0.1:8899/thd/mybatis/insert
    public String insert(){
        SysUser user = new SysUser();
        String id = UUID.randomUUID().toString();
        user.setUserId(id);
        user.setUserName("n_" + id);
        user.setUserMail("tt@163.com");
        user.setUserBirthday(new Date());
        user.setUserSex(1);
        sysUserService.insert(user);
        return "success";
    }


    @ResponseBody
    @RequestMapping(value="/saveMyUser")
    //http://127.0.0.1:8899/thd/mybatis/saveMyUser
    public String saveMyUser(){
        MyUser user = new MyUser();
        String id = UUID.randomUUID().toString();
        user.setUserId(id);
        user.setUserName("ZHANGSAN");
        user.setUserAge(new Random().nextInt(50));
        user.setUserBirthday(new Date());
        user.setUserCreateTime(new Date());
        myUserService.save(user);
        return "success";
    }


    @ResponseBody
    @RequestMapping(value="/updateMyUser/{id}")
    //http://127.0.0.1:8899/thd/mybatis/updateMyUser/1
    public ResponseEntity updateMyUser(@PathVariable String id){
        MyUser user = new MyUser();
        user.setUserId(id);
        user.setUserName("dev");
        user.setUserAge(new Random().nextInt(50));
        user.setUserBirthday(new Date());
        user.setUserCreateTime(new Date());
        int i = myUserService.update(user);
        return ResponseEntity.ok(i);
    }


    @ResponseBody
    @RequestMapping(value="/queryList")
    //http://127.0.0.1:8899/thd/mybatis/queryList
    public List queryList(){
        MyUser user = new MyUser();
        user.setUserName("ZHANGSAN1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            user.setUserBirthday(sdf.parse("2019-10-27"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return myUserService.queryList(user);
    }

    @ResponseBody
    @RequestMapping(value="/queryListByLike")
    //http://127.0.0.1:8899/thd/mybatis/queryListByLike
    public List queryListByLike(){
        MyUser user = new MyUser();
        user.setUserName("%Z%");
        return myUserService.queryListByLike(user);
    }


    @ResponseBody
    @RequestMapping(value="/deleteById/{id}")
    //http://127.0.0.1:8899/thd/mybatis/deleteById/1
    public ResponseEntity deleteById(@PathVariable String id){
        int i = myUserService.deleteById(id);
        return ResponseEntity.ok(i);
    }

    @ResponseBody
    @RequestMapping(value="/queryById/{id}")
    //http://127.0.0.1:8899/thd/mybatis/queryById/1
    public ResponseEntity queryMyUserById(@PathVariable String id){
        MyUser mu = myUserService.queryById(id);
        return ResponseEntity.ok(mu);
    }


    @ResponseBody
    @RequestMapping(value="/queryJoin/{name}/{sex}")
    //http://127.0.0.1:8899/thd/mybatis/queryJoin/ZHANGSAN/1
    public ResponseEntity queryJoin(@PathVariable String name,@PathVariable String sex){
        Map m = new HashMap();
        m.put("name",name);
        m.put("sex",sex);
        List l = myUserService.queryJoin(m);
        return ResponseEntity.ok(l);
    }


    // ------------------------------------- 以下是一些复杂Mapper例子 -------------------------- //

    /**
     * 返回 List<Map> 类型结果
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/selectAllForMap")
    //http://127.0.0.1:8899/thd/mybatis/selectAllForMap
    public ResponseEntity selectAllForMap(){
        List<Map<String,String>> l = this.sysUserMapper.selectAllForMap();
        System.out.println(l);
        return ResponseEntity.ok(l);
    }


    /**
     * 批量插入
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insertBatch")
    //http://127.0.0.1:8899/thd/mybatis/insertBatch
    public ResponseEntity insertBatch(){
        int ct = 1000;
        List<SysUser>  l = new ArrayList<SysUser>(ct);
        int i = 0;
        while(i<ct){
            SysUser su = new SysUser();
            su.setUserId(UUID.randomUUID().toString().replace("-",""));
            su.setUserBirthday(new Date());
            su.setUserMail("user" + i + "@163.com");
            su.setUserName("user_" + i);
            su.setUserSex(1);
            su.setUserStatus("1");
            su.setUserTel("12345678");
            l.add(su);
            i++;
        }
        this.sysUserMapper.insertBatch(l);
        return ResponseEntity.ok("SUCCESS");
    }

    /**
     * in 查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/selectin")
    //http://127.0.0.1:8899/thd/mybatis/selectin
    public ResponseEntity selectin(){
        List<String> param = new ArrayList<String>();
        param.add("user_1");
        param.add("user_2");
        param.add("user_3");
        param.add("user_4");
        param.add("user_5");
        List<SysUser> l = this.sysUserMapper.selectin(param);
        return ResponseEntity.ok(l);
    }


    /**
     * 返回map, key为指定属性，value为实体类结果集
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/selectAllForMapKey")
    //http://127.0.0.1:8899/thd/mybatis/selectAllForMapKey
    public ResponseEntity selectAllForMapKey(){
        Map<String,SysUser> m = this.sysUserMapper.selectAllForMapKey();
        System.out.println(m);
        return ResponseEntity.ok(m);
    }


    /**
     * 统计查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/queryCount")
    //http://127.0.0.1:8899/thd/mybatis/queryCount
    public ResponseEntity queryCount(){
        Integer count = this.sysUserMapper.queryCount("1");
        System.out.println(count);
        return ResponseEntity.ok(count);
    }


    /**
     * 测试分页插件
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/queryByName/{name}/{page}/{pageSize}")
    //http://127.0.0.1:8899/thd/mybatis/queryByName/{name}/{page}/{pageSize}
    public ResponseEntity queryByName(@PathVariable String name,@PathVariable int page,@PathVariable int pageSize){
        PageInfo<SysUser> pi = this.sysUserService.queryByNamePage(name,pageSize,page);
        return ResponseEntity.ok(pi);
    }





}
