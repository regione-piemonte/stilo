/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData.Tipo;
import it.eng.document.TipoData;

import java.math.BigDecimal;
import java.util.Date;


public class AltriAllegatiFatturaXmlBean {

	@NumeroColonna(numero = "1")
	private String idKey;

	@NumeroColonna(numero = "2")
	private String displayFileName;

	@NumeroColonna(numero = "3")
	private String tipologia;
	
	@NumeroColonna(numero = "4")
	private String valori;
	
	@NumeroColonna(numero = "5")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dtInizioValidita;
	
	@NumeroColonna(numero = "6")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dtFineValidita;
	
	@NumeroColonna(numero = "7")
	private BigDecimal idUd;
	
	@NumeroColonna(numero = "8")
	private String descUtenteIns;
	
	@NumeroColonna(numero = "9")
	private String descUtenteUltMod;
	
	@NumeroColonna(numero = "10")
	@TipoData(tipo=Tipo.DATA)
	private Date dataIns;
	
	@NumeroColonna(numero = "11")
	@TipoData(tipo=Tipo.DATA)
	private Date dataUltMod;
	
	@NumeroColonna(numero = "12")
	private Integer flgAnn;

	public String getIdKey() {
		return idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getDisplayFileName() {
		return displayFileName;
	}

	public void setDisplayFileName(String displayFileName) {
		this.displayFileName = displayFileName;
	}

	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getDescUtenteIns() {
		return descUtenteIns;
	}

	public void setDescUtenteIns(String descUtenteIns) {
		this.descUtenteIns = descUtenteIns;
	}

	public String getDescUtenteUltMod() {
		return descUtenteUltMod;
	}

	public void setDescUtenteUltMod(String descUtenteUltMod) {
		this.descUtenteUltMod = descUtenteUltMod;
	}

	public Date getDataIns() {
		return dataIns;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public Date getDataUltMod() {
		return dataUltMod;
	}

	public void setDataUltMod(Date dataUltMod) {
		this.dataUltMod = dataUltMod;
	}

	public Integer getFlgAnn() {
		return flgAnn;
	}

	public void setFlgAnn(Integer flgAnn) {
		this.flgAnn = flgAnn;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getValori() {
		return valori;
	}

	public void setValori(String valori) {
		this.valori = valori;
	}

	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
}
