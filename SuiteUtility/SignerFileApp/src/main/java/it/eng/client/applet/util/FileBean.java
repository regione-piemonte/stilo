package it.eng.client.applet.util;

import java.io.File;

public class FileBean {
	
	private File file;
	private Boolean isPdf=null;
	private Boolean isFirmato=null;
	private String fileNameConvertito;
	
	public FileBean() {
		super();
	}
	
	public FileBean(File file, Boolean isPdf, Boolean isFirmato, String fileNameConvertito) {
		super();
		this.file = file;
		this.isPdf = isPdf;
		this.isFirmato = isFirmato;
		this.fileNameConvertito = fileNameConvertito;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Boolean getIsPdf() {
		return isPdf;
	}

	public void setIsPdf(Boolean isPdf) {
		this.isPdf = isPdf;
	}

	public Boolean getIsFirmato() {
		return isFirmato;
	}

	public void setIsFirmato(Boolean isFirmato) {
		this.isFirmato = isFirmato;
	}

	public String getFileNameConvertito() {
		return fileNameConvertito;
	}

	public void setFileNameConvertito(String fileNameConvertito) {
		this.fileNameConvertito = fileNameConvertito;
	}
	
	
}
