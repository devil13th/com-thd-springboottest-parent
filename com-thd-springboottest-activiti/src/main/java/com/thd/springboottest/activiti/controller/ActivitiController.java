package com.thd.springboottest.activiti.controller;

import com.thd.springboottest.activiti.utils.MyActivitiUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.activiti.controller.TestController
 *
 * @author: wanglei62
 * @DATE: 2019/10/12 15:49
 **/
@Controller
@RequestMapping(value="/activiti")
public class ActivitiController {
    @Autowired
    private MyActivitiUtil util;

    @RequestMapping(value="/test")
    @ResponseBody
    public String test(){
        System.out.println("-----------------");
        ProcessDefinition pd = util.getRepositoryService().getProcessDefinition("myProcess_1:1:6");

        BpmnModel bm = ProcessDefinitionUtil.getBpmnModel("myProcess_1:1:6");
        System.out.println(pd.getDeploymentId());
        System.out.println(util);
        return "SUCCESS";
    }


    @RequestMapping(value="/startProcess")
    @ResponseBody
    public String startProcess(){
        ProcessInstance processInstance = util.getRuntimeService().startProcessInstanceByKey("vacat","key001");
        System.out.println("已成功开启 qingjia 流程：" );
        System.out.println("ActivityId：" +processInstance.getActivityId());
        System.out.println("BusinessKey：" +processInstance.getBusinessKey());
        System.out.println("DeploymentId：" +processInstance.getDeploymentId());
        System.out.println("ID：" +processInstance.getId());
        System.out.println("NAME：" +processInstance.getName());
        return "SUCCESS";
    }







}
