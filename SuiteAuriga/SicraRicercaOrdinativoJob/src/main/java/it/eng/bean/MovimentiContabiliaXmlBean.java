/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class MovimentiContabiliaXmlBean {
	
	@NumeroColonna(numero = "1")
	private String flgEntrataUscita;

	@NumeroColonna(numero = "2")
	private String annoMovimento;

	@NumeroColonna(numero = "3")
	private String numeroMovimento;

	@NumeroColonna(numero = "4")
	private String tipoMovimento;

	@NumeroColonna(numero = "5")
	private String importo;
	
	@NumeroColonna(numero = "6")
	private String codiceMovimento;

	@NumeroColonna(numero = "7")
	private String descrizioneMovimento;

	@NumeroColonna(numero = "8")
	private String numeroCapitolo;

	@NumeroColonna(numero = "9")
	private String numeroArticolo;

	@NumeroColonna(numero = "10")
	private String numeroUEB;

	@NumeroColonna(numero = "11")
	private String codiceCIG;

	@NumeroColonna(numero = "12")
	private String codiceCUP;
	
	@NumeroColonna(numero = "13")
	private String codiceSoggetto;
	
	@NumeroColonna(numero = "14")
	private String parereFinanziario;

	@NumeroColonna(numero = "15")
	private String codicePdC;

	@NumeroColonna(numero = "16")
	private String descrizionePdC;
	
	@NumeroColonna(numero = "17")
	private String codiceStato;
	
	@NumeroColonna(numero = "18")
	private String descrizioneStato;
	
	@NumeroColonna(numero = "19")
	private String annoSub;
	
	@NumeroColonna(numero = "20")
	private String numeroSub;
	
	@NumeroColonna(numero = "21")
	private String descrizioneSub;
	
	@NumeroColonna(numero = "22")
	private String annoModifica;
	
	@NumeroColonna(numero = "23")
	private String numeroModifica;
	
	@NumeroColonna(numero = "24")
	private String descrizioneModifica;
	
	@NumeroColonna(numero = "25")
	private String importoIniziale;	
	
	@NumeroColonna(numero = "26")
	private String importoModifica;
	
	@NumeroColonna(numero = "27")
	private String descrizioneCapitolo;
	
	@NumeroColonna(numero = "28")
	private String descrizioneArticolo;
	
	@NumeroColonna(numero = "29")
	private String motivoAssenzaCIG;

	@NumeroColonna(numero = "30")
	private String codiceClasseSoggetto;
	
	@NumeroColonna(numero = "31")
	private String codiceCategoria;
	
	@NumeroColonna(numero = "32")
	private String descrizioneCategoria;
	
	@NumeroColonna(numero = "33")
	private String codiceCodUE;
	
	@NumeroColonna(numero = "34")
	private String descrizioneCodUE;
	
	@NumeroColonna(numero = "35")
	private String codiceCofog;
	
	@NumeroColonna(numero = "36")
	private String descrizioneCofog;
	
	@NumeroColonna(numero = "37")
	private String codiceGsa;
	
	@NumeroColonna(numero = "38")
	private String descrizioneGsa;
	
	@NumeroColonna(numero = "39")
	private String codiceMacroaggregato;
	
	@NumeroColonna(numero = "40")
	private String descrizioneMacroaggregato;
	
	@NumeroColonna(numero = "41")
	private String codiceMissione;
	
	@NumeroColonna(numero = "42")
	private String descrizioneMissione;
	
	@NumeroColonna(numero = "43")
	private String codiceNaturaRicorrente;
	
	@NumeroColonna(numero = "44")
	private String descrizioneNaturaRicorrente;
	
	@NumeroColonna(numero = "45")
	private String codiceProgramma;
	
	@NumeroColonna(numero = "46")
	private String descrizioneProgramma;
	
	@NumeroColonna(numero = "47")
	private String codiceTipoFinanziamento;
	
	@NumeroColonna(numero = "48")
	private String descrizioneTipoFinanziamento;
	
	@NumeroColonna(numero = "49")
	private String codiceTipologia;
	
	@NumeroColonna(numero = "50")
	private String descrizioneTipologia;
	
	@NumeroColonna(numero = "51")
	private String codiceTitolo;
	
	@NumeroColonna(numero = "52")
	private String descrizioneTitolo;
	
	@NumeroColonna(numero = "53")
	private String descrizioneSoggetto;
	
	@NumeroColonna(numero = "54")
	private String descrizioneClasseSoggetto;
	
	@NumeroColonna(numero = "55")
	private String prenotazione;
	
	@NumeroColonna(numero = "56")
	private String prenotazioneLiquidabile;
	
	@NumeroColonna(numero = "57")
	private String codiceProgetto;
	
	@NumeroColonna(numero = "58")
	private String descrizioneProgetto;						
	
	@NumeroColonna(numero = "59")
	private String codiceTipoDebitoSiope;
	
	@NumeroColonna(numero = "60")
	private String descrizioneTipoDebitoSiope;

	public String getFlgEntrataUscita() {
		return flgEntrataUscita;
	}

	public void setFlgEntrataUscita(String flgEntrataUscita) {
		this.flgEntrataUscita = flgEntrataUscita;
	}

	public String getAnnoMovimento() {
		return annoMovimento;
	}

	public void setAnnoMovimento(String annoMovimento) {
		this.annoMovimento = annoMovimento;
	}

	public String getNumeroMovimento() {
		return numeroMovimento;
	}

	public void setNumeroMovimento(String numeroMovimento) {
		this.numeroMovimento = numeroMovimento;
	}

	public String getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getCodiceMovimento() {
		return codiceMovimento;
	}

	public void setCodiceMovimento(String codiceMovimento) {
		this.codiceMovimento = codiceMovimento;
	}

	public String getDescrizioneMovimento() {
		return descrizioneMovimento;
	}

	public void setDescrizioneMovimento(String descrizioneMovimento) {
		this.descrizioneMovimento = descrizioneMovimento;
	}

	public String getNumeroCapitolo() {
		return numeroCapitolo;
	}

	public void setNumeroCapitolo(String numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}

	public String getNumeroArticolo() {
		return numeroArticolo;
	}

	public void setNumeroArticolo(String numeroArticolo) {
		this.numeroArticolo = numeroArticolo;
	}

	public String getNumeroUEB() {
		return numeroUEB;
	}

	public void setNumeroUEB(String numeroUEB) {
		this.numeroUEB = numeroUEB;
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

	public String getCodiceSoggetto() {
		return codiceSoggetto;
	}

	public void setCodiceSoggetto(String codiceSoggetto) {
		this.codiceSoggetto = codiceSoggetto;
	}

	public String getParereFinanziario() {
		return parereFinanziario;
	}

	public void setParereFinanziario(String parereFinanziario) {
		this.parereFinanziario = parereFinanziario;
	}

	public String getCodicePdC() {
		return codicePdC;
	}

	public void setCodicePdC(String codicePdC) {
		this.codicePdC = codicePdC;
	}

	public String getDescrizionePdC() {
		return descrizionePdC;
	}

	public void setDescrizionePdC(String descrizionePdC) {
		this.descrizionePdC = descrizionePdC;
	}

	public String getCodiceStato() {
		return codiceStato;
	}

	public void setCodiceStato(String codiceStato) {
		this.codiceStato = codiceStato;
	}

	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}

	public String getAnnoSub() {
		return annoSub;
	}

	public void setAnnoSub(String annoSub) {
		this.annoSub = annoSub;
	}

	public String getNumeroSub() {
		return numeroSub;
	}

	public void setNumeroSub(String numeroSub) {
		this.numeroSub = numeroSub;
	}

	public String getDescrizioneSub() {
		return descrizioneSub;
	}

	public void setDescrizioneSub(String descrizioneSub) {
		this.descrizioneSub = descrizioneSub;
	}

	public String getAnnoModifica() {
		return annoModifica;
	}

	public void setAnnoModifica(String annoModifica) {
		this.annoModifica = annoModifica;
	}

	public String getNumeroModifica() {
		return numeroModifica;
	}

	public void setNumeroModifica(String numeroModifica) {
		this.numeroModifica = numeroModifica;
	}

	public String getDescrizioneModifica() {
		return descrizioneModifica;
	}

	public void setDescrizioneModifica(String descrizioneModifica) {
		this.descrizioneModifica = descrizioneModifica;
	}

	public String getImportoIniziale() {
		return importoIniziale;
	}

	public void setImportoIniziale(String importoIniziale) {
		this.importoIniziale = importoIniziale;
	}

	public String getImportoModifica() {
		return importoModifica;
	}

	public void setImportoModifica(String importoModifica) {
		this.importoModifica = importoModifica;
	}

	public String getDescrizioneCapitolo() {
		return descrizioneCapitolo;
	}

	public void setDescrizioneCapitolo(String descrizioneCapitolo) {
		this.descrizioneCapitolo = descrizioneCapitolo;
	}

	public String getDescrizioneArticolo() {
		return descrizioneArticolo;
	}

	public void setDescrizioneArticolo(String descrizioneArticolo) {
		this.descrizioneArticolo = descrizioneArticolo;
	}

	public String getMotivoAssenzaCIG() {
		return motivoAssenzaCIG;
	}

	public void setMotivoAssenzaCIG(String motivoAssenzaCIG) {
		this.motivoAssenzaCIG = motivoAssenzaCIG;
	}

	public String getCodiceClasseSoggetto() {
		return codiceClasseSoggetto;
	}

	public void setCodiceClasseSoggetto(String codiceClasseSoggetto) {
		this.codiceClasseSoggetto = codiceClasseSoggetto;
	}

	public String getCodiceCategoria() {
		return codiceCategoria;
	}

	public void setCodiceCategoria(String codiceCategoria) {
		this.codiceCategoria = codiceCategoria;
	}

	public String getDescrizioneCategoria() {
		return descrizioneCategoria;
	}

	public void setDescrizioneCategoria(String descrizioneCategoria) {
		this.descrizioneCategoria = descrizioneCategoria;
	}

	public String getCodiceCodUE() {
		return codiceCodUE;
	}

	public void setCodiceCodUE(String codiceCodUE) {
		this.codiceCodUE = codiceCodUE;
	}

	public String getDescrizioneCodUE() {
		return descrizioneCodUE;
	}

	public void setDescrizioneCodUE(String descrizioneCodUE) {
		this.descrizioneCodUE = descrizioneCodUE;
	}

	public String getCodiceCofog() {
		return codiceCofog;
	}

	public void setCodiceCofog(String codiceCofog) {
		this.codiceCofog = codiceCofog;
	}

	public String getDescrizioneCofog() {
		return descrizioneCofog;
	}

	public void setDescrizioneCofog(String descrizioneCofog) {
		this.descrizioneCofog = descrizioneCofog;
	}

	public String getCodiceGsa() {
		return codiceGsa;
	}

	public void setCodiceGsa(String codiceGsa) {
		this.codiceGsa = codiceGsa;
	}

	public String getDescrizioneGsa() {
		return descrizioneGsa;
	}

	public void setDescrizioneGsa(String descrizioneGsa) {
		this.descrizioneGsa = descrizioneGsa;
	}

	public String getCodiceMacroaggregato() {
		return codiceMacroaggregato;
	}

	public void setCodiceMacroaggregato(String codiceMacroaggregato) {
		this.codiceMacroaggregato = codiceMacroaggregato;
	}

	public String getDescrizioneMacroaggregato() {
		return descrizioneMacroaggregato;
	}

	public void setDescrizioneMacroaggregato(String descrizioneMacroaggregato) {
		this.descrizioneMacroaggregato = descrizioneMacroaggregato;
	}

	public String getCodiceMissione() {
		return codiceMissione;
	}

	public void setCodiceMissione(String codiceMissione) {
		this.codiceMissione = codiceMissione;
	}

	public String getDescrizioneMissione() {
		return descrizioneMissione;
	}

	public void setDescrizioneMissione(String descrizioneMissione) {
		this.descrizioneMissione = descrizioneMissione;
	}

	public String getCodiceNaturaRicorrente() {
		return codiceNaturaRicorrente;
	}

	public void setCodiceNaturaRicorrente(String codiceNaturaRicorrente) {
		this.codiceNaturaRicorrente = codiceNaturaRicorrente;
	}

	public String getDescrizioneNaturaRicorrente() {
		return descrizioneNaturaRicorrente;
	}

	public void setDescrizioneNaturaRicorrente(String descrizioneNaturaRicorrente) {
		this.descrizioneNaturaRicorrente = descrizioneNaturaRicorrente;
	}

	public String getCodiceProgramma() {
		return codiceProgramma;
	}

	public void setCodiceProgramma(String codiceProgramma) {
		this.codiceProgramma = codiceProgramma;
	}

	public String getDescrizioneProgramma() {
		return descrizioneProgramma;
	}

	public void setDescrizioneProgramma(String descrizioneProgramma) {
		this.descrizioneProgramma = descrizioneProgramma;
	}

	public String getCodiceTipoFinanziamento() {
		return codiceTipoFinanziamento;
	}

	public void setCodiceTipoFinanziamento(String codiceTipoFinanziamento) {
		this.codiceTipoFinanziamento = codiceTipoFinanziamento;
	}

	public String getDescrizioneTipoFinanziamento() {
		return descrizioneTipoFinanziamento;
	}

	public void setDescrizioneTipoFinanziamento(String descrizioneTipoFinanziamento) {
		this.descrizioneTipoFinanziamento = descrizioneTipoFinanziamento;
	}

	public String getCodiceTipologia() {
		return codiceTipologia;
	}

	public void setCodiceTipologia(String codiceTipologia) {
		this.codiceTipologia = codiceTipologia;
	}

	public String getDescrizioneTipologia() {
		return descrizioneTipologia;
	}

	public void setDescrizioneTipologia(String descrizioneTipologia) {
		this.descrizioneTipologia = descrizioneTipologia;
	}

	public String getCodiceTitolo() {
		return codiceTitolo;
	}

	public void setCodiceTitolo(String codiceTitolo) {
		this.codiceTitolo = codiceTitolo;
	}

	public String getDescrizioneTitolo() {
		return descrizioneTitolo;
	}

	public void setDescrizioneTitolo(String descrizioneTitolo) {
		this.descrizioneTitolo = descrizioneTitolo;
	}

	public String getDescrizioneSoggetto() {
		return descrizioneSoggetto;
	}

	public void setDescrizioneSoggetto(String descrizioneSoggetto) {
		this.descrizioneSoggetto = descrizioneSoggetto;
	}

	public String getDescrizioneClasseSoggetto() {
		return descrizioneClasseSoggetto;
	}

	public void setDescrizioneClasseSoggetto(String descrizioneClasseSoggetto) {
		this.descrizioneClasseSoggetto = descrizioneClasseSoggetto;
	}

	public String getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(String prenotazione) {
		this.prenotazione = prenotazione;
	}

	public String getPrenotazioneLiquidabile() {
		return prenotazioneLiquidabile;
	}

	public void setPrenotazioneLiquidabile(String prenotazioneLiquidabile) {
		this.prenotazioneLiquidabile = prenotazioneLiquidabile;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public String getDescrizioneProgetto() {
		return descrizioneProgetto;
	}

	public void setDescrizioneProgetto(String descrizioneProgetto) {
		this.descrizioneProgetto = descrizioneProgetto;
	}

	public String getCodiceTipoDebitoSiope() {
		return codiceTipoDebitoSiope;
	}

	public void setCodiceTipoDebitoSiope(String codiceTipoDebitoSiope) {
		this.codiceTipoDebitoSiope = codiceTipoDebitoSiope;
	}

	public String getDescrizioneTipoDebitoSiope() {
		return descrizioneTipoDebitoSiope;
	}

	public void setDescrizioneTipoDebitoSiope(String descrizioneTipoDebitoSiope) {
		this.descrizioneTipoDebitoSiope = descrizioneTipoDebitoSiope;
	}

}
