/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Modella una directory remota presente in un ftp oppure una directory locale, accomunando le proprietà comuni agli oggetti utilizzati da fs, ftp e sftp
 * 
 * @author denis bragato
 *
 */
public class FileBean {

	private String name;
	private String absolutePath;
	private String absoluteParentPath;
	// private Date modifiedDate;
	// private long size;
	// private boolean isFile;
	// private boolean isDirectory;
	// private File localFile;
	// private boolean alreadyExists;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getAbsoluteParentPath() {
		return absoluteParentPath;
	}

	public void setAbsoluteParentPath(String absoluteParentPath) {
		this.absoluteParentPath = absoluteParentPath;
	}

	@Override
	public String toString() {
		return "FileBean [name=" + name + ", absolutePath=" + absolutePath + ", absoluteParentPath=" + absoluteParentPath + "]";
	}

}
