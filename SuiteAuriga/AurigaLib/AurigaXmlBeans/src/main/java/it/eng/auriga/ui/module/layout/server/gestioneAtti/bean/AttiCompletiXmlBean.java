/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * Formato java dell'xml restituito dalla stored procedure DmpkWfTrovalistalavoro
 * 
 * @author Dancrist
 *
 */
public class AttiCompletiXmlBean {

	@NumeroColonna(numero = "1")
	private String idProcedimento;

	@NumeroColonna(numero = "2")
	private String procedimentoType;

	@NumeroColonna(numero = "3")
	private String tipoAtto;

	@NumeroColonna(numero = "4")
	private String procedimentoObject;

	@NumeroColonna(numero = "5")
	@TipoData(tipo = Tipo.DATA)
	private Date dataAvvio;

	@NumeroColonna(numero = "6")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataDecorrenza;

	@NumeroColonna(numero = "7")
	private String procedimentoPadreDati;

	@NumeroColonna(numero = "8")
	private String estremi;

	@NumeroColonna(numero = "9")
	private String folderData;

	@NumeroColonna(numero = "10")
	private String procedimentoStatus;

	@NumeroColonna(numero = "11")
	private String numeroProposta;

	@NumeroColonna(numero = "12")
	private String numeroAtto;

	@NumeroColonna(numero = "13")
	private String inFase;

	@NumeroColonna(numero = "14")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date terminiDataDecorrenza;

	@NumeroColonna(numero = "15")
	private String statoTermini;

	@NumeroColonna(numero = "16")
	private String ultimaAttivita;

	@NumeroColonna(numero = "17")
	private String ultimaAttivitaMessaggio;

	@NumeroColonna(numero = "18")
	private String note;

	@NumeroColonna(numero = "19")
	private String soggettiEsterniPrincipali;

	@NumeroColonna(numero = "20")
	private String idUdFolder; // Id. fascicolo

	@NumeroColonna(numero = "21")
	private String unitaDocumentariaId; // Id. UD

	@NumeroColonna(numero = "23")
	private String ordinamentoNumeroProposta;

	@NumeroColonna(numero = "24")
	private String ordinamentoNumeroAtto;

	@NumeroColonna(numero = "25")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataProposta;

	@NumeroColonna(numero = "26")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAtto;

	@NumeroColonna(numero = "27")
	private String oggetto;

	@NumeroColonna(numero = "28")
	private String unOrgProponente;

	@NumeroColonna(numero = "29")
	private String avviatoDa;

	@NumeroColonna(numero = "30")
	private String prossimeAttivita;

	@NumeroColonna(numero = "31")
	private String ruoloSmistamento;

	@NumeroColonna(numero = "32")
	private String assegnatario;

	@NumeroColonna(numero = "35")
	private String datiContabiliArt;

	@NumeroColonna(numero = "36")
	private String datiContabiliCap;

	@NumeroColonna(numero = "37")
	private String datiContabiliDDN;

	@NumeroColonna(numero = "38")
	private String datiContabiliEsercizio;

	@NumeroColonna(numero = "39")
	private String datiContabiliImporto;

	@NumeroColonna(numero = "40")
	private String datiContabiliNr;
	
	@NumeroColonna(numero = "41")
	private String uriDispositivoAtto;
	
	@NumeroColonna(numero = "42")
	private String nomeFileDispositivoAtto;
	
	@NumeroColonna(numero = "43")
	@TipoData(tipo = Tipo.DATA)
	private Date dataInvioVerificaContabile;
	
	@NumeroColonna(numero = "44")
	@TipoData(tipo = Tipo.DATA)
	private Date dataInvioApprovazioneDG;
		
	@NumeroColonna(numero = "45")
	private String rup;
	
	@NumeroColonna(numero = "46")
	private String respProcedimento;
	
	@NumeroColonna(numero = "47")
	private String altriRespInConcerto;
	
	@NumeroColonna(numero = "48")
	private String responsabilePEG;
	
