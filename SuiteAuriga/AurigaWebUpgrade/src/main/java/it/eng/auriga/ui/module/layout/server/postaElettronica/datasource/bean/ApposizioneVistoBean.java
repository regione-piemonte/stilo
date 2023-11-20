/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.common.OperazioneMassivaBean;

public class ApposizioneVistoBean extends OperazioneMassivaBean<ArchivioBean>{

	private String esito;
	private String motivazione;
	
	public String getEsito() {
		return esito;
	}
	
	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	public String getMotivazione() {
		return motivazione;
	}
	
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

}
