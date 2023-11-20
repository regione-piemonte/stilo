/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Traccia il risultato dell'esecuzione del metodo GeneraDocumentoJob di GestionEsportazioneAsincronaListe
 * @author massimo malvestio
 *
 */
@XmlRootElement
public class GenerazioneAsincronaResultBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean successful;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
}
