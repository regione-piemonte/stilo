package it.eng.utility.cryptosigner.task.caupdate;

//import it.sauronsoftware.cron4j.SchedulerListener;

//import it.sauronsoftware.cron4j.TaskExecutor;

import org.apache.log4j.Logger;

/**
 * Implementazione di un listener per il tracciamento delle fasi di
 * esecuzione di un task di aggiornamento di CA {@link TaskUpdateCertificateAuthorityList}
 * @author Michele Rigo
 *
 */
public class ListenerUpdateCertificateAuthorityList /*implements SchedulerListener*/{

	Logger log = Logger.getLogger(ListenerUpdateCertificateAuthorityList.class);
	
	
	/*public void taskFailed(TaskExecutor executor, Throwable e) {
		log.error("Errore Task Update Certificate Authority!", e);		
	}

	
	public void taskLaunching(TaskExecutor executor) {
		log.info("Task Update Certificate Authority avviato!");
	}

	
	public void taskSucceeded(TaskExecutor executor) {
		log.info("Task Update Certificate Authority correttamente eseguito!");
	}*/
}