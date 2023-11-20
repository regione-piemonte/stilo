/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;

public class ProtocollazioneRegEmergenzaBean extends ProtocollazioneBean {
	
	private String idRegEmergenza;	
	
	public String getIdRegEmergenza() {
		return idRegEmergenza;
	}
	public void setIdRegEmergenza(String idRegEmergenza) {
		this.idRegEmergenza = idRegEmergenza;
	}
	
}
