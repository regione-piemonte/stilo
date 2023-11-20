/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.bean.VisualBean;

import java.math.BigDecimal;
import java.util.Date;

public class ResponsabileFascicoloBean extends VisualBean {

	private BigDecimal idUOResponsabile;	
	private String typeNodo;	
	private String codice;
	private String descrizione;		
	private String descrizioneEstesa;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	private String flgDefault;
	
	public void setTypeNodo(String typeNodo) {
		this.typeNodo = typeNodo;
	}
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}
	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public String getFlgDefault() {
		return flgDefault;
	}
	public void setFlgDefault(String flgDefault) {
		this.flgDefault = flgDefault;
	}

	public BigDecimal getIdUOResponsabile() {
		return idUOResponsabile;
	}

	public void setIdUOResponsabile(BigDecimal idUOResponsabile) {
		this.idUOResponsabile = idUOResponsabile;
	}

	public String getTypeNodo() {
		return typeNodo;
	}
	
}
