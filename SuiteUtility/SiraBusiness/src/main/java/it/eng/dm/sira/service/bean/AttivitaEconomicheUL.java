package it.eng.dm.sira.service.bean;

import java.util.Calendar;

public class AttivitaEconomicheUL {

	private String codiceIppc;

	private String codiceNace;

	private String codiceNose;

	private Calendar dataCessazione;

	private Calendar dataInizio;

	private String descrizione;

	private String note;

	private Integer numeroAddetti;

	private String periodicitaAttiva;

	private String mesiInCuiSvolgeAttivita;

	public String getCodiceIppc() {
		return codiceIppc;
	}

	public void setCodiceIppc(String codiceIppc) {
		this.codiceIppc = codiceIppc;
	}

	public String getCodiceNace() {
		return codiceNace;
	}

	public void setCodiceNace(String codiceNace) {
		this.codiceNace = codiceNace;
	}

	public String getCodiceNose() {
		return codiceNose;
	}

	public void setCodiceNose(String codiceNose) {
		this.codiceNose = codiceNose;
	}

	public Calendar getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Calendar dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public Calendar getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Calendar dataInizio) {
		this.dataInizio = dataInizio;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getNumeroAddetti() {
		return numeroAddetti;
	}

	public void setNumeroAddetti(Integer numeroAddetti) {
		this.numeroAddetti = numeroAddetti;
	}

	public String getPeriodicitaAttiva() {
		return periodicitaAttiva;
	}

	public void setPeriodicitaAttiva(String periodicitaAttiva) {
		this.periodicitaAttiva = periodicitaAttiva;
	}

	public String getMesiInCuiSvolgeAttivita() {
		return mesiInCuiSvolgeAttivita;
	}

	public void setMesiInCuiSvolgeAttivita(String mesiInCuiSvolgeAttivita) {
		this.mesiInCuiSvolgeAttivita = mesiInCuiSvolgeAttivita;
	}

}
