/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean per l'invio della mail protocollata
 * 
 * @author jravagnan
 */
@XmlRootElement
public class SenderMailProtocollataBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7025594314059680937L;

	private SenderBean mail;

	private RegistrazioneProtocollo regProt;

	private MailLoginBean login;

	public SenderBean getMail() {
		return mail;
	}

	public void setMail(SenderBean mail) {
		this.mail = mail;
	}

	public RegistrazioneProtocollo getRegProt() {
		return regProt;
	}

	public void setRegProt(RegistrazioneProtocollo regProt) {
		this.regProt = regProt;
	}

	public MailLoginBean getLogin() {
		return login;
	}

	public void setLogin(MailLoginBean login) {
		this.login = login;
	}
}