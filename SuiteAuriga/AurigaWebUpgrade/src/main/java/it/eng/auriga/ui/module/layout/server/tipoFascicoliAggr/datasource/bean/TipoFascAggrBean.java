/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.tipoFascicoliAggr.datasource.bean;

/**
 * 
 * @author cristiano
 *
 */

public class TipoFascAggrBean {

	private String idFascAggrTypePadre;
	private String nomeFascAggrTypePadre;

	/**
	 * @return the idFascAggrTypePadre
	 */
	public String getIdFascAggrTypePadre() {
		return idFascAggrTypePadre;
	}

	/**
	 * @param idFascAggrTypePadre
	 *            the idFascAggrTypePadre to set
	 */
	public void setIdFascAggrTypePadre(String idFascAggrTypePadre) {
		this.idFascAggrTypePadre = idFascAggrTypePadre;
	}

	/**
	 * @return the nomeFascAggrTypePadre
	 */
	public String getNomeFascAggrTypePadre() {
		return nomeFascAggrTypePadre;
	}

	/**
	 * @param nomeFascAggrTypePadre
	 *            the nomeFascAggrTypePadre to set
	 */
	public void setNomeFascAggrTypePadre(String nomeFascAggrTypePadre) {
		this.nomeFascAggrTypePadre = nomeFascAggrTypePadre;
	}

}
