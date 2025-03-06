package it.eng.dm.sira.service.variables;

import it.eng.core.business.TFilterFetch;
import it.eng.dm.sira.dao.DaoActGeByteArray;
import it.eng.dm.sira.dao.DaoActReDeployment;
import it.eng.dm.sira.dao.DaoActReProcDef;
import it.eng.dm.sira.dao.DaoActRuTask;
import it.eng.dm.sira.dao.DaoTTaskDeadLine;
import it.eng.dm.sira.entity.ActGeBytearray;
import it.eng.dm.sira.entity.ActReDeployment;
import it.eng.dm.sira.entity.ActReProcdef;
import it.eng.dm.sira.entity.ActRuTask;
import it.eng.dm.sira.entity.TTaskDeadline;
import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.bean.DmtProcess;
import it.eng.spring.utility.SpringAppContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class VariableEngine {

	private static Logger log = Logger.getLogger(VariableEngine.class);

	public static final String DATA_INIZIO = "dt_inizio";

	public static final String DURATA = "durata";

	private static final String DELIMITER = ":";

	public List<String> getTasksDelayed(String data) throws Exception {
		List<String> tasksRes = new ArrayList<String>();
		ProcessesManager man = (ProcessesManager) SpringAppContext.getContext().getBean("processManager");
		List<DmtProcess> processi = man.selectProcessi(data);
		System.out.println("Processi trovati: " + processi.size());
		for (DmtProcess processo : processi) {
			String deploymentName = processo.getDeploymentName();
			DaoActReProcDef daoProcDef = new DaoActReProcDef();
			TFilterFetch<ActReProcdef> filterFetch = new TFilterFetch<ActReProcdef>();
			ActReProcdef filter = new ActReProcdef();
			filter.setDeploymentId(deploymentName);
			filterFetch.setFilter(filter);
			List<ActReProcdef> lista = daoProcDef.search(filterFetch).getData();
			if (lista.size() > 1) {
				throw new SiraBusinessException("più di un processo deployato per lo stesso deployment Name");
			}
			ActReProcdef toUse = lista.get(0);
			String deploymentId = toUse.getDeploymentId();
			DaoActReDeployment daoActDeploy = new DaoActReDeployment();
			TFilterFetch<ActReDeployment> ff = new TFilterFetch<ActReDeployment>();
			ActReDeployment dep = new ActReDeployment();
			dep.setId(deploymentId);
			ff.setFilter(dep);
			List<ActReDeployment> listaDeploy = daoActDeploy.search(ff).getData();
			if (listaDeploy.size() > 1) {
				throw new SiraBusinessException("più di un processo deployato per lo stesso deploymentId");
			}
			ActReDeployment actReDeployment = listaDeploy.get(0);
			DaoActGeByteArray daoByteArray = new DaoActGeByteArray();
			TFilterFetch<ActGeBytearray> tff = new TFilterFetch<ActGeBytearray>();
			ActGeBytearray byteArray = new ActGeBytearray();
			byteArray.setDeploymentId(actReDeployment.getId());
			byteArray.setGenerated(false);
			tff.setFilter(byteArray);
			List<ActGeBytearray> listaByte = daoByteArray.search(tff).getData();
			if (listaByte == null || listaByte.size() > 1)
				throw new Exception("impossibile individuare l'xml del flusso");
			if (listaByte != null && listaByte.size() > 0 && listaByte.get(0).getBytes() != null) {
				String xml = new String(listaByte.get(0).getBytes(), "UTF-8");
				System.out.println("xml: " + xml);
				Map<String, List<String>> variables = verifyXml(xml);
				Set<String> keys = variables.keySet();
				if (keys.size() > 0) {
					for (String chiave : keys) {
						DaoActRuTask daoRuTask = new DaoActRuTask();
						TFilterFetch<ActRuTask> fetchTask = new TFilterFetch<ActRuTask>();
						ActRuTask filtroTask = new ActRuTask();
						filtroTask.setTaskDefKey(chiave);
						filtroTask.setProcInstId(processo.getInstanceId());
						List<ActRuTask> tasks = daoRuTask.search(fetchTask).getData();
						if (tasks.size() > 0) {
							for (ActRuTask task : tasks) {
								DaoTTaskDeadLine daoDL = new DaoTTaskDeadLine();
								TFilterFetch<TTaskDeadline> ffDeadline = new TFilterFetch<TTaskDeadline>();
								TTaskDeadline taskDeadLine = new TTaskDeadline();
								taskDeadLine.setIdTask(task.getTaskDefKey());
								task.setProcInstId(processo.getInstanceId());
								ffDeadline.setFilter(taskDeadLine);
								List<TTaskDeadline> deadlines = daoDL.search(ffDeadline).getData();
								if (deadlines.size() > 0) {
									TTaskDeadline deadLine = deadlines.get(0);
									String durata = deadLine.getDurata();
									Date dataInizio = deadLine.getStartDate();
									Long start = dataInizio.getTime();
									Long now = System.currentTimeMillis();
									if (now - start > convertiDurata(durata)) {
										tasksRes.add(task.getId());
									}
								} else {
									log.warn("nessuna deadline settata per il procedimento");
								}
							}
						}
					}
				}
			}
		}
		return tasksRes;
	}

	private Long convertiDurata(String durata) {
		long durataTotale = 0;
		// 2d:10h:6m
		if (durata.contains(DELIMITER)) {
			StringTokenizer tokenizer = new StringTokenizer(durata, DELIMITER);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token.contains("h")) {
					int ore = Integer.parseInt(token.substring(0, token.length() - 1));
					durataTotale += ore * 3600000;
				} else {
					if (token.contains("m")) {
						int minuti = Integer.parseInt(token.substring(0, token.length() - 1));
						durataTotale += minuti * 60000;
					} else {
						if (token.contains("s")) {
							int secondi = Integer.parseInt(token.substring(0, token.length() - 1));
							durataTotale += secondi * 1000;
						}
					}
				}
			}
		} else {
			if (durata.contains("h")) {
				int ore = Integer.parseInt(durata.substring(0, durata.length() - 1));
				durataTotale += ore * 3600000;
			} else {
				if (durata.contains("m")) {
					int minuti = Integer.parseInt(durata.substring(0, durata.length() - 1));
					durataTotale += minuti * 60000;
				} else {
					if (durata.contains("s")) {
						int secondi = Integer.parseInt(durata.substring(0, durata.length() - 1));
						durataTotale += secondi * 1000;
					}
				}
			}
		}
		return durataTotale;
	}

	private Map<String, List<String>> verifyXml(String xml) {
		// List<String> tasks = new ArrayList<String>();
		Map<String, List<String>> variables = new HashMap<String, List<String>>();
		Scanner scanner = new Scanner(xml);
		while (scanner.hasNextLine()) {
			String toProcess = scanner.nextLine();
			if (toProcess.contains("<userTask id=")) {
				String idTask = toProcess.substring(toProcess.indexOf("id=") + 4, toProcess.indexOf("name=") - 2);
				// per ora non utilizzo il nome del task
				// String nomeTask = null;
				// if (toProcess.contains("activiti")) {
				// nomeTask = toProcess.substring(toProcess.indexOf("name=") + 6, toProcess.indexOf("activiti") - 2);
				// } else {
				// if (toProcess.contains("/"))
				// nomeTask = toProcess.substring(toProcess.indexOf("name=") + 6, toProcess.indexOf("/") - 1);
				// else
				// nomeTask = toProcess.substring(toProcess.indexOf("name=") + 6, toProcess.indexOf(">") - 1);
				// }
				String post = scanner.nextLine();
				if (post.contains("extensionElements")) {
					String postExtension = scanner.nextLine();
					while (!postExtension.contains("</userTask>")) {
						if (postExtension.trim().contains("activiti:formProperty")) {
							String idVar = postExtension.substring(postExtension.indexOf("id=") + 4, postExtension.indexOf("name=") - 2);
							// String nomeVar = postExtension.substring(postExtension.indexOf("name=") + 6, postExtension.indexOf("type") -
							// 2);
							if (idVar.equals(DATA_INIZIO) || idVar.equals(DURATA)) {
								if (variables.get(idTask) == null) {
									List<String> vars = new ArrayList<String>();
									vars.add(idVar);
									variables.put(idTask, vars);
								} else {
									variables.get(idTask).add(idVar);
								}
							}
						}
						if (scanner.hasNextLine()) {
							postExtension = scanner.nextLine();
						} else
							break;
					}
				}
			}
		}
		return variables;
	}

}
