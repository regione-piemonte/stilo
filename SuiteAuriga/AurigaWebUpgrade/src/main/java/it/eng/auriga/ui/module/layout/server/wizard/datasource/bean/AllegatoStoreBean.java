/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class AllegatoStoreBean {
	 @XmlVariabile(nome="#IdUD", tipo = TipoVariabile.SEMPLICE)
	 private Integer idUd;
	 @XmlVariabile(nome="#CodNaturaRelVsUD", tipo = TipoVariabile.SEMPLICE)
	 private String natura = "ALL";
	 @XmlVariabile(nome="#DesOgg", tipo = TipoVariabile.SEMPLICE)
	 private String descrizione;
	 @XmlVariabile(nome="#IdDocType", tipo = TipoVariabile.SEMPLICE)
	 private Integer idDocType;
	
	public Integer getIdUd() {
		return idUd;
	}
	public void setIdUd(Integer idUd) {
		this.idUd = idUd;
	}
	public String getNatura() {
		return natura;
	}
	public void setNatura(String natura) {
		this.natura = natura;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Integer getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(Integer idDocType) {
		this.idDocType = idDocType;
	}	 
}