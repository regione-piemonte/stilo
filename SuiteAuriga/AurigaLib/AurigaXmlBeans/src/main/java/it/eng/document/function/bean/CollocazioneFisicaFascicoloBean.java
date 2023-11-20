/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CollocazioneFisicaFascicoloBean implements Serializable{
	@XmlVariabile(nome="IdTopografico", tipo=TipoVariabile.SEMPLICE)
	private String idTopografico;
	@XmlVariabile(nome="Descrizione", tipo=TipoVariabile.SEMPLICE)
	private String descrizione;
	@XmlVariabile(nome="CodRapido", tipo=TipoVariabile.SEMPLICE)
	private String codRapido;
	public void setIdTopografico(String idTopografico) {
		this.idTopografico = idTopografico;
	}
	public String getIdTopografico() {
		return idTopografico;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}
	public String getCodRapido() {
		return codRapido;
	}
}
