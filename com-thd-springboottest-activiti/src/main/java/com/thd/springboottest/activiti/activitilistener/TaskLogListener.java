package com.thd.springboottest.activiti.activitilistener;

import com.thd.springboottest.activiti.service.ProcessService;
import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * com.thd.springboottest.activiti.activitilistener.TaskLogListener
 *
 * @author: wanglei62
 * @DATE: 2020/7/8 10:24
 **/
@Component("taskLogListener")
public class TaskLogListener implements TaskListener {
    @Autowired
    private ProcessService processServiceImpl;
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
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println(String.format("[task][%s] trigger :%s",delegateTask.getEventName(),this.getClass().getName()));
        System.out.println(String.format("task name :%s,%s",delegateTask.getTaskDefinitionKey(),delegateTask.getName()));
        processServiceImpl.testSpringIocInjection();
    }
}
