/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TProfiliFruitoriMgoBean;
import it.eng.aurigamailbusiness.bean.TProfiliUtentiMgoBean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListaProfiliUtenteSuCasellaOut implements Serializable{

	private static final long serialVersionUID = -8575309914644916912L;

	private List<TProfiliUtentiMgoBean> profiliUtente;

	private List<TProfiliFruitoriMgoBean> profiliFrutiore;

	public List<TProfiliUtentiMgoBean> getProfiliUtente() {
		return profiliUtente;
	}

	public void setProfiliUtente(List<TProfiliUtentiMgoBean> profiliUtente) {
		this.profiliUtente = profiliUtente;
	}

	public List<TProfiliFruitoriMgoBean> getProfiliFrutiore() {
		return profiliFrutiore;
	}

	public void setProfiliFrutiore(List<TProfiliFruitoriMgoBean> profiliFrutiore) {
		this.profiliFrutiore = profiliFrutiore;
	}

}
