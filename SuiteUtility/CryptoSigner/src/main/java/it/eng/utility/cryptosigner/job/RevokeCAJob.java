package it.eng.utility.cryptosigner.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import it.eng.utility.cryptosigner.FactorySigner;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;


public class RevokeCAJob extends QuartzJobBean {

	private static final Logger log = LogManager.getLogger(RevokeCAJob.class);

	private Boolean active = false;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		if( active ) {
			verifyRevokeCA();
		}
	}
	
	public  void verifyRevokeCA() { 
		if( active ) {
			
			try {
				FactorySigner.getInstanceCertificateAuthority().revokeControll();
			} catch (CryptoSignerException e) {
				log.error("Eccezione RevokeCAJob", e);
			}
		}
			
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
