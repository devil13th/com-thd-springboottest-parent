package com.thd.springboottest.utils;

import com.thd.springboottest.Application;
import com.thd.springboottest.activiti.utils.MyActivitiUtilFor5x;
import org.activiti.engine.*;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.thd.springboottest.utils.MyActivitiUtilFor5xTest
 *
 * @author: wanglei62
 * @DATE: 2019/10/12 19:01
 **/
//获取启动类，加载配置，确定装载 Spring 程序的装载方法，它会去寻找 主配置启动类(默认是@SpringBootApplication 注解的类)
//如果不配置@SpringBootTest的classes属性则自动寻找被 @SpringBootApplication 注解的）
@SpringBootTest(classes= Application.class)
//让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)



public class MyActivitiUtilFor5xTest{
    @Autowired
    private MyActivitiUtilFor5x util;

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

    @Before
    public void setUp() throws Exception {
        System.out.println(" ------------------------ junit @setUp() ---------------------- ");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println(" ------------------------ junit @After() ---------------------- ");
    }

    @Test
    public void testAllService() {
        System.out.println(util.getRepositoryService());
        System.out.println(processEngine);
        System.out.println(runtimeService);
        System.out.println(repositoryService);
        System.out.println(taskService);
        System.out.println(managementService);
        System.out.println(identityService);
        System.out.println(historyService);
        System.out.println(formService);
    }
    @Test
    public void testDeploy() {
        Deployment dep = repositoryService.createDeployment().addClasspathResource("processes/MyProcess.bpmn").deploy();
        System.out.println(dep.getId());
        System.out.println(dep.getName());
        System.out.println(dep.getKey());
    }

    @Test
    public void testQueryProcessDefined() {
        ProcessDefinition pd = repositoryService.getProcessDefinition("myProcess_1:8:17504");
        System.out.println("getDeploymentId:" + pd.getDeploymentId());
        System.out.println("getId:" + pd.getId());
        System.out.println("getVersion:" + pd.getVersion());
        System.out.println("getKey:" + pd.getKey());
        System.out.println("getName:" + pd.getName());
        System.out.println("getEngineVersion:" + pd.getEngineVersion());
    }





    @Test
    public void startProcess() {
        Map<String,Object> processVar = new HashMap<String,Object>();
        processVar.put("applyer","zhangsan");
        processVar.put("auditor","lisi");
        ProcessInstance pi = runtimeService.startProcessInstanceById("myProcess_1:8:17504","P20180202-001",processVar);
        System.out.println("getId:" + pi.getId());
        System.out.println("getName:" + pi.getName());
        System.out.println("getProcessInstanceId:" + pi.getProcessInstanceId());
        System.out.println("getBusinessKey:" + pi.getBusinessKey());
    }


    @Test
    public void testQueryprocessInstance() {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId("20001").singleResult();
        System.out.println("getId:" + pi.getId());
        System.out.println("getName:" + pi.getName());
        System.out.println("getProcessInstanceId:" + pi.getProcessInstanceId());
        System.out.println("getBusinessKey:" + pi.getBusinessKey());

    }

    @Test
    public void testQueryTodo() {
        List<Task> assigneeTasks = taskService.createTaskQuery().taskAssignee("applyor").list();
        List<Task> candidateTasks = taskService.createTaskQuery().taskCandidateUser("applyor").list();
        List<Task> tasks = new ArrayList<Task>();
        tasks.addAll(assigneeTasks);
        tasks.addAll(candidateTasks);
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                System.out.println("===================================");
                System.out.println("---- instanceId:" + task.getProcessInstanceId());
                System.out.println("----  executionId:" + task.getExecutionId());
                System.out.println("----  taskId:" + task.getId());
                System.out.println("----  taskName: " + task.getName());
                System.out.println("----  category:" + task.getCategory());
                System.out.println("===================================");
            }
        }
    }
}