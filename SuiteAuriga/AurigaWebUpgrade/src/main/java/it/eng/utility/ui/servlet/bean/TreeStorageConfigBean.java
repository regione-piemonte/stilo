/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class TreeStorageConfigBean {

	private String baseFolderPath;
	private String tempRepositoryBasePath;
	private String nroMaxFiles;
	private String[] repositoryLocations;
	public String getBaseFolderPath() {
		return baseFolderPath;
	}
	public void setBaseFolderPath(String baseFolderPath) {
		this.baseFolderPath = baseFolderPath;
	}
	public String getTempRepositoryBasePath() {
		return tempRepositoryBasePath;
	}
	public void setTempRepositoryBasePath(String tempRepositoryBasePath) {
		this.tempRepositoryBasePath = tempRepositoryBasePath;
	}
	public String getNroMaxFiles() {
		return nroMaxFiles;
	}
	public void setNroMaxFiles(String nroMaxFiles) {
		this.nroMaxFiles = nroMaxFiles;
	}
	public String[] getRepositoryLocations() {
		return repositoryLocations;
	}
	public void setRepositoryLocations(String[] repositoryLocations) {
		this.repositoryLocations = repositoryLocations;
	}

}