	@NumeroColonna(numero = "49")
	private String uoCompDefSpesa;
	
	@NumeroColonna(numero = "50")
	private String dirigenteAdottante;
	
	@NumeroColonna(numero = "72")
	private String codiceCIG;
	
	@NumeroColonna(numero = "73")
	private String nominativi;
	
	@NumeroColonna(numero = "101")
	private String visionato;
	
	@NumeroColonna(numero = "102")
	private String tagApposti;
	
	@NumeroColonna(numero = "103")
	private String flgNotifiche;
	
	@NumeroColonna(numero = "106")
	private String nrAttoStruttura;
	
	@NumeroColonna(numero = "107")
	private String nrLiqContestuale;
	
	@NumeroColonna(numero = "114")
	private String flgPresentiEmendamenti;
	
	/**
	 * Per l'anteprima della proposta atto dalla lista
	 */
	
	@NumeroColonna(numero = "74")
	private String idModDispositivo;
	
	@NumeroColonna(numero = "75")
	private String nomeModDispositivo;
	
	@NumeroColonna(numero = "76")
	private String displayFilenameModDispositivo;
	
	/**/
	
	@NumeroColonna(numero = "77")
	private String parolaChiave;
	
	@NumeroColonna(numero = "78")
	private String iniziativa;
	
	@NumeroColonna(numero = "79")
	private String flgRilevanzaContabile;
	
	@NumeroColonna(numero = "80")
	private String tipoDettaglio;
	
	@NumeroColonna(numero = "81")
	private String codTipo;
	
	@NumeroColonna(numero = "82")
	private String flgImmEseguibile;
	
	@NumeroColonna(numero = "83")
	private String stato;
	
	/**
	 * Per l'anteprima dell'appendice contabile dalla lista
	 */
	
	@NumeroColonna(numero = "84")
	private String idModContabile;
	
	@NumeroColonna(numero = "85")
	private String nomeModContabile;
	
	@NumeroColonna(numero = "86")
	private String displayFilenameModContabile;
	
	/**
	 * Per la visualizzazione del visto reg. contabile dalla lista
	 */
	
	@NumeroColonna(numero = "87")
	private String idDocVistoRegContabile;
	
	@NumeroColonna(numero = "88")
	private String uriVistoRegContabile;
	
	@NumeroColonna(numero = "89")
	private String displayFilenameVistoRegContabile;
	
	/**/
	
	@NumeroColonna(numero = "90")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPrimoInoltroRagioneria;
	
	@NumeroColonna(numero = "91")
	private Integer nroInoltriRagioneria;
	
	@NumeroColonna(numero = "92")
	private String centroDiCosto;
	
	@NumeroColonna(numero = "93")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScadenza;
	
	@NumeroColonna(numero = "94")
	private String ordinativi;
	
	@NumeroColonna(numero = "95")
	private String ultimaAttivitaEsito;
	
	@NumeroColonna(numero = "96")
	private String altUltimaAttivitaEsito;
	
	/**
	 * Per la rigenerazione e l'anteprima del riepilogo firme e visti dalla lista
	 */
	
