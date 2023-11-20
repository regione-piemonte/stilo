/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;
import it.eng.document.NumeroColonna;

/**
 * 
 * @author Cristiano Daniele
 *
 */

@XmlRootElement
public class DestinatariInvioMailXmlBean extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8525546102924049520L;
	/**
	 * indirizzo del destinatario
	 */
	@NumeroColonna(numero = "1")
	private String indirizzo;
	/**
	 * Tipo di destinatario: P = Principale, CC = In conoscenza, CCN = In conoscenza nascosta
	 */
	@NumeroColonna(numero = "2")
	private String tipo;

	/**
	 * @return the indirizzo
	 */
	public String getIndirizzo() {
		return indirizzo;
	}

	/**
	 * @param indirizzo
	 *            the indirizzo to set
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
