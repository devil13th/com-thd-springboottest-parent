package com.thd.springboottest.activiti.controller;

import com.thd.springboottest.activiti.utils.MyActivitiUtil;
import com.thd.springboottest.activiti.vo.User;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * com.thd.springboottest.activiti.controller.TestController
 *
 * @author: wanglei62
 * @DATE: 2019/10/12 15:49
 **/


/*

-- 创建流程
http://127.0.0.1:8899/thd/subprocess/startProcess/01
流程ID：14

-- 设置流程变量
http://127.0.0.1:8899/thd/subprocess/setVariable/14

-- 查看流程变量
http://127.0.0.1:8899/thd/activiti/showProcessVar/14
{"users":[{"input":"zhangsan","audit":"zhangsanAudit"},{"input":"lisi","audit":"lisiAudit"},{"input":"wangwu","audit":"wangwuAudit"}]}

-- 查询所有代办
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:18,taskName:ASSIGN,ProcessInstanceId:14"]

-- 设置待办人
http://127.0.0.1:8899/thd/subprocess/assign/18/zhangsan

-- 查询某人代办
http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/zhangsan
["taskId:18, taskName:ASSIGN"]

-- 完成代办
http://127.0.0.1:8899/thd/subprocess/finishTask/18/zhangsan


-- 查询待办
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:54,taskName:Input Info,ProcessInstanceId:14","taskId:56,taskName:Input Info,ProcessInstanceId:14","taskId:59,taskName:Input Info,ProcessInstanceId:14"]


-- 查询某人代办
http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/zhangsan
["taskId:54, taskName:Input Info"]

http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/lisi
["taskId:56, taskName:Input Info"]

http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/wangwu
["taskId:59, taskName:Input Info"]


-- 完成代办并查询待办 - 循环子流程
http://127.0.0.1:8899/thd/subprocess/finishTask/54/zhangsan
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:56,taskName:Input Info,ProcessInstanceId:14","taskId:59,taskName:Input Info,ProcessInstanceId:14","taskId:62,taskName:Audit,ProcessInstanceId:14"]

http://127.0.0.1:8899/thd/subprocess/finishTask/56/lisi
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:59,taskName:Input Info,ProcessInstanceId:14","taskId:62,taskName:Audit,ProcessInstanceId:14","taskId:65,taskName:Audit,ProcessInstanceId:14"]

http://127.0.0.1:8899/thd/subprocess/finishTask/59/wangwu
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:62,taskName:Audit,ProcessInstanceId:14","taskId:65,taskName:Audit,ProcessInstanceId:14","taskId:68,taskName:Audit,ProcessInstanceId:14"]

-- 查询待办
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:62,taskName:Audit,ProcessInstanceId:14","taskId:65,taskName:Audit,ProcessInstanceId:14","taskId:68,taskName:Audit,ProcessInstanceId:14"]



-- 完成代办并查询待办 - 循环子流程
http://127.0.0.1:8899/thd/subprocess/finishTask/62/zhangsanAudit
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:65,taskName:Audit,ProcessInstanceId:14","taskId:68,taskName:Audit,ProcessInstanceId:14"]

http://127.0.0.1:8899/thd/subprocess/finishTask/65/lisiAudit
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:68,taskName:Audit,ProcessInstanceId:14"]

http://127.0.0.1:8899/thd/subprocess/finishTask/68/wangwuAudit
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:74,taskName:Total,ProcessInstanceId:14"]


-- 设置待办
http://127.0.0.1:8899/thd/subprocess/assign/74/zhangsan

-- 查询某人代办
http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/zhangsan
["taskId:74, taskName:Total"]

-- 完成代办
http://127.0.0.1:8899/thd/subprocess/finishTask/74/zhangsan


-- 查询所有代办
http://127.0.0.1:8899/thd/subprocess/queryTask
[]


 */

@Controller
@RequestMapping(value="/subprocess")
public class SubprocessController {
    @Autowired
    private MyActivitiUtil util;
    @Autowired
    public ProcessEngine processEngine;
    @Autowired
    public RuntimeService runtimeService;
    @Autowired
    public RepositoryService repositoryService;
    @Autowired
    public TaskService taskService;
    @Autowired
    public ManagementService managementService;
    @Autowired
    public IdentityService identityService;
    @Autowired
    public HistoryService historyService;
    @Autowired
    public FormService formService;
    public Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 开启流程
     * @return
     */
    // url: http://127.0.0.1:8899/thd/subprocess/startProcess/01
    @RequestMapping(value="/startProcess/{businessKey}")
    @ResponseBody
    public String startProcess(@PathVariable String businessKey){
        ProcessInstance pi = this.runtimeService.startProcessInstanceByKey("SubProcess",businessKey);
        return pi.getProcessInstanceId();
    }

