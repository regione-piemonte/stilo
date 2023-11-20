/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;

import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ErroreRigaExcelBean;
import it.eng.bean.ExecutionResultBean;

public class CaricamentoDestinatariExcelBean<T> extends ExecutionResultBean<T> {
	
	private String mimetype;
	
	private String displayFileName;
	
	private String uri;
	
	private String companyId;
	
	private String idFoglio;
	
	private List<ErroreRigaExcelBean> listaExcelDatiInError;
	
	private String numRigheDestinatari;

	public String getMimetype() {
		return mimetype;
	}

	public String getDisplayFileName() {
		return displayFileName;
	}

	public String getUri() {
		return uri;
	}

	public String getCompanyId() {
		return companyId;
	}

	public List<ErroreRigaExcelBean> getListaExcelDatiInError() {
		return listaExcelDatiInError;
	}

	public String getNumRigheDestinatari() {
		return numRigheDestinatari;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public void setDisplayFileName(String displayFileName) {
		this.displayFileName = displayFileName;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setListaExcelDatiInError(List<ErroreRigaExcelBean> listaExcelDatiInError) {
		this.listaExcelDatiInError = listaExcelDatiInError;
	}

	public void setNumRigheDestinatari(String numRigheDestinatari) {
		this.numRigheDestinatari = numRigheDestinatari;
	}

	public String getIdFoglio() {
		return idFoglio;
	}

	public void setIdFoglio(String idFoglio) {
		this.idFoglio = idFoglio;
	}
	
}
