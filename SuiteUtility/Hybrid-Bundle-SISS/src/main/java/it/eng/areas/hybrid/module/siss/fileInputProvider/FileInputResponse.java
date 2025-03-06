package it.eng.areas.hybrid.module.siss.fileInputProvider;

import it.eng.areas.hybrid.module.siss.bean.FileBean;
import it.eng.areas.hybrid.module.siss.bean.HashFileBean;

import java.util.List;

public class FileInputResponse {

	private List<HashFileBean> hashfilebean;
 	
	private List<FileBean> fileBeanList;
	
	public List<HashFileBean> getHashfilebean() {
		return hashfilebean;
	}
	public void setHashfilebean(List<HashFileBean> hashfilebean) {
		this.hashfilebean = hashfilebean;
	}
	public List<FileBean> getFileBeanList() {
		return fileBeanList;
	}
	public void setFileBeanList(List<FileBean> fileBeanList) {
		this.fileBeanList = fileBeanList;
	}
	
}
