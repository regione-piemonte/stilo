package it.eng.client.applet.fileProvider;

import java.util.List;

import it.eng.common.bean.FileBean;
import it.eng.common.bean.HashFileBean;

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
