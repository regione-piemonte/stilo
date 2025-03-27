/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import java.util.Date;

public class RelVsPraticheApplEsterneBean {
		
	private String idFolder;
	private String codApplEst;
	private String codPratica;	
	private Date tsAssociazioneApplEst;
	private Boolean flgDaAssociareAssociato;
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
	public Boolean getFlgDaAssociareAssociato() {
		return flgDaAssociareAssociato;
	}
	public void setFlgDaAssociareAssociato(Boolean flgDaAssociareAssociato) {
		this.flgDaAssociareAssociato = flgDaAssociareAssociato;
	}
	public String getFlgAssociazioneApplEst() {
		return flgAssociazioneApplEst;
	}
	public void setFlgAssociazioneApplEst(String flgAssociazioneApplEst) {
		this.flgAssociazioneApplEst = flgAssociazioneApplEst;
	}
	
}
