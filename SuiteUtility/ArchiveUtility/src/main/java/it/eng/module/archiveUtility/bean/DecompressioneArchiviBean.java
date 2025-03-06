package it.eng.module.archiveUtility.bean;

import java.io.File;
import java.util.List;
import java.util.Map;

public class DecompressioneArchiviBean {

	private Map<String,String> erroriArchivi;
	private Map<String,String> erroriFiles;
	private Boolean error;
	private String outPath;
	private List<File> filesEstratti;

	public DecompressioneArchiviBean(){
		super();
	}
	
	public DecompressioneArchiviBean(Map<String, String> erroriArchivi, Map<String, String> erroriFiles, String outPath, Boolean error) {
		super();
		this.erroriArchivi = erroriArchivi;
		this.erroriFiles = erroriFiles;
		this.outPath = outPath;
		this.error = error;
	}

	public Map<String, String> getErroriArchivi() {
		return erroriArchivi;
	}

	public void setErroriArchivi(Map<String, String> erroriArchivi) {
		this.erroriArchivi = erroriArchivi;
	}

	public Map<String, String> getErroriFiles() {
		return erroriFiles;
	}

	public void setErroriFiles(Map<String, String> erroriFiles) {
		this.erroriFiles = erroriFiles;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}
	
	public List<File> getFilesEstratti() {
		return filesEstratti;
	}

	public void setFilesEstratti(List<File> filesEstratti) {
		this.filesEstratti = filesEstratti;
	}

	@Override
	public String toString() {
		return "DecompressioneArchiviBean [erroriArchivi=" + erroriArchivi + ", erroriFiles=" + erroriFiles + ", error="
				+ error + ", outPath=" + outPath + ", filesEstratti=" + filesEstratti + "]";
	}


}
