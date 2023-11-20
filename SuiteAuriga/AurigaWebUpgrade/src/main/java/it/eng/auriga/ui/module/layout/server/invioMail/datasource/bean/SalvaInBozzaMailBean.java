/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

/**
 * 
 * @author cristiano
 *
 */

public class SalvaInBozzaMailBean extends InvioMailBean {

	private static final long serialVersionUID = 1L;

	/**
	 * Id della bozza in fase di creazione o aggiornamento
	 */
	private String idEmailPrincipale;

	private List<ItemLavorazioneMailBean> listaItemInLavorazione;

	private List<InvioMailBean> listaIdEmailInoltrate;

	/**
	 * @return the idEmailBozza
	 */
	public String getIdEmailPrincipale() {
		return idEmailPrincipale;
	}

	/**
	 * @param idEmailBozza
	 *            the idEmailBozza to set
	 */
	public void setIdEmailPrincipale(String idEmailPrincipale) {
		this.idEmailPrincipale = idEmailPrincipale;
	}

	/**
	 * @return the listaItemInLavorazione
	 */
	public List<ItemLavorazioneMailBean> getListaItemInLavorazione() {
		return listaItemInLavorazione;
	}

	/**
	 * @param listaItemInLavorazione
	 *            the listaItemInLavorazione to set
	 */
	public void setListaItemInLavorazione(List<ItemLavorazioneMailBean> listaItemInLavorazione) {
		this.listaItemInLavorazione = listaItemInLavorazione;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<InvioMailBean> getListaIdEmailInoltrate() {
		return listaIdEmailInoltrate;
	}

	public void setListaIdEmailInoltrate(List<InvioMailBean> listaIdEmailInoltrate) {
		this.listaIdEmailInoltrate = listaIdEmailInoltrate;
	}

}
