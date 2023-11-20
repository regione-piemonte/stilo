/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaEsitiArchiviaEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rispostaArchiviaEmail")
public class CloseResponse {
	
	@XmlTransient
	private Map<String,String> messages = new HashMap<String,String>(0);

	@XmlElement(name="esitiArchiviaEmail")
	@ApiModelProperty(name="esitiArchiviaEmail")
	private ListaEsitiArchiviaEmail esitiArchiviaEmailList;

	public ListaEsitiArchiviaEmail getEsitiArchiviaEmailList() {
		return esitiArchiviaEmailList;
	}
	public void setEsitiArchiviaEmailList(ListaEsitiArchiviaEmail esitiArchiviaEmailList) {
		this.esitiArchiviaEmailList = esitiArchiviaEmailList;
	}
	
//	public Map<String, String> getMessages() {
//		return messages;
//	}
//	public void setMessages(Map<String, String> messages) {
//		this.messages = messages;
//	}
	
}
