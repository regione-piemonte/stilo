/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class RestConfig {

	@XmlVariabile(nome = "urlEndpoint", tipo = TipoVariabile.SEMPLICE)
	private String urlEndpoint;
	
	public String getUrlEndpoint() {
		return urlEndpoint;
	}

	public void setUrlEndpoint(String urlEndpoint) {
		this.urlEndpoint = urlEndpoint;
	}

	
	
}
