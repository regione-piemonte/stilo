/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaElaboraDocumentoResponse extends ContabiliaBaseResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String responseElaborazione;
	
	public String getResponseElaborazione() {
		return responseElaborazione;
	}
	
	public void setResponseElaborazione(String responseElaborazione) {
		this.responseElaborazione = responseElaborazione;
	}
	
	@Override
	public String toString() {
		return "ContabiliaElaboraDocumentoResponse [responseElaborazione=" + responseElaborazione + "]";
	}
	
}
