/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Permette di definire valori di configurazione necessari a GestioneEsprotazioneAsincronaListe 
 * @author massimo malvestio
 *
 */
public class GestioneEsportazioneAsincronaListeBean {

	private int corePoolSize;
	
    private int maximumPoolSize;
    
    private long keepAliveTime;
    
    private int workQueueSize;

	private String dateFormat;
    
    /**
	 * @return the corePoolSize
	 */
	public int getCorePoolSize() {
		return corePoolSize;
	}

	/**
	 * @param corePoolSize the corePoolSize to set
	 */
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	/**
	 * @return the maximumPoolSize
	 */
	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	/**
	 * @param maximumPoolSize the maximumPoolSize to set
	 */
	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	/**
	 * @return the keepAliveTime
	 */
	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	/**
	 * @param keepAliveTime the keepAliveTime to set
	 */
	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	/**
	 * @return the workQueueSize
	 */
	public int getWorkQueueSize() {
		return workQueueSize;
	}

	/**
	 * @param workQueueSize the workQueueSize to set
	 */
	public void setWorkQueueSize(int workQueueSize) {
		this.workQueueSize = workQueueSize;
	}

	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}


    
}
