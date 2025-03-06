package it.eng.simplesendermail.service.bean;


public class MailToSendBean extends DummyMailToSendBean {


	private SmtpSenderBean smptSenderBean;
	
	/**
	 * Bean con le configurazioni per l'invio mail
	 * @return
	 */
	public SmtpSenderBean getSmptSenderBean() {
		return smptSenderBean;
	}
	/**
	 * Bean con le configurazioni per l'invio mail
	 * @param smptSenderBean
	 */
	public void setSmptSenderBean(SmtpSenderBean smptSenderBean) {
		this.smptSenderBean = smptSenderBean;
	}
}
