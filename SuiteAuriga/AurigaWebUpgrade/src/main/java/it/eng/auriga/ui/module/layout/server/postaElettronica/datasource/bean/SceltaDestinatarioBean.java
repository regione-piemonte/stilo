/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class SceltaDestinatarioBean {
	
	private String tipoIndirizzo;
	private String tipoDestinatario;
	private List<MittenteProtBean> listaMittenti;
	
	public String getTipoIndirizzo() {
		return tipoIndirizzo;
	}
	public void setTipoIndirizzo(String tipoIndirizzo) {
		this.tipoIndirizzo = tipoIndirizzo;
	}
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}
	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public List<MittenteProtBean> getListaMittenti() {
		return listaMittenti;
	}
	public void setListaMittenti(List<MittenteProtBean> listaMittenti) {
		this.listaMittenti = listaMittenti;
	}

}