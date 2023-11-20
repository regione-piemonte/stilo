/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
