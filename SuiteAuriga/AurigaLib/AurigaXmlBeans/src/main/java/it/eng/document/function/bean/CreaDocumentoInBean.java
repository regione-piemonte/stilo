/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaDocumentoInBean implements Serializable {
	private static final long serialVersionUID = 8827729163041008691L;	
	@XmlVariabile(nome="#FlgTipoProv", tipo = TipoVariabile.SEMPLICE)
	private TipoProvenienza flgTipoProv;	
	@XmlVariabile(nome="#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String oggetto;
	@XmlVariabile(nome="#LivRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private String livelloRiservatezza;
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="#DtTermineRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private Date termineRiservatezza;
	@XmlVariabile(nome="#LivEvidenza", tipo = TipoVariabile.SEMPLICE)
	private String priorita;
	@XmlVariabile(nome="", tipo = TipoVariabile.NESTED)
	private ProtocolloRicevuto protocolloRicevuto;
	@TipoData(tipo=Tipo.DATA)
	@XmlVariabile(nome="#TsArrivo", tipo = TipoVariabile.SEMPLICE)
	private Date dataArrivo;
	@XmlVariabile(nome="#CodMezzoTrasm", tipo = TipoVariabile.SEMPLICE)
	private String messoTrasmissione;
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="#DtRaccomandata", tipo = TipoVariabile.SEMPLICE)
	private Date dtRaccomandata;
	@XmlVariabile(nome="#NroRaccomandata", tipo = TipoVariabile.SEMPLICE)
	private Integer nroRaccomandata;
	@XmlVariabile(nome="#@RelazioniVsUD", tipo=TipoVariabile.LISTA)
	private List<DocumentoCollegato> docCollegato;
	@XmlVariabile(nome="#CollocazioneFisica.IdTopografico", tipo = TipoVariabile.SEMPLICE)
	private String idTopografico;
	@XmlVariabile(nome="#CollocazioneFisica.Descrizione", tipo = TipoVariabile.SEMPLICE)
	private String descrizioneCollocazione;
	@XmlVariabile(nome="#@RegistrazioniDate", tipo=TipoVariabile.LISTA)
	private List<RegistroEmergenza> registroEmergenza;
	@XmlVariabile(nome="#NoteUD", tipo = TipoVariabile.SEMPLICE)
	private String note;
	@XmlVariabile(nome="#IdDocType", tipo = TipoVariabile.SEMPLICE)
	private String tipoDocumento;
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="#DtStesura", tipo = TipoVariabile.SEMPLICE)
	private Date dataStesura;
	@XmlVariabile(nome="#@NumerazioniDaDare", tipo=TipoVariabile.LISTA)
	private List<TipoNumerazioneBean> tipoNumerazioni;
	@XmlVariabile(nome="#@MittentiEsterni", tipo=TipoVariabile.LISTA)
	private List<MittentiDocumentoBean> mittenti;
	@XmlVariabile(nome="#@DestinatariEsterni", tipo=TipoVariabile.LISTA)
	private List<DestinatariBean> destinatari;
	@XmlVariabile(nome="#@GruppiDestinatari", tipo=TipoVariabile.LISTA)
	private List<DistribuzioneBean> gruppi;
	@XmlVariabile(nome="#@Assegnatari", tipo=TipoVariabile.LISTA)
	private List<AssegnatariBean> assegnatari;
	@XmlVariabile(nome="#@ClassFascTitolario", tipo=TipoVariabile.LISTA)
	private List<ClassificheFascicoliDocumentoBean> classifichefascicoli;
	@XmlVariabile(nome="#EmailProv", tipo=TipoVariabile.NESTED)
	private EmailProvBean emailProv;
	@XmlVariabile(nome="#FlgSoloSetDatiRichPubbl", tipo=TipoVariabile.NESTED)
	private EmailProvBean flgSoloSetDatiRichPubbl;
	@XmlVariabile(nome="#RichPubblicazione.DaEliminare", tipo=TipoVariabile.NESTED)
	private EmailProvBean richPubblDaEliminare;
	
	
	public TipoProvenienza getFlgTipoProv() {
		return flgTipoProv;
	}
	public void setFlgTipoProv(TipoProvenienza flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public List<TipoNumerazioneBean> getTipoNumerazioni() {
		return tipoNumerazioni;
	}
	public void setTipoNumerazioni(List<TipoNumerazioneBean> tipoNumerazioni) {
		this.tipoNumerazioni = tipoNumerazioni;
	}
	public List<MittentiDocumentoBean> getMittenti() {
		return mittenti;
	}
	public void setMittenti(List<MittentiDocumentoBean> mittenti) {
		this.mittenti = mittenti;
	}
	
	public List<ClassificheFascicoliDocumentoBean> getClassifichefascicoli() {
		return classifichefascicoli;
	}
	public void setClassifichefascicoli(List<ClassificheFascicoliDocumentoBean> classifichefascicoli) {
		this.classifichefascicoli = classifichefascicoli;
	}
	public void setDestinatari(List<DestinatariBean> destinatari) {
		this.destinatari = destinatari;
	}
	public List<DestinatariBean> getDestinatari() {
		return destinatari;
	}
	public void setGruppi(List<DistribuzioneBean> gruppi) {
		this.gruppi = gruppi;
	}
	public List<DistribuzioneBean> getGruppi() {
		return gruppi;
	}
	public void setAssegnatari(List<AssegnatariBean> assegnatari) {
		this.assegnatari = assegnatari;
	}
	public List<AssegnatariBean> getAssegnatari() {
		return assegnatari;
	}
	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}
	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}
	public void setTermineRiservatezza(Date termineRiservatezza) {
		this.termineRiservatezza = termineRiservatezza;
	}
	public Date getTermineRiservatezza() {
		return termineRiservatezza;
	}
	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}
	public String getPriorita() {
		return priorita;
	}
	public void setProtocolloRicevuto(ProtocolloRicevuto protocolloRicevuto) {
		this.protocolloRicevuto = protocolloRicevuto;
	}
	public ProtocolloRicevuto getProtocolloRicevuto() {
		return protocolloRicevuto;
	}
	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}
	public Date getDataArrivo() {
		return dataArrivo;
	}
	public void setMessoTrasmissione(String messoTrasmissione) {
		this.messoTrasmissione = messoTrasmissione;
	}
	public String getMessoTrasmissione() {
		return messoTrasmissione;
	}
	public void setDtRaccomandata(Date dtRaccomandata) {
		this.dtRaccomandata = dtRaccomandata;
	}
	public Date getDtRaccomandata() {
		return dtRaccomandata;
	}
	public void setNroRaccomandata(Integer nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}
	public Integer getNroRaccomandata() {
		return nroRaccomandata;
	}
	public void setDocCollegato(List<DocumentoCollegato> docCollegato) {
		this.docCollegato = docCollegato;
	}
	public List<DocumentoCollegato> getDocCollegato() {
		return docCollegato;
	}
	public String getIdTopografico() {
		return idTopografico;
	}
	public void setIdTopografico(String idTopografico) {
		this.idTopografico = idTopografico;
	}
	public String getDescrizioneCollocazione() {
		return descrizioneCollocazione;
	}
	public void setDescrizioneCollocazione(String descrizioneCollocazione) {
		this.descrizioneCollocazione = descrizioneCollocazione;
	}
	public List<RegistroEmergenza> getRegistroEmergenza() {
		return registroEmergenza;
	}
	public void setRegistroEmergenza(List<RegistroEmergenza> registroEmergenza) {
		this.registroEmergenza = registroEmergenza;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Date getDataStesura() {
		return dataStesura;
	}
	public void setDataStesura(Date dataStesura) {
		this.dataStesura = dataStesura;
	}
	public void setEmailProv(EmailProvBean emailProv) {
		this.emailProv = emailProv;
	}
	public EmailProvBean getEmailProv() {
		return emailProv;
	}
	public EmailProvBean getFlgSoloSetDatiRichPubbl() {
		return flgSoloSetDatiRichPubbl;
	}
	public EmailProvBean getRichPubblDaEliminare() {
		return richPubblDaEliminare;
	}
	public void setFlgSoloSetDatiRichPubbl(EmailProvBean flgSoloSetDatiRichPubbl) {
		this.flgSoloSetDatiRichPubbl = flgSoloSetDatiRichPubbl;
	}
	public void setRichPubblDaEliminare(EmailProvBean richPubblDaEliminare) {
		this.richPubblDaEliminare = richPubblDaEliminare;
	}
}
