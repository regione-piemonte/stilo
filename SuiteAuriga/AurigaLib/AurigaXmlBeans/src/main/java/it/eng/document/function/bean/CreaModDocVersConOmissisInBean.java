/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class CreaModDocVersConOmissisInBean extends CreaModDocumentoInBean {

	@XmlVariabile(nome = "#FlgVersConOmissis", tipo = TipoVariabile.SEMPLICE)
	private String flgVersConOmissis;
	
	@XmlVariabile(nome = "#IdDocVersIntegrale", tipo = TipoVariabile.SEMPLICE)
	private String idDocVersIntegrale;

	public String getFlgVersConOmissis() {
		return flgVersConOmissis;
	}

	public void setFlgVersConOmissis(String flgVersConOmissis) {
		this.flgVersConOmissis = flgVersConOmissis;
	}

	public String getIdDocVersIntegrale() {
		return idDocVersIntegrale;
	}

	public void setIdDocVersIntegrale(String idDocVersIntegrale) {
		this.idDocVersIntegrale = idDocVersIntegrale;
	}
	
}
