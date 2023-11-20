/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.TipologiaAssegnatario;
import it.eng.document.NumeroColonna;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="assegnatarioEmail")
public class RigaAssegnatariXMLAssegnaEmail {

	@NumeroColonna(numero="1")
	@XmlElement(name="tipoAssegnatario", required=true)
	private TipologiaAssegnatario tipo;
	
	@NumeroColonna(numero="2")
	@XmlElement(name="idAssegnatario", required=true)
	private String idAssegnatario;
	
	@NumeroColonna(numero="3")
	@XmlElement(name="messaggio")
	private String messaggio;	
	
	public TipologiaAssegnatario getTipo() {
		return tipo;
	}
	public void setTipo(TipologiaAssegnatario tipo) {
		this.tipo = tipo;
	}
	
	public String getIdAssegnatario() {
		return idAssegnatario;
	}
	public void setIdAssegnatario(String idAssegnatario) {
		this.idAssegnatario = idAssegnatario;
	}
	
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}	
	
}
