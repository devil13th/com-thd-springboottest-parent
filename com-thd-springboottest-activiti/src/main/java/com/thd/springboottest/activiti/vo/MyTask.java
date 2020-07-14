package com.thd.springboottest.activiti.vo;



/**
 * com.lenovo.dqm.common.common.vo.MyTask
 *
 * @author: wanglei62
 * @DATE: 2019/10/28 15:39
 **/

public class MyTask {
    private String taskId;
    private String taskName;
    private String assignee;
    private String owner;
    private String processInstanceId;
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }


}
