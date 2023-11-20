/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Stato;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;
import java.util.List;

public class CriteriRicerca implements Serializable{
	
	@XmlVariabile(nome="IdDocType", tipo=TipoVariabile.SEMPLICE)
	private String idDocType;
	@XmlVariabile(nome="@StatiDettDoc", tipo=TipoVariabile.LISTA)
	private List<Stato> statiDettDoc;
	@XmlVariabile(nome="OggettoUD", tipo=TipoVariabile.SEMPLICE)
	private String oggettoUD;	

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public List<Stato> getStatiDettDoc() {
		return statiDettDoc;
	}

	public void setStatiDettDoc(List<Stato> statiDettDoc) {
		this.statiDettDoc = statiDettDoc;
	}

	public String getOggettoUD() {
		return oggettoUD;
	}

	public void setOggettoUD(String oggettoUD) {
		this.oggettoUD = oggettoUD;
	}
	

}
