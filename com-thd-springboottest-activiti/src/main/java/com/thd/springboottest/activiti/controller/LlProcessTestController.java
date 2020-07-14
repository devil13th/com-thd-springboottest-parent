package com.thd.springboottest.activiti.controller;

import com.thd.springboottest.activiti.utils.MyActivitiUtil;
import com.thd.springboottest.activiti.vo.CompleteTask;
import com.thd.springboottest.activiti.vo.MyTask;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 并行子流程subprocess及流程变量的使用
 * 例子请导入 llMainpostman_collection.json到postman 然后进行测试
 */
@Controller
@RequestMapping("/llProcessTest")
public class LlProcessTestController  {


    @Autowired
    private MyActivitiUtil myActivitiUtilFor6x;




    @RequestMapping("/queryTaskByUser/{user}")
    @ResponseBody
    public List<MyTask> queryTaskByUser(@PathVariable String user){
        List<Task> task1 = this.myActivitiUtilFor6x.getTaskService().createTaskQuery().taskAssignee(user).list();
        List<Task> task2 = this.myActivitiUtilFor6x.getTaskService().createTaskQuery().taskOwner(user).list();
        List<Task> task3 = this.myActivitiUtilFor6x.getTaskService().createTaskQuery().taskCandidateUser(user).list();

        List<Task> task = new ArrayList<Task>();
        task.addAll(task1);
        task.addAll(task2);
        task.addAll(task3);
        List<MyTask> mt = new ArrayList<MyTask>();

        task.forEach(tk -> {
            MyTask myTask = new MyTask();
            myTask.setOwner(tk.getAssignee());
            myTask.setTaskId(tk.getId());
            myTask.setTaskId(tk.getTaskDefinitionKey());
            mt.add(myTask);
        });
        return mt;
    }


    @RequestMapping("/queryTaskByBusinessKey/{businessKey}")
    @ResponseBody
    public List<MyTask> queryTaskByBusinessKey(@PathVariable String businessKey){
        List<Task> task = this.myActivitiUtilFor6x.getTaskService().createTaskQuery().processInstanceBusinessKey(businessKey).list();
        List<MyTask> mt = new ArrayList<MyTask>();

        task.forEach(tk -> {
            MyTask myTask = new MyTask();
            myTask.setOwner(tk.getAssignee());
            myTask.setTaskId(tk.getId());
            myTask.setTaskName(tk.getTaskDefinitionKey());
            myTask.setProcessInstanceId(tk.getProcessInstanceId());
            mt.add(myTask);
        });
        return mt;
    }


    @RequestMapping("/queryVar/{executeId}")
    @ResponseBody
    public Map<String,Object> queryVar(@PathVariable String executeId){
        Map<String,Object> m = this.myActivitiUtilFor6x.getRuntimeService().getVariables(executeId);
        return m;
    }




    @RequestMapping("/deleteProcessInstanceByBusinessKey/{businessKey}")
    @ResponseBody
    public String deleteProcessInstanceByBusinessKey(@PathVariable String businessKey){
        ProcessInstance pi = this.myActivitiUtilFor6x.getRuntimeService().createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        this.myActivitiUtilFor6x.getRuntimeService().deleteProcessInstance(pi.getProcessInstanceId(),"delete");
        return "SUCCESS";
    }

    @RequestMapping("/deleteProcessDefined/{deploymentId}")
    @ResponseBody
    public String deleteProcessDefined(@PathVariable String deploymentId){
        System.out.println("================ deleteDeploy [" + deploymentId + "] Start ===================");
        //删除流程实例
        List<ProcessInstance> proInsList =  this.myActivitiUtilFor6x.getRuntimeService().createProcessInstanceQuery().deploymentId(deploymentId).list();
        if(proInsList!=null && proInsList.size()>=0){
            for(ProcessInstance pi : proInsList){
                this.myActivitiUtilFor6x.getRuntimeService().deleteProcessInstance(pi.getProcessInstanceId(), "process deploy be deleted");
            }
        }
        //删除流程实例历史

        List<HistoricProcessInstance> hisList = this.myActivitiUtilFor6x.getHistoryService().createHistoricProcessInstanceQuery().deploymentId(deploymentId).list();
        if(hisList!=null && hisList.size()>=0){
            for(HistoricProcessInstance hi : hisList){
                this.myActivitiUtilFor6x.getHistoryService().deleteHistoricProcessInstance(hi.getId());
            }
        }

        //删除流程部署
        this.myActivitiUtilFor6x.getRepositoryService().deleteDeployment(deploymentId);
        return "SUCCESS";
    }


    @RequestMapping("/startProcess/{jobno}")
    @ResponseBody
    public String startProcess(@PathVariable String jobno){
        Map<String,Object> m = new HashMap<String,Object>();


        this.myActivitiUtilFor6x.getRuntimeService().startProcessInstanceByKey("llMain",jobno,m);
        return "SUCCESS";
    }

