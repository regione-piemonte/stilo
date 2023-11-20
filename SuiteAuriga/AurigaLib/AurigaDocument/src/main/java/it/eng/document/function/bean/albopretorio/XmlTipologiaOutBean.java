/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

/**
 * @author Antonio Peluso
 */

@XmlRootElement
public class XmlTipologiaOutBean {
	
	@NumeroColonna(numero = "1")
	private String idType;
	
	@NumeroColonna(numero = "2")
	private String descrizione;
	
	@NumeroColonna(numero = "13")
	private String idTypePadre;

	public String getIdType() {
		return idType;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIdTypePadre() {
		return idTypePadre;
	}

	public void setIdTypePadre(String idTypePadre) {
		this.idTypePadre = idTypePadre;
	}

}
