/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaIdEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaXMLTagTagEmail;

@XmlRootElement(name="richiestaTagEmail")
public class TagRequest extends MutualInput {
	
	@XmlElement(name="emails",required=true)
	@ApiModelProperty(name="emails",required=true)
	private ListaIdEmail listaIdEmail = new ListaIdEmail();
	
	@XmlElement(name="tagEmails",required=true)
	@ApiModelProperty(name="tagEmails")
	private ListaXMLTagTagEmail listaXMLTag = new ListaXMLTagTagEmail();

	public ListaIdEmail getListaIdEmail() {
		return listaIdEmail;
	}
	public void setListaIdEmail(ListaIdEmail listaIdEmail) {
		this.listaIdEmail = listaIdEmail;
	}

	public ListaXMLTagTagEmail getListaXMLTag() {
		return listaXMLTag;
	}
	public void setListaXMLTag(ListaXMLTagTagEmail listaXMLTag) {
		this.listaXMLTag = listaXMLTag;
	}
	
}//TagRequest
