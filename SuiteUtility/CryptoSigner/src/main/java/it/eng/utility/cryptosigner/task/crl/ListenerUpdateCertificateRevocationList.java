package it.eng.utility.cryptosigner.task.crl;


//import it.sauronsoftware.cron4j.SchedulerListener;

//import it.sauronsoftware.cron4j.TaskExecutor;

import org.apache.log4j.Logger;


/**
 * Implementazione di un listener per il tracciamento delle fasi di
 * esecuzione di un task di aggiornamento di CRL {@link TaskUpdateCertificateRevocationList}
 * @author Michele Rigo *
 */
public class ListenerUpdateCertificateRevocationList /*implements SchedulerListener*/{

	Logger log = Logger.getLogger(ListenerUpdateCertificateRevocationList.class);
	
	private String subjectDN; 
	
	public ListenerUpdateCertificateRevocationList(String subjectDN) {
		this.subjectDN = subjectDN;
	}
	
	
	
	/*public void taskFailed(TaskExecutor executor, Throwable e) {
		log.error("Errore Task upadate CRL "+subjectDN, e);		
	}

	
	public void taskLaunching(TaskExecutor executor) {
		log.info("Task CRL "+subjectDN+" avviato!");
	}

	
	public void taskSucceeded(TaskExecutor executor) {
		log.info("Task CRL "+subjectDN+" correttamente eseguito!");
	}*/
}