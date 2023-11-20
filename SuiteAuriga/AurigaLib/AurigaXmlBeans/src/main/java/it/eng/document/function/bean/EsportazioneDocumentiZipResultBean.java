/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Traccia il risultato dell'esecuzione del metodo generaDocumentoJob di EsportazioneDocumentiFormatoZip
 * @author D.Bragato
 *
 */
@XmlRootElement
public class EsportazioneDocumentiZipResultBean implements Serializable {

	private static final long serialVersionUID = 1L;

//	private String uri_xls_out;
//	private String uri_zip_out;
	private boolean successful;
	private String message;

//	public String getUri_xls_out() {
//		return uri_xls_out;
//	}
//	public void setUri_xls_out(String uri_xls_out) {
//		this.uri_xls_out = uri_xls_out;
//	}
//	
//	public String getUri_zip_out() {
//		return uri_zip_out;
//	}
//	public void setUri_zip_out(String uri_zip_out) {
//		this.uri_zip_out = uri_zip_out;
//	}
	
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
