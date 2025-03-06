package it.eng.areas.hybrid.module.cryptoLight.sign;


import java.io.File;

import it.eng.common.type.SignerType;

public class FileBean {
	
	private File file;
	private Boolean isPdf=null;
	private Boolean isFirmato=null;
	private String fileNameConvertito;
	private String fileName;
	private File rootFileDirectory;
	private File outputFile=null;
	private SignerType tipoBusta=null;
	private boolean isFirmaCongiuntaRequired;
	private String idFile;
	private String isFirmaPresente=null;
	private String isFirmaValida=null;
	
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
		if( isFirmato == null)
			isFirmato = false;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getRootFileDirectory() {
		return rootFileDirectory;
	}

	public void setRootFileDirectory(File rootFileDirectory) {
		this.rootFileDirectory = rootFileDirectory;
	}
	
	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	
	public boolean isFirmaCongiuntaRequired() {
		return isFirmaCongiuntaRequired;
	}

	public void setFirmaCongiuntaRequired(boolean isFirmaCongiuntaRequired) {
		this.isFirmaCongiuntaRequired = isFirmaCongiuntaRequired;
	}

	public SignerType getTipoBusta() {
		return tipoBusta;
	}

	public void setTipoBusta(SignerType tipoBusta) {
		this.tipoBusta = tipoBusta;
	}
	
	public String getIsFirmaPresente() {
		return isFirmaPresente;
	}
	public void setIsFirmaPresente(String isFirmaPresente) {
		this.isFirmaPresente = isFirmaPresente;
	}
	public String getIsFirmaValida() {
		return isFirmaValida;
	}
	public void setIsFirmaValida(String isFirmaValida) {
		this.isFirmaValida = isFirmaValida;
	}

	@Override
	public String toString() {
		return "FileBean [file=" + file + ", isPdf=" + isPdf + ", isFirmato="
				+ isFirmato + ", fileNameConvertito=" + fileNameConvertito
				+ ", fileName=" + fileName + ", rootFileDirectory="
				+ rootFileDirectory + ", outputFile=" + outputFile
				+ ", tipoBusta=" + tipoBusta + ", isFirmaCongiuntaRequired="
				+ isFirmaCongiuntaRequired + "]";
	}

	public String getIdFile() {
		return idFile;
	}

	public void setIdFile(String idFile) {
		this.idFile = idFile;
	}

	
		
}
