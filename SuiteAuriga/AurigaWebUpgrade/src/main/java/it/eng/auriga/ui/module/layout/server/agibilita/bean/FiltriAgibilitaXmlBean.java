/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;

public class FiltriAgibilitaXmlBean implements Serializable {
	
	@XmlVariabile(nome="Stato", tipo=TipoVariabile.SEMPLICE)
	private String stato;
	
	@XmlVariabile(nome="CognomeNomeRichiedente", tipo=TipoVariabile.SEMPLICE)
	private String cognomeNomeRichiedente;
	
	@XmlVariabile(nome="RichiesteDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date richiesteDal;
	
	@XmlVariabile(nome="RichiesteAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date richiesteAl;
	
	@XmlVariabile(nome="EvaseDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date evaseDal;
	
	@XmlVariabile(nome="EvaseAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date evaseAl;


	public String getCognomeNomeRichiedente() {
		return cognomeNomeRichiedente;
	}

	public Date getRichiesteDal() {
		return richiesteDal;
	}

	public Date getRichiesteAl() {
		return richiesteAl;
	}

	public Date getEvaseDal() {
		return evaseDal;
	}

	public Date getEvaseAl() {
		return evaseAl;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public void setCognomeNomeRichiedente(String cognomeNomeRichiedente) {
		this.cognomeNomeRichiedente = cognomeNomeRichiedente;
	}

	public void setRichiesteDal(Date richiesteDal) {
		this.richiesteDal = richiesteDal;
	}

	public void setRichiesteAl(Date richiesteAl) {
		this.richiesteAl = richiesteAl;
	}

	public void setEvaseDal(Date evaseDal) {
		this.evaseDal = evaseDal;
	}

	public void setEvaseAl(Date evaseAl) {
		this.evaseAl = evaseAl;
	}

}
