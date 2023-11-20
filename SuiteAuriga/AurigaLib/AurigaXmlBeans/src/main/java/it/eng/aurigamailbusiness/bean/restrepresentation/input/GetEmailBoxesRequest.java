/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement(name = "richiestaElencoCaselleEmail")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "Richiesta per la lista delle caselle email")
public class GetEmailBoxesRequest {
	
	@XmlElement(name = "finalita", required = false) 
	@ApiModelProperty(example = "INVIO", allowableValues="INVIO, SMISTAMENTO", required = false, value = "Restringe l'estrazione alle solo caselle da cui si può inviare o di cui si è smistatori")
	private String finalita;
	
	@XmlElement(name = "tipoCasella", required = false) 
	@ApiModelProperty(example = "PEC", allowableValues="PEC, PEO", required = false, value = "Restringe l'estrazione alle solo caselle certificate (PEC) o ordinarie (PEO)")
	private String tipoCasella;
	
	@XmlElement(name = "indirizzoEmail", required = false) 
	@ApiModelProperty(example = "@pec.it", required = false, value = "Filtro sul nome account (solitamente non distingue tra maiuscole e minuscole)")
    private String indirizzoEmail;
	
	public String getFinalita() {
		return finalita;
	}
	
	public void setFinalita(String finalita) {
		this.finalita = finalita;
	}
	
	public String getTipoCasella() {
		return tipoCasella;
	}
	
	public void setTipoCasella(String tipoCasella) {
		this.tipoCasella = tipoCasella;
	}
	
	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}
	
	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}
	
	
}//GetEmailBoxesRequest
