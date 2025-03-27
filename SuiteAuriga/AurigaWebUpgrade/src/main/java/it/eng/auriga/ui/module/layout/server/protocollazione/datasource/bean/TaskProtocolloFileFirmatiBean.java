/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

public class TaskProtocolloFileFirmatiBean {

	private ProtocollazioneBean protocolloOriginale;
	private TaskFileDaFirmareBean fileFirmati;
	
	public ProtocollazioneBean getProtocolloOriginale() {
		return protocolloOriginale;
	}
	public void setProtocolloOriginale(ProtocollazioneBean protocolloOriginale) {
		this.protocolloOriginale = protocolloOriginale;
	}
	public TaskFileDaFirmareBean getFileFirmati() {
		return fileFirmati;
	}
	public void setFileFirmati(TaskFileDaFirmareBean fileFirmati) {
		this.fileFirmati = fileFirmati;
	}
}
