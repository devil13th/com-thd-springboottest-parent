package com.thd.springboottest.activiti.vo;


import java.util.Map;

/**
 * com.lenovo.dqm.ll.vo.CompleteTask
 *
 * @author: wanglei62
 * @DATE: 2020/7/13 14:02
 **/
public class CompleteTask {
    private String taskId;
    private String currentUser;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public Map<String, Object> getM() {
        return m;
    }

    public void setM(Map<String, Object> m) {
        this.m = m;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    private Map<String,Object> m;
    private String direction;
}
