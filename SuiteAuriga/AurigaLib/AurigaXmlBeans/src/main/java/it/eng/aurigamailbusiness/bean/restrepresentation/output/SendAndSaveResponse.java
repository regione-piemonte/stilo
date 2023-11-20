/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.SenderBean;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rispostaSpedisciESalvaEmail")
public class SendAndSaveResponse {
	
//	@XmlElementWrapper
	@XmlElement(name="emailElaborata")
	private List<SenderBean> sentMails;

//	@XmlElementWrapper
	@XmlElement(name="idEmail")
	private List<String> idEmails;

	
	public List<String> getIdEmails() {
		return idEmails;
	}
	public void setIdEmails(List<String> idEmails) {
		this.idEmails = idEmails;
	}

	public List<SenderBean> getSentMails() {
		return sentMails;
	}
	public void setSentMails(List<SenderBean> sentMails) {
		this.sentMails = sentMails;
	}

}
