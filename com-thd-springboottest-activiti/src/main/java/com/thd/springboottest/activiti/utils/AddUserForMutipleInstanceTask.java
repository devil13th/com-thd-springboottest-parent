package com.thd.springboottest.activiti.utils;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.history.HistoryManager;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.thd.springboottest.activiti.utils.AddTaskForParallelTask
 * Activiti6 会签/串签 加签功能
 * 注意：下面程序中用到了流程变量users 和  user,需要根据实际情况进行修改
 * @author: wanglei62
 * @DATE: 2021/3/8 9:46
 **/
public class AddUserForMutipleInstanceTask implements Command {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    // 会签内部流程变量
    protected final String NUMBER_OF_INSTANCES = "nrOfInstances";
    protected final String NUMBER_OF_ACTIVE_INSTANCES = "nrOfActiveInstances";
    protected final String NUMBER_OF_COMPLETED_INSTANCES = "nrOfCompletedInstances";
    protected final String LOOP_COUNTER = "loopCounter";

    /**
     * 当前任务ID(正处在会签状态下的taskId,可能有多个，随便一个就可以)
     */
    private String taskId;

    /**
     * 审核人
     */
    private List<String> assigneeList;

    /**
     * 任务执行人
     */
    private String assignee;

    public AddUserForMutipleInstanceTask(String taskId, List<String> assigneeList) {

        super();

        if (ObjectUtils.isEmpty(assigneeList)) {
            throw new RuntimeException("assigneeList 不能为空!");
        }

        this.taskId = taskId;
        this.assigneeList = assigneeList;
    }

    public AddUserForMutipleInstanceTask(String taskId, List<String> assigneeList, String assignee) {

        super();

        if (ObjectUtils.isEmpty(assigneeList)) {
            throw new RuntimeException("assigneeList 不能为空!");
        }

        this.taskId = taskId;
        this.assigneeList = assigneeList;
        this.assignee = assignee;
    }

    @Override
    public String execute(CommandContext commandContext) {
        ProcessEngine processEngine = (ProcessEngine)SpringContextUtils.getBean("processEngine");

        TaskEntityImpl task = (TaskEntityImpl) processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        ExecutionEntityImpl execution = (ExecutionEntityImpl) processEngine.getRuntimeService().createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(task.getProcessDefinitionId());
        Process process = bpmnModel.getProcesses().get(0);
        UserTask userTask = (UserTask) process.getFlowElement(task.getTaskDefinitionKey());

        if (userTask.getLoopCharacteristics() == null) {
            // TODO
            logger.error("task:[" + task.getId() + "] 不是会签节任务");
        }

        /**
         *  获取父级
         */
        ExecutionEntityImpl parentNode = execution.getParent();

        /**
         *  获取流程变量
         */
        int nrOfInstances = (int) processEngine.getRuntimeService().getVariableLocal(parentNode.getId(), NUMBER_OF_INSTANCES);
        int nrOfActiveInstances = (int) processEngine.getRuntimeService().getVariableLocal(parentNode.getId(), NUMBER_OF_ACTIVE_INSTANCES);

        /**
         *  获取管理器
         */
        ExecutionEntityManager executionEntityManager = Context.getCommandContext().getExecutionEntityManager();

        Object behavior = userTask.getBehavior();
        if (behavior instanceof ParallelMultiInstanceBehavior) {

            logger.info("task:[" + task.getId() + "] 并行会签 加签 任务");
            /**
             *  设置循环标志变量
             */
            processEngine.getRuntimeService().setVariableLocal(parentNode.getId(), NUMBER_OF_INSTANCES, nrOfInstances + assigneeList.size());
            processEngine.getRuntimeService().setVariableLocal(parentNode.getId(), NUMBER_OF_ACTIVE_INSTANCES, nrOfActiveInstances + assigneeList.size());

            /**
             *  新建任务列表
             */
            for (String assignee : this.assigneeList) {

                /**
                 *  创建 子 execution
                 */
                ExecutionEntity newExecution = executionEntityManager.createChildExecution(parentNode);

                newExecution.setActive(true);
                newExecution.setVariableLocal(LOOP_COUNTER, nrOfInstances);
                newExecution.setVariableLocal("user", assignee);
                newExecution.setCurrentFlowElement(userTask);

                /**
                 * 任务总数 +1
                 */
                nrOfInstances++;

                /**
                 * 推入时间表序列
                 */
                Context.getAgenda().planContinueMultiInstanceOperation(newExecution);
            }

        } else if (behavior instanceof SequentialMultiInstanceBehavior) {
            logger.info("task:[" + task.getId() + "] 串行会签 加签 任务");

            /**
             *  是否需要替换审批人
             */
            boolean changeAssignee = false;
            if (StringUtils.isEmpty(assignee)) {
                assignee = task.getAssignee();
                changeAssignee = true;
            }
            /**
             *  当前任务执行位置
             */
            int loopCounterIndex = -1;

            for (int i = 0; i < assigneeList.size(); i++) {

                String temp = assigneeList.get(i);
                if (assignee.equals(temp)) {
                    loopCounterIndex = i;
                }
            }

            if (loopCounterIndex == -1) {
                throw new RuntimeException("任务审批人不存在于任务执行人列表中");
            }

            /**
             *  修改当前任务执行人
             */
            if (changeAssignee) {
                processEngine.getTaskService().setAssignee(taskId, assignee);
                execution.setVariableLocal("user", assignee);
            }

            /**
             *  修改 计数器位置
             */
            execution.setVariableLocal(LOOP_COUNTER, loopCounterIndex);

            /**
             *  修改全局变量
             */
            Map<String, Object> variables = new HashMap<>(3);
            variables.put(NUMBER_OF_INSTANCES, assigneeList.size());
            variables.put(NUMBER_OF_COMPLETED_INSTANCES, loopCounterIndex);
            variables.put("users", assigneeList);

            processEngine.getRuntimeService().setVariables(parentNode.getId(), variables);
        }


        return "加签成功";
    }


}
