/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.utility.storageutil.HibernateStorageConfig;

@XmlRootElement
@XmlType(propOrder={"hibernateConfiguration", "nroMaxFiles", "rootFSDir", "rootAlias", "nroZeroPadding"})
public class BfileStorageConfig implements Serializable {

	private static final long serialVersionUID = -5570008452920602097L;

	private HibernateStorageConfig hibernateConfiguration;
	private int nroMaxFiles;
	private String rootFSDir;
	private String rootAlias;
	private int nroZeroPadding;

	@XmlElement(name = "hibernate-configuration")
	public HibernateStorageConfig getHibernateConfiguration() {
		return hibernateConfiguration;
	}
	public void setHibernateConfiguration(HibernateStorageConfig hibernateConfiguration) {
		this.hibernateConfiguration = hibernateConfiguration;
	}
	
	public int getNroMaxFiles() {
		return nroMaxFiles;
	}
	public void setNroMaxFiles(int nroMaxFiles) {
		this.nroMaxFiles = nroMaxFiles;
	}
	
	public String getRootFSDir() {
		return rootFSDir;
	}
	public void setRootFSDir(String rootFSDir) {
		this.rootFSDir = rootFSDir;
	}
	
	public String getRootAlias() {
		return rootAlias;
	}
	public void setRootAlias(String rootAlias) {
		this.rootAlias = rootAlias;
	}
	
	public int getNroZeroPadding() {
		return nroZeroPadding;
	}
	public void setNroZeroPadding(int nroZeroPadding) {
		this.nroZeroPadding = nroZeroPadding;
	}
	
}


