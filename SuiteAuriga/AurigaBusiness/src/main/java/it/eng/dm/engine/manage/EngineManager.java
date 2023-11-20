/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.module.config.SessionFile;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.performance.PerformanceLogger;
import it.eng.dm.engine.manage.bean.ActivitiProcess;
import it.eng.dm.engine.manage.bean.ActivitiTask;
import it.eng.spring.utility.SpringAppContext;

/**
 * classe di gestione dei processi
 * 
 * @author jravagnan
 * 
 */
public class EngineManager {

	private static Map<String, ProcessEngine> mapProcessEngine = null;
	private static ProcessEngine processEngine = null;
	
	private static Logger log = Logger.getLogger(EngineManager.class);
	
	private void init() {		
		if(mapProcessEngine == null) {
			ActivitiSessionFileConfigurator lActivitiSessionFileConfigurator = null;
		    try {
		    	lActivitiSessionFileConfigurator = (ActivitiSessionFileConfigurator) SpringAppContext.getContext().getBean("ActivitiSessionFileConfigurator");
		    } catch (Exception e) {}
			if(lActivitiSessionFileConfigurator != null && lActivitiSessionFileConfigurator.getSessions() != null) {	
				mapProcessEngine = new HashMap<String, ProcessEngine>();
				for (SessionFile lSessionFile : lActivitiSessionFileConfigurator.getSessions()){					
					PerformanceLogger lPerformanceLogger = new PerformanceLogger(getUserInfoForPerformanceLogger(), "ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(\"" + lSessionFile.getFileName() + "\").buildProcessEngine() to put in mapProcessEngine");
					lPerformanceLogger.start();
					mapProcessEngine.put(lSessionFile.getSessionName(), ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(lSessionFile.getFileName()).buildProcessEngine());
					lPerformanceLogger.end();
				}
			} 
        }
		if (processEngine == null) { 
			PerformanceLogger lPerformanceLogger = new PerformanceLogger(getUserInfoForPerformanceLogger(), "ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(\"activiti.cfg.xml\").buildProcessEngine() to set processEngine");
			lPerformanceLogger.start();
			processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
			lPerformanceLogger.end();
		}
	}

	public EngineManager() {
		init();
	}
	
	private ProcessEngine getProcessEngine() {
		String ente = null;
		try {
			ente = SubjectUtil.subject.get().getIdDominio();			
		} catch(Exception e) {}		
		if(StringUtils.isNotBlank(ente) && mapProcessEngine != null && mapProcessEngine.containsKey(ente)) {				
			return mapProcessEngine.get(ente);
		} else if(processEngine != null) {
			return processEngine;
		}		
		return null;		
	}

