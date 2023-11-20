/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;

public class FileDaIniettareBean {

	private DocumentBean fileIniezioneFile;
	private boolean selezionaIniezioneFile;
	
	public DocumentBean getFileIniezioneFile() {
		return fileIniezioneFile;
	}
	
	public void setFileIniezioneFile(DocumentBean fileIniezioneFile) {
		this.fileIniezioneFile = fileIniezioneFile;
	}
	
	public boolean isSelezionaIniezioneFile() {
		return selezionaIniezioneFile;
	}
	
	public void setSelezionaIniezioneFile(boolean selezionaIniezioneFile) {
		this.selezionaIniezioneFile = selezionaIniezioneFile;
	}
}
