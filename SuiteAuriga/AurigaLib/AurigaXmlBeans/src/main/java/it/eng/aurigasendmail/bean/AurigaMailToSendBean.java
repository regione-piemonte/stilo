/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AurigaMailToSendBean extends AurigaDummyMailToSendBean implements Serializable {


	private AurigaSmtpSenderBean smptSenderBean;
	
	/**
	 * Bean con le configurazioni per l'invio mail
	 * @return
	 */
	public AurigaSmtpSenderBean getSmptSenderBean() {
		return smptSenderBean;
	}
	/**
	 * Bean con le configurazioni per l'invio mail
	 * @param smptSenderBean
	 */
	public void setSmptSenderBean(AurigaSmtpSenderBean smptSenderBean) {
		this.smptSenderBean = smptSenderBean;
	}
}
