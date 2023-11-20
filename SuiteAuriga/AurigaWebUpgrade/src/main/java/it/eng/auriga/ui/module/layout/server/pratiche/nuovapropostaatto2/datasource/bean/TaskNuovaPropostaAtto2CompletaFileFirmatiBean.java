/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;

public class TaskNuovaPropostaAtto2CompletaFileFirmatiBean {

	private NuovaPropostaAtto2CompletaBean protocolloOriginale;
	private TaskFileDaFirmareBean fileFirmati;
	
	public NuovaPropostaAtto2CompletaBean getProtocolloOriginale() {
		return protocolloOriginale;
	}
	public void setProtocolloOriginale(NuovaPropostaAtto2CompletaBean protocolloOriginale) {
		this.protocolloOriginale = protocolloOriginale;
	}
	public TaskFileDaFirmareBean getFileFirmati() {
		return fileFirmati;
	}
	public void setFileFirmati(TaskFileDaFirmareBean fileFirmati) {
		this.fileFirmati = fileFirmati;
	}
	
}
