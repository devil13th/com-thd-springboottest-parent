package com.thd.springboottest.activiti.utils;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * com.thd.springboottest.activiti.utils.AddTaskForParallelTask
 * Activiti6 会签/串签 减功能
 * 注意：下面程序中用到了流程变量users 和  user,需要根据实际情况进行修改
 * 特别注意：减签功能最好用Activiti的completeTask来实现,然后对业务数据进行标记,标记为人工操作删除
 * @author: wanglei62
 * @DATE: 2021/3/8 9:46
 **/
public class RemoveUserForMutipleInstanceTask implements Command {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    // 会签内部流程变量
    protected final String NUMBER_OF_INSTANCES = "nrOfInstances";
    protected final String NUMBER_OF_ACTIVE_INSTANCES = "nrOfActiveInstances";
    protected final String NUMBER_OF_COMPLETED_INSTANCES = "nrOfCompletedInstances";
    protected final String LOOP_COUNTER = "loopCounter";

    /**
     * 当前任务ID
     */
    private String taskId;

    /**
     * 审核人
     */
    private List<String> assigneeList;

    public RemoveUserForMutipleInstanceTask(String taskId, List<String> assigneeList) {

        super();

        if (ObjectUtils.isEmpty(assigneeList)) {
            throw new RuntimeException("assigneeList 不能为空!");
        }

        this.taskId = taskId;
        this.assigneeList = assigneeList;
    }

    @Override
    public String execute(CommandContext commandContext) {
        ProcessEngine processEngine = (ProcessEngine)SpringContextUtils.getBean("processEngine");
        TaskEntityImpl task = (TaskEntityImpl) processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();

        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(task.getProcessDefinitionId());
        Process process = bpmnModel.getProcesses().get(0);

        UserTask userTask = (UserTask) process.getFlowElement(task.getTaskDefinitionKey());

        if (userTask.getLoopCharacteristics() == null) {
            // TODO
            logger.error("task:[" + task.getId() + "] 不是会签节任务");
        }

        ExecutionEntityImpl execution = (ExecutionEntityImpl) processEngine.getRuntimeService().createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        ExecutionEntityImpl parentNode = execution.getParent();

        /**
         *  获取任务完成数
         */
        int nrOfCompletedInstances = (int) processEngine.getRuntimeService().getVariableLocal(parentNode.getId(), NUMBER_OF_COMPLETED_INSTANCES);
        /**
         *  获取任务未完成数
         */
        int nrOfActiveInstances = (int) processEngine.getRuntimeService().getVariableLocal(parentNode.getId(), NUMBER_OF_ACTIVE_INSTANCES);

        /**
         *  转换判断标识
         */
        Set<String> assigneeSet = new HashSet<>(assigneeList);
        ExecutionEntityManager executionEntityManager = Context.getCommandContext().getExecutionEntityManager();

        Object behavior = userTask.getBehavior();
        /**
         *  进行并行任务 减签
         */
        if (behavior instanceof ParallelMultiInstanceBehavior) {

            logger.info("task:[" + task.getId() + "] 并行会签 减签 任务");

            /**
             *  当前任务列表
             */
            List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(task.getProcessInstance().getProcessInstanceId()).list();

            List<Task> removeTaskList = new ArrayList<>(assigneeSet.size());
            List<Task> existTaskList = new ArrayList<>(taskList.size() - assigneeSet.size());

            taskList.forEach(obj -> {

                if (assigneeSet.contains(obj.getAssignee())) {
                    removeTaskList.add(obj);

                    ExecutionEntityImpl temp = (ExecutionEntityImpl) processEngine.getRuntimeService().createExecutionQuery().executionId(obj.getExecutionId()).singleResult();
                    executionEntityManager.deleteExecutionAndRelatedData(temp, "会签减签", true);

                } else {
                    existTaskList.add(obj);
                }
            });

            /**
             *  修改已完成任务变量,增加被删减任务
             */
            processEngine.getRuntimeService().setVariableLocal(parentNode.getId(), NUMBER_OF_COMPLETED_INSTANCES, nrOfCompletedInstances + removeTaskList.size());
            /**
             *  修改未完成任务变量,减少被删减任务
             */
            processEngine.getRuntimeService().setVariableLocal(parentNode.getId(), NUMBER_OF_ACTIVE_INSTANCES, nrOfActiveInstances - removeTaskList.size());

        } else if (behavior instanceof SequentialMultiInstanceBehavior) {
            logger.info("task:[" + task.getId() + "] 串行会签 减签 任务");

            Object obj = parentNode.getVariableLocal("users");
            if (obj == null || !(obj instanceof ArrayList)) {
                throw new RuntimeException("没有找到任务执行人列表");
            }


            ArrayList<String> sourceAssigneeList = (ArrayList) obj;
            List<String> newAssigneeList = new ArrayList<>();
            boolean flag = false;
            int loopCounterIndex = -1;
            String newAssignee = "";
            for (String temp : sourceAssigneeList) {
                if (!assigneeSet.contains(temp)) {
                    newAssigneeList.add(temp);
                }

                if (flag) {
                    newAssignee = temp;
                    flag = false;
                }

                if (temp.equals(task.getAssignee())) {

                    if (assigneeSet.contains(temp)) {
                        flag = true;
                        loopCounterIndex = newAssigneeList.size();
                    } else {
                        loopCounterIndex = newAssigneeList.size() - 1;
                    }
                }
            }

            /**
             *  修改计数器变量
             */
            Map<String, Object> variables = new HashMap<>();
            variables.put(NUMBER_OF_INSTANCES, newAssigneeList.size());
            variables.put(NUMBER_OF_COMPLETED_INSTANCES, loopCounterIndex > 0 ? loopCounterIndex - 1 : 0);
            variables.put("users", newAssigneeList);
            processEngine.getRuntimeService().setVariablesLocal(parentNode.getId(), variables);

            /**
             *  当前任务需要被删除，需要替换下一个任务审批人
             */
            if (!StringUtils.isEmpty(newAssignee)) {
                processEngine.getTaskService().setAssignee(taskId, newAssignee);
                execution.setVariableLocal(LOOP_COUNTER, loopCounterIndex);
                execution.setVariableLocal("user", newAssignee);
            }
        }
        return "减签成功";
    }


}
