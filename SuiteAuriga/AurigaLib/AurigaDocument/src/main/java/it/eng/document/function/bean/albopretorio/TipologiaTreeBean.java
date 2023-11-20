/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

/**
 * @author FEBUONO
 */

@XmlRootElement
public class TipologiaTreeBean {
	
	@NumeroColonna(numero = "1")
	private String idType;
	
	@NumeroColonna(numero = "2")
	private String descrizione;
	
	@NumeroColonna(numero = "13")
	private String idTypePadre;

	private List<TipologiaTreeBean> children = new ArrayList<>();

	public String getIdType() {
		return idType;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIdTypePadre() {
		return idTypePadre;
	}

	public void setIdTypePadre(String idTypePadre) {
		this.idTypePadre = idTypePadre;
	}

	public List<TipologiaTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<TipologiaTreeBean> children) {
		this.children = children;
	}

}