    @RequestMapping("/completeStartTrigger")
    @ResponseBody
    public String completeStartTrigger(@RequestBody CompleteTask completeTask){


        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId());
//        ProcessStepResult r = this.activitiServiceImpl.nextStep(completeTask.getTaskId(),completeTask.getCurrentUser(),completeTask.getM());
        return "SUCCESS";
    }


    @RequestMapping("/setTaskActionVars/{processInstanceId}")
    @ResponseBody
    public String setTaskActionVars(@PathVariable String processInstanceId){
        Map<String,Object> m = new HashMap<String,Object>();
        // ==== 设置流程变量

        this.myActivitiUtilFor6x.getRuntimeService().setVariables(processInstanceId,m);
        return "SUCCESS";
    }


    @RequestMapping("/completeTriggerManagerApproval")
    @ResponseBody
    public String completeTriggerManagerApproval(@RequestBody CompleteTask completeTask){
        Map<String,Object> m = new HashMap<String,Object>();

        // ==== 设置流程变量
        List<String> assignees = new ArrayList<String>();
        assignees.add("llOwner1");
        assignees.add("llOwner2");
        assignees.add("llOwner3");
//        m.put("direction","toAwnerAccept");
        m.put("takeActionVars",assignees);
        m.put("direction",completeTask.getDirection());
        // ==== 下一步
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId(),m);
       // ProcessStepResult r = this.activitiServiceImpl.nextStep(completeTask.getTaskId(),completeTask.getCurrentUser(),m);

        return "SUCCESS";
    }

    @RequestMapping("/completeOwnerAccept")
    @ResponseBody
    public String completeOwnerAccept(@RequestBody CompleteTask completeTask){
        Map<String,Object> m = new HashMap<String,Object>();

        // ==== 设置流程变量
        List<String> assignees = new ArrayList<String>();
//        assignees.add("llOwner1");
//        assignees.add("llOwner2");
//        assignees.add("llOwner3");
        m.put("direction",completeTask.getDirection());
//        m.put("takeActionVars",assignees);

        // ==== 下一步
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId(),m);
        // ProcessStepResult r = this.activitiServiceImpl.nextStep(completeTask.getTaskId(),completeTask.getCurrentUser(),m);

        return "SUCCESS";
    }




    @RequestMapping("/completeOwnerAnalyse")
    @ResponseBody
    public String completeOwnerAnalyse(@RequestBody CompleteTask completeTask){

        // ==== 下一步
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId());
        // ProcessStepResult r = this.activitiServiceImpl.nextStep(completeTask.getTaskId(),completeTask.getCurrentUser(),m);

        return "SUCCESS";
    }



    @RequestMapping("/completeOwnerTakeAction")
    @ResponseBody
    public String completeOwnerTakeAction(@RequestBody CompleteTask completeTask){

        // ==== 下一步
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId());
        // ProcessStepResult r = this.activitiServiceImpl.nextStep(completeTask.getTaskId(),completeTask.getCurrentUser(),m);

        return "SUCCESS";
    }


    @RequestMapping("/completeOwnerManagerApprove")
    @ResponseBody
    public String completeOwnerManagerApprove(@RequestBody CompleteTask completeTask){
        Map<String,Object> m = new HashMap();
        m.put("direction",completeTask.getDirection());
        // ==== 下一步
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId(),m);


        return "SUCCESS";
    }

    @RequestMapping("/completeTriggerOwnerConfirm")
    @ResponseBody
    public String completeTriggerOwnerConfirm(@RequestBody CompleteTask completeTask){
        Map<String,Object> m = new HashMap();
        m.put("direction",completeTask.getDirection());

        // 如果是修改action 则设置待办人
        if("toUpdateAction".equalsIgnoreCase(completeTask.getDirection())){


            // ==== 设置流程变量
            List<String> assignees = new ArrayList<String>();
            assignees.add("llOwner2");
            assignees.add("llOwner3");
            m.put("updateActionVars",assignees);
        }
        // ==== 下一步
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId(),m);
        return "SUCCESS";
    }


    @RequestMapping("/completeOwnerUpdateAction")
    @ResponseBody
    public String completeOwnerUpdateAction(@RequestBody CompleteTask completeTask){
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId());
        return "SUCCESS";
    }


    @RequestMapping("/completeOwnerManagerApproveUpdate")
    @ResponseBody
    public String completeOwnerManagerApproveUpdate(@RequestBody CompleteTask completeTask){
        Map<String,Object> m = new HashMap();
        m.put("direction",completeTask.getDirection());

        // ==== 下一步
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId(),m);
        return "SUCCESS";
    }

    @RequestMapping("/completeTriggerOwnerManagerApprove")
    @ResponseBody
    public String completeTriggerOwnerManagerApprove(@RequestBody CompleteTask completeTask){
        Map<String,Object> m = new HashMap();
        m.put("direction",completeTask.getDirection());

        // 如果是到committee 则设置待办人
        if("toCommitteeApprove".equalsIgnoreCase(completeTask.getDirection())){


            // ==== 设置流程变量
            List<String> assignees = new ArrayList<String>();
            assignees.add("committee1");
            assignees.add("committee2");
            m.put("committees",assignees);
        }


        // ==== 下一步
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId(),m);
        return "SUCCESS";
    }


    @RequestMapping("/completeCommitteeApprove")
    @ResponseBody
    public String completeCommitteeApprove(@RequestBody CompleteTask completeTask){
        Map<String,Object> m = new HashMap();
        m.put("direction",completeTask.getDirection());

        // ==== 下一步
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId(),m);
        return "SUCCESS";
    }


    @RequestMapping("/completeVpApprove")
    @ResponseBody
    public String completeVpApprove(@RequestBody CompleteTask completeTask){
        Map<String,Object> m = new HashMap();
        m.put("direction",completeTask.getDirection());
        // 如果是到committee 则设置待办人
        if("toCommitteeApprove".equalsIgnoreCase(completeTask.getDirection())){
            // ==== 设置流程变量
            List<String> assignees = new ArrayList<String>();
            assignees.add("committee1");
            assignees.add("committee2");
            m.put("committees",assignees);
        }
        // ==== 下一步
        this.myActivitiUtilFor6x.getTaskService().complete(completeTask.getTaskId(),m);
        return "SUCCESS";
    }




}
