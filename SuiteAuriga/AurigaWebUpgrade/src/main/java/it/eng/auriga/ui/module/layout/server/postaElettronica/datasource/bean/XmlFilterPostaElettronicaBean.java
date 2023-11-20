/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class XmlFilterPostaElettronicaBean {

	@XmlVariabile(nome="CINodo", tipo=TipoVariabile.SEMPLICE)
	private String idNode;
	
	@XmlVariabile(nome = "ClassificaFolder", tipo = TipoVariabile.SEMPLICE)
	private String classificaFolder;
	
	@XmlVariabile(nome = "RicercaNonRicorsiva", tipo = TipoVariabile.SEMPLICE)
	private String ricercaNonRicorsiva;
	
	@XmlVariabile(nome = "IdCasella", tipo = TipoVariabile.SEMPLICE)
	private String idCasella;
	
	@XmlVariabile(nome = "IndirizzoCasella", tipo = TipoVariabile.SEMPLICE)
	private String indirizzoCasella;
	
	@XmlVariabile(nome = "FlgIO", tipo = TipoVariabile.SEMPLICE)
	private String flgIO;
	
	@XmlVariabile(nome = "IndirizzoMittente", tipo = TipoVariabile.SEMPLICE)
	private String indirizzoMittente;
	
	@XmlVariabile(nome = "IndirizzoDestinatario", tipo = TipoVariabile.SEMPLICE)
	private String indirizzoDestinatario;
	
	@XmlVariabile(nome = "OperMittente", tipo = TipoVariabile.SEMPLICE)
	private String operMittente;
	
	@XmlVariabile(nome = "OperDestinatario", tipo = TipoVariabile.SEMPLICE)
	private String operDestinatario;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "InvioDal", tipo = TipoVariabile.SEMPLICE)
	private String invioDal;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "InvioAl", tipo = TipoVariabile.SEMPLICE)
	private String invioAl;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "RicezioneDal", tipo = TipoVariabile.SEMPLICE)
	private String ricezioneDal;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "RicezioneAl", tipo = TipoVariabile.SEMPLICE)
	private String ricezioneAl;
	
	@XmlVariabile(nome = "Oggetto", tipo = TipoVariabile.SEMPLICE)
	private String oggetto;
	
	@XmlVariabile(nome = "OperOggetto", tipo = TipoVariabile.SEMPLICE)
	private String operOggetto;
	
	@XmlVariabile(nome = "Corpo", tipo = TipoVariabile.SEMPLICE)
	private String corpo;
	
	@XmlVariabile(nome = "AssegnazioneEffettuata", tipo = TipoVariabile.SEMPLICE)
	private String assegnazioneEffettuata;
	
	@XmlVariabile(nome = "IncludiAssegnateAdAltri", tipo = TipoVariabile.SEMPLICE)
	private String includiAssegnateAdAltri;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "AssegnazioneDal", tipo = TipoVariabile.SEMPLICE)
	private String assegnazioneDal;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "AssegnazioneAl", tipo = TipoVariabile.SEMPLICE)
	private String assegnazioneAl;
	
	@XmlVariabile(nome = "IdUserAssegnatario", tipo = TipoVariabile.SEMPLICE)
	private String idUserAssegnatario;
	
	@XmlVariabile(nome = "@UOAssegnatari", tipo = TipoVariabile.LISTA)
	private List<DestInvioNotifica> listaUOAssegnatari;
	
	@XmlVariabile(nome = "Categoria", tipo = TipoVariabile.SEMPLICE)
	private String categoria;
	
	@XmlVariabile(nome = "StatoConsolidamento", tipo = TipoVariabile.SEMPLICE)
	private String statoConsolidamento;
	
	@XmlVariabile(nome = "StatoProtocollazione", tipo = TipoVariabile.SEMPLICE)
	private String statoProtocollazione;
	
	@XmlVariabile(nome = "RicevuteConferme", tipo = TipoVariabile.SEMPLICE)
	private String ricevuteConferme;
	
	@XmlVariabile(nome = "RicevuteEccezioni", tipo = TipoVariabile.SEMPLICE)
	private String ricevuteEccezioni;
	
	@XmlVariabile(nome = "RicevutiAggiornamenti", tipo = TipoVariabile.SEMPLICE)
	private String ricevutiAggiornamenti;
	
	@XmlVariabile(nome = "RicevutiAnnullamenti", tipo = TipoVariabile.SEMPLICE)
	private String ricevutiAnnullamenti;
	
	@XmlVariabile(nome = "RicevutiErroriTrasmConsegna", tipo = TipoVariabile.SEMPLICE)
	private String ricevutiErroriTrasmConsegna;
	
	@XmlVariabile(nome = "PresenzaAllegati", tipo = TipoVariabile.SEMPLICE)
	private String presenzaAllegati;
	
	@XmlVariabile(nome = "PresenzaAvvertimenti", tipo = TipoVariabile.SEMPLICE)
	private String presenzaAvvertimenti;
	
	@XmlVariabile(nome = "NroAllegatiDa", tipo = TipoVariabile.SEMPLICE)
	private String nroAllegatiDa;
	
	@XmlVariabile(nome = "NroAllegatiA", tipo = TipoVariabile.SEMPLICE)
	private String nroAllegatiA;
	
	@XmlVariabile(nome = "DimensioneDa", tipo = TipoVariabile.SEMPLICE)
	private String dimensioneDa;
	
	@XmlVariabile(nome = "DimensioneA", tipo = TipoVariabile.SEMPLICE)
	private String dimensioneA;
	
	@XmlVariabile(nome = "NroDestinatariDa", tipo = TipoVariabile.SEMPLICE)
	private String nroDestinatariDa;
	
	@XmlVariabile(nome = "NroDestinatariA", tipo = TipoVariabile.SEMPLICE)
	private String nroDestinatariA;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "DtArchiviazioneDal", tipo = TipoVariabile.SEMPLICE)
	private String dtArchiviazioneDal;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "DtArchiviazioneAl", tipo = TipoVariabile.SEMPLICE)
	private String dtArchiviazioneAl;
	
	@XmlVariabile(nome = "IdEmailRisposteA", tipo = TipoVariabile.SEMPLICE)
	private String idEmailRisposteA;
	
	@XmlVariabile(nome = "IdEmailInoltroDi", tipo = TipoVariabile.SEMPLICE)
	private String idEmailInoltroDi;
	
	@XmlVariabile(nome = "IdEmailRif", tipo = TipoVariabile.SEMPLICE)
	private String idEmailRif;
	
	@XmlVariabile(nome = "CodCategoriaReg", tipo = TipoVariabile.SEMPLICE)
	private String codCategoriaReg;
	
	@XmlVariabile(nome = "RegistroReg", tipo = TipoVariabile.SEMPLICE)
	private String registroReg;
	
	@XmlVariabile(nome = "AnnoRegDa", tipo = TipoVariabile.SEMPLICE)
	private String annoRegDa;
	
	@XmlVariabile(nome = "AnnoRegA", tipo = TipoVariabile.SEMPLICE)
	private String annoRegA;
	
	@XmlVariabile(nome = "NroRegDa", tipo = TipoVariabile.SEMPLICE)
	private String nroRegDa;
	
	@XmlVariabile(nome = "NroRegA", tipo = TipoVariabile.SEMPLICE)
	private String nroRegA;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "DtRegDal", tipo = TipoVariabile.SEMPLICE)
	private String dtRegDal;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "DtRegAl", tipo = TipoVariabile.SEMPLICE)
	private String dtRegAl;
	
	@XmlVariabile(nome = "MessageId", tipo = TipoVariabile.SEMPLICE)
	private String messageId;
	
	@XmlVariabile(nome = "InviataRisposta", tipo = TipoVariabile.SEMPLICE)
	private String inviataRisposta;
	
	@XmlVariabile(nome = "Inoltrata", tipo = TipoVariabile.SEMPLICE)
	private String inoltrata;
	
	@XmlVariabile(nome = "CruscottoMail", tipo = TipoVariabile.SEMPLICE)
	private String cruscottoMail;
	
	@XmlVariabile(nome = "StatoLavorazioneUrgenza", tipo = TipoVariabile.SEMPLICE)
	private String statoLavorazioneUrgenza;
	
	@XmlVariabile(nome = "CodAzioneDaFare", tipo = TipoVariabile.SEMPLICE)
	private String codAzioneDaFare;
	
	@XmlVariabile(nome = "ProgrEmailDa", tipo = TipoVariabile.SEMPLICE)
	private String progrEmailDa;
	
	@XmlVariabile(nome = "ProgrEmailA", tipo = TipoVariabile.SEMPLICE)
	private String progrEmailA;
	
	@XmlVariabile(nome = "AnnoEmailDa", tipo = TipoVariabile.SEMPLICE)
	private String annoEmailDa;
	
	@XmlVariabile(nome = "AnnoEmailA", tipo = TipoVariabile.SEMPLICE)
	private String annoEmailA;
	
	@XmlVariabile(nome = "TipoProgrEmail", tipo = TipoVariabile.SEMPLICE)
	private String tipoProgrEmail;
	
	@XmlVariabile(nome = "Flusso", tipo = TipoVariabile.SEMPLICE)
	private String tipoEmail;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "InseritaDal", tipo = TipoVariabile.SEMPLICE)
	private String inseritaDal;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "InseritaAl", tipo = TipoVariabile.SEMPLICE)
	private String inseritaAl;
	
	@XmlVariabile(nome = "NroGiorniStatoLavorazioneApertoDa", tipo = TipoVariabile.SEMPLICE)
	private String nroGiorniStatoLavorazioneApertoDa;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "NroGiorniStatoLavorazioneApertoAl", tipo = TipoVariabile.SEMPLICE)
	private String nroGiorniStatoLavorazioneApertoAl;
	
	@XmlVariabile(nome = "StatoIAC", tipo = TipoVariabile.SEMPLICE)
	private String statoIAC;
	
	@XmlVariabile(nome = "LockedBy", tipo = TipoVariabile.SEMPLICE)
	private String inCaricoA;

	@XmlVariabile(nome = "IdUOLavoro", tipo = TipoVariabile.SEMPLICE)
	private String idUOLavoro;

	@XmlVariabile(nome = "MovimentatoDa", tipo = TipoVariabile.SEMPLICE)
	private String movimentatoDa;
	
	@XmlVariabile(nome = "MovimentatiDal", tipo = TipoVariabile.SEMPLICE)
	private String dataDiMovimentazioneDal;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "MovimentatiAl", tipo = TipoVariabile.SEMPLICE)
	private String dataDiMovimentazioneAl;

	@XmlVariabile(nome = "LavoratoDa", tipo = TipoVariabile.SEMPLICE)
	private String lavoratoDa;
	
	@XmlVariabile(nome = "LavoratiDal", tipo = TipoVariabile.SEMPLICE)
	private String lavoratiDal;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "LavoratiAl", tipo = TipoVariabile.SEMPLICE)
	private String lavoratiAl;

	@XmlVariabile(nome = "MessageId", tipo = TipoVariabile.SEMPLICE)
	private String idMessaggio;
	
	@XmlVariabile(nome = "MittentePECPEO", tipo = TipoVariabile.SEMPLICE)
	private String mittentePECPEO; 
	
	@XmlVariabile(nome = "ItemLavorazione.Note", tipo = TipoVariabile.SEMPLICE)
	private String noteApposte;
	
	@XmlVariabile(nome = "ItemLavorazione.Tag", tipo = TipoVariabile.SEMPLICE)
	private String tagApposto;
	
	@XmlVariabile(nome = "ItemLavorazione.NomeFile", tipo = TipoVariabile.SEMPLICE)
	private String nomeFileAssociato;
	
	@XmlVariabile(nome = "OperItemLavorazione.NomeFile", tipo = TipoVariabile.SEMPLICE)
	private String operFileAssociato;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "ChiusuraDal", tipo = TipoVariabile.SEMPLICE)
	private String chiusuraDal;
	
	@TipoData(tipo = Tipo.DATA)
	@XmlVariabile(nome = "ChiusuraAl", tipo = TipoVariabile.SEMPLICE)
	private String chiusuraAl;
	
	@XmlVariabile(nome = "NomeAttach", tipo = TipoVariabile.SEMPLICE)
	private String nomeAttach;
	
	@XmlVariabile(nome = "OperNomeAttach", tipo = TipoVariabile.SEMPLICE)
	private String operNomeAttach;
	
	@XmlVariabile(nome = "ChiusuraEffettuataDa", tipo = TipoVariabile.SEMPLICE)
	private String chiusuraEffettuataDa;
	
	@XmlVariabile(nome = "IdRichMassivaInvioDaXls", tipo = TipoVariabile.SEMPLICE)
	private String idRichMassivaInvioDaXls;
	
	@XmlVariabile(nome = "RicercaMailArchiviate", tipo = TipoVariabile.SEMPLICE)
	private String ricercaMailArchiviate;
		
	@XmlVariabile(nome = "IdUdTrasmessa", tipo = TipoVariabile.SEMPLICE)
	private String idUdTrasmessa;

	public String getIdNode() {
		return idNode;
	}

	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}

	public String getClassificaFolder() {
		return classificaFolder;
	}

	public void setClassificaFolder(String classificaFolder) {
		this.classificaFolder = classificaFolder;
	}

	public String getRicercaNonRicorsiva() {
		return ricercaNonRicorsiva;
	}

	public void setRicercaNonRicorsiva(String ricercaNonRicorsiva) {
		this.ricercaNonRicorsiva = ricercaNonRicorsiva;
	}

	public String getIdCasella() {
		return idCasella;
	}

	public void setIdCasella(String idCasella) {
		this.idCasella = idCasella;
	}

	public String getIndirizzoCasella() {
		return indirizzoCasella;
	}

	public void setIndirizzoCasella(String indirizzoCasella) {
		this.indirizzoCasella = indirizzoCasella;
	}

	public String getFlgIO() {
		return flgIO;
	}

	public void setFlgIO(String flgIO) {
		this.flgIO = flgIO;
	}

	public String getIndirizzoMittente() {
		return indirizzoMittente;
	}

	public void setIndirizzoMittente(String indirizzoMittente) {
		this.indirizzoMittente = indirizzoMittente;
	}

	public String getIndirizzoDestinatario() {
		return indirizzoDestinatario;
	}

	public void setIndirizzoDestinatario(String indirizzoDestinatario) {
		this.indirizzoDestinatario = indirizzoDestinatario;
	}

	public String getOperMittente() {
		return operMittente;
	}

	public void setOperMittente(String operMittente) {
		this.operMittente = operMittente;
	}

	public String getOperDestinatario() {
		return operDestinatario;
	}

	public void setOperDestinatario(String operDestinatario) {
		this.operDestinatario = operDestinatario;
	}

	public String getInvioDal() {
		return invioDal;
	}

	public void setInvioDal(String invioDal) {
		this.invioDal = invioDal;
	}

	public String getInvioAl() {
		return invioAl;
	}

	public void setInvioAl(String invioAl) {
		this.invioAl = invioAl;
	}

	public String getRicezioneDal() {
		return ricezioneDal;
	}

	public void setRicezioneDal(String ricezioneDal) {
		this.ricezioneDal = ricezioneDal;
	}

	public String getRicezioneAl() {
		return ricezioneAl;
	}

	public void setRicezioneAl(String ricezioneAl) {
		this.ricezioneAl = ricezioneAl;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getOperOggetto() {
		return operOggetto;
	}

	public void setOperOggetto(String operOggetto) {
		this.operOggetto = operOggetto;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getAssegnazioneEffettuata() {
		return assegnazioneEffettuata;
	}

	public void setAssegnazioneEffettuata(String assegnazioneEffettuata) {
		this.assegnazioneEffettuata = assegnazioneEffettuata;
	}

	public String getIncludiAssegnateAdAltri() {
		return includiAssegnateAdAltri;
	}

	public void setIncludiAssegnateAdAltri(String includiAssegnateAdAltri) {
		this.includiAssegnateAdAltri = includiAssegnateAdAltri;
	}

	public String getAssegnazioneDal() {
		return assegnazioneDal;
	}

	public void setAssegnazioneDal(String assegnazioneDal) {
		this.assegnazioneDal = assegnazioneDal;
	}

	public String getAssegnazioneAl() {
		return assegnazioneAl;
	}

	public void setAssegnazioneAl(String assegnazioneAl) {
		this.assegnazioneAl = assegnazioneAl;
	}

	public String getIdUserAssegnatario() {
		return idUserAssegnatario;
	}

	public void setIdUserAssegnatario(String idUserAssegnatario) {
		this.idUserAssegnatario = idUserAssegnatario;
	}

	public List<DestInvioNotifica> getListaUOAssegnatari() {
		return listaUOAssegnatari;
	}

	public void setListaUOAssegnatari(List<DestInvioNotifica> listaUOAssegnatari) {
		this.listaUOAssegnatari = listaUOAssegnatari;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getStatoConsolidamento() {
		return statoConsolidamento;
	}

	public void setStatoConsolidamento(String statoConsolidamento) {
		this.statoConsolidamento = statoConsolidamento;
	}

	public String getStatoProtocollazione() {
		return statoProtocollazione;
	}

	public void setStatoProtocollazione(String statoProtocollazione) {
		this.statoProtocollazione = statoProtocollazione;
	}

	public String getRicevuteConferme() {
		return ricevuteConferme;
	}

	public void setRicevuteConferme(String ricevuteConferme) {
		this.ricevuteConferme = ricevuteConferme;
	}

	public String getRicevuteEccezioni() {
		return ricevuteEccezioni;
	}

	public void setRicevuteEccezioni(String ricevuteEccezioni) {
		this.ricevuteEccezioni = ricevuteEccezioni;
	}

	public String getRicevutiAggiornamenti() {
		return ricevutiAggiornamenti;
	}

	public void setRicevutiAggiornamenti(String ricevutiAggiornamenti) {
		this.ricevutiAggiornamenti = ricevutiAggiornamenti;
	}

	public String getRicevutiAnnullamenti() {
		return ricevutiAnnullamenti;
	}

	public void setRicevutiAnnullamenti(String ricevutiAnnullamenti) {
		this.ricevutiAnnullamenti = ricevutiAnnullamenti;
	}

	public String getRicevutiErroriTrasmConsegna() {
		return ricevutiErroriTrasmConsegna;
	}

	public void setRicevutiErroriTrasmConsegna(String ricevutiErroriTrasmConsegna) {
		this.ricevutiErroriTrasmConsegna = ricevutiErroriTrasmConsegna;
	}

	public String getPresenzaAllegati() {
		return presenzaAllegati;
	}

	public void setPresenzaAllegati(String presenzaAllegati) {
		this.presenzaAllegati = presenzaAllegati;
	}

	public String getPresenzaAvvertimenti() {
		return presenzaAvvertimenti;
	}

	public void setPresenzaAvvertimenti(String presenzaAvvertimenti) {
		this.presenzaAvvertimenti = presenzaAvvertimenti;
	}

	public String getNroAllegatiDa() {
		return nroAllegatiDa;
	}

	public void setNroAllegatiDa(String nroAllegatiDa) {
		this.nroAllegatiDa = nroAllegatiDa;
	}

	public String getNroAllegatiA() {
		return nroAllegatiA;
	}

	public void setNroAllegatiA(String nroAllegatiA) {
		this.nroAllegatiA = nroAllegatiA;
	}

	public String getDimensioneDa() {
		return dimensioneDa;
	}

	public void setDimensioneDa(String dimensioneDa) {
		this.dimensioneDa = dimensioneDa;
	}

	public String getDimensioneA() {
		return dimensioneA;
	}

	public void setDimensioneA(String dimensioneA) {
		this.dimensioneA = dimensioneA;
	}

	public String getNroDestinatariDa() {
		return nroDestinatariDa;
	}

	public void setNroDestinatariDa(String nroDestinatariDa) {
		this.nroDestinatariDa = nroDestinatariDa;
	}

	public String getNroDestinatariA() {
		return nroDestinatariA;
	}

	public void setNroDestinatariA(String nroDestinatariA) {
		this.nroDestinatariA = nroDestinatariA;
	}

	public String getDtArchiviazioneDal() {
		return dtArchiviazioneDal;
	}

	public void setDtArchiviazioneDal(String dtArchiviazioneDal) {
		this.dtArchiviazioneDal = dtArchiviazioneDal;
	}

	public String getDtArchiviazioneAl() {
		return dtArchiviazioneAl;
	}

	public void setDtArchiviazioneAl(String dtArchiviazioneAl) {
		this.dtArchiviazioneAl = dtArchiviazioneAl;
	}

	public String getIdEmailRisposteA() {
		return idEmailRisposteA;
	}

	public void setIdEmailRisposteA(String idEmailRisposteA) {
		this.idEmailRisposteA = idEmailRisposteA;
	}

	public String getIdEmailInoltroDi() {
		return idEmailInoltroDi;
	}

	public void setIdEmailInoltroDi(String idEmailInoltroDi) {
		this.idEmailInoltroDi = idEmailInoltroDi;
	}

	public String getIdEmailRif() {
		return idEmailRif;
	}

	public void setIdEmailRif(String idEmailRif) {
		this.idEmailRif = idEmailRif;
	}

	public String getCodCategoriaReg() {
		return codCategoriaReg;
	}

	public void setCodCategoriaReg(String codCategoriaReg) {
		this.codCategoriaReg = codCategoriaReg;
	}

	public String getRegistroReg() {
		return registroReg;
	}

	public void setRegistroReg(String registroReg) {
		this.registroReg = registroReg;
	}

	public String getAnnoRegDa() {
		return annoRegDa;
	}

	public void setAnnoRegDa(String annoRegDa) {
		this.annoRegDa = annoRegDa;
	}

	public String getAnnoRegA() {
		return annoRegA;
	}

	public void setAnnoRegA(String annoRegA) {
		this.annoRegA = annoRegA;
	}

	public String getNroRegDa() {
		return nroRegDa;
	}

	public void setNroRegDa(String nroRegDa) {
		this.nroRegDa = nroRegDa;
	}

	public String getNroRegA() {
		return nroRegA;
	}

	public void setNroRegA(String nroRegA) {
		this.nroRegA = nroRegA;
	}

	public String getDtRegDal() {
		return dtRegDal;
	}

	public void setDtRegDal(String dtRegDal) {
		this.dtRegDal = dtRegDal;
	}

	public String getDtRegAl() {
		return dtRegAl;
	}

	public void setDtRegAl(String dtRegAl) {
		this.dtRegAl = dtRegAl;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getInviataRisposta() {
		return inviataRisposta;
	}

	public void setInviataRisposta(String inviataRisposta) {
		this.inviataRisposta = inviataRisposta;
	}

	public String getInoltrata() {
		return inoltrata;
	}

	public void setInoltrata(String inoltrata) {
		this.inoltrata = inoltrata;
	}

	public String getCruscottoMail() {
		return cruscottoMail;
	}

	public void setCruscottoMail(String cruscottoMail) {
		this.cruscottoMail = cruscottoMail;
	}

	public String getStatoLavorazioneUrgenza() {
		return statoLavorazioneUrgenza;
	}

	public void setStatoLavorazioneUrgenza(String statoLavorazioneUrgenza) {
		this.statoLavorazioneUrgenza = statoLavorazioneUrgenza;
	}

	public String getCodAzioneDaFare() {
		return codAzioneDaFare;
	}

	public void setCodAzioneDaFare(String codAzioneDaFare) {
		this.codAzioneDaFare = codAzioneDaFare;
	}

	public String getProgrEmailDa() {
		return progrEmailDa;
	}

	public void setProgrEmailDa(String progrEmailDa) {
		this.progrEmailDa = progrEmailDa;
	}

	public String getProgrEmailA() {
		return progrEmailA;
	}

	public void setProgrEmailA(String progrEmailA) {
		this.progrEmailA = progrEmailA;
	}

	public String getAnnoEmailDa() {
		return annoEmailDa;
	}

	public void setAnnoEmailDa(String annoEmailDa) {
		this.annoEmailDa = annoEmailDa;
	}

	public String getAnnoEmailA() {
		return annoEmailA;
	}

	public void setAnnoEmailA(String annoEmailA) {
		this.annoEmailA = annoEmailA;
	}

	public String getTipoProgrEmail() {
		return tipoProgrEmail;
	}

	public void setTipoProgrEmail(String tipoProgrEmail) {
		this.tipoProgrEmail = tipoProgrEmail;
	}

	public String getTipoEmail() {
		return tipoEmail;
	}

	public void setTipoEmail(String tipoEmail) {
		this.tipoEmail = tipoEmail;
	}

	public String getInseritaDal() {
		return inseritaDal;
	}

	public void setInseritaDal(String inseritaDal) {
		this.inseritaDal = inseritaDal;
	}

	public String getInseritaAl() {
		return inseritaAl;
	}

	public void setInseritaAl(String inseritaAl) {
		this.inseritaAl = inseritaAl;
	}

	public String getNroGiorniStatoLavorazioneApertoDa() {
		return nroGiorniStatoLavorazioneApertoDa;
	}

	public void setNroGiorniStatoLavorazioneApertoDa(String nroGiorniStatoLavorazioneApertoDa) {
		this.nroGiorniStatoLavorazioneApertoDa = nroGiorniStatoLavorazioneApertoDa;
	}

	public String getNroGiorniStatoLavorazioneApertoAl() {
		return nroGiorniStatoLavorazioneApertoAl;
	}

	public void setNroGiorniStatoLavorazioneApertoAl(String nroGiorniStatoLavorazioneApertoAl) {
		this.nroGiorniStatoLavorazioneApertoAl = nroGiorniStatoLavorazioneApertoAl;
	}

	public String getStatoIAC() {
		return statoIAC;
	}

	public void setStatoIAC(String statoIAC) {
		this.statoIAC = statoIAC;
	}

	public String getInCaricoA() {
		return inCaricoA;
	}

	public void setInCaricoA(String inCaricoA) {
		this.inCaricoA = inCaricoA;
	}

	public String getIdUOLavoro() {
		return idUOLavoro;
	}

	public void setIdUOLavoro(String idUOLavoro) {
		this.idUOLavoro = idUOLavoro;
	}

	public String getMovimentatoDa() {
		return movimentatoDa;
	}

	public void setMovimentatoDa(String movimentatoDa) {
		this.movimentatoDa = movimentatoDa;
	}

	public String getDataDiMovimentazioneDal() {
		return dataDiMovimentazioneDal;
	}

	public void setDataDiMovimentazioneDal(String dataDiMovimentazioneDal) {
		this.dataDiMovimentazioneDal = dataDiMovimentazioneDal;
	}

	public String getDataDiMovimentazioneAl() {
		return dataDiMovimentazioneAl;
	}

	public void setDataDiMovimentazioneAl(String dataDiMovimentazioneAl) {
		this.dataDiMovimentazioneAl = dataDiMovimentazioneAl;
	}

	public String getLavoratoDa() {
		return lavoratoDa;
	}

	public void setLavoratoDa(String lavoratoDa) {
		this.lavoratoDa = lavoratoDa;
	}

	public String getLavoratiDal() {
		return lavoratiDal;
	}

	public void setLavoratiDal(String lavoratiDal) {
		this.lavoratiDal = lavoratiDal;
	}

	public String getLavoratiAl() {
		return lavoratiAl;
	}

	public void setLavoratiAl(String lavoratiAl) {
		this.lavoratiAl = lavoratiAl;
	}

	public String getIdMessaggio() {
		return idMessaggio;
	}

	public void setIdMessaggio(String idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public String getMittentePECPEO() {
		return mittentePECPEO;
	}

	public void setMittentePECPEO(String mittentePECPEO) {
		this.mittentePECPEO = mittentePECPEO;
	}

	public String getNoteApposte() {
		return noteApposte;
	}

	public void setNoteApposte(String noteApposte) {
		this.noteApposte = noteApposte;
	}

	public String getTagApposto() {
		return tagApposto;
	}

	public void setTagApposto(String tagApposto) {
		this.tagApposto = tagApposto;
	}

	public String getNomeFileAssociato() {
		return nomeFileAssociato;
	}

	public void setNomeFileAssociato(String nomeFileAssociato) {
		this.nomeFileAssociato = nomeFileAssociato;
	}

	public String getOperFileAssociato() {
		return operFileAssociato;
	}

	public void setOperFileAssociato(String operFileAssociato) {
		this.operFileAssociato = operFileAssociato;
	}

	public String getChiusuraDal() {
		return chiusuraDal;
	}

	public void setChiusuraDal(String chiusuraDal) {
		this.chiusuraDal = chiusuraDal;
	}

	public String getChiusuraAl() {
		return chiusuraAl;
	}

	public void setChiusuraAl(String chiusuraAl) {
		this.chiusuraAl = chiusuraAl;
	}

	public String getNomeAttach() {
		return nomeAttach;
	}

	public void setNomeAttach(String nomeAttach) {
		this.nomeAttach = nomeAttach;
	}

	public String getOperNomeAttach() {
		return operNomeAttach;
	}

	public void setOperNomeAttach(String operNomeAttach) {
		this.operNomeAttach = operNomeAttach;
	}

	public String getChiusuraEffettuataDa() {
		return chiusuraEffettuataDa;
	}

	public void setChiusuraEffettuataDa(String chiusuraEffettuataDa) {
		this.chiusuraEffettuataDa = chiusuraEffettuataDa;
	}

	public String getIdRichMassivaInvioDaXls() {
		return idRichMassivaInvioDaXls;
	}

	public void setIdRichMassivaInvioDaXls(String idRichMassivaInvioDaXls) {
		this.idRichMassivaInvioDaXls = idRichMassivaInvioDaXls;
	}

	public String getRicercaMailArchiviate() {
		return ricercaMailArchiviate;
	}

	public void setRicercaMailArchiviate(String ricercaMailArchiviate) {
		this.ricercaMailArchiviate = ricercaMailArchiviate;
	}

	public String getIdUdTrasmessa() {
		return idUdTrasmessa;
	}

	public void setIdUdTrasmessa(String idUdTrasmessa) {
		this.idUdTrasmessa = idUdTrasmessa;
	}
	
}