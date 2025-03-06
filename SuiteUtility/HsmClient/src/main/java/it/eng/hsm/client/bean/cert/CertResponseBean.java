package it.eng.hsm.client.bean.cert;

import java.util.List;

import it.eng.hsm.client.bean.MessageBean;

public class CertResponseBean {
	
	private MessageBean messageBean;
	private List<CertBean> certList;
	
	public MessageBean getMessageBean() {
		return messageBean;
	}
	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
	}
	public List<CertBean> getCertList() {
		return certList;
	}
	public void setCertList(List<CertBean> certList) {
		this.certList = certList;
	}
	
}
