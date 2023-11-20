/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OpzioniTimbroDocBean;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;

import java.util.HashMap;
import java.util.List;

public class FirmaMassivaFilesBean {

	private List<FileDaFirmareBean> files;
	private List<IdFileBean> daNonTrasmettere;
	private String commonName;
	private Boolean documentoPrincipaleNonFirmato;
	private HashMap<String, String> errorMessages;
	private OpzioniTimbroDocBean opzioniTimbro;

	public List<FileDaFirmareBean> getFiles() {
		return files;
	}

	public void setFiles(List<FileDaFirmareBean> files) {
		this.files = files;
	}

	public List<IdFileBean> getDaNonTrasmettere() {
		return daNonTrasmettere;
	}

	public void setDaNonTrasmettere(List<IdFileBean> daNonTrasmettere) {
		this.daNonTrasmettere = daNonTrasmettere;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public Boolean getDocumentoPrincipaleNonFirmato() {
		return documentoPrincipaleNonFirmato;
	}

	public void setDocumentoPrincipaleNonFirmato(Boolean documentoPrincipaleNonFirmato) {
		this.documentoPrincipaleNonFirmato = documentoPrincipaleNonFirmato;
	}
	
	public HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	public OpzioniTimbroDocBean getOpzioniTimbro() {
		return opzioniTimbro;
	}

	public void setOpzioniTimbro(OpzioniTimbroDocBean opzioniTimbro) {
		this.opzioniTimbro = opzioniTimbro;
	}
	
}
