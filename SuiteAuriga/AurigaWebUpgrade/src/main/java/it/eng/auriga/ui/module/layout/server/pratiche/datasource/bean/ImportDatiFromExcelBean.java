/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;

public class ImportDatiFromExcelBean {

	private String uriExcel;
	private String mimeType;

	private List<HashMap<String, Object>> valoriAttrLista;
	private List<DettColonnaAttributoListaBean> dettAttrLista;
	private List<DatiXlsInErrorBean> listaDatiXlsInError;
	
	public String getUriExcel() {
		return uriExcel;
	}
	public void setUriExcel(String uriExcel) {
		this.uriExcel = uriExcel;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public List<DatiXlsInErrorBean> getListaDatiXlsInError() {
		return listaDatiXlsInError;
	}
	public void setListaDatiXlsInError(List<DatiXlsInErrorBean> listaDatiXlsInError) {
		this.listaDatiXlsInError = listaDatiXlsInError;
	}
	
	public List<DettColonnaAttributoListaBean> getDettAttrLista() {
		return dettAttrLista;
	}
	public void setDettAttrLista(List<DettColonnaAttributoListaBean> dettAttrLista) {
		this.dettAttrLista = dettAttrLista;
	}
	public List<HashMap<String, Object>> getValoriAttrLista() {
		return valoriAttrLista;
	}
	public void setValoriAttrLista(List<HashMap<String, Object>> valoriAttrLista) {
		this.valoriAttrLista = valoriAttrLista;
	}
	
}



