/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean che mappa le configurazioni di FileSystemStorage
 * 
 * @author Mattia Zanin
 * 
 */
@XmlRootElement
public class FileSystemStorageConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String baseFolderPath;
	private String[] repositoryLocations;
	private String tempRepositoryBasePath;
	private int nroMaxFiles;
	private String archiveExt;

	public void setBaseFolderPath(String baseFolderPath) {
		this.baseFolderPath = baseFolderPath;
	}

	public String getBaseFolderPath() {
		return baseFolderPath;
	}

	public void setRepositoryLocations(String[] repositoryLocations) {
		this.repositoryLocations = repositoryLocations;
	}

	public String[] getRepositoryLocations() {
		return repositoryLocations;
	}

	public void setTempRepositoryBasePath(String tempRepositoryBasePath) {
		this.tempRepositoryBasePath = tempRepositoryBasePath;
	}

	public String getTempRepositoryBasePath() {
		return tempRepositoryBasePath;
	}

	public int getNroMaxFiles() {
		return nroMaxFiles;
	}

	public void setNroMaxFiles(int nroMaxFiles) {
		this.nroMaxFiles = nroMaxFiles;
	}

	public String getArchiveExt() {
		return archiveExt;
	}

	public void setArchiveExt(String archiveExt) {
		this.archiveExt = archiveExt;
	}

}
