/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;

public class TaskPropostaOrganigrammaFileFirmatiBean {

	private PropostaOrganigrammaBean protocolloOriginale;
	private TaskFileDaFirmareBean fileFirmati;
	
	public PropostaOrganigrammaBean getProtocolloOriginale() {
		return protocolloOriginale;
	}
	public void setProtocolloOriginale(PropostaOrganigrammaBean protocolloOriginale) {
		this.protocolloOriginale = protocolloOriginale;
	}
	public TaskFileDaFirmareBean getFileFirmati() {
		return fileFirmati;
	}
	public void setFileFirmati(TaskFileDaFirmareBean fileFirmati) {
		this.fileFirmati = fileFirmati;
	}
	
}
