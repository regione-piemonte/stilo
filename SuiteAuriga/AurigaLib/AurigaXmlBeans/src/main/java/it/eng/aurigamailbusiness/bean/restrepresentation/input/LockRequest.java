/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaIdEmail;

@XmlRootElement(name="richiestaLockEmail")
public class LockRequest extends MutualInput {
	
	@XmlElement(name="emails",required=true)
	@ApiModelProperty(name="emails", required=true)
	private ListaIdEmail listaIdEmail = new ListaIdEmail();
	
	@XmlElement(name="motivi")
	private String motivi;
	
	@XmlElement(name="idUtenteDestinatarioLock",required=true)
	@ApiModelProperty(required=true)
	protected String idUtenteDestinatarioLock;
	
	public String getIdUtenteDestinatarioLock() {
		return idUtenteDestinatarioLock;
	}
	public void setIdUtenteDestinatarioLock(String idUtenteDestinatarioLock) {
		this.idUtenteDestinatarioLock = idUtenteDestinatarioLock;
	}
	
	public ListaIdEmail getListaIdEmail() {
		return listaIdEmail;
	}
	public void setListaIdEmail(ListaIdEmail listaIdEmail) {
		this.listaIdEmail = listaIdEmail;
	}
	
	public String getMotivi() {
		return motivi;
	}
	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}
	
}//LockRequest
