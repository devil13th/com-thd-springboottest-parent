/** 
 * Class Description:activiti常用方法工具类
 * Date : 2016年11月12日 下午10:41:17
 * Auth : wanglei 
 */

package com.thd.springboottest.activiti.utils;

import org.activiti.bpmn.model.*;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
//import org.activiti5.engine.impl.pvm.PvmActivity;
//import org.activiti5.engine.impl.pvm.PvmTransition;
//import org.activiti5.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
@Component
public class MyActivitiUtil {

	public ClassPathXmlApplicationContext context;
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
	 * 构造函数
	 */
	public MyActivitiUtil() {

	}

	/**
	 * 构造函数
	 * 
	 * @param SpringCfgXmlPathForActiviti
	 *            activiti-spring配置文件位置
	 *            使用classpath的路径例如com/thd/activiti/activiti.cfg1.xml
	 */
	public MyActivitiUtil(String SpringCfgXmlPathForActiviti) {
		log.info("---- MyActivitiUtil Construction ："
				+ SpringCfgXmlPathForActiviti);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { SpringCfgXmlPathForActiviti });
		processEngine = (ProcessEngine) context.getBean("processEngine");
		runtimeService = (RuntimeService) context.getBean("runtimeService");
		repositoryService = (RepositoryService) context
				.getBean("repositoryService");
		taskService = (TaskService) context.getBean("taskService");
		managementService = (ManagementService) context
				.getBean("managementService");
		
