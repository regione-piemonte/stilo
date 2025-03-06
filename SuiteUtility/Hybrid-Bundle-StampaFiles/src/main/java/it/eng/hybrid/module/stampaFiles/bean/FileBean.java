package it.eng.hybrid.module.stampaFiles.bean;

import java.io.File;

public class FileBean {
	
	private File file;
	private String fileName;
	private String idFile;
	
	public FileBean() {
		super();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getIdFile() {
		return idFile;
	}

	public void setIdFile(String idFile) {
		this.idFile = idFile;
	}

}
