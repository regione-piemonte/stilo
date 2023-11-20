/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;

public class TaskFascicoloFileFirmatiBean {

	private ArchivioBean fascicoloOriginale;
	private TaskFileDaFirmareBean fileFirmati;
	
	public ArchivioBean getFascicoloOriginale() {
		return fascicoloOriginale;
	}
	public void setFascicoloOriginale(ArchivioBean fascicoloOriginale) {
		this.fascicoloOriginale = fascicoloOriginale;
	}
	public TaskFileDaFirmareBean getFileFirmati() {
		return fileFirmati;
	}
	public void setFileFirmati(TaskFileDaFirmareBean fileFirmati) {
		this.fileFirmati = fileFirmati;
	}
}
