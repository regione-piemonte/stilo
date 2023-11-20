/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class EliminazioneEmailBean extends OperazioneMassivaPostaElettronicaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<EliminazioneBean> listaEliminazione;
	private String messaggio;
	
	public List<EliminazioneBean> getListaEliminazione() {
		return listaEliminazione;
	}
	public void setListaEliminazione(List<EliminazioneBean> listaEliminazione) {
		this.listaEliminazione = listaEliminazione;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

}