		identityService = (IdentityService) context.getBean("identityService");
		historyService = (HistoryService) context.getBean("historyService");
		formService = (FormService) context.getBean("formService");
	}

	/**
	 * 部署流程 Method Description : 部署流程
	 * 
	 * @param bpmnPath
	 *            bpmn的所在包路径
	 * @return
	 */
	public Deployment deploy(String bpmnPath) {
		log.info("================ deploy [" + bpmnPath	+ "] Start ===================");
		Deployment deploy = repositoryService.createDeployment().addClasspathResource(bpmnPath).deploy();
		log.info("---- 部署ID：" + deploy.getId());
		log.info("================ deploy [" + bpmnPath	+ "] End ===================");
		return deploy;
	}
	
	
	/**
	 * Method Description : 删除已部署的流程
	 * 
	 * @param deploymentId 部署ID
	 * @return
	 */
	public void deleteDeploy(String deploymentId) {
		log.info("================ deleteDeploy [" + deploymentId + "] Start ===================");
		//删除流程实例
		List<ProcessInstance> proInsList =  runtimeService.createProcessInstanceQuery().deploymentId(deploymentId).list();
		if(proInsList!=null && proInsList.size()>=0){
			for(ProcessInstance pi : proInsList){
				this.runtimeService.deleteProcessInstance(pi.getProcessInstanceId(), "process deploy be deleted");
			}
		}
		//删除流程实例历史
		
		List<HistoricProcessInstance> hisList = this.historyService.createHistoricProcessInstanceQuery().deploymentId(deploymentId).list();
		if(hisList!=null && hisList.size()>=0){
			for(HistoricProcessInstance hi : hisList){
				this.historyService.deleteHistoricProcessInstance(hi.getId());
			}
		}
				
		//删除流程部署
		repositoryService.deleteDeployment(deploymentId);
		log.info("================ deleteDeploy [" + deploymentId + "] End ===================");
	}
	
	public void addVarableToProcessInstance(Map map,String executionId){
		log.info("================ addVarableToProcessInstance  Start ===================");
		runtimeService.setVariablesLocal(executionId, map);
		log.info("================ addVarableToProcessInstance  End ===================");
	}
	
	/**
	 * 查询某任务的代办人
	 * @param taskId 任务ID
	 * @return
	 */
	public List<IdentityLink> queryCandidateForTask(String taskId){
		log.info("================ queryCandidateForTask [" + taskId + "] Start ===================");
		List<IdentityLink> candidaters = taskService.getIdentityLinksForTask(taskId);
		log.info("================ queryCandidateForTask [" + taskId + "] End ===================");
		return candidaters;
	}
	
	/**
	 * 查询某人个人待办 Method Description : 查询某人个人待办
	 * 
	 * @param assignee
	 *            人员标识
	 * @return
	 */
	public List<Task> queryTaskForAssignee(String assignee) {
		log.info("================ queryTaskForAssignee [" + assignee
				+ "] Start ===================");
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee)
				.list();
		if (tasks != null && tasks.size() > 0) {
			for (Task task : tasks) {
				log.info("===================================");
				log.info("---- instanceId:" + task.getProcessInstanceId());
				log.info("----  executionId:" + task.getExecutionId());
				log.info("----  taskId:" + task.getId());
				log.info("----  taskName: " + task.getName());
				log.info("----  category:" + task.getCategory());
				log.info("===================================");
			}
		}
		log.info("================ queryTaskForAssignee [" + assignee
				+ "] End ===================");
		return tasks;
	}

	/**
	 * 查询某人的组待办 Method Description : 查询某人的组待办
	 * 
	 * @param candidate
	 *            人员标识
	 * @return
	 */
	public List<Task> queryTaskForCandidate(String candidate) {
		log.info("================ queryTaskForCandidate [" + candidate
				+ "] Start ===================");
		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateUser(candidate).list();
		if (tasks != null && tasks.size() > 0) {
			for (Task task : tasks) {
				log.info("===================================");
				log.info("---- instanceId:" + task.getProcessInstanceId());
				log.info("----  executionId:" + task.getExecutionId());
				log.info("----  taskId:" + task.getId());
				log.info("----  taskName: " + task.getName());
				log.info("----  category:" + task.getCategory());
				log.info("===================================");
			}
		}
		log.info("================ queryTaskForCandidate [" + candidate
				+ "] End ===================");
		return tasks;
	}

	/**
	 * 查询某人个人待办和组待办 Method Description : 查询某人个人待办和组待办
	 * 
	 * @param assigneeOrcandidate
	 *            人员标识
	 * @return
	 */
	public List<Task> queryTaskForUser(String assigneeOrcandidate) {
		log.info("================ queryTaskForUser [" + assigneeOrcandidate
				+ "] Start ===================");
		List<Task> taskList = new ArrayList<Task>();
		taskList.addAll(queryTaskForAssignee(assigneeOrcandidate));
		taskList.addAll(queryTaskForCandidate(assigneeOrcandidate));
		log.info("================ queryTaskForUser [" + assigneeOrcandidate
				+ "] End ===================");
		return taskList;
	}

	/**
	 * 完成某任务 Method Description : 完成某任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param variableMap
	 *            流程变量
	 */
	public void completeTask(String taskId, Map<String, Object> variableMap) {
		log.info("================ complateTask  taskId:[" + taskId
				+ "] Start ===================");
		if (variableMap == null) {
			taskService.complete(taskId);
		} else {
			taskService.complete(taskId, variableMap);
		}
		log.info("================ complateTask  taskId:[" + taskId
				+ "] End ===================");
	}
	/**
	 * 签收某任务 Method Description : 签收某任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            人员ID
	 */
	public void claimTask(String taskId, String userId) {
		log.info("================ claimTask  taskId:[" + taskId
				+ "]  userId:[" + userId + "]Start ===================");
		taskService.claim(taskId, userId);
		log.info("================ claimTask  taskId:[" + taskId
				+ "]  userId:[" + userId + "]End ===================");
	}

	/**
	 * 签收并完成某任务 Method Description : 签收并完成某任务
	 * 
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            人员ID
	 * @param variableMap
	 *            流程变量
	 */
	public void claimAndComplateTask(String taskId, String userId,
			Map<String, Object> variableMap) {
		log.info("================ takeTask  taskId:[" + taskId + "]  userId:["
				+ userId + "]Start ===================");
		taskService.claim(taskId, userId);
		if (variableMap == null) {
			completeTask(taskId, null);
		} else {
			completeTask(taskId, variableMap);
		}
		log.info("================ takeTask  taskId:[" + taskId + "]  userId:["
				+ userId + "]End ===================");
	}

	/**
	 * 挂起某个流程实例 Method Description : 挂起某个流程实例
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 */
	public void suspendsProcessInstance(String processInstanceId) {
		log.info("================ suspendsProcessInstance  processInstanceId:["
				+ processInstanceId + "] Start ===================");
		runtimeService.suspendProcessInstanceById(processInstanceId);
		log.info("================ suspendsProcessInstance  processInstanceId:["
				+ processInstanceId + "] End ===================");
	}

	/**
	 * 激活某个已挂起的流程实例 Method Description : 激活某个已挂起的流程实例
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 */
	public void activateProcessInstance(String processInstanceId) {
		log.info("================ activateProcessInstance  processInstanceId:["
				+ processInstanceId + "] Start ===================");
		runtimeService.activateProcessInstanceById(processInstanceId);
		log.info("================ activateProcessInstance  processInstanceId:["
				+ processInstanceId + "] End ===================");
	}
	
	/**
	 * 删除某个流程实例
	 * Method Description : 删除某个流程实例
	 * @param processInstanceId 流程实例ID
	 * @param deleteReason 取消原因
	 */
	public void cancelProcessInstance(String processInstanceId,String deleteReason) {
		log.info("================ cancelProcessInstance  processInstanceId:["
				+ processInstanceId + "] Start ===================");
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
		log.info("================ cancelProcessInstance  processInstanceId:["
				+ processInstanceId + "] End ===================");
	}

	/**
	 * Method Description : 根据流程实例查询当前executions
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return List<Execution>
	 */
	public List<Execution> queryCurrentExecutes(String processInstanceId) {
		List<Execution> executionList = runtimeService.createExecutionQuery()
				.processInstanceId(processInstanceId).list();
		return executionList;
	}

	/**
	 * Method Description : 根据流程实例查询当前task
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return List<Task>
	 */
	public List<Task> queryCurrentTaskByProcessInstanceId(
			String processInstanceId) {
		List<Task> taskList = taskService.createTaskQuery()
				.processInstanceId(processInstanceId).list();
		return taskList;

	}

	/**
	 * Method Description : 根据业务主键查询当前task
	 * 
	 * @param businessKey
	 *            业务主键
	 * @return
	 */
	public List<Task> queryCurrentTaskByBusinessKey(String businessKey) {
		List<Task> taskList = taskService.createTaskQuery()
				.processInstanceBusinessKey(businessKey).list();
		return taskList;
	}

	/**
	 * 获取task的下一步节点
	 * 5.x failure
	 * 6.x ok
	 * @param taskId 当前待办id
	 * @return
	 */
	public List<FlowElement> queryNextTaskByCurrentTaskId(String taskId){
		List<FlowElement> nextNodes = new ArrayList<FlowElement>();
		Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		ExecutionEntity ee = (ExecutionEntity) processEngine.getRuntimeService().createExecutionQuery().executionId(task.getExecutionId()).singleResult();
		//当前节点
		String crruentActivityId = ee.getActivityId();
		//获取流程的bpmn
		BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(task.getProcessDefinitionId());
		FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(crruentActivityId);
		// 输出连线
		List<SequenceFlow> outFlows = flowNode.getOutgoingFlows();
		for (SequenceFlow sequenceFlow : outFlows) {
			// 下一个节点
			FlowElement targetFlow = sequenceFlow.getTargetFlowElement();
			nextNodes.add(targetFlow);
			if (targetFlow instanceof EndEvent) {// 如果下个节点为结束节点
				System.out.println(sequenceFlow.getId() + "," + sequenceFlow.getName() + "," + sequenceFlow.getTargetFlowElement());
			}
		}
		return nextNodes;

	}

	/**
	 * 获取task的上一步节点
	 * 5.x failure
	 * 6.x ok
	 * @param taskId 当前待办id
	 * @return
	 */
	public List<FlowElement> queryPrevTaskByCurrentTaskId(String taskId){
		List<FlowElement> nextNodes = new ArrayList<FlowElement>();
		Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		ExecutionEntity ee = (ExecutionEntity) processEngine.getRuntimeService().createExecutionQuery().executionId(task.getExecutionId()).singleResult();
		//当前节点
		String crruentActivityId = ee.getActivityId();
		//获取流程的bpmn
		BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(task.getProcessDefinitionId());
		FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(crruentActivityId);
		// 输入连线
		List<SequenceFlow> inFlows = flowNode.getIncomingFlows();
		for (SequenceFlow sequenceFlow : inFlows) {
			// 上一个节点
			FlowElement targetFlow = sequenceFlow.getSourceFlowElement();
			nextNodes.add(targetFlow);
			if (targetFlow instanceof StartEvent) {// 如果上个节点为开始节点
				System.out.println(sequenceFlow.getId() + "," + sequenceFlow.getName() + "," + sequenceFlow.getTargetFlowElement());
			}
		}
		return nextNodes;

	}

    /**
     * 获取task的上一步节点
     * 5.x ok
     * 6.x failure
     * @param taskId 当前待办id
     * @return
     */
