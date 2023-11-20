/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * Modella una directory remota presente in un ftp, accomunando le proprietà
 * comuni agli oggetti utilizzati da ftp e sftp
 * 
 * @author massimo malvestio
 *
 */
public class GenericRemoteFile {

	private String name;
	private Date modifiedDate;
	private long size;
	private boolean isFile;
	private boolean isDirectory;
	//private boolean isLink;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	public boolean isFile() {
		return isFile;
	}
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	
	public boolean isDirectory() {
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	
	/*public boolean isLink() {
		return isLink;
	}
	public void setLink(boolean isLink) {
		this.isLink = isLink;
	}*/
	
	@Override
	public String toString() {
		return String.format(
				"GenericRemoteFile [name=%s, modifiedDate=%s, size=%s, isFile=%s, isDirectory=%s, isLink=%s]", name,
				modifiedDate, size, isFile, isDirectory, "NOT_YET_IMPLEMENTED");
	}
}
