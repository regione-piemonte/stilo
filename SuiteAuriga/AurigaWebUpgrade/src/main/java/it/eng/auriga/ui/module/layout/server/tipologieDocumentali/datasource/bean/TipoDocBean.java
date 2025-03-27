/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.tipologieDocumentali.datasource.bean;

/**
 * 
 * @author cristiano
 *
 */

public class TipoDocBean {

	private String idDocumentoTypePadre;
	private String nomeDocumentoTypePadre;

	/**
	 * @return the idDocumentoTypePadre
	 */
	public String getIdDocumentoTypePadre() {
		return idDocumentoTypePadre;
	}

	/**
	 * @param idDocumentoTypePadre
	 *            the idDocumentoTypePadre to set
	 */
	public void setIdDocumentoTypePadre(String idDocumentoTypePadre) {
		this.idDocumentoTypePadre = idDocumentoTypePadre;
	}

	/**
	 * @return the nomeDocumentoTypePadre
	 */
	public String getNomeDocumentoTypePadre() {
		return nomeDocumentoTypePadre;
	}

	/**
	 * @param nomeDocumentoTypePadre
	 *            the nomeDocumentoTypePadre to set
	 */
	public void setNomeDocumentoTypePadre(String nomeDocumentoTypePadre) {
		this.nomeDocumentoTypePadre = nomeDocumentoTypePadre;
	}

}
