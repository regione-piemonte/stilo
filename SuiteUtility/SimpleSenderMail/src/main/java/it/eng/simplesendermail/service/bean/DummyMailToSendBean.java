package it.eng.simplesendermail.service.bean;

import java.util.List;

public class DummyMailToSendBean {
	private String body;
	private String subject;
	private List<String> addressTo;
	private List<String> addressCc;
	private List<String> addressBcc;
	private boolean isHtml;
	private String from;
	private boolean confermaLettura;
	private List<AttachmentMailToSendBean> attachmentMailToSendBeans;
	
	/**
	 * Body della mail
	 * @return body dela mail
	 */
	public String getBody() {
		return body;
	}
	/**
	 * Setta il body della mail
	 * @param body della mail
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * Get oggetto della mail
	 * @return oggetto della mail
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * Setta l'oggetto della mail
	 * @param subject oggetto della mail
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * Get Lista degli indirizzi TO
	 * @return lista degli indirizzi TO
	 */
	public List<String> getAddressTo() {
		return addressTo;
	}
	/**
	 * Set Lista degli indirizzi TO
	 * @param addressTo lista degli indirizzi TO
	 */
	public void setAddressTo(List<String> addressTo) {
		this.addressTo = addressTo;
	}
	/**
	 * Get Lista degli indirizzi CC
	 * @param addressTo lista degli indirizzi CC
	 */
	public List<String> getAddressCc() {
		return addressCc;
	}
	/**
	 * Set Lista degli indirizzi CC
	 * @param addressTo lista degli indirizzi CC
	 */
	public void setAddressCc(List<String> addressCc) {
		this.addressCc = addressCc;
	}
	/**
	 * Get Lista degli indirizzi BCC
	 * @param addressTo lista degli indirizzi BCC
	 */
	public List<String> getAddressBcc() {
		return addressBcc;
	}
	/**
	 * Set Lista degli indirizzi BCC
	 * @param addressTo lista degli indirizzi BCC
	 */
	public void setAddressBcc(List<String> addressBcc) {
		this.addressBcc = addressBcc;
	}
	/**
	 * Restituisce true se la mail è in formato html
	 * @return
	 */
	public boolean isHtml() {
		return isHtml;
	}
	/**
	 * Specifica se la mail è in formato html
	 * @param isHtml
	 */
	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}
	/**
	 * Indirizzo di partenza
	 * @return
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * Setta l'indirizzo di partenza
	 * @param from
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * Attachments della mail
	 * @return attachments della mail
	 */
	public List<AttachmentMailToSendBean> getAttachmentMailToSendBeans() {
		return attachmentMailToSendBeans;
	}
	/**
	 * Attachments della mail
	 * @param attachmentMailToSendBeans Attachments della mail
	 */
	public void setAttachmentMailToSendBeans(
			List<AttachmentMailToSendBean> attachmentMailToSendBeans) {
		this.attachmentMailToSendBeans = attachmentMailToSendBeans;
	}
	public boolean isConfermaLettura() {
		return confermaLettura;
	}
	public void setConfermaLettura(boolean confermaLettura) {
		this.confermaLettura = confermaLettura;
	}
}
