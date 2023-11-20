/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CompilaModelloAttivitaBean;

public class CompilaListaModelliNuovaPropostaAtto2CompletaBean {
	
	private String processId;
	private String idUd;
	private List<CompilaModelloAttivitaBean> listaRecordModelli;
	private NuovaPropostaAtto2CompletaBean dettaglioBean;
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public List<CompilaModelloAttivitaBean> getListaRecordModelli() {
		return listaRecordModelli;
	}

	public void setListaRecordModelli(List<CompilaModelloAttivitaBean> listaRecordModelli) {
		this.listaRecordModelli = listaRecordModelli;
	}

	public NuovaPropostaAtto2CompletaBean getDettaglioBean() {
		return dettaglioBean;
	}

	public void setDettaglioBean(NuovaPropostaAtto2CompletaBean dettaglioBean) {
		this.dettaglioBean = dettaglioBean;
	}
	
}