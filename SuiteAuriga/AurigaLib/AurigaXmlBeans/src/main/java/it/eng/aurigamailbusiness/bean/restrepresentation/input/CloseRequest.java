/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaIdEmail;

@XmlRootElement(name="richiestaArchiviaEmail")
public class CloseRequest extends MutualInput {

	@XmlElement(name="emails",required=true)
	@ApiModelProperty(name="emails", required=true)
	private ListaIdEmail listaIdEmail = new ListaIdEmail();
	
	@XmlElement(name="motivi")
	private String motivi;
	
//	@XmlElement(name="operazioneRichiesta")
//    private String operazioneRichiesta;
	
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
	
//	public String getOperazioneRichiesta() {
//		return operazioneRichiesta;
//	}
//	public void setOperazioneRichiesta(String operazioneRichiesta) {
//		this.operazioneRichiesta = operazioneRichiesta;
//	}
	
}//CloseRequest
