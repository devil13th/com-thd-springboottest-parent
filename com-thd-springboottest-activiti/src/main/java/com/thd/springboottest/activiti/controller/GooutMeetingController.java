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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * com.thd.springboottest.activiti.controller.GooutMeetingController
 *
 * @author: wanglei62
 * @DATE: 2021/1/21 15:28
 **/


/*
----- 开启流程
http://127.0.0.1:8899/thd/gooutMetting/startProcess/01
创建工作生成的流程实例ID processInstanceId : 20

============================ 不通过流程  ==========================

----- 设置流程变量
http://127.0.0.1:8899/thd/gooutMetting/setProcessVar/20
SUCCESS

----- 查看流程变量
http://127.0.0.1:8899/thd/activiti/showProcessVar/20
{"totalCount":3,"passCount":0,"assigneeA":["zhangsan","lisi","wangwu"]}

----- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:24,taskName:Task One,ProcessInstanceId:20,parentTaskId:null"]

----- 完成代办
http://127.0.0.1:8899/thd/gooutMetting/finishTask/24/zhangsan
SUCCESS

----- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:24,taskName:Task One,ProcessInstanceId:20,parentTaskId:null"]

----- zhangsan会签(通过)
http://127.0.0.1:8899/thd/gooutMetting/finishMeetingTask/47/zhangsan/Y
SUCCESS

----- 查看流程变量 -- passCount=1
http://127.0.0.1:8899/thd/activiti/showProcessVar/20
{"totalCount":4,"passCount":1,"assigneeA":["zhangsan","lisi","wangwu","zhaoliu"]}


----- lisi会签(不通过)
http://127.0.0.1:8899/thd/gooutMetting/finishMeetingTask/49/zhangsan/N
SUCCESS

----- 查看流程变量 -- passCount=1
http://127.0.0.1:8899/thd/activiti/showProcessVar/20
{"totalCount":4,"passCount":1,"assigneeA":["zhangsan","lisi","wangwu","zhaoliu"]}

----- lisi会签(不通过)
http://127.0.0.1:8899/thd/gooutMetting/finishMeetingTask/49/lisi/N
SUCCESS

----- 查看流程变量 -- passCount=1
http://127.0.0.1:8899/thd/activiti/showProcessVar/20
{"totalCount":4,"passCount":1,"assigneeA":["zhangsan","lisi","wangwu","zhaoliu"]}

----- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:52,taskName:Metting,ProcessInstanceId:20,parentTaskId:null","taskId:55,taskName:Metting,ProcessInstanceId:20,parentTaskId:null"]

----- wangwu会签(不通过)
http://127.0.0.1:8899/thd/gooutMetting/finishMeetingTask/52/wangwu/N
SUCCESS

----- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:55,taskName:Metting,ProcessInstanceId:20,parentTaskId:null"]

----- zhaoliu会签(不通过)
http://127.0.0.1:8899/thd/gooutMetting/finishMeetingTask/55/zhaoliu/N
SUCCESS

----- 查询待办 -- 已经到了前一步
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:59,taskName:Task One,ProcessInstanceId:20,parentTaskId:null"]

============================ 通过流程  ==========================

----- 设置流程变量
http://127.0.0.1:8899/thd/gooutMetting/setProcessVar/20
SUCCESS

----- 完成代办
http://127.0.0.1:8899/thd/gooutMetting/finishTask/59/zhangsan
SUCCESS

----- 查询待办
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:73,taskName:Metting,ProcessInstanceId:20,parentTaskId:null","taskId:75,taskName:Metting,ProcessInstanceId:20,parentTaskId:null","taskId:77,taskName:Metting,ProcessInstanceId:20,parentTaskId:null","taskId:79,taskName:Metting,ProcessInstanceId:20,parentTaskId:null"]

----- zhangsan会签(不通过)
http://127.0.0.1:8899/thd/gooutMetting/finishMeetingTask/73/zhangsan/N
SUCCESS

----- 查看流程变量 -- passCount=1
http://127.0.0.1:8899/thd/activiti/showProcessVar/20
{"totalCount":4,"passCount":0,"assigneeA":["zhangsan","lisi","wangwu","zhaoliu"]}

----- zhangsan会签(通过)
http://127.0.0.1:8899/thd/gooutMetting/finishMeetingTask/75/zhangsan/Y
SUCCESS

----- 查看流程变量 -- passCount=1
http://127.0.0.1:8899/thd/activiti/showProcessVar/20
{"totalCount":4,"passCount":1,"assigneeA":["zhangsan","lisi","wangwu","zhaoliu"]}


----- lisi会签(通过)
http://127.0.0.1:8899/thd/gooutMetting/finishMeetingTask/77/lisi/Y
{"totalCount":4,"passCount":2,"assigneeA":["zhangsan","lisi","wangwu","zhaoliu"]}

----- 查询待办 -- 已经到了最后一步
http://127.0.0.1:8899/thd/activiti/queryTask
["taskId:82,taskName:Record,ProcessInstanceId:20,parentTaskId:null"]


 */
