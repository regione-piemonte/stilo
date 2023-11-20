/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class DatiContabiliAVBXmlBean {
	
	@NumeroColonna(numero = "1")
	private String missione;
	
	@NumeroColonna(numero = "2")
	private String desMissione;
	
	@NumeroColonna(numero = "3")
	private String programma;
	
	@NumeroColonna(numero = "4")
	private String desProgramma;
	
	@NumeroColonna(numero = "5")
	private String livelliPdC;
	
	@NumeroColonna(numero = "6")
	private String desLivelliPdC;
	
	@NumeroColonna(numero = "7")
	private String annoBilancio;
	
	@NumeroColonna(numero = "8")
	private String capitolo;
	
	@NumeroColonna(numero = "9")
	private String centroDiCosto;
	
	@NumeroColonna(numero = "10")
	private String nroImpAcc;
	
	@NumeroColonna(numero = "11")
	private String subImpAcc;
	
	@NumeroColonna(numero = "12")
	private String annoGestResidui;
	
	@NumeroColonna(numero = "13")
	private String nroLiquidazione;
		
	@NumeroColonna(numero = "14")
	private String annoLiquidazione;		
	
	@NumeroColonna(numero = "15")
	private String importo;			
	
	@NumeroColonna(numero = "16")
	private String flgPrenotazione;
	
	@NumeroColonna(numero = "17")
	private String flgSoggettiVari;
	
	@NumeroColonna(numero = "18")
	private String nominativoSogg;
	
	@NumeroColonna(numero = "19")
	private String codFisPIVASogg; 
	
	@NumeroColonna(numero = "20")
	private String sedeSogg;			
	
	@NumeroColonna(numero = "21")
	private String codiceCIG;
	
	@NumeroColonna(numero = "22")
	private String codiceCUP;
	
	@NumeroColonna(numero = "23")
	private String iban;
	
	@NumeroColonna(numero = "24")
	private String nroMandato;
	
	@NumeroColonna(numero = "25")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataMandato;

	public String getMissione() {
		return missione;
	}

	public void setMissione(String missione) {
		this.missione = missione;
	}

	public String getDesMissione() {
		return desMissione;
	}

	public void setDesMissione(String desMissione) {
		this.desMissione = desMissione;
	}

	public String getProgramma() {
		return programma;
	}

	public void setProgramma(String programma) {
		this.programma = programma;
	}

	public String getDesProgramma() {
		return desProgramma;
	}

	public void setDesProgramma(String desProgramma) {
		this.desProgramma = desProgramma;
	}

	public String getLivelliPdC() {
		return livelliPdC;
	}

	public void setLivelliPdC(String livelliPdC) {
		this.livelliPdC = livelliPdC;
	}

	public String getDesLivelliPdC() {
		return desLivelliPdC;
	}

	public void setDesLivelliPdC(String desLivelliPdC) {
		this.desLivelliPdC = desLivelliPdC;
	}

	public String getAnnoBilancio() {
		return annoBilancio;
	}

	public void setAnnoBilancio(String annoBilancio) {
		this.annoBilancio = annoBilancio;
	}

	public String getCapitolo() {
		return capitolo;
	}

	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public String getNroImpAcc() {
		return nroImpAcc;
	}

	public void setNroImpAcc(String nroImpAcc) {
		this.nroImpAcc = nroImpAcc;
	}

	public String getSubImpAcc() {
		return subImpAcc;
	}

	public void setSubImpAcc(String subImpAcc) {
		this.subImpAcc = subImpAcc;
	}

	public String getAnnoGestResidui() {
		return annoGestResidui;
	}

	public void setAnnoGestResidui(String annoGestResidui) {
		this.annoGestResidui = annoGestResidui;
	}

	public String getNroLiquidazione() {
		return nroLiquidazione;
	}

	public void setNroLiquidazione(String nroLiquidazione) {
		this.nroLiquidazione = nroLiquidazione;
	}

	public String getAnnoLiquidazione() {
		return annoLiquidazione;
	}

	public void setAnnoLiquidazione(String annoLiquidazione) {
		this.annoLiquidazione = annoLiquidazione;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getFlgPrenotazione() {
		return flgPrenotazione;
	}

	public void setFlgPrenotazione(String flgPrenotazione) {
		this.flgPrenotazione = flgPrenotazione;
	}

	public String getFlgSoggettiVari() {
		return flgSoggettiVari;
	}

	public void setFlgSoggettiVari(String flgSoggettiVari) {
		this.flgSoggettiVari = flgSoggettiVari;
	}

	public String getNominativoSogg() {
		return nominativoSogg;
	}

	public void setNominativoSogg(String nominativoSogg) {
		this.nominativoSogg = nominativoSogg;
	}

	public String getCodFisPIVASogg() {
		return codFisPIVASogg;
	}

	public void setCodFisPIVASogg(String codFisPIVASogg) {
		this.codFisPIVASogg = codFisPIVASogg;
	}

	public String getSedeSogg() {
		return sedeSogg;
	}

	public void setSedeSogg(String sedeSogg) {
		this.sedeSogg = sedeSogg;
	}

	public String getCodiceCIG() {
		return codiceCIG;
	}

	public void setCodiceCIG(String codiceCIG) {
		this.codiceCIG = codiceCIG;
	}

	public String getCodiceCUP() {
		return codiceCUP;
	}

	public void setCodiceCUP(String codiceCUP) {
		this.codiceCUP = codiceCUP;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getNroMandato() {
		return nroMandato;
	}

	public void setNroMandato(String nroMandato) {
		this.nroMandato = nroMandato;
	}

	public Date getDataMandato() {
		return dataMandato;
	}

	public void setDataMandato(Date dataMandato) {
		this.dataMandato = dataMandato;
	}
	
}
