/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Alberto Calvelli
 *
 */

public class ScadenzeConfigurateBean {
	private BigDecimal idProcessType;
	private String tipo;
	private String rowId;
	private String descrizioneScadenze;
	private BigDecimal durataPeriodo;
	private Date validoDal;
	private Date validoFinoAl;
	
	public String getDescrizioneScadenze() {
		return descrizioneScadenze;
	}
	public void setDescrizioneScadenze(String descrizioneScadenze) {
		this.descrizioneScadenze = descrizioneScadenze;
	}
	public BigDecimal getDurataPeriodo() {
		return durataPeriodo;
	}
	public void setDurataPeriodo(BigDecimal durataPeriodo) {
		this.durataPeriodo = durataPeriodo;
	}
	public Date getValidoDal() {
		return validoDal;
	}
	public void setValidoDal(Date validoDal) {
		this.validoDal = validoDal;
	}
	public Date getValidoFinoAl() {
		return validoFinoAl;
	}
	public void setValidoFinoAl(Date validoFinoAl) {
		this.validoFinoAl = validoFinoAl;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getIdProcessType() {
		return idProcessType;
	}
	public void setIdProcessType(BigDecimal idProcessType) {
		this.idProcessType = idProcessType;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
}