//	public List<PvmActivity> queryPrevTaskByCurrentTaskId(String taskId) {
//		List<PvmActivity> resultList = new ArrayList<PvmActivity>();
//		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//
//		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
//				.getDeployedProcessDefinition(task.getProcessDefinitionId());
//		List<ActivityImpl> activitiList = def.getActivities();
//
//		String excId = task.getExecutionId();
//		ExecutionEntity execution = (ExecutionEntity) runtimeService
//				.createExecutionQuery().executionId(excId).singleResult();
//		String activitiId = execution.getActivityId();
//
//		for (ActivityImpl activityImpl : activitiList) {
//			String id = activityImpl.getId();
//			if (activitiId.equals(id)) {
//				//System.out.println("当前任务：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
//				List<PvmTransition> inTransitions = activityImpl.getIncomingTransitions();// 获取到某个节点出来的所有线路
//				for (PvmTransition tr : inTransitions) {
//					PvmActivity ac = tr.getSource(); // 获取线路的终点节点
//					//System.out.println("上一步任务任务NAME：" + ac.getProperty("name"));
//					//System.out.println("上一步任务任务ID：" + ac.getId());
//					resultList.add(ac);
//				}
//				break;
//			}
//		}
//		return resultList;
//	}
//
//	/**
//	 *
//	 * Method Description : 查询当前待办的下一步待办
//	 * @param taskId 当前待办ID
//	 * @return
//	 */
//	public List<PvmActivity> queryNextTaskByCurrentTaskId(String taskId) {
//		List<PvmActivity> resultList = new ArrayList<PvmActivity>();
//		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
//				.getDeployedProcessDefinition(task.getProcessDefinitionId());
//		List<ActivityImpl> activitiList = def.getActivities();
//		String excId = task.getExecutionId();
//		ExecutionEntity execution = (ExecutionEntity) runtimeService
//				.createExecutionQuery().executionId(excId).singleResult();
//		String activitiId = execution.getActivityId();
//
//		for (ActivityImpl activityImpl : activitiList) {
//			String id = activityImpl.getId();
//			if (activitiId.equals(id)) {
//				//System.out.println("当前任务：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
//				List<PvmTransition> outTransitions = activityImpl
//						.getOutgoingTransitions();// 获取从某个节点出来的所有线路
//				for (PvmTransition tr : outTransitions) {
//					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
//					//System.out.println("下一步任务任务NAME：" + ac.getProperty("name"));
//					//System.out.println("下一步任务任务ID：" + ac.getId());
//					resultList.add(ac);
//				}
//				break;
//			}
//		}
//		return resultList;
//	}
//
//	/**
//	 * @param currentTaskEntity
//	 *            当前任务节点
//	 * @param targetTaskDefinitionKey
//	 *            目标任务节点（在模型定义里面的节点名称）
//	 */
//	private void jump(final TaskEntity currentTaskEntity,
//					  String targetTaskDefinitionKey) {
//
//		String processDefinitionId = currentTaskEntity.getProcessDefinitionId();
//		RepositoryServiceImpl repositoryServiceImpl = (RepositoryServiceImpl) repositoryService;
//		ProcessDefinitionEntity pde =  (ProcessDefinitionEntity) repositoryServiceImpl.getDeployedProcessDefinition(processDefinitionId);
//
//		//ProcessDefinitionEntity pde = getProcessDefinition(processEngine,currentTaskEntity.getProcessDefinitionId());
//		final ActivityImpl activity = pde.findActivity(targetTaskDefinitionKey);
//
//		final ExecutionEntity execution = (ExecutionEntity) runtimeService
//				.createExecutionQuery()
//				.executionId(currentTaskEntity.getExecutionId()).singleResult();
//
//		((RuntimeServiceImpl) runtimeService).getCommandExecutor().execute(
//				new Command<Void>() {
//					public Void execute(CommandContext commandContext) {
//
//						// 创建新任务
//						execution.setActivity(activity);
//						execution.executeActivity(activity);
//
//						// 删除当前的任务
//						// 不能删除当前正在执行的任务，所以要先清除掉关联
//						currentTaskEntity.setExecutionId(null);
//						taskService.saveTask(currentTaskEntity);
//						taskService.deleteTask(currentTaskEntity.getId(), true);
//						return null;
//					}
//				});
//
//	}
//	/* ---------------------- 自由流 start ----------------------- */
//	/**
//	 * 跳转至指定活动节点
//	 * @param processInstanceId 流程实例ID
//	 * @param targetTaskDefinitionKey 目标节点ID(XML定义的活动节点的ID属性)
//	 */
//	public void jump(String processInstanceId, String targetTaskDefinitionKey) {
//		TaskEntity currentTask = (TaskEntity) processEngine.getTaskService()
//				.createTaskQuery().processInstanceId(processInstanceId)
//				.singleResult();
//		jump(currentTask, targetTaskDefinitionKey);
//	}

	/**
	 * 
	 * Method Description : 为某个Task添加候选人
	 * 
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            人员ID
	 */
	public void addCandidateUserToTask(String taskId, String userId) {
		taskService.addCandidateUser(taskId, userId);
	}

	/**
	 * 
	 * Method Description : 为某个Task删除加候选人
	 * 
	 * @param taskId
	 *            任务ID
	 * @param userId
	 *            人员ID
	 */
	public void deleteCandidateUserFromTask(String taskId, String userId) {
		taskService.deleteCandidateUser(taskId, userId);
	}

	/**
	 * 
	 * Method Description : 为某个Task替换候选人
	 * 
	 * @param taskId
	 *            任务ID
	 * @param oldUserId
	 *            已有候选人
	 * @param newUserId
	 *            新候选人
	 */
	public void replaceCandidateUserFromTask(String taskId, String oldUserId,
			String newUserId) {
		taskService.deleteCandidateUser(taskId, oldUserId);
		taskService.addCandidateUser(taskId, newUserId);
	}
	
	/**
	 * 获取流程定义的所有节点
	 * Method Description : ########
	 * @param procDefId
	 */
	public void getAllElement(String procDefId) {
		try {
			System.out.println(procDefId);
			BpmnModel model = repositoryService.getBpmnModel(procDefId);
			if (model != null) {
				Collection<FlowElement> flowElements = model.getMainProcess()
						.getFlowElements();
				for (FlowElement e : flowElements) {
					System.out.println("flowelement id:" + e.getId()
							+ "  name:" + e.getName() + "   class:"
							+ e.getClass().toString());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Task queryCurrentTaskDef(String taskId){
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		//待办ID(数据库表ID)
		//task.getId();
		//待办name(xml定义的name属性)
		//task.getName();
		//待办的id(xml定义的id属性)
		//task.getTaskDefinitionKey();
		return task;
	}
	





	
	
	/**
	 * 
	 * Method Description : 绘制流程图
	 * @param processDefinedId 流程定义ID
	 * @param imgPath 图片路径 PNG类型的图片
	 * @throws Exception
	 */
	public void drawBpmn(String processDefinedId,String imgPath) throws Exception{
		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinedId);
		System.out.println(processDefinition.getDiagramResourceName());
		InputStream imageStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
        File f = new File(imgPath);
        FileUtils.copyInputStreamToFile(imageStream, f);
	}
	
	
	/**
	 * 
	 * Method Description : 绘制流程图，待办节点红色显示(在流程图中显示当前步骤)
	 * @param taskId 待办ID
	 * @throws Exception
	 */
	public InputStream drawBpmnForInputStreamWithCurrentTask(String taskId) throws Exception{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("com/thd/activiti/test06/draw.bpmn");    //获取xml文件流  
//      System.out.println(is);
//      XMLInputFactory xmlFactory  = XMLInputFactory.newInstance();  
//      XMLStreamReader reader = xmlFactory.createXMLStreamReader(is); 
//      BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(reader);
//      ProcessDiagramGenerator pdg = new DefaultProcessDiagramGenerator();
//      System.out.println(runtimeService.getActiveActivityIds("60001"));
//      InputStream img = pdg.generateDiagram(bpmnModel, "png",  runtimeService.getActiveActivityIds("60001"), new ArrayList<String>());
//      System.out.println(img);
//      File f1 = new File("D://ab.png");
//      FileUtils.copyInputStreamToFile(img, f1);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		
      BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
      ProcessDiagramGenerator pdg = new DefaultProcessDiagramGenerator();
      Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
      System.out.println(runtimeService.getActiveActivityIds(execution.getId()));
      
      
     /* InputStream img = pdg.generateDiagram(
      		bpmnModel, 
      		"png",  
      		runtimeService.getActiveActivityIds(execution.getId()), 
      		new ArrayList<String>()
      		);*/
      
      InputStream img =  pdg.generateDiagram(
      		bpmnModel,
      		"png", 
      		runtimeService.getActiveActivityIds(execution.getId()), 
      		new ArrayList<String>(),
      		processEngine.getProcessEngineConfiguration().getActivityFontName(), 
      		processEngine.getProcessEngineConfiguration().getLabelFontName(),
      		null,
      		null, 
      		1.0);
		return img;
	}
	
	/**
	 * 
	 * Method Description : 绘制流程图，待办节点红色显示(在流程图中显示当前步骤)
	 * @param taskId 待办ID
	 * @param imgPath 图片位置 PNG类型的图片
	 * @throws Exception
	 */
	public void drawBpmnWithCurrentTask(String taskId,String imgPath) throws Exception{
		InputStream img = drawBpmnForInputStreamWithCurrentTask(taskId);
        //System.out.println(img);
        File f1 = new File(imgPath);
        FileUtils.copyInputStreamToFile(img, f1);
	}

	
	public String createSqlOfHistoryTask(String processInstanceId){
		String sql = 
				" select "+
				" taskhis.ID_ as taskHisId, "+
				" taskhis.NAME_ as taskName, "+
				" taskhis.TASK_DEF_KEY_ as taskKey, "+
				" taskhis.START_TIME_ as startTime, "+
				" taskhis.END_TIME_ as endTime, "+
				" taskhis.ASSIGNEE_ as assignee, "+
				" pins.BUSINESS_KEY_ as businessKey, "+
				" procdef.KEY_ as procDefKey "+
				" from "+
				" act_hi_taskinst taskhis "+
				" left join act_hi_procinst pins on taskhis.PROC_INST_ID_ = pins.PROC_INST_ID_ "+
				" left join act_re_procdef procdef on pins.PROC_DEF_ID_ = procdef.ID_ ";
		return sql;
	}
	
	
	public String createSqlOfCurrentTask(String processInstanceId){
		String sql = 
				" select "+ 
				" task.ID_ as taskId, "+ 
				" task.NAME_ as taskName, "+ 
				" task.TASK_DEF_KEY_ as taskKey, "+ 
				" pins.BUSINESS_KEY_ as businessKey, "+ 
				" procdef.KEY_ as procDefKey, "+ 
				" t.GROUP_USER as groupUser  "+ 
				" from act_hi_procinst pins "+ 
				" left join act_ru_task task on pins.PROC_INST_ID_ = task.PROC_INST_ID_ "+ 
				" left join act_re_procdef procdef on pins.PROC_DEF_ID_ = procdef.ID_ "+ 
				" left join  "+ 
				" ( "+ 
				" select lk.TASK_ID_ as TASK_ID_,GROUP_CONCAT(u.user_name) as GROUP_USER from act_ru_identitylink lk left join se_user u on lk.USER_ID_ = u.user_id  "+ 
				"  where lk.TYPE_ = 'candidate' group by lk.TASK_ID_ "+ 
				" ) t on task.ID_ = t.TASK_ID_ ";
		return sql;
	}
	
	
	
	/*
	 * ---- SQL查询待办方法
	 */
	/*
  	  select * from ( 
	  SELECT DISTINCT 
		INS.BUSINESS_KEY_ as BUSSINESSKEY,
	  TASK.ID_ as TASKID, 
	  TASK.EXECUTION_ID_ as EXECUTIONID, 
	  TASK.PROC_INST_ID_ as PROCINSID, 
	  TASK.PROC_DEF_ID_ as PROCDEFID, 
	  TASK.NAME_ as TASKNAME, 
	  TASK.TASK_DEF_KEY_ as TASKDEFKEY,
	  TASK.CREATE_TIME_ as TASKCREATETIME, 
	  'assignee' as ASSIGNTYPE, 
	  TASK.ASSIGNEE_ as TASKUSERID 
	  FROM ACT_RU_TASK TASK 
	  LEFT JOIN ACT_RE_PROCDEF D on TASK.PROC_DEF_ID_ = D.ID_ 
		LEFT JOIN ACT_HI_PROCINST INS on TASK.PROC_INST_ID_ = INS.ID_
		WHERE TASK.ASSIGNEE_ IS NOT NULL  AND TASK.SUSPENSION_STATE_ = '1'
	  
	  UNION
	  
	  SELECT DISTINCT 
		INS.BUSINESS_KEY_ as BUSSINESSKEY,
	  TASK.ID_ as TASKID, 
	  TASK.EXECUTION_ID_ as EXECUTIONID,
	  TASK.PROC_INST_ID_ as PROCINSID, 
	  TASK.PROC_DEF_ID_ as PROCDEFID,
	  TASK.NAME_ as TASKNAME, 
	  TASK.TASK_DEF_KEY_ as TASKDEFKEY,
	  TASK.CREATE_TIME_ as TASKCREATETIME, 
	  I.TYPE_ as ASSIGNTYPE, 
	  I.USER_ID_ as TASKUSERID 
	  FROM ACT_RU_TASK TASK 
		LEFT join ACT_RE_PROCDEF D on TASK.PROC_DEF_ID_ = D.ID_ 
	  left join ACT_RU_VARIABLE V ON V.PROC_INST_ID_=TASK.PROC_INST_ID_ 
	  LEFT JOIN ACT_RU_IDENTITYLINK I ON I.TASK_ID_ = TASK.ID_ 
		LEFT JOIN ACT_HI_PROCINST INS on TASK.PROC_INST_ID_ = INS.ID_
	  WHERE TASK.ASSIGNEE_ IS NULL AND I.TYPE_ = 'candidate'  AND TASK.SUSPENSION_STATE_ = '1'
	  ) ACTIVITI 
	 */
	
	
	
/*	--------- 当前步骤
	select 
	task.ID_,
	task.NAME_,
	task.TASK_DEF_KEY_,
	pins.BUSINESS_KEY_,
	procdef.KEY_,
	t.GROUP_USER
	from act_hi_procinst pins
	left join act_ru_task task on pins.PROC_INST_ID_ = task.PROC_INST_ID_
	left join act_re_procdef procdef on pins.PROC_DEF_ID_ = procdef.ID_
	left join 
	(
	select lk.TASK_ID_ as TASK_ID_,GROUP_CONCAT(u.user_name) as GROUP_USER from act_ru_identitylink lk left join se_user u on lk.USER_ID_ = u.user_id
	 where lk.TYPE_ = 'candidate' group by lk.TASK_ID_
	) t on task.ID_ = t.TASK_ID_
*/



/*
	 ------- 历史轨迹
	select
	taskhis.ID_,
	taskhis.NAME_,
	taskhis.TASK_DEF_KEY_,
	taskhis.START_TIME_,
	taskhis.END_TIME_,
	taskhis.ASSIGNEE_,
	pins.BUSINESS_KEY_,
	procdef.KEY_
	from
	act_hi_taskinst taskhis
	left join act_hi_procinst pins on taskhis.PROC_INST_ID_ = pins.PROC_INST_ID_
	left join act_re_procdef procdef on pins.PROC_DEF_ID_ = procdef.ID_
	*/
	
// ---- 查询当前流程实例及所有激活的用户待办
//	select 
//	distinct
//	task.name_ as taskName,
//	task.task_def_key_ as taskDefKey,
//	task.id_ as taskId,
//	task.CREATE_TIME_ as taskCreateTime,
//	instan.business_key_ as businessKey,
//	procdef.id_ as procDefId,
//	instan.START_TIME_ as instanStartTime
//	from act_ru_task task 
//	left join act_hi_procinst instan on task.proc_inst_id_ = instan.proc_inst_id_
//	left join act_re_procdef procdef on instan.proc_def_id_ = procdef.id_
//	order by instan.business_key_, instan.START_TIME_ desc , task.CREATE_TIME_ desc


	
	/**
	 * 获取流程定义中的节点
	 * @param procDefId
	 * @throws Exception
	 */
	public void getNodeOfProcessDefined(String procDefId) throws Exception{
        BpmnModel model = repositoryService.getBpmnModel(procDefId);
        if(model != null) { 
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for(FlowElement e : flowElements) {
                System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString()); 
               
                if("org.activiti.bpmn.model.UserTask".equals(e.getClass().getName())){
                	System.out.println(" ----------------------  Task Info [" + e.getName() + "] start ------------------ ");
                    System.out.println(e.getId());
                    System.out.println(e.getName());
                    System.out.println(e.getAttributes());
                    UserTask t= (UserTask)e;
                	System.out.println(t.getCandidateUsers());
                    System.out.println(" ----------------------  Task Info [" + e.getName() + "] end------------------ ");
                }
            }  
        }  
	}
	

	public ClassPathXmlApplicationContext getContext() {
		return context;
	}

	public void setContext(ClassPathXmlApplicationContext context) {
		this.context = context;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public FormService getFormService() {
		return formService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

}
