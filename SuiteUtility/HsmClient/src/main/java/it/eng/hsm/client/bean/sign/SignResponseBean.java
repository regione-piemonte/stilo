package it.eng.hsm.client.bean.sign;

import it.eng.hsm.client.bean.MessageBean;


import java.util.ArrayList;
import java.util.List;


public class SignResponseBean {
	
	private MessageBean message;
	private List<HashResponseBean> hashResponseBean = new ArrayList<HashResponseBean>();
	private List<FileResponseBean> fileResponseBean = new ArrayList<FileResponseBean>();
	
	public List<FileResponseBean> getFileResponseBean() {
		return fileResponseBean;
	}

	public void setFileResponseBean(List<FileResponseBean> fileResponseBean) {
		this.fileResponseBean = fileResponseBean;
	}

	public List<HashResponseBean> getHashResponseBean() {
		return hashResponseBean;
	}

	public void setHashResponseBean(List<HashResponseBean> hashResponseBean) {
		this.hashResponseBean = hashResponseBean;
	}

	public MessageBean getMessage() {
		return message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}
	
}
