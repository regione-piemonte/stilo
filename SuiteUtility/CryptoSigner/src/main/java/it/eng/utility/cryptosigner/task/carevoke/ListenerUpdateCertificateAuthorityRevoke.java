package it.eng.utility.cryptosigner.task.carevoke;

//import it.sauronsoftware.cron4j.SchedulerListener;
//import it.sauronsoftware.cron4j.TaskExecutor;

import org.apache.log4j.Logger;

/**
 * Implementazione di un listener per il tracciamento delle fasi di
 * esecuzione di un task di aggiornamento di certificati {@link TaskUpdateCertificateAuthorityRevoke}
 * @author Michele Rigo
 *
 */
public class ListenerUpdateCertificateAuthorityRevoke /*implements SchedulerListener*/{

	Logger log = Logger.getLogger(ListenerUpdateCertificateAuthorityRevoke.class);
	
	
	/*public void taskFailed(TaskExecutor executor, Throwable e) {
		log.error("Errore Task Revoke Certificate Authority!", e);		
	}

	
	public void taskLaunching(TaskExecutor executor) {
		log.info("Task Revoke Certificate Authority avviato!");
	}

	
	public void taskSucceeded(TaskExecutor executor) {
		log.info("Task Revoke Certificate Authority correttamente eseguito!");
	}*/
}