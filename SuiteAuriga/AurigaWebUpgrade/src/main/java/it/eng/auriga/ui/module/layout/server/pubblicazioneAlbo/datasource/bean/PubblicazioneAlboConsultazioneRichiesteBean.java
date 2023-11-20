/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocCollegatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OpzioniTimbroDocBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class PubblicazioneAlboConsultazioneRichiesteBean {
		
	private BigDecimal idUdFolder;
	private String idRichPubbl;
	private Boolean isRettifica;
	private Date dataInizioPubblicazione;	
	private String giorniPubblicazione;
	private Date dataFinePubblicazione;
	private Date tsRichiestaPubblicazione;	
	private Date tsRegistrazione;	
	private Date dataAdozione;
	private String richiedentePubblicazione;		
	private String oggetto;
	private String oggettoHtml;
	private String segnatura;
	private String segnaturaXOrd;
	private String tipo;
	private String nomeTipo;
	private String mittenti;
	private String tipoMittente;
	private List<MittenteProtBean> mittenteRichiedenteInterno;
	private List<MittenteProtBean> mittenteRichiedenteEsterno;
	
	private String tipoRegNum;
	private String siglaRegNum;
	private String annoRegNum;
	private String nroRegNum;
	private Date dataEsecutivita;
	private String formaPubblicazione;
	
	// ************************************
	// FilePrimario
	// ************************************
	private BigDecimal idDocPrimario;
	private String nomeFilePrimario;
	private String uriFilePrimario;
	private Boolean remoteUriFilePrimario;
	private String idAttachEmailPrimario;
	private MimeTypeFirmaBean infoFile;
	private Boolean isDocPrimarioChanged;
	private String mimetypeVerPreFirma;
	private String uriFileVerPreFirma;
	private String nomeFileVerPreFirma;
	private String flgConvertibilePdfVerPreFirma;
	private String improntaVerPreFirma;
	private MimeTypeFirmaBean infoFileVerPreFirma;
	private Boolean flgNoPubblPrimario;
	private Boolean flgDatiSensibili;
	private Boolean flgSostituisciVerPrec;
	private String nroLastVer;
	private Boolean flgOriginaleCartaceo;
	private Boolean flgCopiaSostitutiva;
	private Boolean flgTimbratoFilePostReg;
	private Boolean flgTimbraFilePostReg;
	private OpzioniTimbroDocBean opzioniTimbro;
	private String tipoModFilePrimario;
	private List<AllegatoProtocolloBean> listaFilePrimarioVerPubbl;
	// Vers. con omissis
	private DocumentBean filePrimarioOmissis;
	
	private List<AllegatoProtocolloBean> listaAllegati;

	private List<DocCollegatoBean> listaDocumentiCollegati;
	
	// Abilitazioni
	private Boolean abilModificabile; 
	private Boolean abilProrogaPubblicazione;
	private Boolean abilAnnullamentoPubblicazione;
	private Boolean abilRettificaPubblicazione;
	
	private String statoAtto;
	
	// ************* RICHIESTE PUBBLICAZIONE *************
	private Boolean flgPresenzaPubblicazioni;
	private String idAnnPubblicazione;
	private String motivoAnnPubblicazione;
	private String idProrogaPubblicazione;
	private Date dataAProrogaPubblicazione;
	private String giorniDurataProrogaPubblicazione;
	private String motivoProrogaPubblicazione;
	private String idRettificaPubblicazione;
	private String motivoRettificaPubblicazione;
	private String notePubblicazione;
	
	public BigDecimal getIdUdFolder() {
		return idUdFolder;
	}
	public void setIdUdFolder(BigDecimal idUdFolder) {
		this.idUdFolder = idUdFolder;
	}
	public String getIdRichPubbl() {
		return idRichPubbl;
	}
	public void setIdRichPubbl(String idRichPubbl) {
		this.idRichPubbl = idRichPubbl;
	}
	public Boolean getIsRettifica() {
		return isRettifica;
	}
	public void setIsRettifica(Boolean isRettifica) {
		this.isRettifica = isRettifica;
	}
	public Date getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}
	public void setDataInizioPubblicazione(Date dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}
	public String getGiorniPubblicazione() {
		return giorniPubblicazione;
	}
	public void setGiorniPubblicazione(String giorniPubblicazione) {
		this.giorniPubblicazione = giorniPubblicazione;
	}
	public Date getDataFinePubblicazione() {
		return dataFinePubblicazione;
	}
	public void setDataFinePubblicazione(Date dataFinePubblicazione) {
		this.dataFinePubblicazione = dataFinePubblicazione;
	}
	public Date getTsRichiestaPubblicazione() {
		return tsRichiestaPubblicazione;
	}
	public void setTsRichiestaPubblicazione(Date tsRichiestaPubblicazione) {
		this.tsRichiestaPubblicazione = tsRichiestaPubblicazione;
	}
	public Date getTsRegistrazione() {
		return tsRegistrazione;
	}
	public void setTsRegistrazione(Date tsRegistrazione) {
		this.tsRegistrazione = tsRegistrazione;
	}
	public Date getDataAdozione() {
		return dataAdozione;
	}
	public void setDataAdozione(Date dataAdozione) {
		this.dataAdozione = dataAdozione;
	}
	public String getRichiedentePubblicazione() {
		return richiedentePubblicazione;
	}
	public void setRichiedentePubblicazione(String richiedentePubblicazione) {
		this.richiedentePubblicazione = richiedentePubblicazione;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getOggettoHtml() {
		return oggettoHtml;
	}
	public void setOggettoHtml(String oggettoHtml) {
		this.oggettoHtml = oggettoHtml;
	}
	public String getSegnatura() {
		return segnatura;
	}
	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}
	public String getSegnaturaXOrd() {
		return segnaturaXOrd;
	}
	public void setSegnaturaXOrd(String segnaturaXOrd) {
		this.segnaturaXOrd = segnaturaXOrd;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNomeTipo() {
		return nomeTipo;
	}
	public void setNomeTipo(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}
	public String getMittenti() {
		return mittenti;
	}
	public void setMittenti(String mittenti) {
		this.mittenti = mittenti;
	}
	public String getTipoMittente() {
		return tipoMittente;
	}
	public void setTipoMittente(String tipoMittente) {
		this.tipoMittente = tipoMittente;
	}
	public List<MittenteProtBean> getMittenteRichiedenteInterno() {
		return mittenteRichiedenteInterno;
	}
	public void setMittenteRichiedenteInterno(List<MittenteProtBean> mittenteRichiedenteInterno) {
		this.mittenteRichiedenteInterno = mittenteRichiedenteInterno;
	}
	public List<MittenteProtBean> getMittenteRichiedenteEsterno() {
		return mittenteRichiedenteEsterno;
	}
	public void setMittenteRichiedenteEsterno(List<MittenteProtBean> mittenteRichiedenteEsterno) {
		this.mittenteRichiedenteEsterno = mittenteRichiedenteEsterno;
	}
	public String getTipoRegNum() {
		return tipoRegNum;
	}
	public void setTipoRegNum(String tipoRegNum) {
		this.tipoRegNum = tipoRegNum;
	}
	public String getSiglaRegNum() {
		return siglaRegNum;
	}
	public void setSiglaRegNum(String siglaRegNum) {
		this.siglaRegNum = siglaRegNum;
	}
	public String getAnnoRegNum() {
		return annoRegNum;
	}
	public void setAnnoRegNum(String annoRegNum) {
		this.annoRegNum = annoRegNum;
	}
	public String getNroRegNum() {
		return nroRegNum;
	}
	public void setNroRegNum(String nroRegNum) {
		this.nroRegNum = nroRegNum;
	}
	public Date getDataEsecutivita() {
		return dataEsecutivita;
	}
	public void setDataEsecutivita(Date dataEsecutivita) {
		this.dataEsecutivita = dataEsecutivita;
	}
	public String getFormaPubblicazione() {
		return formaPubblicazione;
	}
	public void setFormaPubblicazione(String formaPubblicazione) {
		this.formaPubblicazione = formaPubblicazione;
	}
	public BigDecimal getIdDocPrimario() {
		return idDocPrimario;
	}
	public void setIdDocPrimario(BigDecimal idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}
	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}
	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}
	public String getUriFilePrimario() {
		return uriFilePrimario;
	}
	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}
	public Boolean getRemoteUriFilePrimario() {
		return remoteUriFilePrimario;
	}
	public void setRemoteUriFilePrimario(Boolean remoteUriFilePrimario) {
		this.remoteUriFilePrimario = remoteUriFilePrimario;
	}
	public String getIdAttachEmailPrimario() {
		return idAttachEmailPrimario;
	}
	public void setIdAttachEmailPrimario(String idAttachEmailPrimario) {
		this.idAttachEmailPrimario = idAttachEmailPrimario;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public Boolean getIsDocPrimarioChanged() {
		return isDocPrimarioChanged;
	}
	public void setIsDocPrimarioChanged(Boolean isDocPrimarioChanged) {
		this.isDocPrimarioChanged = isDocPrimarioChanged;
	}
	public String getMimetypeVerPreFirma() {
		return mimetypeVerPreFirma;
	}
	public void setMimetypeVerPreFirma(String mimetypeVerPreFirma) {
		this.mimetypeVerPreFirma = mimetypeVerPreFirma;
	}
	public String getUriFileVerPreFirma() {
		return uriFileVerPreFirma;
	}
	public void setUriFileVerPreFirma(String uriFileVerPreFirma) {
		this.uriFileVerPreFirma = uriFileVerPreFirma;
	}
	public String getNomeFileVerPreFirma() {
		return nomeFileVerPreFirma;
	}
	public void setNomeFileVerPreFirma(String nomeFileVerPreFirma) {
		this.nomeFileVerPreFirma = nomeFileVerPreFirma;
	}
	public String getFlgConvertibilePdfVerPreFirma() {
		return flgConvertibilePdfVerPreFirma;
	}
	public void setFlgConvertibilePdfVerPreFirma(String flgConvertibilePdfVerPreFirma) {
		this.flgConvertibilePdfVerPreFirma = flgConvertibilePdfVerPreFirma;
	}
	public String getImprontaVerPreFirma() {
		return improntaVerPreFirma;
	}
	public void setImprontaVerPreFirma(String improntaVerPreFirma) {
		this.improntaVerPreFirma = improntaVerPreFirma;
	}
	public MimeTypeFirmaBean getInfoFileVerPreFirma() {
		return infoFileVerPreFirma;
	}
	public void setInfoFileVerPreFirma(MimeTypeFirmaBean infoFileVerPreFirma) {
		this.infoFileVerPreFirma = infoFileVerPreFirma;
	}
	public Boolean getFlgNoPubblPrimario() {
		return flgNoPubblPrimario;
	}
	public void setFlgNoPubblPrimario(Boolean flgNoPubblPrimario) {
		this.flgNoPubblPrimario = flgNoPubblPrimario;
	}
	public Boolean getFlgDatiSensibili() {
		return flgDatiSensibili;
	}
	public void setFlgDatiSensibili(Boolean flgDatiSensibili) {
		this.flgDatiSensibili = flgDatiSensibili;
	}
	public Boolean getFlgSostituisciVerPrec() {
		return flgSostituisciVerPrec;
	}
	public void setFlgSostituisciVerPrec(Boolean flgSostituisciVerPrec) {
		this.flgSostituisciVerPrec = flgSostituisciVerPrec;
	}
	public String getNroLastVer() {
		return nroLastVer;
	}
	public void setNroLastVer(String nroLastVer) {
		this.nroLastVer = nroLastVer;
	}
	public Boolean getFlgOriginaleCartaceo() {
		return flgOriginaleCartaceo;
	}
	public void setFlgOriginaleCartaceo(Boolean flgOriginaleCartaceo) {
		this.flgOriginaleCartaceo = flgOriginaleCartaceo;
	}
	public Boolean getFlgCopiaSostitutiva() {
		return flgCopiaSostitutiva;
	}
	public void setFlgCopiaSostitutiva(Boolean flgCopiaSostitutiva) {
		this.flgCopiaSostitutiva = flgCopiaSostitutiva;
	}
	public Boolean getFlgTimbratoFilePostReg() {
		return flgTimbratoFilePostReg;
	}
	public void setFlgTimbratoFilePostReg(Boolean flgTimbratoFilePostReg) {
		this.flgTimbratoFilePostReg = flgTimbratoFilePostReg;
	}
	public Boolean getFlgTimbraFilePostReg() {
		return flgTimbraFilePostReg;
	}
	public void setFlgTimbraFilePostReg(Boolean flgTimbraFilePostReg) {
		this.flgTimbraFilePostReg = flgTimbraFilePostReg;
	}
	public OpzioniTimbroDocBean getOpzioniTimbro() {
		return opzioniTimbro;
	}
	public void setOpzioniTimbro(OpzioniTimbroDocBean opzioniTimbro) {
		this.opzioniTimbro = opzioniTimbro;
	}
	public String getTipoModFilePrimario() {
		return tipoModFilePrimario;
	}
	public void setTipoModFilePrimario(String tipoModFilePrimario) {
		this.tipoModFilePrimario = tipoModFilePrimario;
	}
	public List<AllegatoProtocolloBean> getListaFilePrimarioVerPubbl() {
		return listaFilePrimarioVerPubbl;
	}
	public void setListaFilePrimarioVerPubbl(List<AllegatoProtocolloBean> listaFilePrimarioVerPubbl) {
		this.listaFilePrimarioVerPubbl = listaFilePrimarioVerPubbl;
	}
	public DocumentBean getFilePrimarioOmissis() {
		return filePrimarioOmissis;
	}
	public void setFilePrimarioOmissis(DocumentBean filePrimarioOmissis) {
		this.filePrimarioOmissis = filePrimarioOmissis;
	}
	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	public List<DocCollegatoBean> getListaDocumentiCollegati() {
		return listaDocumentiCollegati;
	}
	public void setListaDocumentiCollegati(List<DocCollegatoBean> listaDocumentiCollegati) {
		this.listaDocumentiCollegati = listaDocumentiCollegati;
	}
	public Boolean getAbilModificabile() {
		return abilModificabile;
	}
	public void setAbilModificabile(Boolean abilModificabile) {
		this.abilModificabile = abilModificabile;
	}
	public Boolean getAbilProrogaPubblicazione() {
		return abilProrogaPubblicazione;
	}
	public void setAbilProrogaPubblicazione(Boolean abilProrogaPubblicazione) {
		this.abilProrogaPubblicazione = abilProrogaPubblicazione;
	}
	public Boolean getAbilAnnullamentoPubblicazione() {
		return abilAnnullamentoPubblicazione;
	}
	public void setAbilAnnullamentoPubblicazione(Boolean abilAnnullamentoPubblicazione) {
		this.abilAnnullamentoPubblicazione = abilAnnullamentoPubblicazione;
	}
	public Boolean getAbilRettificaPubblicazione() {
		return abilRettificaPubblicazione;
	}
	public void setAbilRettificaPubblicazione(Boolean abilRettificaPubblicazione) {
		this.abilRettificaPubblicazione = abilRettificaPubblicazione;
	}
	public String getStatoAtto() {
		return statoAtto;
	}
	public void setStatoAtto(String statoAtto) {
		this.statoAtto = statoAtto;
	}
	public Boolean getFlgPresenzaPubblicazioni() {
		return flgPresenzaPubblicazioni;
	}
	public void setFlgPresenzaPubblicazioni(Boolean flgPresenzaPubblicazioni) {
		this.flgPresenzaPubblicazioni = flgPresenzaPubblicazioni;
	}
	public String getIdAnnPubblicazione() {
		return idAnnPubblicazione;
	}
	public void setIdAnnPubblicazione(String idAnnPubblicazione) {
		this.idAnnPubblicazione = idAnnPubblicazione;
	}
	public String getMotivoAnnPubblicazione() {
		return motivoAnnPubblicazione;
	}
	public void setMotivoAnnPubblicazione(String motivoAnnPubblicazione) {
		this.motivoAnnPubblicazione = motivoAnnPubblicazione;
	}
	public String getIdProrogaPubblicazione() {
		return idProrogaPubblicazione;
	}
	public void setIdProrogaPubblicazione(String idProrogaPubblicazione) {
		this.idProrogaPubblicazione = idProrogaPubblicazione;
	}
	public Date getDataAProrogaPubblicazione() {
		return dataAProrogaPubblicazione;
	}
	public void setDataAProrogaPubblicazione(Date dataAProrogaPubblicazione) {
		this.dataAProrogaPubblicazione = dataAProrogaPubblicazione;
	}
	public String getGiorniDurataProrogaPubblicazione() {
		return giorniDurataProrogaPubblicazione;
	}
	public void setGiorniDurataProrogaPubblicazione(String giorniDurataProrogaPubblicazione) {
		this.giorniDurataProrogaPubblicazione = giorniDurataProrogaPubblicazione;
	}
	public String getMotivoProrogaPubblicazione() {
		return motivoProrogaPubblicazione;
	}
	public void setMotivoProrogaPubblicazione(String motivoProrogaPubblicazione) {
		this.motivoProrogaPubblicazione = motivoProrogaPubblicazione;
	}
	public String getIdRettificaPubblicazione() {
		return idRettificaPubblicazione;
	}
	public void setIdRettificaPubblicazione(String idRettificaPubblicazione) {
		this.idRettificaPubblicazione = idRettificaPubblicazione;
	}
	public String getMotivoRettificaPubblicazione() {
		return motivoRettificaPubblicazione;
	}
	public void setMotivoRettificaPubblicazione(String motivoRettificaPubblicazione) {
		this.motivoRettificaPubblicazione = motivoRettificaPubblicazione;
	}
	public String getNotePubblicazione() {
		return notePubblicazione;
	}
	public void setNotePubblicazione(String notePubblicazione) {
		this.notePubblicazione = notePubblicazione;
	}
}