	@NumeroColonna(numero = "108")
	private String idDocRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "109")
	private String uriRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "110")
	private String nomeFileRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "111")
	private String idModRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "112")
	private String nomeModRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "113")
	private String displayFilenameModRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "115")
	private String flgGeneraFileUnionePerLibroFirma;
	
	@NumeroColonna(numero = "116")
	private String activityName;
	
	@NumeroColonna(numero = "117")
	private String uoCompetente;
	
	@NumeroColonna(numero = "118")
	private String flgPrevistaNumerazione;
	
	@NumeroColonna(numero = "119")
	private String idModRiepilogoFirmeVisti2;
	
	@NumeroColonna(numero = "120")
	private String nomeModRiepilogoFirmeVisti2;
	
	@NumeroColonna(numero = "121")
	private String displayFilenameModRiepilogoFirmeVisti2;
	
	@NumeroColonna(numero = "134")
	private String prossimoTaskAppongoFirmaVisto;
	
	@NumeroColonna(numero = "135")
	private String prossimoTaskRifiutoFirmaVisto;
	
	@NumeroColonna(numero = "136")
	private String assegnatarioSG;
	
	@NumeroColonna(numero = "137")
	private String programmazioneAcquisti;
	
	@NumeroColonna(numero = "138")
	private String cui;
	
	@NumeroColonna(numero = "139")
	private String assegnatarioUffAcquisti;
	
	/**
	 * Getters and Setters
	 */

	public String getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(String idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public String getProcedimentoType() {
		return procedimentoType;
	}

	public void setProcedimentoType(String procedimentoType) {
		this.procedimentoType = procedimentoType;
	}

	public String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public String getProcedimentoObject() {
		return procedimentoObject;
	}

	public void setProcedimentoObject(String procedimentoObject) {
		this.procedimentoObject = procedimentoObject;
	}

	public Date getDataAvvio() {
		return dataAvvio;
	}

	public void setDataAvvio(Date dataAvvio) {
		this.dataAvvio = dataAvvio;
	}

	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}

	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}

	public String getProcedimentoPadreDati() {
		return procedimentoPadreDati;
	}

	public void setProcedimentoPadreDati(String procedimentoPadreDati) {
		this.procedimentoPadreDati = procedimentoPadreDati;
	}

	public String getEstremi() {
		return estremi;
	}

	public void setEstremi(String estremi) {
		this.estremi = estremi;
	}

	public String getFolderData() {
		return folderData;
	}

	public void setFolderData(String folderData) {
		this.folderData = folderData;
	}

	public String getProcedimentoStatus() {
		return procedimentoStatus;
	}

	public void setProcedimentoStatus(String procedimentoStatus) {
		this.procedimentoStatus = procedimentoStatus;
	}

	public String getNumeroProposta() {
		return numeroProposta;
	}

	public void setNumeroProposta(String numeroProposta) {
		this.numeroProposta = numeroProposta;
	}

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public String getInFase() {
		return inFase;
	}

	public void setInFase(String inFase) {
		this.inFase = inFase;
	}

	public Date getTerminiDataDecorrenza() {
		return terminiDataDecorrenza;
	}

	public void setTerminiDataDecorrenza(Date terminiDataDecorrenza) {
		this.terminiDataDecorrenza = terminiDataDecorrenza;
	}

	public String getStatoTermini() {
		return statoTermini;
	}

	public void setStatoTermini(String statoTermini) {
		this.statoTermini = statoTermini;
	}

	public String getUltimaAttivita() {
		return ultimaAttivita;
	}

	public void setUltimaAttivita(String ultimaAttivita) {
		this.ultimaAttivita = ultimaAttivita;
	}

	public String getUltimaAttivitaMessaggio() {
		return ultimaAttivitaMessaggio;
	}

	public void setUltimaAttivitaMessaggio(String ultimaAttivitaMessaggio) {
		this.ultimaAttivitaMessaggio = ultimaAttivitaMessaggio;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSoggettiEsterniPrincipali() {
		return soggettiEsterniPrincipali;
	}

	public void setSoggettiEsterniPrincipali(String soggettiEsterniPrincipali) {
		this.soggettiEsterniPrincipali = soggettiEsterniPrincipali;
	}

	public String getIdUdFolder() {
		return idUdFolder;
	}

	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}

	public String getUnitaDocumentariaId() {
		return unitaDocumentariaId;
	}

	public void setUnitaDocumentariaId(String unitaDocumentariaId) {
		this.unitaDocumentariaId = unitaDocumentariaId;
	}

	public String getOrdinamentoNumeroProposta() {
		return ordinamentoNumeroProposta;
	}

	public void setOrdinamentoNumeroProposta(String ordinamentoNumeroProposta) {
		this.ordinamentoNumeroProposta = ordinamentoNumeroProposta;
	}

	public String getOrdinamentoNumeroAtto() {
		return ordinamentoNumeroAtto;
	}

	public void setOrdinamentoNumeroAtto(String ordinamentoNumeroAtto) {
		this.ordinamentoNumeroAtto = ordinamentoNumeroAtto;
	}

	public Date getDataProposta() {
		return dataProposta;
	}

	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}

	public Date getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getUnOrgProponente() {
		return unOrgProponente;
	}

	public void setUnOrgProponente(String unOrgProponente) {
		this.unOrgProponente = unOrgProponente;
	}

	public String getAvviatoDa() {
		return avviatoDa;
	}

	public void setAvviatoDa(String avviatoDa) {
		this.avviatoDa = avviatoDa;
	}

	public String getProssimeAttivita() {
		return prossimeAttivita;
	}

	public void setProssimeAttivita(String prossimeAttivita) {
		this.prossimeAttivita = prossimeAttivita;
	}

	public String getRuoloSmistamento() {
		return ruoloSmistamento;
	}

	public void setRuoloSmistamento(String ruoloSmistamento) {
		this.ruoloSmistamento = ruoloSmistamento;
	}

	public String getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(String assegnatario) {
		this.assegnatario = assegnatario;
	}

	public String getDatiContabiliArt() {
		return datiContabiliArt;
	}

	public void setDatiContabiliArt(String datiContabiliArt) {
		this.datiContabiliArt = datiContabiliArt;
	}

	public String getDatiContabiliCap() {
		return datiContabiliCap;
	}

	public void setDatiContabiliCap(String datiContabiliCap) {
		this.datiContabiliCap = datiContabiliCap;
	}

	public String getDatiContabiliDDN() {
		return datiContabiliDDN;
	}

	public void setDatiContabiliDDN(String datiContabiliDDN) {
		this.datiContabiliDDN = datiContabiliDDN;
	}

	public String getDatiContabiliEsercizio() {
		return datiContabiliEsercizio;
	}

	public void setDatiContabiliEsercizio(String datiContabiliEsercizio) {
		this.datiContabiliEsercizio = datiContabiliEsercizio;
	}

	public String getDatiContabiliImporto() {
		return datiContabiliImporto;
	}

	public void setDatiContabiliImporto(String datiContabiliImporto) {
		this.datiContabiliImporto = datiContabiliImporto;
	}

	public String getDatiContabiliNr() {
		return datiContabiliNr;
	}

	public void setDatiContabiliNr(String datiContabiliNr) {
		this.datiContabiliNr = datiContabiliNr;
	}

	public String getUriDispositivoAtto() {
		return uriDispositivoAtto;
	}

	public void setUriDispositivoAtto(String uriDispositivoAtto) {
		this.uriDispositivoAtto = uriDispositivoAtto;
	}

	public String getNomeFileDispositivoAtto() {
		return nomeFileDispositivoAtto;
	}

	public void setNomeFileDispositivoAtto(String nomeFileDispositivoAtto) {
		this.nomeFileDispositivoAtto = nomeFileDispositivoAtto;
	}

	public Date getDataInvioVerificaContabile() {
		return dataInvioVerificaContabile;
	}

	public void setDataInvioVerificaContabile(Date dataInvioVerificaContabile) {
		this.dataInvioVerificaContabile = dataInvioVerificaContabile;
	}

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
	}

	public String getRespProcedimento() {
		return respProcedimento;
	}

	public void setRespProcedimento(String respProcedimento) {
		this.respProcedimento = respProcedimento;
	}

	public String getAltriRespInConcerto() {
		return altriRespInConcerto;
	}

	public void setAltriRespInConcerto(String altriRespInConcerto) {
		this.altriRespInConcerto = altriRespInConcerto;
	}

	public String getResponsabilePEG() {
		return responsabilePEG;
	}

	public void setResponsabilePEG(String responsabilePEG) {
		this.responsabilePEG = responsabilePEG;
	}

	public String getUoCompDefSpesa() {
		return uoCompDefSpesa;
	}

	public void setUoCompDefSpesa(String uoCompDefSpesa) {
		this.uoCompDefSpesa = uoCompDefSpesa;
	}

	public String getDirigenteAdottante() {
		return dirigenteAdottante;
	}

	public void setDirigenteAdottante(String dirigenteAdottante) {
		this.dirigenteAdottante = dirigenteAdottante;
	}

	public String getCodiceCIG() {
		return codiceCIG;
	}

	public void setCodiceCIG(String codiceCIG) {
		this.codiceCIG = codiceCIG;
	}

	public String getNominativi() {
		return nominativi;
	}

	public void setNominativi(String nominativi) {
		this.nominativi = nominativi;
	}

	public String getVisionato() {
		return visionato;
	}

	public void setVisionato(String visionato) {
		this.visionato = visionato;
	}

	public String getTagApposti() {
		return tagApposti;
	}

	public void setTagApposti(String tagApposti) {
		this.tagApposti = tagApposti;
	}

	public String getFlgNotifiche() {
		return flgNotifiche;
	}

	public void setFlgNotifiche(String flgNotifiche) {
		this.flgNotifiche = flgNotifiche;
	}

	public String getNrAttoStruttura() {
		return nrAttoStruttura;
	}

	public void setNrAttoStruttura(String nrAttoStruttura) {
		this.nrAttoStruttura = nrAttoStruttura;
	}

	public String getNrLiqContestuale() {
		return nrLiqContestuale;
	}

	public void setNrLiqContestuale(String nrLiqContestuale) {
		this.nrLiqContestuale = nrLiqContestuale;
	}

	public String getIdModDispositivo() {
		return idModDispositivo;
	}

	public void setIdModDispositivo(String idModDispositivo) {
		this.idModDispositivo = idModDispositivo;
	}

	public String getNomeModDispositivo() {
		return nomeModDispositivo;
	}

	public void setNomeModDispositivo(String nomeModDispositivo) {
		this.nomeModDispositivo = nomeModDispositivo;
	}

	public String getDisplayFilenameModDispositivo() {
		return displayFilenameModDispositivo;
	}

	public void setDisplayFilenameModDispositivo(String displayFilenameModDispositivo) {
		this.displayFilenameModDispositivo = displayFilenameModDispositivo;
	}

	public String getParolaChiave() {
		return parolaChiave;
	}

	public void setParolaChiave(String parolaChiave) {
		this.parolaChiave = parolaChiave;
	}

	public String getIniziativa() {
		return iniziativa;
	}

	public void setIniziativa(String iniziativa) {
		this.iniziativa = iniziativa;
	}

	public String getFlgRilevanzaContabile() {
		return flgRilevanzaContabile;
	}

	public void setFlgRilevanzaContabile(String flgRilevanzaContabile) {
		this.flgRilevanzaContabile = flgRilevanzaContabile;
	}

	public String getTipoDettaglio() {
		return tipoDettaglio;
	}

	public void setTipoDettaglio(String tipoDettaglio) {
		this.tipoDettaglio = tipoDettaglio;
	}

	public String getCodTipo() {
		return codTipo;
	}

	public void setCodTipo(String codTipo) {
		this.codTipo = codTipo;
	}

	public String getFlgImmEseguibile() {
		return flgImmEseguibile;
	}

	public void setFlgImmEseguibile(String flgImmEseguibile) {
		this.flgImmEseguibile = flgImmEseguibile;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getIdModContabile() {
		return idModContabile;
	}

	public void setIdModContabile(String idModContabile) {
		this.idModContabile = idModContabile;
	}

	public String getNomeModContabile() {
		return nomeModContabile;
	}

	public void setNomeModContabile(String nomeModContabile) {
		this.nomeModContabile = nomeModContabile;
	}

	public String getDisplayFilenameModContabile() {
		return displayFilenameModContabile;
	}

	public void setDisplayFilenameModContabile(String displayFilenameModContabile) {
		this.displayFilenameModContabile = displayFilenameModContabile;
	}

	public String getIdDocVistoRegContabile() {
		return idDocVistoRegContabile;
	}

	public void setIdDocVistoRegContabile(String idDocVistoRegContabile) {
		this.idDocVistoRegContabile = idDocVistoRegContabile;
	}

	public String getUriVistoRegContabile() {
		return uriVistoRegContabile;
	}

	public void setUriVistoRegContabile(String uriVistoRegContabile) {
		this.uriVistoRegContabile = uriVistoRegContabile;
	}

	public String getDisplayFilenameVistoRegContabile() {
		return displayFilenameVistoRegContabile;
	}

	public void setDisplayFilenameVistoRegContabile(String displayFilenameVistoRegContabile) {
		this.displayFilenameVistoRegContabile = displayFilenameVistoRegContabile;
	}

	public Date getDataPrimoInoltroRagioneria() {
		return dataPrimoInoltroRagioneria;
	}

	public void setDataPrimoInoltroRagioneria(Date dataPrimoInoltroRagioneria) {
		this.dataPrimoInoltroRagioneria = dataPrimoInoltroRagioneria;
	}

	public Integer getNroInoltriRagioneria() {
		return nroInoltriRagioneria;
	}

	public void setNroInoltriRagioneria(Integer nroInoltriRagioneria) {
		this.nroInoltriRagioneria = nroInoltriRagioneria;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getOrdinativi() {
		return ordinativi;
	}

	public void setOrdinativi(String ordinativi) {
		this.ordinativi = ordinativi;
	}

	public String getUltimaAttivitaEsito() {
		return ultimaAttivitaEsito;
	}

	public void setUltimaAttivitaEsito(String ultimaAttivitaEsito) {
		this.ultimaAttivitaEsito = ultimaAttivitaEsito;
	}

	public String getAltUltimaAttivitaEsito() {
		return altUltimaAttivitaEsito;
	}

	public void setAltUltimaAttivitaEsito(String altUltimaAttivitaEsito) {
		this.altUltimaAttivitaEsito = altUltimaAttivitaEsito;
	}

	public String getIdDocRiepilogoFirmeVisti() {
		return idDocRiepilogoFirmeVisti;
	}

	public void setIdDocRiepilogoFirmeVisti(String idDocRiepilogoFirmeVisti) {
		this.idDocRiepilogoFirmeVisti = idDocRiepilogoFirmeVisti;
	}

	public String getUriRiepilogoFirmeVisti() {
		return uriRiepilogoFirmeVisti;
	}

	public void setUriRiepilogoFirmeVisti(String uriRiepilogoFirmeVisti) {
		this.uriRiepilogoFirmeVisti = uriRiepilogoFirmeVisti;
	}

	public String getNomeFileRiepilogoFirmeVisti() {
		return nomeFileRiepilogoFirmeVisti;
	}

	public void setNomeFileRiepilogoFirmeVisti(String nomeFileRiepilogoFirmeVisti) {
		this.nomeFileRiepilogoFirmeVisti = nomeFileRiepilogoFirmeVisti;
	}

	public String getIdModRiepilogoFirmeVisti() {
		return idModRiepilogoFirmeVisti;
	}

	public void setIdModRiepilogoFirmeVisti(String idModRiepilogoFirmeVisti) {
		this.idModRiepilogoFirmeVisti = idModRiepilogoFirmeVisti;
	}

	public String getNomeModRiepilogoFirmeVisti() {
		return nomeModRiepilogoFirmeVisti;
	}

	public void setNomeModRiepilogoFirmeVisti(String nomeModRiepilogoFirmeVisti) {
		this.nomeModRiepilogoFirmeVisti = nomeModRiepilogoFirmeVisti;
	}

	public String getDisplayFilenameModRiepilogoFirmeVisti() {
		return displayFilenameModRiepilogoFirmeVisti;
	}

	public void setDisplayFilenameModRiepilogoFirmeVisti(String displayFilenameModRiepilogoFirmeVisti) {
		this.displayFilenameModRiepilogoFirmeVisti = displayFilenameModRiepilogoFirmeVisti;
	}

	public String getFlgPresentiEmendamenti() {
		return flgPresentiEmendamenti;
	}

	public void setFlgPresentiEmendamenti(String flgPresentiEmendamenti) {
		this.flgPresentiEmendamenti = flgPresentiEmendamenti;
	}
	
	public String getFlgGeneraFileUnionePerLibroFirma() {
		return flgGeneraFileUnionePerLibroFirma;
	}

	public void setFlgGeneraFileUnionePerLibroFirma(String flgGeneraFileUnionePerLibroFirma) {
		this.flgGeneraFileUnionePerLibroFirma = flgGeneraFileUnionePerLibroFirma;
	}
	
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getDataInvioApprovazioneDG() {
		return dataInvioApprovazioneDG;
	}

	public void setDataInvioApprovazioneDG(Date dataInvioApprovazioneDG) {
		this.dataInvioApprovazioneDG = dataInvioApprovazioneDG;
	}

	public String getUoCompetente() {
		return uoCompetente;
	}

	public void setUoCompetente(String uoCompetente) {
		this.uoCompetente = uoCompetente;
	}
	
	public String getFlgPrevistaNumerazione() {
		return flgPrevistaNumerazione;
	}

	public void setFlgPrevistaNumerazione(String flgPrevistaNumerazione) {
		this.flgPrevistaNumerazione = flgPrevistaNumerazione;
	}

	public String getIdModRiepilogoFirmeVisti2() {
		return idModRiepilogoFirmeVisti2;
	}

	public void setIdModRiepilogoFirmeVisti2(String idModRiepilogoFirmeVisti2) {
		this.idModRiepilogoFirmeVisti2 = idModRiepilogoFirmeVisti2;
	}

	public String getNomeModRiepilogoFirmeVisti2() {
		return nomeModRiepilogoFirmeVisti2;
	}

	public void setNomeModRiepilogoFirmeVisti2(String nomeModRiepilogoFirmeVisti2) {
		this.nomeModRiepilogoFirmeVisti2 = nomeModRiepilogoFirmeVisti2;
	}

	public String getDisplayFilenameModRiepilogoFirmeVisti2() {
		return displayFilenameModRiepilogoFirmeVisti2;
	}

	public void setDisplayFilenameModRiepilogoFirmeVisti2(String displayFilenameModRiepilogoFirmeVisti2) {
		this.displayFilenameModRiepilogoFirmeVisti2 = displayFilenameModRiepilogoFirmeVisti2;
	}

	public String getProssimoTaskAppongoFirmaVisto() {
		return prossimoTaskAppongoFirmaVisto;
	}
	
	public void setProssimoTaskAppongoFirmaVisto(String prossimoTaskAppongoFirmaVisto) {
		this.prossimoTaskAppongoFirmaVisto = prossimoTaskAppongoFirmaVisto;
	}

	public String getProssimoTaskRifiutoFirmaVisto() {
		return prossimoTaskRifiutoFirmaVisto;
	}
	
	public void setProssimoTaskRifiutoFirmaVisto(String prossimoTaskRifiutoFirmaVisto) {
		this.prossimoTaskRifiutoFirmaVisto = prossimoTaskRifiutoFirmaVisto;
	}

	public String getAssegnatarioSG() {
		return assegnatarioSG;
	}

	public void setAssegnatarioSG(String assegnatarioSG) {
		this.assegnatarioSG = assegnatarioSG;
	}

	public String getProgrammazioneAcquisti() {
		return programmazioneAcquisti;
	}

	public void setProgrammazioneAcquisti(String programmazioneAcquisti) {
		this.programmazioneAcquisti = programmazioneAcquisti;
	}

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getAssegnatarioUffAcquisti() {
		return assegnatarioUffAcquisti;
	}

	public void setAssegnatarioUffAcquisti(String assegnatarioUffAcquisti) {
		this.assegnatarioUffAcquisti = assegnatarioUffAcquisti;
	}
	
}