/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class AbilitaAnnullamentoInvioBean extends OperazioneMassivaPostaElettronicaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<PostaElettronicaBean> listaAnnullamenti;
	private String messaggio;

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	/**
	 * @return the listaAnnullamenti
	 */
	public List<PostaElettronicaBean> getListaAnnullamenti() {
		return listaAnnullamenti;
	}

	/**
	 * @param listaAnnullamenti
	 *            the listaAnnullamenti to set
	 */
	public void setListaAnnullamenti(List<PostaElettronicaBean> listaAnnullamenti) {
		this.listaAnnullamenti = listaAnnullamenti;
	}

}
