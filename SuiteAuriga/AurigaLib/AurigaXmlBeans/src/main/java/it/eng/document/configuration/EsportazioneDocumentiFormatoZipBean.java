/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Permette di definire valori di configurazione necessari a EsportazioneDocumentiFormatoZip 
 * @author D.Bragato
 *
 */
public class EsportazioneDocumentiFormatoZipBean {

	private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
    private int workQueueSize;
    private String tempPath;

    public int getCorePoolSize() {
		return corePoolSize;
	}
    public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
    
    public int getMaximumPoolSize() {
		return maximumPoolSize;
	}
    public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
    
    public long getKeepAliveTime() {
		return keepAliveTime;
	}
    public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}
    
    public int getWorkQueueSize() {
		return workQueueSize;
	}
    public void setWorkQueueSize(int workQueueSize) {
		this.workQueueSize = workQueueSize;
	}
    
    public String getTempPath() {
		return tempPath;
	}
    public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
}
