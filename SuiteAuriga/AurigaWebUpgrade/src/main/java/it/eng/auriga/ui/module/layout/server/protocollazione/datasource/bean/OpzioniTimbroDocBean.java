/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

/**
 * 
 * @author DANCRIST
 *
 */

public class OpzioniTimbroDocBean {
	
	private String rotazioneTimbro;
	private String posizioneTimbro;
	private String tipoPaginaTimbro;
	private Integer paginaDa;
	private Integer paginaA;
	
	public String getRotazioneTimbro() {
		return rotazioneTimbro;
	}

	public void setRotazioneTimbro(String rotazioneTimbro) {
		this.rotazioneTimbro = rotazioneTimbro;
	}
	
	public String getPosizioneTimbro() {
		return posizioneTimbro;
	}

	public void setPosizioneTimbro(String posizioneTimbro) {
		this.posizioneTimbro = posizioneTimbro;
	}

	public String getTipoPaginaTimbro() {
		return tipoPaginaTimbro;
	}

	public void setTipoPaginaTimbro(String tipoPaginaTimbro) {
		this.tipoPaginaTimbro = tipoPaginaTimbro;
	}
	
	public Integer getPaginaDa() {
		return paginaDa;
	}

	public void setPaginaDa(Integer paginaDa) {
		this.paginaDa = paginaDa;
	}

	public Integer getPaginaA() {
		return paginaA;
	}
	
	public void setPaginaA(Integer paginaA) {
		this.paginaA = paginaA;
	}

}