    /**
     * 查询所有代办
     * @return
     */
    @RequestMapping(value="/queryTask")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/subprocess/queryTask
    public List<String> queryTask(){
        List<Task> t =  this.taskService.createTaskQuery().list();
        List<String> l = t.stream().map(task -> {
            return "taskId:" + task.getId() + ",taskName:" + task.getName() + ",ProcessInstanceId:" + task.getProcessInstanceId();
        }).collect(Collectors.toList());
        return l;
    }


    /**
     * 设置流程变量
     * @return
     */
    @RequestMapping(value="/setVariable/{processInstanceId}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/subprocess/setVariable/5
    public String setVariable(@PathVariable String processInstanceId){
//        List<User> users = new ArrayList<User>();
//        users.add(new User("zhangsan","zhangsanAudit"));
//        users.add(new User("lisi","lisiAudit"));
//        users.add(new User("wangwu","wangwuAudit"));
//        this.runtimeService.setVariable(processInstanceId,"users",users);


        List<Map<String,String>> users = new ArrayList<Map<String,String>>();
        Map<String,String> m = new HashMap<String,String>();
        m.put("input","zhangsan");
        m.put("audit","zhangsanAudit");

        Map<String,String> m1 = new HashMap<String,String>();
        m1.put("input","lisi");
        m1.put("audit","lisiAudit");

        Map<String,String> m2 = new HashMap<String,String>();
        m2.put("input","wangwu");
        m2.put("audit","wangwuAudit");
        users.add(m);
        users.add(m1);
        users.add(m2);
        this.runtimeService.setVariable(processInstanceId,"users",users);
        return "SUCCESS";
    }


    /**
     * 给某个任务添加人
     * @param taskId
     * @param user
     * @return
     */
    @RequestMapping(value="/assign/{taskId}/{user}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/subprocess/assign/2513/zhangsan
    public String assign(@PathVariable String taskId,@PathVariable String user){
        this.util.addCandidateUserToTask(taskId,user);
        return "SUCCESS";
    }

    /**
     * 查询某人任务
     * @param user
     * @return
     */
    @RequestMapping(value="/queryTaskByUser/{user}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/zhangsan
    public List<String> queryTaskByUser(@PathVariable String user){
        List<Task> t =  this.taskService.createTaskQuery().taskCandidateUser(user).list();
        List<Task> t2 =  this.taskService.createTaskQuery().taskAssignee(user).list();
        t.addAll(t2);
        List<String> l = t.stream().map(task -> {
            return "taskId:" + task.getId() + ", taskName:" + task.getName();
        }).collect(Collectors.toList());
        return l;
    }



    /**
     * 完成代办
     * @param taskId 待办ID
     * @param user
     * @return
     */

    @RequestMapping(value="/finishTask/{taskId}/{user}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/subprocess/finishTask/2513/zhangsan
    public String finishTask(@PathVariable String taskId,@PathVariable String user){
        this.taskService.complete(taskId);
        return "SUCCESS";
    }


    /**
     * 查看流程变量
     * @param processInstanceId 流程实例ID
     * @return
     */
    // url : http://127.0.0.1:8899/thd/activiti/showProcessVar/2509
    @RequestMapping(value="/showProcessVar/{processInstanceId}")
    @ResponseBody
    public Map setProcessVar(@PathVariable String processInstanceId){
        Map m = this.runtimeService.getVariables(processInstanceId);
        return m;
    }

    /**
     * 设置流程变量
     * @param processInstanceId 流程实例ID
     * @param varName 流程变量名称
     * @param varValue 流程变量值
     * @return
     */
    @RequestMapping(value="/setProcessVar/{processInstanceId}/{varName}/{varValue}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/activiti/setProcessVar/2509/a/b
    public String setProcessVar(@PathVariable String processInstanceId,@PathVariable String varName,@PathVariable String varValue){
        this.runtimeService.setVariableLocal(processInstanceId,varName,varValue);
        return "SUCCESS";
    }

}
