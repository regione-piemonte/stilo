/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Definisce la configurazione del job
 * 
 * @author massimo malvestio
 *
 */
public class GenerazioneFileAsincronaConfigurationBean {

	private String jobType;

	private String tempDir;

	private Integer maxNumRetrievablePages;

	private Integer paginationThreshold;

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public Integer getMaxNumRetrievablePages() {
		return maxNumRetrievablePages;
	}

	public void setMaxNumRetrievablePages(Integer maxNumRetrievablePages) {
		this.maxNumRetrievablePages = maxNumRetrievablePages;
	}

	public Integer getPaginationThreshold() {
		return paginationThreshold;
	}

	public void setPaginationThreshold(Integer paginationThreshold) {
		this.paginationThreshold = paginationThreshold;
	}

}
