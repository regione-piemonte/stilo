/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraInputRicercaAnagrafica implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codice;
	private String stringaRicerca;
	private String applicazione;
	private String codiceTrasco;
	private BigInteger numMaxRisultati;
	private String codiceFiscale;
	private String partitaIva;
	private BigInteger escludiDaBonificare;
	private BigInteger escludiNominativoFalso;
	private BigInteger escludiSoggettoIrriconoscibile;
	private Calendar dataFineAttivita;
	
	public Long getCodice() {
		return codice;
	}
	
	public void setCodice(Long codice) {
		this.codice = codice;
	}
	
	public String getStringaRicerca() {
		return stringaRicerca;
	}
	
	public void setStringaRicerca(String stringaRicerca) {
		this.stringaRicerca = stringaRicerca;
	}
	
	public String getApplicazione() {
		return applicazione;
	}
	
	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}
	
	public String getCodiceTrasco() {
		return codiceTrasco;
	}
	
	public void setCodiceTrasco(String codiceTrasco) {
		this.codiceTrasco = codiceTrasco;
	}
	
	public BigInteger getNumMaxRisultati() {
		return numMaxRisultati;
	}
	
	public void setNumMaxRisultati(BigInteger numMaxRisultati) {
		this.numMaxRisultati = numMaxRisultati;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}
	
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
	public BigInteger getEscludiDaBonificare() {
		return escludiDaBonificare;
	}
	
	public void setEscludiDaBonificare(BigInteger escludiDaBonificare) {
		this.escludiDaBonificare = escludiDaBonificare;
	}
	
	public BigInteger getEscludiNominativoFalso() {
		return escludiNominativoFalso;
	}
	
	public void setEscludiNominativoFalso(BigInteger escludiNominativoFalso) {
		this.escludiNominativoFalso = escludiNominativoFalso;
	}
	
	public BigInteger getEscludiSoggettoIrriconoscibile() {
		return escludiSoggettoIrriconoscibile;
	}
	
	public void setEscludiSoggettoIrriconoscibile(BigInteger escludiSoggettoIrriconoscibile) {
		this.escludiSoggettoIrriconoscibile = escludiSoggettoIrriconoscibile;
	}
	
	public Calendar getDataFineAttivita() {
		return dataFineAttivita;
	}
	
	public void setDataFineAttivita(Calendar dataFineAttivita) {
		this.dataFineAttivita = dataFineAttivita;
	}
	
}
