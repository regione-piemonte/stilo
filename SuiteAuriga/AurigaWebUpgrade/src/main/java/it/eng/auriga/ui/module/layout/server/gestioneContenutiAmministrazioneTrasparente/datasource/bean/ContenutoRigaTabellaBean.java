/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;

public class ContenutoRigaTabellaBean {

	private String idContenuto;
	private List<DettColonnaAttributoListaBean> listDettColonnaAttributoLista;
	private Integer numeroRiga;
	private HashMap<String, Object>  contenuto;	
	private List<HashMap<String, Object>> contenutoList = new ArrayList<HashMap<String, Object>>();
	private String idRiga;
	
	
	private Date tsPubblDal;
	private Date tsPubblAl;
	
	private String uoLavoro;

	public String getIdContenuto() {
		return idContenuto;
	}

	public void setIdContenuto(String idContenuto) {
		this.idContenuto = idContenuto;
	}

	public List<DettColonnaAttributoListaBean> getListDettColonnaAttributoLista() {
		return listDettColonnaAttributoLista;
	}

	public void setListDettColonnaAttributoLista(List<DettColonnaAttributoListaBean> listDettColonnaAttributoLista) {
		this.listDettColonnaAttributoLista = listDettColonnaAttributoLista;
	}

	public Integer getNumeroRiga() {
		return numeroRiga;
	}

	public void setNumeroRiga(Integer numeroRiga) {
		this.numeroRiga = numeroRiga;
	}

	

	public Date getTsPubblDal() {
		return tsPubblDal;
	}

	public void setTsPubblDal(Date tsPubblDal) {
		this.tsPubblDal = tsPubblDal;
	}

	public Date getTsPubblAl() {
		return tsPubblAl;
	}

	public void setTsPubblAl(Date tsPubblAl) {
		this.tsPubblAl = tsPubblAl;
	}

	public HashMap<String, Object> getContenuto() {
		return contenuto;
	}

	public void setContenuto(HashMap<String, Object> contenuto) {
		this.contenuto = contenuto;
	}

	public List<HashMap<String, Object>> getContenutoList() {
		return contenutoList;
	}

	public void setContenutoList(List<HashMap<String, Object>> contenutoList) {
		this.contenutoList = contenutoList;
	}

	public String getUoLavoro() {
		return uoLavoro;
	}

	public void setUoLavoro(String uoLavoro) {
		this.uoLavoro = uoLavoro;
	}

	public String getIdRiga() {
		return idRiga;
	}

	public void setIdRiga(String idRiga) {
		this.idRiga = idRiga;
	}

}
