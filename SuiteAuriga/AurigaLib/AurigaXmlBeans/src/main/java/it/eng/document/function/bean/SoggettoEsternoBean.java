/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class SoggettoEsternoBean implements Serializable {

	private static final long serialVersionUID = 5611310655086324291L;

	// 1: Identificativo del soggetto nella rubrica soggetti del sistema
	@NumeroColonna(numero = "1")
	private String idSoggetto;

	// 2: Codice identificativo del soggetto nell'anagrafe o sistema di provenienza
	@NumeroColonna(numero = "2")
	private String provCISogg;

	// 3: Se valorizzato (valore fisso 1) indica che il soggetto è una persona fisica (altrimenti si intende essere una persona giuridica)
	@NumeroColonna(numero = "3")
	private Flag flgPersFisica;

	// 4: Denominazione (se persona giuridica) o cognome (se persona fisica)
	@NumeroColonna(numero = "4")
	private String denominazioneCognome;

	// 5: Nome (se persona fisica)
	@NumeroColonna(numero = "5")
	private String nome;

	// 6: Codice fiscale
	@NumeroColonna(numero = "6")
	private String codFiscale;

	// 7: Partita IVA
	@NumeroColonna(numero = "7")
	private String partitaIva;

	// 8: Sesso (valori F/M/NULL) (se persona fisica)
	@NumeroColonna(numero = "8")
	private String sesso;

	// 9: Data di nascita/istituzione (nel formato dato dal parametro FMT_STD_DATA)
	@NumeroColonna(numero = "9")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private String dataNascitaIstituzione;

	// 10: Codice ISTAT del comune di nascita/istituzione
	@NumeroColonna(numero = "10")
	private String codIstatComuneNascitaIstituzione;

	// 11: Nome del comune (se italiano) o città estera di nascita/istituzione
	@NumeroColonna(numero = "11")
	private String nomeComuneNascitaIstituzione;

	// 12: Codice ISTAT dello stato estero di nascita/istituzione
	@NumeroColonna(numero = "12")
	private String codIstatStatoNascitaIstituzione;

	// 13: Nome dello stato estero di nascita/istituzione
	@NumeroColonna(numero = "13")
	private String nomeStatoNascitaIstituzione;

	// 14: Codice ISTAT dello stato di cittadinanza
	@NumeroColonna(numero = "14")
	private String codIstatStatoCittadinanza;

	// 15: Nome dello stato di cittadinanza
	@NumeroColonna(numero = "15")
	private String nomeStatoCittadinanza;

	// 16: Indicatore di soggetto da registrare in rubrica soggetti se non vi è già presente (in base ai criteri stabiliti per il test di esistenza). Valore
	// fisso 1.
	@NumeroColonna(numero = "16")
	private String flgDaSalvareInRubrica = "1";

	// 17: (obblig. se campo successivo non è valorizzato) Codice della natura della relazione del soggetto con il documento (firmatario, contraente ecc).
	// Valori da dizionario.
	@NumeroColonna(numero = "17")
	private String codNaturaRel;

	// 18: (obblig. se campo precedente non è valorizzato) Descrizione della natura della relazione del soggetto con il documento (firmatario, contraente ecc).
	// Valori da dizionario.
	@NumeroColonna(numero = "18")
	private String desNaturaRel;

	// 19: Dettagli sulla natura della relazione tra soggetto e documento
	@NumeroColonna(numero = "19")
	private String dettagliRel;

	// 20: Titolo di studio/professionale
	@NumeroColonna(numero = "20")
	private String titolo;

	// 21: Cod. rapido assegnatogli in rubrica
	@NumeroColonna(numero = "21")
	private String codiceRapido;

	@NumeroColonna(numero = "22")
	private String tipoSoggetto;

	@NumeroColonna(numero = "23")
	private String estremiDocRiconoscimento;

	// 24: Codice toponomastico (cioè nel viario) della via/piazza ecc. dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "24")
	private String codToponimo;

	// 25: Via/piazza ecc. dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "25")
	private String toponimoIndirizzo;

	// 26: Frazione dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "26")
	private String frazione;

	// 27: N.ro civico (senza eventuale esponente o altre "appendici" quali colore ecc che vanno in colonna 47) dell'indirizzo fisico di invio al destinatario
	// (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "27")
	private String civico;

	// 28: Interno del civico dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "28")
	private String interno;

	// 31: Codice Avviamento Postale dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "31")
	private String cap;

	// 32: Codice istat del comune (se italiano) dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "32")
	private String comune;

	// 33: Nome del comune/città dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "33")
	private String nomeComuneCitta;

	private String provincia;

	// 34: Codice istat dello stato estero dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "34")
	private String stato;

	// 35: Nome dello stato estero dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "35")
	private String nomeStato;

	// 45: Zona dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "45")
	private String zona;

	// 46: Complemento (i.e. altri dati) dell'indirizzo fisico di invio al destinatario (se invio tramite posta ordinaria)
	@NumeroColonna(numero = "46")
	private String complementoIndirizzo;

	// 47: Appendici del civico dell'indirizzo fisico di invio al destinatario
	@NumeroColonna(numero = "47")
	private String appendici;

	// 49: Tipo di toponimo dell'indirizzo fisico di invio al destinatario
	@NumeroColonna(numero = "49")
	private String tipoToponimo;

	// 50: indirizzo email del contribuente
	@NumeroColonna(numero = "50")
	private String emailContribuente;
	
	// 51: Se valorizzato (valore fisso 1) indica che il soggetto è anche un mittente
	@NumeroColonna(numero = "51")
	private Flag flgAncheMittente;
	
	@NumeroColonna(numero = "52")
	private String tipoDocRiconoscimento;
	
	@NumeroColonna(numero = "53")
	private String telefonoContribuente;
	
	// Tipologia del soggetto richiedente, usata per l'incaricato del prelievo su richiesta acesso atti
	// U: Ufficio
	// E: Richiedente esterno
	@NumeroColonna(numero = "54")
	private String tipoRichiedente;

	public String getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getProvCISogg() {
		return provCISogg;
	}

	public void setProvCISogg(String provCISogg) {
		this.provCISogg = provCISogg;
	}

	public Flag getFlgPersFisica() {
		return flgPersFisica;
	}

	public void setFlgPersFisica(Flag flgPersFisica) {
		this.flgPersFisica = flgPersFisica;
	}

	public String getDenominazioneCognome() {
		return denominazioneCognome;
	}

	public void setDenominazioneCognome(String denominazioneCognome) {
		this.denominazioneCognome = denominazioneCognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getDataNascitaIstituzione() {
		return dataNascitaIstituzione;
	}

	public void setDataNascitaIstituzione(String dataNascitaIstituzione) {
		this.dataNascitaIstituzione = dataNascitaIstituzione;
	}

	public String getCodIstatComuneNascitaIstituzione() {
		return codIstatComuneNascitaIstituzione;
	}

	public void setCodIstatComuneNascitaIstituzione(String codIstatComuneNascitaIstituzione) {
		this.codIstatComuneNascitaIstituzione = codIstatComuneNascitaIstituzione;
	}

	public String getNomeComuneNascitaIstituzione() {
		return nomeComuneNascitaIstituzione;
	}

	public void setNomeComuneNascitaIstituzione(String nomeComuneNascitaIstituzione) {
		this.nomeComuneNascitaIstituzione = nomeComuneNascitaIstituzione;
	}

	public String getCodIstatStatoNascitaIstituzione() {
		return codIstatStatoNascitaIstituzione;
	}

	public void setCodIstatStatoNascitaIstituzione(String codIstatStatoNascitaIstituzione) {
		this.codIstatStatoNascitaIstituzione = codIstatStatoNascitaIstituzione;
	}

	public String getNomeStatoNascitaIstituzione() {
		return nomeStatoNascitaIstituzione;
	}

	public void setNomeStatoNascitaIstituzione(String nomeStatoNascitaIstituzione) {
		this.nomeStatoNascitaIstituzione = nomeStatoNascitaIstituzione;
	}

	public String getCodIstatStatoCittadinanza() {
		return codIstatStatoCittadinanza;
	}

	public void setCodIstatStatoCittadinanza(String codIstatStatoCittadinanza) {
		this.codIstatStatoCittadinanza = codIstatStatoCittadinanza;
	}

	public String getNomeStatoCittadinanza() {
		return nomeStatoCittadinanza;
	}

	public void setNomeStatoCittadinanza(String nomeStatoCittadinanza) {
		this.nomeStatoCittadinanza = nomeStatoCittadinanza;
	}

	public String getFlgDaSalvareInRubrica() {
		return flgDaSalvareInRubrica;
	}

	public void setFlgDaSalvareInRubrica(String flgDaSalvareInRubrica) {
		this.flgDaSalvareInRubrica = flgDaSalvareInRubrica;
	}

	public String getCodNaturaRel() {
		return codNaturaRel;
	}

	public void setCodNaturaRel(String codNaturaRel) {
		this.codNaturaRel = codNaturaRel;
	}

	public String getDesNaturaRel() {
		return desNaturaRel;
	}

	public void setDesNaturaRel(String desNaturaRel) {
		this.desNaturaRel = desNaturaRel;
	}

	public String getDettagliRel() {
		return dettagliRel;
	}

	public void setDettagliRel(String dettagliRel) {
		this.dettagliRel = dettagliRel;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getCodiceRapido() {
		return codiceRapido;
	}

	public void setCodiceRapido(String codiceRapido) {
		this.codiceRapido = codiceRapido;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getEstremiDocRiconoscimento() {
		return estremiDocRiconoscimento;
	}

	public void setEstremiDocRiconoscimento(String estremiDocRiconoscimento) {
		this.estremiDocRiconoscimento = estremiDocRiconoscimento;
	}

	public String getCodToponimo() {
		return codToponimo;
	}

	public void setCodToponimo(String codToponimo) {
		this.codToponimo = codToponimo;
	}

	public String getToponimoIndirizzo() {
		return toponimoIndirizzo;
	}

	public void setToponimoIndirizzo(String toponimoIndirizzo) {
		this.toponimoIndirizzo = toponimoIndirizzo;
	}

	public String getFrazione() {
		return frazione;
	}

	public void setFrazione(String frazione) {
		this.frazione = frazione;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getNomeComuneCitta() {
		return nomeComuneCitta;
	}

	public void setNomeComuneCitta(String nomeComuneCitta) {
		this.nomeComuneCitta = nomeComuneCitta;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getNomeStato() {
		return nomeStato;
	}

	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getComplementoIndirizzo() {
		return complementoIndirizzo;
	}

	public void setComplementoIndirizzo(String complementoIndirizzo) {
		this.complementoIndirizzo = complementoIndirizzo;
	}

	public String getAppendici() {
		return appendici;
	}

	public void setAppendici(String appendici) {
		this.appendici = appendici;
	}

	public String getTipoToponimo() {
		return tipoToponimo;
	}

	public void setTipoToponimo(String tipoToponimo) {
		this.tipoToponimo = tipoToponimo;
	}

	public String getEmailContribuente() {
		return emailContribuente;
	}

	public void setEmailContribuente(String emailContribuente) {
		this.emailContribuente = emailContribuente;
	}

	public Flag getFlgAncheMittente() {
		return flgAncheMittente;
	}

	public void setFlgAncheMittente(Flag flgAncheMittente) {
		this.flgAncheMittente = flgAncheMittente;
	}

	public String getTipoDocRiconoscimento() {
		return tipoDocRiconoscimento;
	}

	public void setTipoDocRiconoscimento(String tipoDocRiconoscimento) {
		this.tipoDocRiconoscimento = tipoDocRiconoscimento;
	}

	public String getTelefonoContribuente() {
		return telefonoContribuente;
	}

	public void setTelefonoContribuente(String telefonoContribuente) {
		this.telefonoContribuente = telefonoContribuente;
	}
	
	public String getTipoRichiedente() {
		return tipoRichiedente;
	}

	public void setTipoRichiedente(String tipoRichiedente) {
		this.tipoRichiedente = tipoRichiedente;
	}	

}
