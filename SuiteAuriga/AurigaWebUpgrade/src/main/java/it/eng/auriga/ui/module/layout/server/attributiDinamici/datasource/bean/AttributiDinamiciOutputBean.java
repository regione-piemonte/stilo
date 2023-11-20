/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;

public class AttributiDinamiciOutputBean {

	private String rowId;
	private List<AttributoBean> attributiAdd;
	private HashMap<String, List<DettColonnaAttributoListaBean>> mappaDettAttrLista;
	private HashMap<String, List<HashMap<String, String>>> mappaValoriAttrLista;
	private HashMap<String, List<HashMap<String, String>>> mappaVariazioniAttrLista;
	private HashMap<String, DocumentBean> mappaDocumenti;
	private List<AttributoXRicercaBean> attributiXRicerca;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public List<AttributoBean> getAttributiAdd() {
		return attributiAdd;
	}

	public void setAttributiAdd(List<AttributoBean> attributiAdd) {
		this.attributiAdd = attributiAdd;
	}

	public HashMap<String, List<DettColonnaAttributoListaBean>> getMappaDettAttrLista() {
		return mappaDettAttrLista;
	}

	public void setMappaDettAttrLista(HashMap<String, List<DettColonnaAttributoListaBean>> mappaDettAttrLista) {
		this.mappaDettAttrLista = mappaDettAttrLista;
	}

	public HashMap<String, List<HashMap<String, String>>> getMappaValoriAttrLista() {
		return mappaValoriAttrLista;
	}

	public void setMappaValoriAttrLista(HashMap<String, List<HashMap<String, String>>> mappaValoriAttrLista) {
		this.mappaValoriAttrLista = mappaValoriAttrLista;
	}

	public HashMap<String, List<HashMap<String, String>>> getMappaVariazioniAttrLista() {
		return mappaVariazioniAttrLista;
	}

	public void setMappaVariazioniAttrLista(HashMap<String, List<HashMap<String, String>>> mappaVariazioniAttrLista) {
		this.mappaVariazioniAttrLista = mappaVariazioniAttrLista;
	}

	public HashMap<String, DocumentBean> getMappaDocumenti() {
		return mappaDocumenti;
	}

	public void setMappaDocumenti(HashMap<String, DocumentBean> mappaDocumenti) {
		this.mappaDocumenti = mappaDocumenti;
	}

	public List<AttributoXRicercaBean> getAttributiXRicerca() {
		return attributiXRicerca;
	}

	public void setAttributiXRicerca(List<AttributoXRicercaBean> attributiXRicerca) {
		this.attributiXRicerca = attributiXRicerca;
	}

}
