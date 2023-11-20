/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;

import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;

/**
 * Rimappa i dati esposti da DmpkWfTrovalistalavoroBean
 * 
 * @author Dancrist
 *
 */
public class AttiCompletiBean {

	private String idProcedimento;
	private String procedimentoType;
	private String tipoAtto;
	private String procedimentoObject;
	private Date dataAvvio;
	private Date dataDecorrenza;
	private String procedimentoPadreData;
	private String estremi;
	private String folderData;
	private String procedimentoStatus;
	private String numeroProposta;
	private String numeroAtto;
	private String inFase;
	private String terminiDataDecorrenza;
	private String statoTermini;
	private String ultimaAttivita;
	private String ultimaAttivitaEsito;
	private String altUltimaAttivitaEsito;
	private String ultimaAttivitaMessaggio;
	private String note;
	private String soggettiEsterniPrincipali;
	private String idUdFolder;
	private String unitaDocumentariaId;
	private String ordinamentoNumeroProposta;
	private String ordinamentoNumeroAtto;
	private Date dataProposta;
	private Date dataAtto;
	private String oggetto;
	private String unOrgProponente;
	private String avviatoDa;
	private String prossimeAttivita;
	private String ruoloSmistamento;
	private String assegnatario;
	private String datiContabiliArt;
	private String datiContabiliCap;
	private String datiContabiliDDN;
	private String datiContabiliEsercizio;
	private String datiContabiliImporto;
	private String datiContabiliNr;
	private String uriDispositivoAtto;
	private String nomeFileDispositivoAtto;
	private Date dataInvioVerificaContabile;
	private Date dataInvioApprovazioneDG;
	private String estensori;
	private String dirigenteAdottante;
	private String respProcedimento;
	private String rup;
	private String altriRespInConcerto;
	private String responsabilePEG;
	private String uoCompDefSpesa;
	private String codiceCIG;
	private String nominativi;
	private String parolaChiave;
	private String iniziativa;
	private String flgRilevanzaContabile;
	private String tipoDettaglio;
	private String codTipo;
	private String flgImmEseguibile;
	private String stato;
	private Date dataPrimoInoltroRagioneria;
	private Integer nroInoltriRagioneria;
	private String statoRagioneria;
	private String centroDiCosto;
	private Date dataScadenza;
	private String ordinativi;
	private String flgVisionato;
	private String altFlgVisionato;
	private String tagApposti;
	private String flgNotifiche;
	private String nrAttoStruttura;
	private String nrLiqContestuale;
	private String flgPresentiEmendamenti;
	
	/**
	 * Per l'anteprima della proposta atto dalla lista
	 */
	private String uriModCopertina;
	private String tipoModCopertina;
	private String idModDispositivo;
	private String nomeModDispositivo;
	private String displayFilenameModDispositivo;
	
	/**
	 * Per l'anteprima dell'appendice contabile dalla lista
	 */
	// private String uriModContabile;
	// private String tipoModContabile;
	private String idModContabile;
	private String nomeModContabile;
	private String displayFilenameModContabile;
	
	/**
	 * Per la visualizzazione del visto reg. contabile dalla lista
	 */
	private String idDocVistoRegContabile;
	private String uriVistoRegContabile;
	private String displayFilenameVistoRegContabile;
	
	/**
	 * Per la rigenerazione e l'anteprima del riepilogo firme e visti dalla lista
	 */
	private String idDocRiepilogoFirmeVisti;
	private String uriRiepilogoFirmeVisti;
	private String nomeFileRiepilogoFirmeVisti;
	private String idModRiepilogoFirmeVisti;
	private String nomeModRiepilogoFirmeVisti;
	private String displayFilenameModRiepilogoFirmeVisti;
	private String flgGeneraFileUnionePerLibroFirma;
	private String activityName;
	private String uoCompetente;
	private String flgPrevistaNumerazione;
	private String idModRiepilogoFirmeVisti2;
	private String nomeModRiepilogoFirmeVisti2;
	private String displayFilenameModRiepilogoFirmeVisti2;
	private String prossimoTaskAppongoFirmaVisto;
	private String prossimoTaskRifiutoFirmaVisto;
	private String assegnatarioSG;
	private String programmazioneAcquisti;
	private String cui;
	private String assegnatarioUffAcquisti;
	
	/**
	 * Variabili appoggio per il rilascio/revoca del visto massivo
	 */
	private AttProcBean attoProcedimento;
	private Boolean rilascioVisto;
	private HashMap<String, String> errorMessages;
	private Boolean esitoNumerazioneOk;
	private Boolean esitoGenerazioniDaModelloOk;

	/**
	 * Getter and Setter
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

	public String getProcedimentoPadreData() {
		return procedimentoPadreData;
	}

	public void setProcedimentoPadreData(String procedimentoPadreData) {
		this.procedimentoPadreData = procedimentoPadreData;
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

	public String getTerminiDataDecorrenza() {
		return terminiDataDecorrenza;
	}

	public void setTerminiDataDecorrenza(String terminiDataDecorrenza) {
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

	public String getEstensori() {
		return estensori;
	}

	public void setEstensori(String estensori) {
		this.estensori = estensori;
	}

	public String getDirigenteAdottante() {
		return dirigenteAdottante;
	}

	public void setDirigenteAdottante(String dirigenteAdottante) {
		this.dirigenteAdottante = dirigenteAdottante;
	}

	public String getRespProcedimento() {
		return respProcedimento;
	}

	public void setRespProcedimento(String respProcedimento) {
		this.respProcedimento = respProcedimento;
	}

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
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

	public String getStatoRagioneria() {
		return statoRagioneria;
	}

	public void setStatoRagioneria(String statoRagioneria) {
		this.statoRagioneria = statoRagioneria;
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

	public String getFlgVisionato() {
		return flgVisionato;
	}

	public void setFlgVisionato(String flgVisionato) {
		this.flgVisionato = flgVisionato;
	}

	public String getAltFlgVisionato() {
		return altFlgVisionato;
	}

	public void setAltFlgVisionato(String altFlgVisionato) {
		this.altFlgVisionato = altFlgVisionato;
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

	public String getUriModCopertina() {
		return uriModCopertina;
	}

	public void setUriModCopertina(String uriModCopertina) {
		this.uriModCopertina = uriModCopertina;
	}

	public String getTipoModCopertina() {
		return tipoModCopertina;
	}

	public void setTipoModCopertina(String tipoModCopertina) {
		this.tipoModCopertina = tipoModCopertina;
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
	
	public AttProcBean getAttoProcedimento() {
		return attoProcedimento;
	}
	
	public void setAttoProcedimento(AttProcBean attoProcedimento) {
		this.attoProcedimento = attoProcedimento;
	}

	public Boolean getRilascioVisto() {
		return rilascioVisto;
	}
	
	public void setRilascioVisto(Boolean rilascioVisto) {
		this.rilascioVisto = rilascioVisto;
	}

	public HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}
	
	public void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public Boolean getEsitoNumerazioneOk() {
		return esitoNumerazioneOk;
	}

	public void setEsitoNumerazioneOk(Boolean esitoNumerazioneOk) {
		this.esitoNumerazioneOk = esitoNumerazioneOk;
	}

	public Boolean getEsitoGenerazioniDaModelloOk() {
		return esitoGenerazioniDaModelloOk;
	}
	
	public void setEsitoGenerazioniDaModelloOk(Boolean esitoGenerazioniDaModelloOk) {
		this.esitoGenerazioniDaModelloOk = esitoGenerazioniDaModelloOk;
	}
	
}