	/**
	 * completa il task con id come argomento
	 * 
	 * @param idTask
	 * @return
	 */
	public boolean completeTask(String idTask) {
		TaskService service = getProcessEngine().getTaskService();
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger(getUserInfoForPerformanceLogger(), "EngineManager.completeTask(" + idTask + ")");
		  	lPerformanceLogger.start();		
			service.complete(idTask);
			lPerformanceLogger.end();					
		} catch (Exception e) {
			log.error("Impossibile completare il task",e);
			return false;
		}
		return true;
	}

	/**
	 * fa partire il processo con la chiave in argomento
	 * 
	 * @param processKey
	 * @return
	 */
	public String startProcess(String processKey) {
		RuntimeService service = getProcessEngine().getRuntimeService();
		ProcessInstance instance = service.startProcessInstanceByKey(processKey);
		return instance.getProcessInstanceId();
	}

	/**
	 * mette in sospensione il processo con la chiave in argomento
	 * 
	 * @param processKey
	 * @return
	 */
	public void suspendProcess(String processInstanceId) {
		RuntimeService service = getProcessEngine().getRuntimeService();
		service.suspendProcessInstanceById(processInstanceId);
	}

	/**
	 * fa il deploy del processo partendo dall'xml in argomento
	 * 
	 * @param resourceName
	 * @return
	 */
	public String deployProcess(String resourceName) {
		RepositoryService repositoryService = getProcessEngine().getRepositoryService();
		Deployment deployment = repositoryService.createDeployment().addClasspathResource(resourceName).deploy();
		return deployment.getId();
	}

	/**
	 * restituisce le variabili di un task
	 * 
	 * @param idTask
	 * @return
	 */
	public Map<String, Object> getVariables(String idTask) {
		TaskService service = getProcessEngine().getTaskService();
		if (service != null) {
			return service.getVariables(idTask);
		}
		return null;
	}

	public void setVariableTask(String taskid, String name, String value) throws Exception {
		TaskService service = getProcessEngine().getTaskService();
		if (service != null) {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger(getUserInfoForPerformanceLogger(), "EngineManager.setVariableTask(" + taskid + ", " + name + ", " + value + ")");
		  	lPerformanceLogger.start();		
			service.setVariable(taskid, name, value);			
		  	lPerformanceLogger.end();		
		} else {
			log.error("Impossibile associare una variabile al seguente task id: "+taskid);
			throw new Exception("Impossibile associare una variabile al seguente task id: "+taskid);
		}
	}

	/**
	 * setta una variabile con relativo valore nel flusso d'esecuzione
	 * 
	 * @param executionId
	 * @param name
	 * @param value
	 */
	public void setVariable(String executionId, String name, String value) {
		RuntimeService service = getProcessEngine().getRuntimeService();
		service.setVariable(executionId, name, value);
	}

	/**
	 * cancella un task
	 * 
	 * @param idTask
	 */
	public void deleteTask(String idTask) {
		TaskService service = getProcessEngine().getTaskService();
		service.deleteTask(idTask);
	}

	/**
	 * aggiunge un task
	 * 
	 * @param idTask
	 */
	public Task newTask(String idTask) {
		TaskService service = getProcessEngine().getTaskService();
		return service.newTask();
	}

	/**
	 * data una chiave restituisce la lista di processi
	 * 
	 * @param key
	 * @return
	 */
	public List<ActivitiProcess> getListaProcessi(String key) {
		List<ActivitiProcess> processi = new ArrayList<ActivitiProcess>();
		RepositoryService repositoryService = getProcessEngine().getRepositoryService();
		if (StringUtils.isEmpty(key)) {
			List<ProcessDefinition> deployments = repositoryService.createProcessDefinitionQuery().list();
			for (ProcessDefinition deploy : deployments) {
				ActivitiProcess processo = new ActivitiProcess();
				processo.setId(deploy.getId());
				processo.setName(deploy.getName());
				processo.setKey(deploy.getKey());
				processo.setResource(deploy.getResourceName());
				processi.add(processo);
			}
		} else {
			List<ProcessDefinition> deployments = repositoryService.createProcessDefinitionQuery().processDefinitionKeyLike(key).list();
			for (ProcessDefinition deploy : deployments) {
				ActivitiProcess processo = new ActivitiProcess();
				processo.setId(deploy.getId());
				processo.setName(deploy.getName());
				processo.setKey(deploy.getKey());
				processo.setResource(deploy.getResourceName());
				processi.add(processo);
			}
		}
		return processi;
	}

	/**
	 * dato un utente restituisce la lista di task assegnati
	 * 
	 * @param userId
	 * @return
	 */
	public List<ActivitiTask> getListaTaskUser(String userId) {
		TaskService service = getProcessEngine().getTaskService();
		List<ActivitiTask> lista = new ArrayList<ActivitiTask>();
		List<Task> tasks = service.createTaskQuery().taskAssignee(userId).list();
		for (Task ta : tasks) {
			ActivitiTask at = new ActivitiTask();
			at.setDefinition(ta.getDescription());
			at.setId(ta.getId());
			at.setAssignee(ta.getAssignee());
			at.setKey(ta.getTaskDefinitionKey());
			at.setName(ta.getName());
			at.setPriority(String.valueOf(ta.getPriority()));
			at.setOwner(ta.getOwner());
			lista.add(at);
		}
		return lista;
	}

	/**
	 * dato un id di processo restituisce la lista dei task
	 * 
	 * @param processId
	 * @return
	 */
	public List<ActivitiTask> getListaTask(String processId) {
		TaskService service = getProcessEngine().getTaskService();
		List<ActivitiTask> lista = new ArrayList<ActivitiTask>();
		List<Task> tasks = service.createTaskQuery().processDefinitionId(processId).list();
		for (Task ta : tasks) {
			ActivitiTask at = new ActivitiTask();
			at.setDefinition(ta.getDescription());
			at.setId(ta.getId());
			at.setAssignee(ta.getAssignee());
			at.setKey(ta.getTaskDefinitionKey());
			at.setName(ta.getName());
			at.setPriority(String.valueOf(ta.getPriority()));
			at.setOwner(ta.getOwner());
			lista.add(at);
		}
		return lista;
	}

	/**
	 * dato l'id dell'istanza restituisce la lista di task
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public List<ActivitiTask> getListaTaskIntance(String processInstanceId) {
		TaskService service = getProcessEngine().getTaskService();
		List<ActivitiTask> lista = new ArrayList<ActivitiTask>();
		List<Task> tasks = service.createTaskQuery().processInstanceId(processInstanceId).list();
		for (Task ta : tasks) {
			ActivitiTask at = new ActivitiTask();
			at.setDefinition(ta.getDescription());
			at.setId(ta.getId());
			at.setAssignee(ta.getAssignee());
			at.setKey(ta.getTaskDefinitionKey());
			at.setName(ta.getName());
			at.setPriority(String.valueOf(ta.getPriority()));
			at.setOwner(ta.getOwner());
			lista.add(at);
		}
		return lista;
	}

	/**
	 * restituisce l'execution id a partire dall' id di istanza
	 * 
	 * @param procInstId
	 * @return
	 */
	public String getExecutionId(String procInstId) {
		RuntimeService rs = getProcessEngine().getRuntimeService();
		List<Execution> lista = rs.createExecutionQuery().processInstanceId(procInstId).list();
		return lista.get(0).getId();
	}

	/**
	 * restituisce la lista degli utenti
	 * 
	 * @return
	 */
	public List<User> getUserList() {
		List<User> users = getProcessEngine().getIdentityService().createUserQuery().list();
		return users;
	}
	
	/**
	 * Restituisce la stringa contenente le informazioni utente da riportare nel performance log
	 */
	private static String getUserInfoForPerformanceLogger() {
		// Inserire eventuali informazioni utili nel log
		return "";
	}

}
