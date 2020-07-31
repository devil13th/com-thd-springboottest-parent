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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*


-- 开启流程
http://127.0.0.1:8899/thd/ex/startProcess/01
流程实例:17

-- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:21,taskName:A,ProcessInstanceId:17"]

-- 设置流程变量
http://127.0.0.1:8899/thd/ex/setProcessVar/17/direction/1

-- 查看流程变量
http://127.0.0.1:8899/thd/ex/showProcessVar/17
{"direction":"1"}

-- 完成代办
http://127.0.0.1:8899/thd/ex/finishTask/21/zhangsan

-- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:27,taskName:B2,ProcessInstanceId:17","taskId:29,taskName:B3,ProcessInstanceId:17","taskId:31,taskName:B1,ProcessInstanceId:17"]

-- 完成代办
http://127.0.0.1:8899/thd/ex/finishTask/27/zhangsan
http://127.0.0.1:8899/thd/ex/finishTask/29/zhangsan
http://127.0.0.1:8899/thd/ex/finishTask/31/zhangsan

-- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:36,taskName:C,ProcessInstanceId:17"]

-- 完成代办
http://127.0.0.1:8899/thd/ex/finishTask/36/zhangsan

-- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:40,taskName:D1,ProcessInstanceId:17","taskId:42,taskName:D3,ProcessInstanceId:17"]

-- 完成代办
http://127.0.0.1:8899/thd/ex/finishTask/40/zhangsan
http://127.0.0.1:8899/thd/ex/finishTask/42/zhangsan

-- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask

-- 完成代办
http://127.0.0.1:8899/thd/ex/finishTask/46/zhangsan

-- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask

 */

@Controller
@RequestMapping(value="/ex")
public class ExecutionTestController {
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
    // url: http://127.0.0.1:8899/thd/ex/startProcess/01
    @RequestMapping(value="/startProcess/{businessKey}")
    @ResponseBody
    public String startProcess(@PathVariable String businessKey){
        ProcessInstance pi = this.runtimeService.startProcessInstanceByKey("executionTest",businessKey);
        return pi.getProcessInstanceId();
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

    /**
     * 完成代办
     * @param taskId 待办ID
     * @param user
     * @return
     */

    @RequestMapping(value="/finishTask/{taskId}/{user}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/ex/finishTask/2513/zhangsan
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



}
