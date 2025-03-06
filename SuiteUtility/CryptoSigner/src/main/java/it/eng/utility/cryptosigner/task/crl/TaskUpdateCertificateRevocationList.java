package it.eng.utility.cryptosigner.task.crl;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.data.SignerUtil;
//import it.sauronsoftware.cron4j.Task;
//import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.security.cert.X509CRL;

/**
 * Task di aggiornamento delle CRL
 * @author Rigo Michele
 * @verison 0.1
 */
public class TaskUpdateCertificateRevocationList /*extends Task*/{ 
	
	private String distributionPoint;
	
	public TaskUpdateCertificateRevocationList(String distributionPoint) {
		this.distributionPoint = distributionPoint;
	}
	
	
	
	/*public void execute(TaskExecutionContext context) throws RuntimeException {
		try{
			//In base all'url passato in ingresso recupero la CRL.
			X509CRL crl = SignerUtil.newInstance().getCrlByURL(distributionPoint);
			
			if (crl != null) {
				//Salvo la crl sullo storage per il certificato passato in ingresso.
				FactorySigner.getInstanceCRLStorage().insertCRL(crl, null, null, null);
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}*/
}