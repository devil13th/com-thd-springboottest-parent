package com.thd.springboottest.activiti.controller;

import com.thd.springboottest.activiti.utils.MyActivitiUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.activiti.engine.repository.ProcessDefinition;
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

并签流程 / 监听器

-- 创建流程实例
http://127.0.0.1:8899/thd/activiti/startProcess/ListenerTest/01
创建工作生成的流程实例ID processInstanceId : 5

-- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask
["9,One Task,5"]

-- 设置代办人
http://127.0.0.1:8899/thd/activiti/assign/9/zhangsan

-- 查询某代办人的代办
http://127.0.0.1:8899/thd/activiti/queryTaskByUser/zhangsan
["2513,One Task"]

-- 完成代办
http://127.0.0.1:8899/thd/activiti/finishTask/2509/zhangsan

-- 查看流程变量
http://127.0.0.1:8899/thd/activiti/showProcessVar/5
 */
@Controller
@RequestMapping(value="/activiti")
public class ActivitiController {
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
    @RequestMapping(value="/test")
    @ResponseBody
    public String test(){
        System.out.println("-----------------");
//        ProcessDefinition pd = util.getRepositoryService().getProcessDefinition("myProcess_1:1:6");
//
//        BpmnModel bm = ProcessDefinitionUtil.getBpmnModel("myProcess_1:1:6");
//        System.out.println(pd.getDeploymentId());
//        System.out.println(util);
        return "SUCCESS";
    }






    @RequestMapping(value="/deploy")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/activiti/deploy?bpmnName=ListenerTest.bpmn20.xml
    public String deploy(@RequestParam String bpmnName){
        this.log.info("deploy process " + bpmnName);
//        ProcessDefinition pd = util.getRepositoryService().getProcessDefinition("myProcess_1:1:6");
//
//        BpmnModel bm = ProcessDefinitionUtil.getBpmnModel("myProcess_1:1:6");
//        System.out.println(pd.getDeploymentId());
//        System.out.println(util);

        this.repositoryService.createDeployment().addClasspathResource("processes/" + bpmnName).deploy();
        return "SUCCESS";
    }



    /**
     * 开启流程
     * @param processDefinedKey 流程定义key
     * @param businessKey 业务主键
     * @return
     */
    // url: http://127.0.0.1:8899/thd/activiti/startProcess/ListenerTest/01
    @RequestMapping(value="/startProcess/{processDefinedKey}/{businessKey}")
    @ResponseBody
    public String startProcess(@PathVariable String processDefinedKey,@PathVariable String businessKey){
        ProcessInstance pi = this.runtimeService.startProcessInstanceByKey(processDefinedKey,businessKey);
        return pi.getProcessInstanceId();
    }

    /**
     * 查询所有代办
     * @return
     */
    @RequestMapping(value="/queryTask")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/activiti/queryTask
    public List<String> queryTask(){
        List<Task> t =  this.taskService.createTaskQuery().list();
        List<String> l = t.stream().map(task -> {
            return "taskId:" + task.getId() + ",taskName:" + task.getName() + ",ProcessInstanceId:" + task.getProcessInstanceId();
        }).collect(Collectors.toList());
        return l;
    }


    /**
     * 给某个任务添加人
     * @param taskId
     * @param user
     * @return
     */
    @RequestMapping(value="/assign/{taskId}/{user}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/activiti/assign/2513/zhangsan
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
    // url : http://127.0.0.1:8899/thd/activiti/queryTaskByUser/zhangsan
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
    // url : http://127.0.0.1:8899/thd/activiti/finishTask/2513/zhangsan
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
    public Map showProcessVar(@PathVariable String processInstanceId){
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
