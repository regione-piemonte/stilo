/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.function.bean.KeyValueBean;

public class RecuperoDocumentoServiceBean {

	private String username;
	private String password;
	private String tipoRichiesta;
	private String descrizione;
	private List<KeyValueBean> chiaviRicerca;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTipoRichiesta() {
		return tipoRichiesta;
	}
	public void setTipoRichiesta(String tipoRichiesta) {
		this.tipoRichiesta = tipoRichiesta;
	}
	public List<KeyValueBean> getChiaviRicerca() {
		return chiaviRicerca;
	}
	public void setChiaviRicerca(List<KeyValueBean> chiaviRicerca) {
		this.chiaviRicerca = chiaviRicerca;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
