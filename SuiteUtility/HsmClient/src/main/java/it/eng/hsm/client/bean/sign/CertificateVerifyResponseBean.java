package it.eng.hsm.client.bean.sign;

import java.util.List;

import it.eng.hsm.client.bean.MessageBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class CertificateVerifyResponseBean {
	
	private List<CertificateVerifyBean> listaCertificatiItagile;
	private CertificateVerifyBean certificateItagile;
	private MessageBean message;
	
	public List<CertificateVerifyBean> getListaCertificatiItagile() {
		return listaCertificatiItagile;
	}
	public void setListaCertificatiItagile(List<CertificateVerifyBean> listaCertificatiItagile) {
		this.listaCertificatiItagile = listaCertificatiItagile;
	}
	public CertificateVerifyBean getCertificateItagile() {
		return certificateItagile;
	}
	public void setCertificateItagile(CertificateVerifyBean certificateItagile) {
		this.certificateItagile = certificateItagile;
	}
	public MessageBean getMessage() {
		return message;
	}
	public void setMessage(MessageBean message) {
		this.message = message;
	}

}