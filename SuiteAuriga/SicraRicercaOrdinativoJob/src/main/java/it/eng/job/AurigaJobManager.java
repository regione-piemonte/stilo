/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.jobmanager.quartz.config.JobManager;

public class AurigaJobManager extends JobManager {
	
	private String businessType;
	private String serviceUrl;
	private int businessCallRetryNum = 1;
	private int businessCallRetryInterval = 0;

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	/**
	 * 
	 * @return numero di tentativi di chiamata ai servizi di business, Preimpostato a 1
	 */
	public int getBusinessCallRetryNum() {
		return businessCallRetryNum;
	}

	/**
	 * @see AurigaJobManager#getBusinessCallRetryNum()
	 * @param businessCallRetryNum
	 */
	public void setBusinessCallRetryNum(int businessCallRetryNum) {
		this.businessCallRetryNum = businessCallRetryNum;
	}

	/**
	 * 
	 * @return intervallo in millisecondi di attesa 
	 * prima di effettuare un nuovo tentativo di chiamata ai servizi di business
	 */
	public int getBusinessCallRetryInterval() {
		return businessCallRetryInterval;
	}

	/**
	 * @see AurigaJobManager#getBusinessCallRetryInterval()
	 * @param businessCallRetryInterval
	 */
	public void setBusinessCallRetryInterval(int businessCallRetryInterval) {
		this.businessCallRetryInterval = businessCallRetryInterval;
	}

}
