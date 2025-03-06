package it.eng.utility.cryptosigner;

//import it.sauronsoftware.cron4j.Scheduler;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

/**
 * Definisce il singleton che si occupa della configurazione del 
 * CryptoSigner in termini di:
 * <ul>
 * 	<li>Task schedulati</li>
 * 	<li>Componenti definiti nel contesto Spring</li>
 * </ul>
 * @author Michele Rigo
 *
 */
public class CryptoSingleton {

	private CryptoSingleton() {}
	
	private static CryptoSingleton singleton = null;
	
	public static synchronized CryptoSingleton getInstance() {
		if (singleton==null) {
			singleton = new CryptoSingleton();
		}
		return singleton;
	}

	/**
	 * Lista dei task attivi per l'update delle CRL
	 */

	//private List<ScheduleBean> tasks;
	/**
	 * Application Context di spring per la cofigurazione
	 */
	private ApplicationContext context;

	/**
	 * Registra uno scheduler sul singleton
	 * @param scheduler
	 */
	/*public void registerSchedule(Scheduler scheduler,String id,String subjectDN) {
		if (tasks==null) {
			tasks = new ArrayList<ScheduleBean>();	
		}
		boolean insert = true;
		ScheduleBean bean = new ScheduleBean();
		bean.setId(id);
		bean.setSchedule(scheduler);
		bean.setSubjectDN(subjectDN);
		
		for (int i=0;i<tasks.size();i++) {
			if (tasks.get(i).getSubjectDN().equals(subjectDN)) {
				insert = false;
				tasks.set(i, bean);
			}
		}
		if (insert) {
			tasks.add(bean);		
		}
		
	}*/
	
	/**
	 * Recupera la lista dei task attivi per l'update delle CRL
	 * @return list
	 */
	/*public List<ScheduleBean> getTasks() {
		return tasks;
	}*/

	/**
	 * Definisce la lista dei task attivi per l'update delle CRL
	 * @param tasks
	 */
	/*public void setTasks(List<ScheduleBean> tasks) {
		this.tasks = tasks;
	}*/
	
	/**
	 * Recupera il bean di configurazione configurato nel contesto spring
	 * @return configuration
	 */
	public CryptoConfiguration getConfiguration() {
		return (CryptoConfiguration)context.getBean(CryptoConstants.CRYPTO_CONFIGURATION);
	}

	/**
	 * Definisce l'application context di spring per la cofigurazione
	 * @param context
	 */
	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	/**
	 * Recupera l'Application Context di spring per la cofigurazione
	 * @return context
	 */
	protected ApplicationContext getContext() {
		return this.context;
	}
	
	/**
	 * Recupera il task di controllo revoca dei certificati di certificazione 
	 * @return scheduler
	 */
	/*public Scheduler getCARevokeTask() {
		Scheduler ret = null;
		for (int i=0;i<this.tasks.size();i++) {
			if (this.tasks.get(i).getSubjectDN().equals(CryptoConstants.CA_REVOKE_TASK)) {
				//Task trovato
				ret = this.tasks.get(i).getSchedule();
				break;
			}
		}
		return ret;
	}*/
	
	/**
	 * Recupera il task di update dei certificati di certificazione 
	 * @return scheduler
	 */
	/*public Scheduler getCAUpdateTask() {
		Scheduler ret = null;
		for (int i=0;i<this.tasks.size();i++) {
			if (this.tasks.get(i).getSubjectDN().equals(CryptoConstants.CA_REVOKE_TASK)) {
				//Task trovato
				ret = this.tasks.get(i).getSchedule();
				break;
			}
		}
		return ret;
	}*/

	/**
	 * Bean contente le informazioni dei task schedulati
	 * @author Rigo Michele
	 * @version 0.1
	 */
	/*public class ScheduleBean{
		private Scheduler schedule;
		private String id;
		private String subjectDN;
		
		public Scheduler getSchedule() {
			return schedule;
		}
		public void setSchedule(Scheduler schedule) {
			this.schedule = schedule;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getSubjectDN() {
			return subjectDN;
		}
		public void setSubjectDN(String subjectDN) {
			this.subjectDN = subjectDN;
		}
	}*/
}