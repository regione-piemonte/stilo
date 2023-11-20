/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean che mappa le configurazioni di CenteraStorage
 * @author Mattia Zanin
 *
 */
@XmlRootElement
public class CenteraStorageConfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private String[] centeraPoolAddresses;
	private String tempRepositoryBasePath;
	private String secondaryStorageUser;
	
	public void setCenteraPoolAddresses(String[] centeraPoolAddresses) {
		this.centeraPoolAddresses = centeraPoolAddresses;
	}
	
	public String[] getCenteraPoolAddresses() {
		return centeraPoolAddresses;
	}
	
	public void setTempRepositoryBasePath(String tempRepositoryBasePath) {
		this.tempRepositoryBasePath = tempRepositoryBasePath;
	}
	
	public String getTempRepositoryBasePath() {
		return tempRepositoryBasePath;
	}

	public String getSecondaryStorageUser() {
		return secondaryStorageUser;
	}

	public void setSecondaryStorageUser(String secondaryStorageUser) {
		this.secondaryStorageUser = secondaryStorageUser;
	}
	
}
