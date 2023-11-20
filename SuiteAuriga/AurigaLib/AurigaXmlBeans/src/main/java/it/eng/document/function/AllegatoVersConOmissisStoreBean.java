/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class AllegatoVersConOmissisStoreBean extends AllegatoStoreBean {
	
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
