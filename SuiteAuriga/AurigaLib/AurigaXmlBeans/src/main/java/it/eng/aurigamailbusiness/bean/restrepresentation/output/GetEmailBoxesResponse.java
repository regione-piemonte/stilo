/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaCaselleInvRic;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rispostaElencoCaselleEmail")
@ApiModel(description = "Risposta con la lista delle caselle email")
public class GetEmailBoxesResponse {

	@XmlElement(name = "finalita")
	@ApiModelProperty(name="finalita", example = "INVIO")
	private String finalita;

	@XmlElement(name = "caselleEmail")
	@ApiModelProperty(name = "caselleEmail")
	private ListaCaselleInvRic listaCaselleInvRic;

	public String getFinalita() {
		return finalita;
	}

	public void setFinalita(String finalita) {
		this.finalita = finalita;
	}

	public ListaCaselleInvRic getListaCaselleInvRic() {
		return listaCaselleInvRic;
	}

	public void setListaCaselleInvRic(ListaCaselleInvRic listaCaselleInvRic) {
		this.listaCaselleInvRic = listaCaselleInvRic;
	}

}
