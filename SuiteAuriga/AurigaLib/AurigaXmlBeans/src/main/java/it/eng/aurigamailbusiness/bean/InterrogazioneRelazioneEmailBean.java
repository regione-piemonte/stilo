/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InterrogazioneRelazioneEmailBean extends AbstractBean {

	private static final long serialVersionUID = -3550804011262243976L;

	private String idEmail;

	private Dizionario dizionario;

	private Categoria categoria;

	private TipoRelazione tipoRelazione;

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public Dizionario getDizionario() {
		return dizionario;
	}

	public void setDizionario(Dizionario dizionario) {
		this.dizionario = dizionario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public TipoRelazione getTipoRelazione() {
		return tipoRelazione;
	}

	public void setTipoRelazione(TipoRelazione tipoRelazione) {
		this.tipoRelazione = tipoRelazione;
	}

}
