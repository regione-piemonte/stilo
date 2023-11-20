/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author DANCRIST
 *
 */

public class ArgomentiOdgXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idUd;
	
	@NumeroColonna(numero = "2")
	private String tipo;
	
	@NumeroColonna(numero = "3")
	private String codTipo;
	
	@NumeroColonna(numero = "4")
	private String estremiPropostaUD;
	
	@NumeroColonna(numero = "5")
	private String nrOrdineOdg;
	
	@NumeroColonna(numero = "6")
	private String oggetto;
	
	@NumeroColonna(numero = "7")
	private String nominativoProponente;
	
	@NumeroColonna(numero = "8")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtInoltro;
	
	@NumeroColonna(numero = "9")
	private String flgInoltro;
	
	@NumeroColonna(numero = "10")
	private String flgAggiunto;
	
	@NumeroColonna(numero = "11")
	private Integer nroAllegati;
	
	@NumeroColonna(numero = "12")
	private String estremiPropostaDelibera;
	
	@NumeroColonna(numero = "13")
	private String idUdPropostaDelibera;

	@NumeroColonna(numero = "14")
	private String esitoDiscussione;
	
	@NumeroColonna(numero = "15")
	private String numeroFinaleAtto;
	
	@NumeroColonna(numero = "16")
	private String idProcessoAuriga;
	
	@NumeroColonna(numero = "17")
	private String idTipoFlussoActiviti;
	
	@NumeroColonna(numero = "18")
	private String idIstanzaFlussoActiviti;
	
	@NumeroColonna(numero = "19")
	private String nomeTaskActiviti;
	
	@NumeroColonna(numero = "20")
	private String strutturaProponente;
	
	@NumeroColonna(numero = "21")
	private String centroDiCosto;
	
	@NumeroColonna(numero = "22")
	private String nroCircoscrizione;
	
	@NumeroColonna(numero = "23")
	private String nomeCircoscrizione;
	
	@NumeroColonna(numero = "24")
	private String flgReinviata;
	
	@NumeroColonna(numero = "25")
	private String flgPresenteInOdg;
	
	@NumeroColonna(numero = "26")
	private String nrOrdUltimoOdgCons;
	
	@NumeroColonna(numero = "27")
	@TipoData(tipo = Tipo.DATA)
	private Date dtOdgConsolidato;
	
	@NumeroColonna(numero = "28")
	private String flgElimina;
	
	@NumeroColonna(numero = "29")
	private String uriFilePrimario;
	
	@NumeroColonna(numero = "30")
	private String nomeFilePrimario;
	
	@NumeroColonna(numero = "31")
	private String statoRevisioneTesto;
	
	@NumeroColonna(numero = "32")
	private String flgEmendamenti;
	
	@NumeroColonna(numero = "33")
	private String estremiPropostaUDXOrd;
	
	@NumeroColonna(numero = "34")
	private String iniziativaDelibera;
	
	@NumeroColonna(numero = "35")
	private String dettTipoAtto;
	
	@NumeroColonna(numero = "36")
	private String flgImmEseguibile;
	
	@NumeroColonna(numero = "37")
	private String noteAtto;
	
	/**
	 * Solo per DISCUSSIONE SEDUTA
	 */
	
	@NumeroColonna(numero = "38")
	private String firmeDaAcquisire;
	
	@NumeroColonna(numero = "39")
	private String firmeApposte;
	
	@NumeroColonna(numero = "40")
	private String attoDaFirmare;
	
	/**
	 * Per la rigenerazione e l'anteprima del riepilogo firme e visti dalla lista
	 */
	
	@NumeroColonna(numero = "41")
	private String idDocRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "42")
	private String uriRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "43")
	private String nomeFileRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "44")
	private String idModRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "45")
	private String nomeModRiepilogoFirmeVisti;
	
	@NumeroColonna(numero = "46")
	private String displayFilenameModRiepilogoFirmeVisti;
	
	//***************************************************
	
	@NumeroColonna(numero = "47")
	private String flgFuoriPacco;

	@NumeroColonna(numero = "48")
	private String eventoContabiliaInChiusuraSeduta;
	
	//***************************************************
	
	@NumeroColonna(numero = "49")
	private String inDefinizione;
	
	@NumeroColonna(numero = "50")
	private String ultimPassoIter;
	
	@NumeroColonna(numero = "51")
	private String uriSchedaSintesi;
	
	@NumeroColonna(numero = "52")
	private String idDocSchedaSintesi;
	
	@NumeroColonna(numero = "53")
	private String flgAttoTrasmesso;
	
	@NumeroColonna(numero = "54")
	private String estremiUDTrasmissione;
	
	@NumeroColonna(numero = "55")
	private String idUDTrasmissione;
	
	private String idDocPrimario;
	
	/**
	 * Getters and Setters
	 */

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodTipo() {
		return codTipo;
	}

	public void setCodTipo(String codTipo) {
		this.codTipo = codTipo;
	}

	public String getEstremiPropostaUD() {
		return estremiPropostaUD;
	}

	public void setEstremiPropostaUD(String estremiPropostaUD) {
		this.estremiPropostaUD = estremiPropostaUD;
	}

	public String getNrOrdineOdg() {
		return nrOrdineOdg;
	}

	public void setNrOrdineOdg(String nrOrdineOdg) {
		this.nrOrdineOdg = nrOrdineOdg;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getNominativoProponente() {
		return nominativoProponente;
	}

	public void setNominativoProponente(String nominativoProponente) {
		this.nominativoProponente = nominativoProponente;
	}

	public Date getDtInoltro() {
		return dtInoltro;
	}

	public void setDtInoltro(Date dtInoltro) {
		this.dtInoltro = dtInoltro;
	}

	public String getFlgInoltro() {
		return flgInoltro;
	}

	public void setFlgInoltro(String flgInoltro) {
		this.flgInoltro = flgInoltro;
	}

	public String getFlgAggiunto() {
		return flgAggiunto;
	}

	public void setFlgAggiunto(String flgAggiunto) {
		this.flgAggiunto = flgAggiunto;
	}

	public Integer getNroAllegati() {
		return nroAllegati;
	}

	public void setNroAllegati(Integer nroAllegati) {
		this.nroAllegati = nroAllegati;
	}

	public String getEstremiPropostaDelibera() {
		return estremiPropostaDelibera;
	}

	public void setEstremiPropostaDelibera(String estremiPropostaDelibera) {
		this.estremiPropostaDelibera = estremiPropostaDelibera;
	}

	public String getIdUdPropostaDelibera() {
		return idUdPropostaDelibera;
	}

	public void setIdUdPropostaDelibera(String idUdPropostaDelibera) {
		this.idUdPropostaDelibera = idUdPropostaDelibera;
	}

	public String getEsitoDiscussione() {
		return esitoDiscussione;
	}

	public void setEsitoDiscussione(String esitoDiscussione) {
		this.esitoDiscussione = esitoDiscussione;
	}

	public String getNumeroFinaleAtto() {
		return numeroFinaleAtto;
	}

	public void setNumeroFinaleAtto(String numeroFinaleAtto) {
		this.numeroFinaleAtto = numeroFinaleAtto;
	}

	public String getIdProcessoAuriga() {
		return idProcessoAuriga;
	}

	public void setIdProcessoAuriga(String idProcessoAuriga) {
		this.idProcessoAuriga = idProcessoAuriga;
	}

	public String getIdTipoFlussoActiviti() {
		return idTipoFlussoActiviti;
	}

	public void setIdTipoFlussoActiviti(String idTipoFlussoActiviti) {
		this.idTipoFlussoActiviti = idTipoFlussoActiviti;
	}

	public String getIdIstanzaFlussoActiviti() {
		return idIstanzaFlussoActiviti;
	}

	public void setIdIstanzaFlussoActiviti(String idIstanzaFlussoActiviti) {
		this.idIstanzaFlussoActiviti = idIstanzaFlussoActiviti;
	}

	public String getNomeTaskActiviti() {
		return nomeTaskActiviti;
	}

	public void setNomeTaskActiviti(String nomeTaskActiviti) {
		this.nomeTaskActiviti = nomeTaskActiviti;
	}

	public String getStrutturaProponente() {
		return strutturaProponente;
	}

	public void setStrutturaProponente(String strutturaProponente) {
		this.strutturaProponente = strutturaProponente;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public String getNroCircoscrizione() {
		return nroCircoscrizione;
	}

	public void setNroCircoscrizione(String nroCircoscrizione) {
		this.nroCircoscrizione = nroCircoscrizione;
	}

	public String getNomeCircoscrizione() {
		return nomeCircoscrizione;
	}

	public void setNomeCircoscrizione(String nomeCircoscrizione) {
		this.nomeCircoscrizione = nomeCircoscrizione;
	}

	public String getFlgReinviata() {
		return flgReinviata;
	}

	public void setFlgReinviata(String flgReinviata) {
		this.flgReinviata = flgReinviata;
	}

	public String getFlgPresenteInOdg() {
		return flgPresenteInOdg;
	}

	public void setFlgPresenteInOdg(String flgPresenteInOdg) {
		this.flgPresenteInOdg = flgPresenteInOdg;
	}

	public String getNrOrdUltimoOdgCons() {
		return nrOrdUltimoOdgCons;
	}

	public void setNrOrdUltimoOdgCons(String nrOrdUltimoOdgCons) {
		this.nrOrdUltimoOdgCons = nrOrdUltimoOdgCons;
	}

	public Date getDtOdgConsolidato() {
		return dtOdgConsolidato;
	}

	public void setDtOdgConsolidato(Date dtOdgConsolidato) {
		this.dtOdgConsolidato = dtOdgConsolidato;
	}

	public String getFlgElimina() {
		return flgElimina;
	}

	public void setFlgElimina(String flgElimina) {
		this.flgElimina = flgElimina;
	}

	public String getUriFilePrimario() {
		return uriFilePrimario;
	}

	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}

	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}

	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}

	public String getStatoRevisioneTesto() {
		return statoRevisioneTesto;
	}

	public void setStatoRevisioneTesto(String statoRevisioneTesto) {
		this.statoRevisioneTesto = statoRevisioneTesto;
	}

	public String getFlgEmendamenti() {
		return flgEmendamenti;
	}

	public void setFlgEmendamenti(String flgEmendamenti) {
		this.flgEmendamenti = flgEmendamenti;
	}

	public String getEstremiPropostaUDXOrd() {
		return estremiPropostaUDXOrd;
	}

	public void setEstremiPropostaUDXOrd(String estremiPropostaUDXOrd) {
		this.estremiPropostaUDXOrd = estremiPropostaUDXOrd;
	}

	public String getIniziativaDelibera() {
		return iniziativaDelibera;
	}

	public void setIniziativaDelibera(String iniziativaDelibera) {
		this.iniziativaDelibera = iniziativaDelibera;
	}

	public String getDettTipoAtto() {
		return dettTipoAtto;
	}

	public void setDettTipoAtto(String dettTipoAtto) {
		this.dettTipoAtto = dettTipoAtto;
	}

	public String getFlgImmEseguibile() {
		return flgImmEseguibile;
	}

	public void setFlgImmEseguibile(String flgImmEseguibile) {
		this.flgImmEseguibile = flgImmEseguibile;
	}

	public String getNoteAtto() {
		return noteAtto;
	}

	public void setNoteAtto(String noteAtto) {
		this.noteAtto = noteAtto;
	}

	public String getFirmeDaAcquisire() {
		return firmeDaAcquisire;
	}

	public void setFirmeDaAcquisire(String firmeDaAcquisire) {
		this.firmeDaAcquisire = firmeDaAcquisire;
	}

	public String getFirmeApposte() {
		return firmeApposte;
	}

	public void setFirmeApposte(String firmeApposte) {
		this.firmeApposte = firmeApposte;
	}

	public String getAttoDaFirmare() {
		return attoDaFirmare;
	}

	public void setAttoDaFirmare(String attoDaFirmare) {
		this.attoDaFirmare = attoDaFirmare;
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

	public String getFlgFuoriPacco() {
		return flgFuoriPacco;
	}

	public void setFlgFuoriPacco(String flgFuoriPacco) {
		this.flgFuoriPacco = flgFuoriPacco;
	}

	public String getEventoContabiliaInChiusuraSeduta() {
		return eventoContabiliaInChiusuraSeduta;
	}

	public void setEventoContabiliaInChiusuraSeduta(String eventoContabiliaInChiusuraSeduta) {
		this.eventoContabiliaInChiusuraSeduta = eventoContabiliaInChiusuraSeduta;
	}

	public String getInDefinizione() {
		return inDefinizione;
	}

	public void setInDefinizione(String inDefinizione) {
		this.inDefinizione = inDefinizione;
	}

	public String getUltimPassoIter() {
		return ultimPassoIter;
	}

	public void setUltimPassoIter(String ultimPassoIter) {
		this.ultimPassoIter = ultimPassoIter;
	}

	public String getUriSchedaSintesi() {
		return uriSchedaSintesi;
	}

	public void setUriSchedaSintesi(String uriSchedaSintesi) {
		this.uriSchedaSintesi = uriSchedaSintesi;
	}

	public String getIdDocSchedaSintesi() {
		return idDocSchedaSintesi;
	}

	public void setIdDocSchedaSintesi(String idDocSchedaSintesi) {
		this.idDocSchedaSintesi = idDocSchedaSintesi;
	}

	public String getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}

	public String getFlgAttoTrasmesso() {
		return flgAttoTrasmesso;
	}

	public void setFlgAttoTrasmesso(String flgAttoTrasmesso) {
		this.flgAttoTrasmesso = flgAttoTrasmesso;
	}

	public String getEstremiUDTrasmissione() {
		return estremiUDTrasmissione;
	}

	public void setEstremiUDTrasmissione(String estremiUDTrasmissione) {
		this.estremiUDTrasmissione = estremiUDTrasmissione;
	}

	public String getIdUDTrasmissione() {
		return idUDTrasmissione;
	}

	public void setIdUDTrasmissione(String idUDTrasmissione) {
		this.idUDTrasmissione = idUDTrasmissione;
	}
}