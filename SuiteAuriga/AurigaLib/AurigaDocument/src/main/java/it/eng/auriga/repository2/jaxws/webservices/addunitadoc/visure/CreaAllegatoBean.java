/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class CreaAllegatoBean {
	 
	 @XmlVariabile(nome="#IdUD", tipo = TipoVariabile.SEMPLICE)
	 private String idUd;
	 
	 @XmlVariabile(nome="#CodNaturaRelVsUD", tipo = TipoVariabile.SEMPLICE)
	 private String codNaturaRelVsUd = "ALL";
	 
	 
	 
	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getCodNaturaRelVsUd() {
		return codNaturaRelVsUd;
	}

	public void setCodNaturaRelVsUd(String codNaturaRelVsUd) {
		this.codNaturaRelVsUd = codNaturaRelVsUd;
	}

	

	
	
}