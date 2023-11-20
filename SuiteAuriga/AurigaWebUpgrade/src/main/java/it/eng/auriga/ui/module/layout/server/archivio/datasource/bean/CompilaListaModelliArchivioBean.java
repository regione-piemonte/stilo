/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CompilaModelloAttivitaBean;

public class CompilaListaModelliArchivioBean {
	
	private String processId;
	private String idFolder;
	private List<CompilaModelloAttivitaBean> listaRecordModelli;
	private ArchivioBean dettaglioBean;
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public List<CompilaModelloAttivitaBean> getListaRecordModelli() {
		return listaRecordModelli;
	}

	public void setListaRecordModelli(List<CompilaModelloAttivitaBean> listaRecordModelli) {
		this.listaRecordModelli = listaRecordModelli;
	}

	public ArchivioBean getDettaglioBean() {
		return dettaglioBean;
	}

	public void setDettaglioBean(ArchivioBean dettaglioBean) {
		this.dettaglioBean = dettaglioBean;
	}
	
}