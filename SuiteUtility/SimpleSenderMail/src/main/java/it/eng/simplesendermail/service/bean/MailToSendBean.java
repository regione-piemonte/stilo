/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


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
