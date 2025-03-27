/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class IstanzaConcSUAADSPXConcorrBean extends VisualBean{

	@NumeroColonna(numero="1")
	private String idUd;
	@NumeroColonna(numero="2")
	private String protocolloIstanza;
	@NumeroColonna(numero="3")
	private String codPratica;
	@NumeroColonna(numero="4")
	private String nroPubblicazione;
	
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getProtocolloIstanza() {
		return protocolloIstanza;
	}
	public void setProtocolloIstanza(String protocolloIstanza) {
		this.protocolloIstanza = protocolloIstanza;
	}
	public String getCodPratica() {
		return codPratica;
	}
	public void setCodPratica(String codPratica) {
		this.codPratica = codPratica;
	}
	public String getNroPubblicazione() {
		return nroPubblicazione;
	}
	public void setNroPubblicazione(String nroPubblicazione) {
		this.nroPubblicazione = nroPubblicazione;
	}
	
}
