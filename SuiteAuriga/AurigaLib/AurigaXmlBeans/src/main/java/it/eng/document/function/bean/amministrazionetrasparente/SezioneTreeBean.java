/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class SezioneTreeBean {

	@NumeroColonna(numero = "1")
	private String id;
	
	@NumeroColonna(numero = "2")
	private String title;
	
	@NumeroColonna(numero = "3")
	private String idPadre;
	
	private List<SezioneTreeBean> submenu = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public List<SezioneTreeBean> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<SezioneTreeBean> submenu) {
		this.submenu = submenu;
	}
	
}
