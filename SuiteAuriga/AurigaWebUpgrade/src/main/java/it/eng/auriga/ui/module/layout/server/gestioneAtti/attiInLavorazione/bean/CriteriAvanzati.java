/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoCompetente;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoProponente;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class CriteriAvanzati implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@XmlVariabile(nome="NroPropostaDa", tipo=TipoVariabile.SEMPLICE)
	private String nroPropostaDa;
	
	@XmlVariabile(nome="NroPropostaA", tipo=TipoVariabile.SEMPLICE)
	private String nroPropostaA;
	
	@XmlVariabile(nome="SigleProposta", tipo=TipoVariabile.SEMPLICE)
	private String sigleProposta;
	
	@XmlVariabile(nome="NroAttoDa", tipo=TipoVariabile.SEMPLICE)
	private String nroAttoDa;
	
	@XmlVariabile(nome="NroAttoA", tipo=TipoVariabile.SEMPLICE)
	private String nroAttoA;
	
	@XmlVariabile(nome="SigleAtto", tipo=TipoVariabile.SEMPLICE)
	private String sigleAtto;
	
	@XmlVariabile(nome="DataAttoDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAttoDa;

	@XmlVariabile(nome="DataAttoA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAttoA;
	
	@XmlVariabile(nome="OggettoProposta", tipo=TipoVariabile.SEMPLICE)
	private String oggettoProposta;	
	
	@XmlVariabile(nome="IdUserAvvio", tipo=TipoVariabile.SEMPLICE)
	private String idUserAvvio;
	
	@XmlVariabile(nome="IdUOProponentiProposta", tipo=XmlVariabile.TipoVariabile.LISTA)
	private List<UoProponente> idUOProponentiProposta;
	 
	@XmlVariabile(nome="IdUOCompetentiProposta", tipo=XmlVariabile.TipoVariabile.LISTA)
	private List<UoCompetente> idUOCompetentiProposta;
	
	@XmlVariabile(nome="SoloEseguibili", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgSoloEseguibili;
	
	@XmlVariabile(nome="SoloAttiConWF", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgSoloAttiConWF;
	
	@XmlVariabile(nome="SoloProcedimentiAmm", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgSoloProcedimentiAmm;
	
	@XmlVariabile(nome="Istruttore", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String istruttore;
	
	@XmlVariabile(nome="RicercatoreVisuraSUE", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String ricercatoreIncaricato;
	
	@XmlVariabile(nome="Richiedente", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String richiedente;
	
	@XmlVariabile(nome="Indirizzo", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String indirizzo;
	
	@XmlVariabile(nome="AnnoPraticaDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String annoPraticaDa;
	
	@XmlVariabile(nome="AnnoPraticaA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String annoPraticaA;
	
	@XmlVariabile(nome="NroPraticaDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String nroPraticaDa;
	
	@XmlVariabile(nome="NroPraticaA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String nroPraticaA;
	
	@XmlVariabile(nome="UDC", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String udc;
	
	@XmlVariabile(nome="NroProtIniDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String nroProtIniDa;
	
	@XmlVariabile(nome="NroProtIniA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String nroProtIniA;
	
	@XmlVariabile(nome="DtProtIniDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtProtIniDa;

	@XmlVariabile(nome="DtProtIniA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtProtIniA;
	
	@XmlVariabile(nome="DtAppuntamento1Da", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtAppuntamento1Da;

	@XmlVariabile(nome="DtAppuntamento1A", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtAppuntamento1A;
	
	@XmlVariabile(nome="DtAppuntamento2Da", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtAppuntamento2Da;

	@XmlVariabile(nome="DtAppuntamento2A", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtAppuntamento2A;
	
	@XmlVariabile(nome="DataPresentazioneInstanzaDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtPresentazioneInstanzaDa;
	
	@XmlVariabile(nome="DataPresentazioneIstanzaA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtPresentazioneIstanzaA;
	
	@XmlVariabile(nome="IdUserAssegnatario", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String idUserAssegnatario;
	
	@XmlVariabile(nome="IdUserAssegnatarioIstruttoria", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String idUserAssegnatarioIstruttoria;
	
	@XmlVariabile(nome="IdUserAssegnatarioPreIstruttoria", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String idUserAssegnatarioPreIstruttoria;
	
	@XmlVariabile(nome="Richiedente", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String denominazioneRichiedente;
	
	@XmlVariabile(nome="CFPIVA_Richiedente", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String codFiscaleRichiedente;
	
	@XmlVariabile(nome="ListaTipiTributo", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String tipiTributo;
	
	@XmlVariabile(nome="AttoRif.Tipo", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String tipoAttoRif;
	
	@XmlVariabile(nome="AttoRif.AnnoImpostaDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String annoAttoRifDa;
	
	@XmlVariabile(nome="AttoRif.AnnoImpostaA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String annoAttoRifA;
	
	@XmlVariabile(nome="AttoRif.Numero", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String numeroAttoRif;
	
	@XmlVariabile(nome="AttoRif.Registro", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String registroAttoRif;
	
	@XmlVariabile(nome="AttoRif.Esito", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String esitoAttoRif;
	
	@XmlVariabile(nome="TipoRichiedenteVisuraSUE", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String tipoRichiedenteVisuraSUE;
	
	@XmlVariabile(nome="RichAttiDiFabbrica", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String richAttiDiFabbrica;
	
	@XmlVariabile(nome="FabbricatoAttiCostrFino1996", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String fabbricatoAttiCostrFino1996;
	
	@XmlVariabile(nome="FabbricatoAttiCostrDa1997", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String fabbricatoAttiCostrDa1997;
	
	@XmlVariabile(nome="MotivoVisuraSUE", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String motivoVisuraSUE;
	
	@XmlVariabile(nome="RichiestaVisuraCartacea", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String richiestaVisuraCartacea;
	
	@XmlVariabile(nome="NroEmendamentoDa", tipo=TipoVariabile.SEMPLICE)
	private String nroEmendamentoDa;
	
	@XmlVariabile(nome="NroEmendamentoA", tipo=TipoVariabile.SEMPLICE)
	private String nroEmendamentoA;
	
	@XmlVariabile(nome="ParoleChiave", tipo=TipoVariabile.SEMPLICE)
	private String paroleChiave;	
	
	@XmlVariabile(nome="CodStatoDettUDIni", tipo=TipoVariabile.SEMPLICE)
	private String stato;
	
	@XmlVariabile(nome="CdCAtto", tipo=TipoVariabile.SEMPLICE)
	private String centroDiCosto;
	
	@XmlVariabile(nome="DataScadenzaDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScadenzaDa;
	
	@XmlVariabile(nome="DataScadenzaA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScadenzaA;
	
	@XmlVariabile(nome="FlgVisionato", tipo=TipoVariabile.SEMPLICE)
	private String flgVisionato;
	
	@XmlVariabile(nome="TagApposti", tipo=TipoVariabile.SEMPLICE)
	private String tagApposti;
	
	@XmlVariabile(nome="RichModificheVisuraSUE", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String richModificheVisuraSUE;
	
	@XmlVariabile(nome="DocAggiuntiVariatiDaUltimoAccesso", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgDocAggUltAcc;
	
	@XmlVariabile(nome="DocAggiuntiVariatiDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtDocAggiuntiVariatiDal;

	@XmlVariabile(nome="DocAggiuntiVariatiAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtDocAggiuntiVariatiAl;
	
	@XmlVariabile(nome="IscrittoInOdgSedutaDataDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date iscrittoInOdgSedutaDataDa;

	@XmlVariabile(nome="IscrittoInOdgSedutaDataA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date iscrittoInOdgSedutaDataA;
	
	@XmlVariabile(nome="DeterminaContrarreConGara", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String determinaContrarreConGara;

	@XmlVariabile(nome="TipoAffidamento", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String tipoAffidamento;

	@XmlVariabile(nome="DeterminaRimodulazioneSpesaPostAggiudica", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String determinaRimodulazioneSpesaPostAggiudica;

	@XmlVariabile(nome="MaterieTipiAtto", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String materieTipiAtto;
		
	@XmlVariabile(nome="LiquidazioneContestualeImpegni", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String liquidazioneContestualeImpegni;

	@XmlVariabile(nome="LiquidazioneContestualeAltriAspettiCont", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String liquidazioneContestualeAltriAspettiCont;

	@XmlVariabile(nome="DeterminaAggiudicaGara", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String determinaAggiudicaGara;

	@XmlVariabile(nome="DataAutPrelievoArchivioDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtAutPrelievoArchivioDal;

	@XmlVariabile(nome="DataAutPrelievoArchivioAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtAutPrelievoArchivioAl;
	
	@XmlVariabile(nome="ArchiviCoinvolti", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String archiviCoinvolti;
	
	@XmlVariabile(nome="PresenzaOpere", tipo=TipoVariabile.SEMPLICE)
	private String presenzaOpere;
	
	@XmlVariabile(nome="CodOpere", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String codOpere;
	
	@XmlVariabile(nome="Paziente.Cognome", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String cognomePaziente;
	
	@XmlVariabile(nome="Medico.Cognome", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String cognomeMedico;
	
	@XmlVariabile(nome="FlgPazienteMinorenne", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgPazienteMinorenne;
	
	@XmlVariabile(nome="IdUserIstruttore", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String idUserIstruttore;
	
	@XmlVariabile(nome="FlgProgrammazioneAcquisti", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgProgrammazioneAcquisti;
	
	@XmlVariabile(nome="CUI", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String cui;
	
	@XmlVariabile(nome="CIG", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String cig;
	
	@XmlVariabile(nome="CUp", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String cup;
	
	@XmlVariabile(nome="SoloAutotuteleCEDTributi", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String soloAutotuteleCEDTributi;
	
	public String getNroPropostaDa() {
		return nroPropostaDa;
	}

	public void setNroPropostaDa(String nroPropostaDa) {
		this.nroPropostaDa = nroPropostaDa;
	}

	public String getNroPropostaA() {
		return nroPropostaA;
	}

	public void setNroPropostaA(String nroPropostaA) {
		this.nroPropostaA = nroPropostaA;
	}

	public String getSigleProposta() {
		return sigleProposta;
	}

	public void setSigleProposta(String sigleProposta) {
		this.sigleProposta = sigleProposta;
	}

	public String getNroAttoDa() {
		return nroAttoDa;
	}

	public void setNroAttoDa(String nroAttoDa) {
		this.nroAttoDa = nroAttoDa;
	}

	public String getNroAttoA() {
		return nroAttoA;
	}

	public void setNroAttoA(String nroAttoA) {
		this.nroAttoA = nroAttoA;
	}

	public String getSigleAtto() {
		return sigleAtto;
	}

	public void setSigleAtto(String sigleAtto) {
		this.sigleAtto = sigleAtto;
	}

	public Date getDataAttoDa() {
		return dataAttoDa;
	}

	public void setDataAttoDa(Date dataAttoDa) {
		this.dataAttoDa = dataAttoDa;
	}

	public Date getDataAttoA() {
		return dataAttoA;
	}

	public void setDataAttoA(Date dataAttoA) {
		this.dataAttoA = dataAttoA;
	}

	public String getOggettoProposta() {
		return oggettoProposta;
	}

	public void setOggettoProposta(String oggettoProposta) {
		this.oggettoProposta = oggettoProposta;
	}

	public String getIdUserAvvio() {
		return idUserAvvio;
	}

	public void setIdUserAvvio(String idUserAvvio) {
		this.idUserAvvio = idUserAvvio;
	}

	public List<UoProponente> getIdUOProponentiProposta() {
		return idUOProponentiProposta;
	}

	public void setIdUOProponentiProposta(List<UoProponente> idUOProponentiProposta) {
		this.idUOProponentiProposta = idUOProponentiProposta;
	}

	public String getFlgSoloEseguibili() {
		return flgSoloEseguibili;
	}

	public void setFlgSoloEseguibili(String flgSoloEseguibili) {
		this.flgSoloEseguibili = flgSoloEseguibili;
	}

	public String getFlgSoloAttiConWF() {
		return flgSoloAttiConWF;
	}

	public void setFlgSoloAttiConWF(String flgSoloAttiConWF) {
		this.flgSoloAttiConWF = flgSoloAttiConWF;
	}

	public String getFlgSoloProcedimentiAmm() {
		return flgSoloProcedimentiAmm;
	}

	public void setFlgSoloProcedimentiAmm(String flgSoloProcedimentiAmm) {
		this.flgSoloProcedimentiAmm = flgSoloProcedimentiAmm;
	}

	public String getIstruttore() {
		return istruttore;
	}

	public void setIstruttore(String istruttore) {
		this.istruttore = istruttore;
	}
	
	public String getRicercatoreIncaricato() {
		return ricercatoreIncaricato;
	}

	public void setRicercatoreIncaricato(String ricercatoreIncaricato) {
		this.ricercatoreIncaricato = ricercatoreIncaricato;
	}

	public String getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getAnnoPraticaDa() {
		return annoPraticaDa;
	}

	public void setAnnoPraticaDa(String annoPraticaDa) {
		this.annoPraticaDa = annoPraticaDa;
	}

	public String getAnnoPraticaA() {
		return annoPraticaA;
	}

	public void setAnnoPraticaA(String annoPraticaA) {
		this.annoPraticaA = annoPraticaA;
	}

	public String getNroPraticaDa() {
		return nroPraticaDa;
	}

	public void setNroPraticaDa(String nroPraticaDa) {
		this.nroPraticaDa = nroPraticaDa;
	}

	public String getNroPraticaA() {
		return nroPraticaA;
	}

	public void setNroPraticaA(String nroPraticaA) {
		this.nroPraticaA = nroPraticaA;
	}

	public String getUdc() {
		return udc;
	}

	public void setUdc(String udc) {
		this.udc = udc;
	}

	public String getNroProtIniDa() {
		return nroProtIniDa;
	}

	public void setNroProtIniDa(String nroProtIniDa) {
		this.nroProtIniDa = nroProtIniDa;
	}

	public String getNroProtIniA() {
		return nroProtIniA;
	}

	public void setNroProtIniA(String nroProtIniA) {
		this.nroProtIniA = nroProtIniA;
	}

	public Date getDtProtIniDa() {
		return dtProtIniDa;
	}

	public void setDtProtIniDa(Date dtProtIniDa) {
		this.dtProtIniDa = dtProtIniDa;
	}

	public Date getDtProtIniA() {
		return dtProtIniA;
	}

	public void setDtProtIniA(Date dtProtIniA) {
		this.dtProtIniA = dtProtIniA;
	}

	public Date getDtAppuntamento1Da() {
		return dtAppuntamento1Da;
	}

	public void setDtAppuntamento1Da(Date dtAppuntamento1Da) {
		this.dtAppuntamento1Da = dtAppuntamento1Da;
	}

	public Date getDtAppuntamento1A() {
		return dtAppuntamento1A;
	}

	public void setDtAppuntamento1A(Date dtAppuntamento1A) {
		this.dtAppuntamento1A = dtAppuntamento1A;
	}

	public Date getDtAppuntamento2Da() {
		return dtAppuntamento2Da;
	}

	public void setDtAppuntamento2Da(Date dtAppuntamento2Da) {
		this.dtAppuntamento2Da = dtAppuntamento2Da;
	}

	public Date getDtAppuntamento2A() {
		return dtAppuntamento2A;
	}

	public void setDtAppuntamento2A(Date dtAppuntamento2A) {
		this.dtAppuntamento2A = dtAppuntamento2A;
	}
	
	public Date getDtPresentazioneInstanzaDa() {
		return dtPresentazioneInstanzaDa;
	}

	public void setDtPresentazioneInstanzaDa(Date dtPresentazioneInstanzaDa) {
		this.dtPresentazioneInstanzaDa = dtPresentazioneInstanzaDa;
	}

	public Date getDtPresentazioneIstanzaA() {
		return dtPresentazioneIstanzaA;
	}

	public void setDtPresentazioneIstanzaA(Date dtPresentazioneIstanzaA) {
		this.dtPresentazioneIstanzaA = dtPresentazioneIstanzaA;
	}

	public String getIdUserAssegnatario() {
		return idUserAssegnatario;
	}

	public void setIdUserAssegnatario(String idUserAssegnatario) {
		this.idUserAssegnatario = idUserAssegnatario;
	}

	public String getTipiTributo() {
		return tipiTributo;
	}

	public void setTipiTributo(String tipiTributo) {
		this.tipiTributo = tipiTributo;
	}

	public String getTipoAttoRif() {
		return tipoAttoRif;
	}

	public void setTipoAttoRif(String tipoAttoRif) {
		this.tipoAttoRif = tipoAttoRif;
	}

	public String getAnnoAttoRifDa() {
		return annoAttoRifDa;
	}

	public void setAnnoAttoRifDa(String annoAttoRifDa) {
		this.annoAttoRifDa = annoAttoRifDa;
	}

	public String getAnnoAttoRifA() {
		return annoAttoRifA;
	}

	public void setAnnoAttoRifA(String annoAttoRifA) {
		this.annoAttoRifA = annoAttoRifA;
	}

	public String getNumeroAttoRif() {
		return numeroAttoRif;
	}

	public void setNumeroAttoRif(String numeroAttoRif) {
		this.numeroAttoRif = numeroAttoRif;
	}

	public String getRegistroAttoRif() {
		return registroAttoRif;
	}

	public void setRegistroAttoRif(String registroAttoRif) {
		this.registroAttoRif = registroAttoRif;
	}

	public String getEsitoAttoRif() {
		return esitoAttoRif;
	}

	public void setEsitoAttoRif(String esitoAttoRif) {
		this.esitoAttoRif = esitoAttoRif;
	}

	public String getTipoRichiedenteVisuraSUE() {
		return tipoRichiedenteVisuraSUE;
	}

	public void setTipoRichiedenteVisuraSUE(String tipoRichiedenteVisuraSUE) {
		this.tipoRichiedenteVisuraSUE = tipoRichiedenteVisuraSUE;
	}

	public String getRichAttiDiFabbrica() {
		return richAttiDiFabbrica;
	}

	public void setRichAttiDiFabbrica(String richAttiDiFabbrica) {
		this.richAttiDiFabbrica = richAttiDiFabbrica;
	}

	public String getFabbricatoAttiCostrFino1996() {
		return fabbricatoAttiCostrFino1996;
	}

	public void setFabbricatoAttiCostrFino1996(String fabbricatoAttiCostrFino1996) {
		this.fabbricatoAttiCostrFino1996 = fabbricatoAttiCostrFino1996;
	}

	public String getFabbricatoAttiCostrDa1997() {
		return fabbricatoAttiCostrDa1997;
	}

	public void setFabbricatoAttiCostrDa1997(String fabbricatoAttiCostrDa1997) {
		this.fabbricatoAttiCostrDa1997 = fabbricatoAttiCostrDa1997;
	}

	public String getMotivoVisuraSUE() {
		return motivoVisuraSUE;
	}

	public void setMotivoVisuraSUE(String motivoVisuraSUE) {
		this.motivoVisuraSUE = motivoVisuraSUE;
	}

	public String getRichiestaVisuraCartacea() {
		return richiestaVisuraCartacea;
	}

	public void setRichiestaVisuraCartacea(String richiestaVisuraCartacea) {
		this.richiestaVisuraCartacea = richiestaVisuraCartacea;
	}

	public String getNroEmendamentoDa() {
		return nroEmendamentoDa;
	}

	public void setNroEmendamentoDa(String nroEmendamentoDa) {
		this.nroEmendamentoDa = nroEmendamentoDa;
	}

	public String getNroEmendamentoA() {
		return nroEmendamentoA;
	}

	public void setNroEmendamentoA(String nroEmendamentoA) {
		this.nroEmendamentoA = nroEmendamentoA;
	}

	public String getParoleChiave() {
		return paroleChiave;
	}

	public void setParoleChiave(String paroleChiave) {
		this.paroleChiave = paroleChiave;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public Date getDataScadenzaDa() {
		return dataScadenzaDa;
	}

	public void setDataScadenzaDa(Date dataScadenzaDa) {
		this.dataScadenzaDa = dataScadenzaDa;
	}

	public Date getDataScadenzaA() {
		return dataScadenzaA;
	}

	public void setDataScadenzaA(Date dataScadenzaA) {
		this.dataScadenzaA = dataScadenzaA;
	}

	public String getFlgVisionato() {
		return flgVisionato;
	}

	public void setFlgVisionato(String flgVisionato) {
		this.flgVisionato = flgVisionato;
	}

	public String getTagApposti() {
		return tagApposti;
	}

	public void setTagApposti(String tagApposti) {
		this.tagApposti = tagApposti;
	}

	public String getRichModificheVisuraSUE() {
		return richModificheVisuraSUE;
	}

	public void setRichModificheVisuraSUE(String richModificheVisuraSUE) {
		this.richModificheVisuraSUE = richModificheVisuraSUE;
	}

	public String getFlgDocAggUltAcc() {
		return flgDocAggUltAcc;
	}

	public void setFlgDocAggUltAcc(String flgDocAggUltAcc) {
		this.flgDocAggUltAcc = flgDocAggUltAcc;
	}

	public Date getDtDocAggiuntiVariatiDal() {
		return dtDocAggiuntiVariatiDal;
	}

	public void setDtDocAggiuntiVariatiDal(Date dtDocAggiuntiVariatiDal) {
		this.dtDocAggiuntiVariatiDal = dtDocAggiuntiVariatiDal;
	}

	public Date getDtDocAggiuntiVariatiAl() {
		return dtDocAggiuntiVariatiAl;
	}

	public void setDtDocAggiuntiVariatiAl(Date dtDocAggiuntiVariatiAl) {
		this.dtDocAggiuntiVariatiAl = dtDocAggiuntiVariatiAl;
	}

	public Date getIscrittoInOdgSedutaDataDa() {
		return iscrittoInOdgSedutaDataDa;
	}

	public void setIscrittoInOdgSedutaDataDa(Date iscrittoInOdgSedutaDataDa) {
		this.iscrittoInOdgSedutaDataDa = iscrittoInOdgSedutaDataDa;
	}

	public Date getIscrittoInOdgSedutaDataA() {
		return iscrittoInOdgSedutaDataA;
	}

	public void setIscrittoInOdgSedutaDataA(Date iscrittoInOdgSedutaDataA) {
		this.iscrittoInOdgSedutaDataA = iscrittoInOdgSedutaDataA;
	}

	public String getDeterminaContrarreConGara() {
		return determinaContrarreConGara;
	}

	public void setDeterminaContrarreConGara(String determinaContrarreConGara) {
		this.determinaContrarreConGara = determinaContrarreConGara;
	}

	public String getTipoAffidamento() {
		return tipoAffidamento;
	}

	public void setTipoAffidamento(String tipoAffidamento) {
		this.tipoAffidamento = tipoAffidamento;
	}

	public String getDeterminaRimodulazioneSpesaPostAggiudica() {
		return determinaRimodulazioneSpesaPostAggiudica;
	}

	public void setDeterminaRimodulazioneSpesaPostAggiudica(String determinaRimodulazioneSpesaPostAggiudica) {
		this.determinaRimodulazioneSpesaPostAggiudica = determinaRimodulazioneSpesaPostAggiudica;
	}

	public String getMaterieTipiAtto() {
		return materieTipiAtto;
	}

	public void setMaterieTipiAtto(String materieTipiAtto) {
		this.materieTipiAtto = materieTipiAtto;
	}

	public String getLiquidazioneContestualeImpegni() {
		return liquidazioneContestualeImpegni;
	}

	public void setLiquidazioneContestualeImpegni(String liquidazioneContestualeImpegni) {
		this.liquidazioneContestualeImpegni = liquidazioneContestualeImpegni;
	}

	public String getLiquidazioneContestualeAltriAspettiCont() {
		return liquidazioneContestualeAltriAspettiCont;
	}

	public void setLiquidazioneContestualeAltriAspettiCont(String liquidazioneContestualeAltriAspettiCont) {
		this.liquidazioneContestualeAltriAspettiCont = liquidazioneContestualeAltriAspettiCont;
	}

	public String getDeterminaAggiudicaGara() {
		return determinaAggiudicaGara;
	}

	public void setDeterminaAggiudicaGara(String determinaAggiudicaGara) {
		this.determinaAggiudicaGara = determinaAggiudicaGara;
	}

	public List<UoCompetente> getIdUOCompetentiProposta() {
		return idUOCompetentiProposta;
	}

	public void setIdUOCompetentiProposta(List<UoCompetente> idUOCompetentiProposta) {
		this.idUOCompetentiProposta = idUOCompetentiProposta;
	}

	public Date getDtAutPrelievoArchivioDal() {
		return dtAutPrelievoArchivioDal;
	}

	public Date getDtAutPrelievoArchivioAl() {
		return dtAutPrelievoArchivioAl;
	}

	public void setDtAutPrelievoArchivioDal(Date dtAutPrelievoArchivioDal) {
		this.dtAutPrelievoArchivioDal = dtAutPrelievoArchivioDal;
	}

	public void setDtAutPrelievoArchivioAl(Date dtAutPrelievoArchivioAl) {
		this.dtAutPrelievoArchivioAl = dtAutPrelievoArchivioAl;
	}

	public String getArchiviCoinvolti() {
		return archiviCoinvolti;
	}

	public void setArchiviCoinvolti(String archiviCoinvolti) {
		this.archiviCoinvolti = archiviCoinvolti;
	}

	public String getPresenzaOpere() {
		return presenzaOpere;
	}

	public void setPresenzaOpere(String presenzaOpere) {
		this.presenzaOpere = presenzaOpere;
	}

	public String getCodOpere() {
		return codOpere;
	}

	public void setCodOpere(String codOpere) {
		this.codOpere = codOpere;
	}

	public String getCognomePaziente() {
		return cognomePaziente;
	}

	public String getCognomeMedico() {
		return cognomeMedico;
	}

	public void setCognomePaziente(String cognomePaziente) {
		this.cognomePaziente = cognomePaziente;
	}

	public void setCognomeMedico(String cognomeMedico) {
		this.cognomeMedico = cognomeMedico;
	}

	public String getFlgPazienteMinorenne() {
		return flgPazienteMinorenne;
	}

	public void setFlgPazienteMinorenne(String flgPazienteMinorenne) {
		this.flgPazienteMinorenne = flgPazienteMinorenne;
	}

	public String getIdUserIstruttore() {
		return idUserIstruttore;
	}

	public void setIdUserIstruttore(String idUserIstruttore) {
		this.idUserIstruttore = idUserIstruttore;
	}

	public String getIdUserAssegnatarioIstruttoria() {
		return idUserAssegnatarioIstruttoria;
	}

	public void setIdUserAssegnatarioIstruttoria(String idUserAssegnatarioIstruttoria) {
		this.idUserAssegnatarioIstruttoria = idUserAssegnatarioIstruttoria;
	}

	public String getIdUserAssegnatarioPreIstruttoria() {
		return idUserAssegnatarioPreIstruttoria;
	}

	public void setIdUserAssegnatarioPreIstruttoria(String idUserAssegnatarioPreIstruttoria) {
		this.idUserAssegnatarioPreIstruttoria = idUserAssegnatarioPreIstruttoria;
	}

	public String getDenominazioneRichiedente() {
		return denominazioneRichiedente;
	}

	public void setDenominazioneRichiedente(String denominazioneRichiedente) {
		this.denominazioneRichiedente = denominazioneRichiedente;
	}

	public String getCodFiscaleRichiedente() {
		return codFiscaleRichiedente;
	}

	public void setCodFiscaleRichiedente(String codFiscaleRichiedente) {
		this.codFiscaleRichiedente = codFiscaleRichiedente;
	}

	public String getFlgProgrammazioneAcquisti() {
		return flgProgrammazioneAcquisti;
	}

	public void setFlgProgrammazioneAcquisti(String flgProgrammazioneAcquisti) {
		this.flgProgrammazioneAcquisti = flgProgrammazioneAcquisti;
	}

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getSoloAutotuteleCEDTributi() {
		return soloAutotuteleCEDTributi;
	}

	public void setSoloAutotuteleCEDTributi(String soloAutotuteleCEDTributi) {
		this.soloAutotuteleCEDTributi = soloAutotuteleCEDTributi;
	}
	
}