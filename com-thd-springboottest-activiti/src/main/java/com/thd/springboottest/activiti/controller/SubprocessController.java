package com.thd.springboottest.activiti.controller;

import com.thd.springboottest.activiti.utils.MyActivitiUtil;
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
流程ID：8

-- 设置流程变量
http://127.0.0.1:8899/thd/subprocess/setVariable/8

-- 查看流程变量
http://127.0.0.1:8899/thd/activiti/showProcessVar/8
{"users":["zhangsan","lisi","wangwu"]}

-- 查询所有代办
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:12,taskName:ASSIGN,ProcessInstanceId:8"]

-- 设置待办人
http://127.0.0.1:8899/thd/subprocess/assign/12/zhangsan

-- 查询某人代办
http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/zhangsan
["taskId:12, taskName:ASSIGN"]

-- 完成代办
http://127.0.0.1:8899/thd/subprocess/finishTask/12/zhangsan


-- 查询待办
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:42,taskName:Input Info,ProcessInstanceId:8","taskId:44,taskName:Input Info,ProcessInstanceId:8","taskId:47,taskName:Input Info,ProcessInstanceId:8"]


-- 查询某人代办
http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/zhangsan
["taskId:42, taskName:Input Info"]

http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/lisi
["taskId:44, taskName:Input Info"]

http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/wangwu
["taskId:47, taskName:Input Info"]


-- 完成代办并查询待办 - 循环子流程
http://127.0.0.1:8899/thd/subprocess/finishTask/42/zhangsan
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:44,taskName:Input Info,ProcessInstanceId:8","taskId:47,taskName:Input Info,ProcessInstanceId:8","taskId:50,taskName:Audit,ProcessInstanceId:8"]

http://127.0.0.1:8899/thd/subprocess/finishTask/44/lisi
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:47,taskName:Input Info,ProcessInstanceId:8","taskId:50,taskName:Audit,ProcessInstanceId:8","taskId:52,taskName:Audit,ProcessInstanceId:8"]

http://127.0.0.1:8899/thd/subprocess/finishTask/47/wangwu
["taskId:50,taskName:Audit,ProcessInstanceId:8","taskId:52,taskName:Audit,ProcessInstanceId:8","taskId:54,taskName:Audit,ProcessInstanceId:8"]

-- 查询待办
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:50,taskName:Audit,ProcessInstanceId:8","taskId:52,taskName:Audit,ProcessInstanceId:8","taskId:54,taskName:Audit,ProcessInstanceId:8"]



-- 完成代办并查询待办 - 循环子流程
http://127.0.0.1:8899/thd/subprocess/finishTask/50/zhangsan
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:52,taskName:Audit,ProcessInstanceId:8","taskId:54,taskName:Audit,ProcessInstanceId:8"]

http://127.0.0.1:8899/thd/subprocess/finishTask/52/lisi
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:54,taskName:Audit,ProcessInstanceId:8"]

http://127.0.0.1:8899/thd/subprocess/finishTask/54/wangwu
http://127.0.0.1:8899/thd/subprocess/queryTask
["taskId:59,taskName:Total,ProcessInstanceId:8"]


-- 设置待办
http://127.0.0.1:8899/thd/subprocess/assign/59/zhangsan

-- 查询某人代办
http://127.0.0.1:8899/thd/subprocess/queryTaskByUser/zhangsan

-- 完成代办
http://127.0.0.1:8899/thd/subprocess/finishTask/59/zhangsan


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
        List<String> users = new ArrayList<String>();
        users.add("zhangsan");
        users.add("lisi");
        users.add("wangwu");

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
