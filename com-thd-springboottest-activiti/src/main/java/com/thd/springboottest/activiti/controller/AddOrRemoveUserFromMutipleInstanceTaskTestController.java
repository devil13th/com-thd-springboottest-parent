package com.thd.springboottest.activiti.controller;

import com.thd.springboottest.activiti.utils.AddUserForMutipleInstanceTask;
import com.thd.springboottest.activiti.utils.MyActivitiUtil;
import com.thd.springboottest.activiti.utils.RemoveUserForMutipleInstanceTask;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.thd.springboottest.activiti.controller.ParellelTest
 *
 * @author: wanglei62
 * @DATE: 2021/3/8 9:22
 **/
@Controller
@RequestMapping("/addOrRemoveUserFromMutipleInstanceTaskTest")
public class AddOrRemoveUserFromMutipleInstanceTaskTestController {

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

    @ResponseBody
    @RequestMapping("/test")
    public ResponseEntity test(){
        System.out.println("test");
        return ResponseEntity.ok("SUCCESS");
    }


    /**
     * 开启流程
     * @return
     */
    // url: http://127.0.0.1:8899/thd/addOrRemoveUserFromMutipleInstanceTaskTest/startProcess/01
    @RequestMapping(value="/startProcess/{businessKey}")
    @ResponseBody
    public String startProcess(@PathVariable String businessKey){
        Map m = new HashMap();
        List<String> l = Arrays.asList("zhangsan","lisi","wangwu");
        m.put("users",l);
        m.put("judge","zhaoliu");
        ProcessInstance pi = this.runtimeService.startProcessInstanceByKey("AddOrRemoveUserFromMutipleInstanceTask",businessKey,m);
        return pi.getProcessInstanceId();
    }

    /**
     * 会签加签
     * @param taskId 正处在待办状态下的会签TaskId
     * @param user
     * @return
     */
    @RequestMapping(value="/addTaskForParallelTask/{taskId}/{user}")
    @ResponseBody
    // url: http://127.0.0.1:8899/thd/addOrRemoveUserFromMutipleInstanceTaskTest/addTaskForParallelTask/22501/sunqi
    public String addTaskForParallelTask(@PathVariable String taskId,@PathVariable String user){
        List<String> l = Arrays.asList(user);
        processEngine.getManagementService().executeCommand(new AddUserForMutipleInstanceTask(taskId,l));
        return "SUCCESS";
    }

    /**
     * 会签减签
     * @param taskId 正处在待办状态下的会签TaskId
     * @param users
     * @return
     */
    @RequestMapping(value="/removeTaskForParallelTask/{taskId}/{users}")
    @ResponseBody
    // url: http://127.0.0.1:8899/thd/addOrRemoveUserFromMutipleInstanceTaskTest/removeTaskForParallelTask/22501/sunqi
    public String removeTaskForParallelTask(@PathVariable String taskId,@PathVariable String users){
        List<String> l = Arrays.asList(users.split(","));
        processEngine.getManagementService().executeCommand(new RemoveUserForMutipleInstanceTask(taskId,l));
        return "SUCCESS";
    }



}
