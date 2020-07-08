package com.thd.springboottest.activiti.activitilistener;

import com.thd.springboottest.activiti.service.ProcessService;
import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("executionLogListener")
public class ExecutionLogListener implements ExecutionListener{
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
    public void notify(DelegateExecution delegateExecution) {
        System.out.println(String.format("[execution][%s]  trigger :%s",delegateExecution.getEventName(),this.getClass().getName()));
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();

        //System.out.println(String.format("processInstanceId:%s,%s",pi.getProcessInstanceId(),pi.getProcessDefinitionKey()));
        processServiceImpl.testSpringIocInjection();
    }
}
