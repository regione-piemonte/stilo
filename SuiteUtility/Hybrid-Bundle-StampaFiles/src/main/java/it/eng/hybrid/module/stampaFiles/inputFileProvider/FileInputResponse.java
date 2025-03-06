package it.eng.hybrid.module.stampaFiles.inputFileProvider;

import it.eng.hybrid.module.stampaFiles.bean.FileBean;

import java.util.List;

public class FileInputResponse {

	private List<FileBean> fileBeanList;
	
	public List<FileBean> getFileBeanList() {
		return fileBeanList;
	}
	public void setFileBeanList(List<FileBean> fileBeanList) {
		this.fileBeanList = fileBeanList;
	}
	
}
