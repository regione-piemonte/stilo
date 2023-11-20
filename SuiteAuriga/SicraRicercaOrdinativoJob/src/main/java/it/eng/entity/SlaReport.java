/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.Timestamp;

public class SlaReport  implements java.io.Serializable {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idSpAoo;
	private int pSogl;
	private Timestamp pDataIni;
	private Timestamp pDataFin;
	private String errMsgout;
	private String descSoc;
	private String meseAnno;
	double totali ;
	double slaOk ;
	double slaKo ;
	double percOk ;
	private String numFatt;
	private String dataFattura;
	private String dataRicezione;
	private String dataInvioSdi;
	private String lastDataInvioSdi;
	private String stato;
	private int giorni;
	private int ore;
	private int minuti;
	private String oreTotali;
	private String esito;
	
    
	public int getIdSpAoo() {
		return idSpAoo;
	}
	public void setIdSpAoo(int idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	public int getpSogl() {
		return pSogl;
	}
	public void setpSogl(int pSogl) {
		this.pSogl = pSogl;
	}
	public Timestamp getpDataIni() {
		return pDataIni;
	}
	public void setpDataIni(Timestamp pDataIni) {
		this.pDataIni = pDataIni;
	}
	public Timestamp getpDataFin() {
		return pDataFin;
	}
	public void setpDataFin(Timestamp pDataFin) {
		this.pDataFin = pDataFin;
	}
	public String getErrMsgout() {
		return errMsgout;
	}
	public void setErrMsgout(String errMsgout) {
		this.errMsgout = errMsgout;
	}
	public double getTotali() {
		return totali;
	}
	public void setTotali(double totali) {
		this.totali = totali;
	}
	public double getSlaOk() {
		return slaOk;
	}
	public void setSlaOk(double slaOk) {
		this.slaOk = slaOk;
	}
	public double getSlaKo() {
		return slaKo;
	}
	public void setSlaKo(double slaKo) {
		this.slaKo = slaKo;
	}
	public double getPercOk() {
		return percOk;
	}
	public void setPercOk(double percOk) {
		this.percOk = percOk;
	}
	
	public String getDescSoc() {
		return descSoc;
	}
	public void setDescSoc(String descSoc) {
		this.descSoc = descSoc;
	}
	public String getMeseAnno() {
		return meseAnno;
	}
	public void setMeseAnno(String meseAnno) {
		this.meseAnno = meseAnno;
	}
	public String getNumFatt() {
		return numFatt;
	}
	public void setNumFatt(String numFatt) {
		this.numFatt = numFatt;
	}
	public String getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(String dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public String getDataInvioSdi() {
		return dataInvioSdi;
	}
	public void setDataInvioSdi(String dataInvioSdi) {
		this.dataInvioSdi = dataInvioSdi;
	}
	public int getGiorni() {
		return giorni;
	}
	public void setGiorni(int giorni) {
		this.giorni = giorni;
	}
	public int getOre() {
		return ore;
	}
	public void setOre(int ore) {
		this.ore = ore;
	}
	public int getMinuti() {
		return minuti;
	}
	public void setMinuti(int minuti) {
		this.minuti = minuti;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getLastDataInvioSdi() {
		return lastDataInvioSdi;
	}
	public void setLastDataInvioSdi(String lastDataInvioSdi) {
		this.lastDataInvioSdi = lastDataInvioSdi;
	}
	public String getOreTotali() {
		return oreTotali;
	}
	public void setOreTotali(String oreTotali) {
		this.oreTotali = oreTotali;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	public String getDataFattura() {
		return dataFattura;
	}
	public void setDataFattura(String dataFattura) {
		this.dataFattura = dataFattura;
	}
	@Override
	public String toString() {
		return "SlaReport [idSpAoo=" + idSpAoo + ", pSogl=" + pSogl + ", pDataIni=" + pDataIni + ", pDataFin="
				+ pDataFin + ", errMsgout=" + errMsgout + ", descSoc=" + descSoc + ", meseAnno=" + meseAnno
				+ ", totali=" + totali + ", slaOk=" + slaOk + ", slaKo=" + slaKo + ", percOk=" + percOk + ", numFatt="
				+ numFatt + ", dataRicezione=" + dataRicezione + ", dataInvioSdi=" + dataInvioSdi
				+ ", lastDataInvioSdi=" + lastDataInvioSdi + ", stato=" + stato + ", giorni=" + giorni + ", ore=" + ore
				+ ", minuti=" + minuti + ", oreTotali=" + oreTotali + ", esito=" + esito + "]";
	}
	
		
	
}	
