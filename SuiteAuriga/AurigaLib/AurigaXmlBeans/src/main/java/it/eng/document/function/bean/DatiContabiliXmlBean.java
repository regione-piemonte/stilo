/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class DatiContabiliXmlBean {
	
	@NumeroColonna(numero = "1")
	private String tipoDettaglio;

	@NumeroColonna(numero = "2")
	private String numeroDettaglio;

	@NumeroColonna(numero = "3")
	private String subNumero;

	@NumeroColonna(numero = "4")
	private String annoCrono;

	@NumeroColonna(numero = "5")
	private String numeroCrono;

	@NumeroColonna(numero = "6")
	private String annoEsercizio;

	@NumeroColonna(numero = "7")
	private String flgEntrataUscita;

	@NumeroColonna(numero = "8")
	private String capitolo;

	@NumeroColonna(numero = "9")
	private String articolo;

	@NumeroColonna(numero = "10")
	private String numero;

	@NumeroColonna(numero = "11")
	private String descrizioneCapitolo;

	@NumeroColonna(numero = "12")
	private String liv5PdC;

	@NumeroColonna(numero = "13")
	private String descrizionePdC;

	@NumeroColonna(numero = "14")
	private String annoCompetenza;

	@NumeroColonna(numero = "15")
	private String importoDisponibile;

	@NumeroColonna(numero = "16")
	private String importo;

	@NumeroColonna(numero = "17")
	private String oggetto;

	@NumeroColonna(numero = "18")
	private String codiceCIG;

	@NumeroColonna(numero = "19")
	private String codiceCUP;

	@NumeroColonna(numero = "20")
	private String codiceGAMIPBM;

	@NumeroColonna(numero = "21")
	private String desUnitaOrgCdR;

	@NumeroColonna(numero = "22")
	private String annoEsigibilitaDebito;

	@NumeroColonna(numero = "23")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataEsigibilitaDa;
	
	@NumeroColonna(numero = "24")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataEsigibilitaA;
	
	@NumeroColonna(numero = "25")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScadenzaEntrata;
	
	@NumeroColonna(numero = "26")
	private String dichiarazioneDL78;

	@NumeroColonna(numero = "27")
	private String tipoFinanziamento;

	@NumeroColonna(numero = "28")
	private String denominazioneSogg;

	@NumeroColonna(numero = "29")
	private String codFiscaleSogg;

	@NumeroColonna(numero = "30")
	private String indirizzoSogg;

	@NumeroColonna(numero = "31")
	private String cap;

	@NumeroColonna(numero = "32")
	private String localita;

	@NumeroColonna(numero = "33")
	private String provincia;
	
	@NumeroColonna(numero = "34")
	private String codUnitaOrgCdR;
	
	@NumeroColonna(numero = "35")
	private String flgCorrelata;

	@NumeroColonna(numero = "36")
	private String specifiche;

	@NumeroColonna(numero = "37")
	private String note;
	
	@NumeroColonna(numero = "38")
	private String titolo;

	@NumeroColonna(numero = "39")
	private String subCrono;
	
	@NumeroColonna(numero = "40")
	private String codTipoFinanziamento;
	
	@NumeroColonna(numero = "41")
	private String codPIVASogg;
	
	@NumeroColonna(numero = "42")
	private String flgValidato;
	
	@NumeroColonna(numero = "43")
	private String flgSoggDaPubblicare;
	
	@NumeroColonna(numero = "44")
	private String settore;
	
	@NumeroColonna(numero = "45")
	private String descrizioneSettore;
	
	public String getTipoDettaglio() {
		return tipoDettaglio;
	}
	public void setTipoDettaglio(String tipoDettaglio) {
		this.tipoDettaglio = tipoDettaglio;
	}
	public String getNumeroDettaglio() {
		return numeroDettaglio;
	}
	public void setNumeroDettaglio(String numeroDettaglio) {
		this.numeroDettaglio = numeroDettaglio;
	}
	public String getSubNumero() {
		return subNumero;
	}
	public void setSubNumero(String subNumero) {
		this.subNumero = subNumero;
	}
	public String getAnnoCrono() {
		return annoCrono;
	}
	public void setAnnoCrono(String annoCrono) {
		this.annoCrono = annoCrono;
	}
	public String getNumeroCrono() {
		return numeroCrono;
	}
	public void setNumeroCrono(String numeroCrono) {
		this.numeroCrono = numeroCrono;
	}
	public String getAnnoEsercizio() {
		return annoEsercizio;
	}
	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	public String getFlgEntrataUscita() {
		return flgEntrataUscita;
	}
	public void setFlgEntrataUscita(String flgEntrataUscita) {
		this.flgEntrataUscita = flgEntrataUscita;
	}
	public String getCapitolo() {
		return capitolo;
	}
	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}	
	public String getArticolo() {
		return articolo;
	}
	public void setArticolo(String articolo) {
		this.articolo = articolo;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDescrizioneCapitolo() {
		return descrizioneCapitolo;
	}
	public void setDescrizioneCapitolo(String descrizioneCapitolo) {
		this.descrizioneCapitolo = descrizioneCapitolo;
	}	
	public String getLiv5PdC() {
		return liv5PdC;
	}
	public void setLiv5PdC(String liv5PdC) {
		this.liv5PdC = liv5PdC;
	}
	public String getDescrizionePdC() {
		return descrizionePdC;
	}
	public void setDescrizionePdC(String descrizionePdC) {
		this.descrizionePdC = descrizionePdC;
	}	
	public String getAnnoCompetenza() {
		return annoCompetenza;
	}
	public void setAnnoCompetenza(String annoCompetenza) {
		this.annoCompetenza = annoCompetenza;
	}
	public String getImportoDisponibile() {
		return importoDisponibile;
	}
	public void setImportoDisponibile(String importoDisponibile) {
		this.importoDisponibile = importoDisponibile;
	}	
	public String getImporto() {
		return importo;
	}
	public void setImporto(String importo) {
		this.importo = importo;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
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
	public String getCodiceGAMIPBM() {
		return codiceGAMIPBM;
	}
	public void setCodiceGAMIPBM(String codiceGAMIPBM) {
		this.codiceGAMIPBM = codiceGAMIPBM;
	}
	public String getDesUnitaOrgCdR() {
		return desUnitaOrgCdR;
	}
	public void setDesUnitaOrgCdR(String desUnitaOrgCdR) {
		this.desUnitaOrgCdR = desUnitaOrgCdR;
	}
	public String getAnnoEsigibilitaDebito() {
		return annoEsigibilitaDebito;
	}
	public void setAnnoEsigibilitaDebito(String annoEsigibilitaDebito) {
		this.annoEsigibilitaDebito = annoEsigibilitaDebito;
	}
	public Date getDataEsigibilitaDa() {
		return dataEsigibilitaDa;
	}
	public void setDataEsigibilitaDa(Date dataEsigibilitaDa) {
		this.dataEsigibilitaDa = dataEsigibilitaDa;
	}
	public Date getDataEsigibilitaA() {
		return dataEsigibilitaA;
	}
	public void setDataEsigibilitaA(Date dataEsigibilitaA) {
		this.dataEsigibilitaA = dataEsigibilitaA;
	}
	public Date getDataScadenzaEntrata() {
		return dataScadenzaEntrata;
	}
	public void setDataScadenzaEntrata(Date dataScadenzaEntrata) {
		this.dataScadenzaEntrata = dataScadenzaEntrata;
	}
	public String getDichiarazioneDL78() {
		return dichiarazioneDL78;
	}
	public void setDichiarazioneDL78(String dichiarazioneDL78) {
		this.dichiarazioneDL78 = dichiarazioneDL78;
	}
	public String getTipoFinanziamento() {
		return tipoFinanziamento;
	}
	public void setTipoFinanziamento(String tipoFinanziamento) {
		this.tipoFinanziamento = tipoFinanziamento;
	}
	public String getDenominazioneSogg() {
		return denominazioneSogg;
	}
	public void setDenominazioneSogg(String denominazioneSogg) {
		this.denominazioneSogg = denominazioneSogg;
	}
	public String getCodFiscaleSogg() {
		return codFiscaleSogg;
	}
	public void setCodFiscaleSogg(String codFiscaleSogg) {
		this.codFiscaleSogg = codFiscaleSogg;
	}
	public String getIndirizzoSogg() {
		return indirizzoSogg;
	}
	public void setIndirizzoSogg(String indirizzoSogg) {
		this.indirizzoSogg = indirizzoSogg;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String localita) {
		this.localita = localita;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCodUnitaOrgCdR() {
		return codUnitaOrgCdR;
	}
	public void setCodUnitaOrgCdR(String codUnitaOrgCdR) {
		this.codUnitaOrgCdR = codUnitaOrgCdR;
	}
	public String getFlgCorrelata() {
		return flgCorrelata;
	}
	public void setFlgCorrelata(String flgCorrelata) {
		this.flgCorrelata = flgCorrelata;
	}
	public String getSpecifiche() {
		return specifiche;
	}
	public void setSpecifiche(String specifiche) {
		this.specifiche = specifiche;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getSubCrono() {
		return subCrono;
	}
	public void setSubCrono(String subCrono) {
		this.subCrono = subCrono;
	}
	public String getCodTipoFinanziamento() {
		return codTipoFinanziamento;
	}
	public void setCodTipoFinanziamento(String codTipoFinanziamento) {
		this.codTipoFinanziamento = codTipoFinanziamento;
	}
	public String getCodPIVASogg() {
		return codPIVASogg;
	}
	public void setCodPIVASogg(String codPIVASogg) {
		this.codPIVASogg = codPIVASogg;
	}
	public String getFlgValidato() {
		return flgValidato;
	}
	public void setFlgValidato(String flgValidato) {
		this.flgValidato = flgValidato;
	}
	public String getFlgSoggDaPubblicare() {
		return flgSoggDaPubblicare;
	}
	public void setFlgSoggDaPubblicare(String flgSoggDaPubblicare) {
		this.flgSoggDaPubblicare = flgSoggDaPubblicare;
	}
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public String getDescrizioneSettore() {
		return descrizioneSettore;
	}
	public void setDescrizioneSettore(String descrizioneSettore) {
		this.descrizioneSettore = descrizioneSettore;
	}
		
}
