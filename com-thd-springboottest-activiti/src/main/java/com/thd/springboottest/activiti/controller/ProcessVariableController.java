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
 *  流程变量结论:
 *
 *  local的流程变量按照执行ID(executionId),待办ID设置后  是从流程、执行ID、待办ID往下覆盖的
 *      task的localVariable 只能根据taskId查看
 *      execution的localVariable 只能executionId查看
 *
 *  全局的流程变量按照执行ID(executionId),待办ID(TaskId)设置后 ,可以根据流程，执行ID和TaskId查看
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


--------- 开始测试流程变量

-- 设置全局的task变量
http://127.0.0.1:8899/thd/pv/saveTaskVar/31/taskAGloab/taskA_global
http://127.0.0.1:8899/thd/pv/saveTaskVar/34/taskBGloab/taskB_global

-- 设置局部的task变量
http://127.0.0.1:8899/thd/pv/saveTaskLocalVar/31/taskALocal/taskA_local
http://127.0.0.1:8899/thd/pv/saveTaskLocalVar/34/taskBLocal/taskB_local

-- 设置全局的execution变量
http://127.0.0.1:8899/thd/pv/saveProcessVar/15/executeAGloab/executeA_global
http://127.0.0.1:8899/thd/pv/saveProcessVar/29/executeBGloab/executeB_global

-- 设置局部的execution变量
http://127.0.0.1:8899/thd/pv/saveProcessLocalVar/15/executeALocal/executeA_local
http://127.0.0.1:8899/thd/pv/saveProcessLocalVar/29/executeBLocal/executeB_local

-- 查看全局的流程变量
http://127.0.0.1:8899/thd/pv/showProcessVar/14
{"taskAGloab":"taskA_global","tester":"zhaoliu","executeBGloab":"executeB_global","developer":"wangwu","designer":"lisi","taskBGloab":"taskB_global","executeAGloab":"executeA_global","direction":"back"}

-- 查看execution局部变量
http://127.0.0.1:8899/thd/pv/showProcessVar/15
{"taskAGloab":"taskA_global","executeALocal":"executeA_local","tester":"zhaoliu","executeBGloab":"executeB_global","developer":"wangwu","designer":"lisi","taskBGloab":"taskB_global","executeAGloab":"executeA_global","direction":"back"}

-- 查看execution局部变量
http://127.0.0.1:8899/thd/pv/showProcessVar/29
{"executeBLocal":"executeB_local","taskAGloab":"taskA_global","tester":"zhaoliu","executeBGloab":"executeB_global","developer":"wangwu","designer":"lisi","taskBGloab":"taskB_global","executeAGloab":"executeA_global","direction":"back"}

-- 查看task局部变量
http://127.0.0.1:8899/thd/pv/showTaskVar/31
{"taskALocal":"taskA_local"}

http://127.0.0.1:8899/thd/pv/showTaskVar/34
{"taskBLocal":"taskB_local"}


--------- 测试流程变量结束



-- 完成代办
http://127.0.0.1:8899/thd/pv/finishTask/31

-- 查看待办
http://127.0.0.1:8899/thd/pv/queryTask/01
[{"taskId":"30","taskName":"develop","assignee":"wangwu","owner":null,"processInstanceId":"14"}]

-- 完成代办
http://127.0.0.1:8899/thd/pv/finishTask/34

-- 查看待办
http://127.0.0.1:8899/thd/pv/queryTask/01
[{"taskId":"35","taskName":"test","assignee":"zhaoliu","owner":null,"processInstanceId":"14"}]

-- 完成代办
http://127.0.0.1:8899/thd/pv/finishTask/47

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
     * 测试不同的流程变量设置
     * @return
     */
    @RequestMapping(value="/saveProcessVar/{processInstanceId}/{name}/{value}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/pv/saveProcessVar/14
    public String saveProcessVar(@PathVariable String processInstanceId,@PathVariable String name,@PathVariable String value){
        this.runtimeService.setVariable(processInstanceId,name,value);
        return "SUCCESS";
    }

    /**
     * 测试不同的流程变量设置
     * @return
     */
    @RequestMapping(value="/saveProcessLocalVar/{processInstanceId}/{name}/{value}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/pv/saveProcessLocalVar/14
    public String saveProcessLocalVar(@PathVariable String processInstanceId,@PathVariable String name,@PathVariable String value){
        this.runtimeService.setVariableLocal(processInstanceId,name,value);
        return "SUCCESS";
    }





    /**
     * 测试不同的流程变量设置
     * @return
     */
    @RequestMapping(value="/saveTaskVar/{taskId}/{name}/{value}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/pv/saveTaskVar/14
    public String saveTaskVar(@PathVariable String taskId,@PathVariable String name,@PathVariable String value){
        this.taskService.setVariable(taskId,name,value);
        return "SUCCESS";
    }
    /**
     * 测试不同的流程变量设置
     * @return
     */
    @RequestMapping(value="/saveTaskLocalVar/{taskId}/{name}/{value}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/pv/saveTaskLocalVar/27
    public String saveTaskLocalVar(@PathVariable String taskId,@PathVariable String name,@PathVariable String value){
        this.taskService.setVariableLocal(taskId,name,value);


        String processInstanceId = this.taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
        String executionId = this.taskService.createTaskQuery().taskId(taskId).singleResult().getExecutionId();
        Map<String,String> map = new HashMap<String,String>();

        this.taskService.setVariableLocal(taskId,name,value);
        this.taskService.setVariable(processInstanceId,name,value);
        this.taskService.setVariablesLocal(taskId,map);
        this.taskService.setVariables(processInstanceId,map);

        this.runtimeService.setVariableLocal(executionId,name,value);
        this.runtimeService.setVariable(executionId,name,value);
        this.runtimeService.setVariablesLocal(executionId,map);
        this.runtimeService.setVariables(executionId,map);
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

    /**
     * 查看待办的流程变量
     * @param taskId 流程实例ID
     * @return
     */
    // url : http://127.0.0.1:8899/thd/pv/showTaskVar/14
    @RequestMapping(value="/showTaskVar/{taskId}")
    @ResponseBody
    public Map showTaskVar(@PathVariable String taskId){
        Map m = this.taskService.getVariablesLocal(taskId);
        return m;
    }




}
