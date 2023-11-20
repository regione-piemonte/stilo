/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;
import it.eng.document.NumeroColonna;

@XmlRootElement
public class TipoUOXmlBean {

	@NumeroColonna(numero = "1")	// cod. tipo UO
	private String codTipo;
	@NumeroColonna(numero = "2")	// descrizione tipo UO
	private String descTipo;
	@NumeroColonna(numero = "3")	// livelli gerarchici in cui è ammesso il tipo di UO
	private String livelliAmmessi;
	
	public String getCodTipo() {
		return codTipo;
	}
	public void setCodTipo(String codTipo) {
		this.codTipo = codTipo;
	}
	public String getDescTipo() {
		return descTipo;
	}
	public void setDescTipo(String descTipo) {
		this.descTipo = descTipo;
	}
	public String getLivelliAmmessi() {
		return livelliAmmessi;
	}
	public void setLivelliAmmessi(String livelliAmmessi) {
		this.livelliAmmessi = livelliAmmessi;
	}
	

	
}