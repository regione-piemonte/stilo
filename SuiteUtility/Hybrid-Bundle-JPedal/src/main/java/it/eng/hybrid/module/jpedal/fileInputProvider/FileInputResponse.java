package it.eng.hybrid.module.jpedal.fileInputProvider;

import java.io.File;

public class FileInputResponse {

	private byte[] fileContent;
	private String fileName;
	private File file;
	
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	
}
