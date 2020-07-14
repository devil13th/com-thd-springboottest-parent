package com.thd.springboottest.activiti.controller;

import com.thd.springboottest.activiti.utils.MyActivitiUtil;
import com.thd.springboottest.activiti.vo.MyTask;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * assignee、并行网关、流程变量的使用
 *
 * @author: wanglei62
 * @DATE: 2019/10/12 15:49
 **/

/*
-- 开启流程
http://127.0.0.1:8899/thd/pv/startProcess/01
返回流程实例ID：14

-- 查询待办
http://127.0.0.1:8899/thd/pv/queryTask/01
[{"taskId":"18","taskName":"assign","assignee":"zhangsan","owner":null,"processInstanceId":"14"}]

-- 设置流程变量
http://127.0.0.1:8899/thd/pv/setVariable/14

-- 查看流程变量
http://127.0.0.1:8899/thd/pv/showProcessVar/14
{"tester":"zhaoliu","developer":"wangwu","designer":"lisi","direction":"toEnd"}


-- 完成代办
http://127.0.0.1:8899/thd/pv/finishTask/18

-- 查看待办
http://127.0.0.1:8899/thd/pv/queryTask/01
[{"taskId":"27","taskName":"design","assignee":"lisi","owner":null,"processInstanceId":"14"},{"taskId":"30","taskName":"develop","assignee":"wangwu","owner":null,"processInstanceId":"14"}]

-- 完成代办
http://127.0.0.1:8899/thd/pv/finishTask/27

-- 查看待办
http://127.0.0.1:8899/thd/pv/queryTask/01
[{"taskId":"30","taskName":"develop","assignee":"wangwu","owner":null,"processInstanceId":"14"}]

-- 完成代办
http://127.0.0.1:8899/thd/pv/finishTask/30

-- 查看待办
http://127.0.0.1:8899/thd/pv/queryTask/01
[{"taskId":"35","taskName":"test","assignee":"zhaoliu","owner":null,"processInstanceId":"14"}]

-- 完成代办
http://127.0.0.1:8899/thd/pv/finishTask/35

-- 查询待办
http://127.0.0.1:8899/thd/pv/queryTask/01
[{"taskId":"41","taskName":"assign","assignee":"zhangsan","owner":null,"processInstanceId":"14"}]
 */

@Controller
@RequestMapping(value="/pv")
public class ProcessVariableController {
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
    // url: http://127.0.0.1:8899/thd/pv/startProcess/01
    @RequestMapping(value="/startProcess/{businessKey}")
    @ResponseBody
    public String startProcess(@PathVariable String businessKey){
        ProcessInstance pi = this.runtimeService.startProcessInstanceByKey("ProcessVariable",businessKey);
        return pi.getProcessInstanceId();
    }

    /**
     * 查询所有代办
     * @return
     */
    @RequestMapping(value="/queryTask/{businessKey}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/pv/queryTask/01
    public List<MyTask> queryTask(@PathVariable String businessKey){
        List<Task> t =  this.taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
        List<MyTask> mt = new ArrayList<MyTask>();
        t.forEach(item -> {
            MyTask task = new MyTask();
            task.setTaskId(item.getId());
            task.setAssignee(item.getAssignee());
            task.setProcessInstanceId(item.getProcessInstanceId());
            task.setTaskName(item.getName());
            mt.add(task);
        });

        return mt;
    }


    /**
     * 设置流程变量
     * @return
     */
    @RequestMapping(value="/setVariable/{processInstanceId}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/pv/setVariable/14
    public String setVariable(@PathVariable String processInstanceId){
        Map<String,Object> m = new HashMap<String,Object>();

        m.put("designer","lisi");
        m.put("developer","wangwu");
        m.put("tester","zhaoliu");
        m.put("direction","back");

        this.runtimeService.setVariables(processInstanceId,m);
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
     * @return
     */

    @RequestMapping(value="/finishTask/{taskId}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/pv/finishTask/18
    public String finishTask(@PathVariable String taskId){
        this.taskService.complete(taskId);
        return "SUCCESS";
    }


    /**
     * 查看流程变量
     * @param processInstanceId 流程实例ID
     * @return
     */
    // url : http://127.0.0.1:8899/thd/pv/showProcessVar/14
    @RequestMapping(value="/showProcessVar/{processInstanceId}")
    @ResponseBody
    public Map setProcessVar(@PathVariable String processInstanceId){
        Map m = this.runtimeService.getVariables(processInstanceId);
        return m;
    }



}
