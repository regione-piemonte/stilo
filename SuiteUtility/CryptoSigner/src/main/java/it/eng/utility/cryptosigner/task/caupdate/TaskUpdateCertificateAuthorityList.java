package it.eng.utility.cryptosigner.task.caupdate;

import org.apache.log4j.Logger;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
//import it.sauronsoftware.cron4j.Task;
//import it.sauronsoftware.cron4j.TaskExecutionContext;

/**
 * Task di aggiornamento delle CA
 * 
 * @author Rigo Michele
 * @verison 0.1
 */
public class TaskUpdateCertificateAuthorityList /*extends Task*/ {

	private static final Logger log = Logger.getLogger(TaskUpdateCertificateAuthorityList.class);

	/*public void execute(TaskExecutionContext context) throws RuntimeException {
		try {
			FactorySigner.getInstanceCertificateAuthority().updateCertificate();
		} catch (CryptoSignerException e) {
			log.error("Eccezione TaskUpdateCertificateAuthorityList", e);
		}
	}*/
}