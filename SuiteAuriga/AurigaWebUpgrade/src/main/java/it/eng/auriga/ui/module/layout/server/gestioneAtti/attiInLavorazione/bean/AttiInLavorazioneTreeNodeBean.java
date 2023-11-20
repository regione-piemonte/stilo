/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;

public class AttiInLavorazioneTreeNodeBean extends TreeNodeBean {

	private String descrizione;
	private String matchaFiltro;
	private String selezionabile;
	private String urlAttivita;
	private String messaggio;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getMatchaFiltro() {
		return matchaFiltro;
	}

	public void setMatchaFiltro(String matchaFiltro) {
		this.matchaFiltro = matchaFiltro;
	}

	public String getSelezionabile() {
		return selezionabile;
	}

	public void setSelezionabile(String selezionabile) {
		this.selezionabile = selezionabile;
	}

	public String getUrlAttivita() {
		return urlAttivita;
	}

	public void setUrlAttivita(String urlAttivita) {
		this.urlAttivita = urlAttivita;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

}