@Controller
@RequestMapping(value="/gooutMetting")
public class GooutMeetingController {
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
    // url: http://127.0.0.1:8899/thd/gooutMetting/startProcess/01
    @RequestMapping(value="/startProcess/{businessKey}")
    @ResponseBody
    public String startProcess(@PathVariable String businessKey){
        ProcessInstance pi = this.runtimeService.startProcessInstanceByKey("GooutMeetting",businessKey);
        return pi.getProcessInstanceId();
    }


    /**
     * 设置人员流程变量
     * @return
     */
    // url: http://127.0.0.1:8899/thd/gooutMetting/setProcessVar/20
    @RequestMapping(value="/setProcessVar/{processInstanceId}")
    @ResponseBody
    public String setProcessVar(@PathVariable String processInstanceId){
        List<String> users = new ArrayList<String>();
        users.add("zhangsan");
        users.add("lisi");
        users.add("wangwu");
        users.add("zhaoliu");
        // 设置待办人
        this.runtimeService.setVariable(processInstanceId,"assigneeA",users);
        // 设置通过票数0
        this.runtimeService.setVariable(processInstanceId,"passCount",0);
        // 设置总票数3
        this.runtimeService.setVariable(processInstanceId,"totalCount",users.size());
        return "SUCCESS";
    }

    /**
     * 完成一般代办
     * @param taskId 待办ID
     * @param user
     * @return
     */
    @RequestMapping(value="/finishTask/{taskId}/{user}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/gooutMetting/finishTask/2513/zhangsan
    public String finishTask(@PathVariable String taskId,@PathVariable String user){
        this.taskService.complete(taskId);
        return "SUCCESS";
    }


    /**
     * 完成会签待办 -------- 重点!!!!!!!!!!!
     * @param taskId 待办ID
     * @param user
     * @return
     */
    @RequestMapping(value="/finishMeetingTask/{taskId}/{user}/{isAgree}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/gooutMetting/finishMeetingTask/2513/zhangsan/Y
    public String finishMeetingTask(@PathVariable String taskId,@PathVariable String user,@PathVariable String isAgree){

        Task t = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        if("Y".equalsIgnoreCase(isAgree)){
            Map m = this.runtimeService.getVariables(t.getProcessInstanceId());
            int passCount = Integer.valueOf(m.get("passCount").toString());
            this.log.info("投票通过票数:" + passCount);
            passCount++;
            this.runtimeService.setVariable(t.getProcessInstanceId(),"passCount",passCount);
        }
        this.taskService.complete(taskId);

        return "SUCCESS";
    }


    /**
     * 查询所有代办
     * @return
     */
    @RequestMapping(value="/queryTask")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/gooutMetting/queryTask
    public List<String> queryTask(){
        List<Task> t =  this.taskService.createTaskQuery().list();
        List<String> l = t.stream().map(task -> {
            return "taskId:" + task.getId() + ",taskName:" + task.getName() + ",ProcessInstanceId:" + task.getProcessInstanceId();
        }).collect(Collectors.toList());
        return l;
    }
}
