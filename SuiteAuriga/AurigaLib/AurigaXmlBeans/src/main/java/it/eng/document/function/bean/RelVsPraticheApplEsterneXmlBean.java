/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class RelVsPraticheApplEsterneXmlBean {

	@NumeroColonna(numero = "1")
	private String idFolder;
	
	@NumeroColonna(numero = "2")
	private String codApplEst;
	
	@NumeroColonna(numero = "3")
	private String codPratica;
	
	@NumeroColonna(numero = "4")
	@TipoData(tipo = Tipo.DATA)
	private Date tsAssociazioneApplEst;
	
	@NumeroColonna(numero = "5")
	private String flgDaAssociareAssociato;
	
	@NumeroColonna(numero = "6")
	private String flgAssociazioneApplEst;

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getCodApplEst() {
		return codApplEst;
	}

	public void setCodApplEst(String codApplEst) {
		this.codApplEst = codApplEst;
	}

	public String getCodPratica() {
		return codPratica;
	}

	public void setCodPratica(String codPratica) {
		this.codPratica = codPratica;
	}

	public Date getTsAssociazioneApplEst() {
		return tsAssociazioneApplEst;
	}

	public void setTsAssociazioneApplEst(Date tsAssociazioneApplEst) {
		this.tsAssociazioneApplEst = tsAssociazioneApplEst;
	}

	public String getFlgDaAssociareAssociato() {
		return flgDaAssociareAssociato;
	}

	public void setFlgDaAssociareAssociato(String flgDaAssociareAssociato) {
		this.flgDaAssociareAssociato = flgDaAssociareAssociato;
	}

	public String getFlgAssociazioneApplEst() {
		return flgAssociazioneApplEst;
	}

	public void setFlgAssociazioneApplEst(String flgAssociazioneApplEst) {
		this.flgAssociazioneApplEst = flgAssociazioneApplEst;
	}
	
}
