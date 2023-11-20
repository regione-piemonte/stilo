/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class AllegatoProtocolloBean {

	private Boolean flgSalvato;
	private Boolean flgNoModificaDati;
	private String idTipoFileAllegatoSalvato;
	private String idTipoFileAllegato;
	private String descTipoFileAllegato;
	private String listaTipiFileAllegato;
	private String flgRichiestaFirmaDigitale;
	private String dictionaryEntrySezione;
	private String descrizioneFileAllegato;
	private BigDecimal idDocAllegato;
	private File fileAllegato;		
	private String percorsoRelFileAllegati;
	private String nomeFileAllegato;
	private String uriFileAllegato;
	private Date tsInsLastVerFileAllegato;
	private Boolean remoteUri;
	private String idAttachEmail;
	private MimeTypeFirmaBean infoFile;
	private Boolean isChanged;
	private Boolean flgProvEsterna;
	private Boolean flgParteDispositivo;
	private Boolean flgParteDispositivoSalvato;
	private Boolean flgParere;
	private Boolean flgParereDaUnire;
	private Boolean flgDatiProtettiTipo1;
	private Boolean flgDatiProtettiTipo2;
	private Boolean flgDatiProtettiTipo3;
	private Boolean flgDatiProtettiTipo4;
	private String mimetypeVerPreFirma;
	private String uriFileVerPreFirma;
	private String nomeFileVerPreFirma;
	private String flgConvertibilePdfVerPreFirma;
	private String improntaVerPreFirma;
	private MimeTypeFirmaBean infoFileVerPreFirma;
	private String idTask;
	private String idTaskVer;
	private Boolean flgNoPubblAllegato;
	private Boolean flgPubblicaSeparato;
	private String flgPaginaFileUnione;
	private String nroPagFileUnione;
	private String nroPagFileUnioneOmissis;	
	private Boolean flgDatiSensibili;
	private Boolean flgSostituisciVerPrec;
	private Boolean flgDaFirmare;
	private Boolean flgDaProtocollare;
	private String idUdAppartenenza;	
	private String ruoloUd;	
	private Boolean isUdSenzaFileImportata;
	private String estremiProtUd;
	private Date dataRicezione;
	private Boolean fileImportato;
	private Boolean collegaDocumentoImportato;
	private String nroUltimaVersione;
	private String numeroProgrAllegato;
	private Boolean flgOriginaleCartaceo;
	private Boolean flgCopiaSostitutiva;	
	private Boolean isPubblicato;
	private String idEmail;
	private Boolean flgTimbratoFilePostReg;
	private Boolean flgTimbraFilePostReg;
	private OpzioniTimbroDocBean opzioniTimbro;	
	private String esitoInvioACTASerieAttiIntegrali;
	private String esitoInvioACTASeriePubbl;
	// Dettaglio doc. contratti con barcode
	private String tipoBarcodeContratto;
	private String barcodeContratto;
	private String energiaGasContratto;
	private String tipoSezioneContratto;
	private String codContratto;
	private String flgPresentiFirmeContratto;
	private String flgFirmeCompleteContratto;
	private String nroFirmePrevisteContratto;
	private String nroFirmeCompilateContratto;
	private String flgIncertezzaRilevazioneFirmeContratto;	
	// Vers. con omissis
	private BigDecimal idDocOmissis;
	protected String nomeFileOmissis;
	protected String uriFileOmissis; 
	protected Boolean remoteUriOmissis; 
	protected MimeTypeFirmaBean infoFileOmissis; 
	protected Boolean isChangedOmissis; 
	private String mimetypeVerPreFirmaOmissis;
	private String uriFileVerPreFirmaOmissis;
	private String nomeFileVerPreFirmaOmissis;
	private String flgConvertibilePdfVerPreFirmaOmissis;
	private String improntaVerPreFirmaOmissis;
	private MimeTypeFirmaBean infoFileVerPreFirmaOmissis;
	private Boolean flgSostituisciVerPrecOmissis;
	private String nroUltimaVersioneOmissis;	
	private Boolean flgTimbratoFilePostRegOmissis;
	private Boolean flgTimbraFilePostRegOmissis;
	private OpzioniTimbroDocBean opzioniTimbroOmissis;	
	private String nroProtocollo;
	private String annoProtocollo;
	private Date dataProtocollo;
	private String flgTipoProvXProt;
	private String idUDScansione;
	private Boolean flgIdUdAppartenenzaContieneAllegati;
	private Boolean flgAllegatoDaImportare;
	private String nroAllegato;
	private Boolean flgGenAutoDaModello;
	private Boolean flgGenDaModelloDaFirmare;
	private String idUdAllegato;
	
	public Boolean getFlgSalvato() {
		return flgSalvato;
	}
	public void setFlgSalvato(Boolean flgSalvato) {
		this.flgSalvato = flgSalvato;
	}
	public Boolean getFlgNoModificaDati() {
		return flgNoModificaDati;
	}
	public void setFlgNoModificaDati(Boolean flgNoModificaDati) {
		this.flgNoModificaDati = flgNoModificaDati;
	}
	public String getIdTipoFileAllegatoSalvato() {
		return idTipoFileAllegatoSalvato;
	}
	public void setIdTipoFileAllegatoSalvato(String idTipoFileAllegatoSalvato) {
		this.idTipoFileAllegatoSalvato = idTipoFileAllegatoSalvato;
	}
	public String getIdTipoFileAllegato() {
		return idTipoFileAllegato;
	}
	public void setIdTipoFileAllegato(String idTipoFileAllegato) {
		this.idTipoFileAllegato = idTipoFileAllegato;
	}
	public String getDescTipoFileAllegato() {
		return descTipoFileAllegato;
	}
	public void setDescTipoFileAllegato(String descTipoFileAllegato) {
		this.descTipoFileAllegato = descTipoFileAllegato;
	}
	public String getListaTipiFileAllegato() {
		return listaTipiFileAllegato;
	}
	public String getFlgRichiestaFirmaDigitale() {
		return flgRichiestaFirmaDigitale;
	}
	public void setFlgRichiestaFirmaDigitale(String flgRichiestaFirmaDigitale) {
		this.flgRichiestaFirmaDigitale = flgRichiestaFirmaDigitale;
	}
	public void setListaTipiFileAllegato(String listaTipiFileAllegato) {
		this.listaTipiFileAllegato = listaTipiFileAllegato;
	}
	public String getDictionaryEntrySezione() {
		return dictionaryEntrySezione;
	}
	public void setDictionaryEntrySezione(String dictionaryEntrySezione) {
		this.dictionaryEntrySezione = dictionaryEntrySezione;
	}
	public String getDescrizioneFileAllegato() {
		return descrizioneFileAllegato;
	}
	public void setDescrizioneFileAllegato(String descrizioneFileAllegato) {
		this.descrizioneFileAllegato = descrizioneFileAllegato;
	}
	public BigDecimal getIdDocAllegato() {
		return idDocAllegato;
	}
	public void setIdDocAllegato(BigDecimal idDocAllegato) {
		this.idDocAllegato = idDocAllegato;
	}
	public File getFileAllegato() {
		return fileAllegato;
	}
	public void setFileAllegato(File fileAllegato) {
		this.fileAllegato = fileAllegato;
	}
	public String getPercorsoRelFileAllegati() {
		return percorsoRelFileAllegati;
	}
	public void setPercorsoRelFileAllegati(String percorsoRelFileAllegati) {
		this.percorsoRelFileAllegati = percorsoRelFileAllegati;
	}
	public String getNomeFileAllegato() {
		return nomeFileAllegato;
	}
	public void setNomeFileAllegato(String nomeFileAllegato) {
		this.nomeFileAllegato = nomeFileAllegato;
	}
	public String getUriFileAllegato() {
		return uriFileAllegato;
	}
	public void setUriFileAllegato(String uriFileAllegato) {
		this.uriFileAllegato = uriFileAllegato;
	}
	public Date getTsInsLastVerFileAllegato() {
		return tsInsLastVerFileAllegato;
	}
	public void setTsInsLastVerFileAllegato(Date tsInsLastVerFileAllegato) {
		this.tsInsLastVerFileAllegato = tsInsLastVerFileAllegato;
	}
	public Boolean getRemoteUri() {
		return remoteUri;
	}
	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}
	public String getIdAttachEmail() {
		return idAttachEmail;
	}
	public void setIdAttachEmail(String idAttachEmail) {
		this.idAttachEmail = idAttachEmail;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public Boolean getIsChanged() {
		return isChanged;
	}
	public void setIsChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}
	
	public Boolean getFlgProvEsterna() {
		return flgProvEsterna;
	}
	public void setFlgProvEsterna(Boolean flgProvEsterna) {
		this.flgProvEsterna = flgProvEsterna;
	}
	public Boolean getFlgParteDispositivo() {
		return flgParteDispositivo;
	}
	public void setFlgParteDispositivo(Boolean flgParteDispositivo) {
		this.flgParteDispositivo = flgParteDispositivo;
	}
	public Boolean getFlgParteDispositivoSalvato() {
		return flgParteDispositivoSalvato;
	}
	public void setFlgParteDispositivoSalvato(Boolean flgParteDispositivoSalvato) {
		this.flgParteDispositivoSalvato = flgParteDispositivoSalvato;
	}
	public Boolean getFlgParere() {
		return flgParere;
	}
	public void setFlgParere(Boolean flgParere) {
		this.flgParere = flgParere;
	}
	public Boolean getFlgParereDaUnire() {
		return flgParereDaUnire;
	}
	public void setFlgParereDaUnire(Boolean flgParereDaUnire) {
		this.flgParereDaUnire = flgParereDaUnire;
	}
	public Boolean getFlgDatiProtettiTipo1() {
		return flgDatiProtettiTipo1;
	}
	public void setFlgDatiProtettiTipo1(Boolean flgDatiProtettiTipo1) {
		this.flgDatiProtettiTipo1 = flgDatiProtettiTipo1;
	}
	public Boolean getFlgDatiProtettiTipo2() {
		return flgDatiProtettiTipo2;
	}
	public void setFlgDatiProtettiTipo2(Boolean flgDatiProtettiTipo2) {
		this.flgDatiProtettiTipo2 = flgDatiProtettiTipo2;
	}
	public Boolean getFlgDatiProtettiTipo3() {
		return flgDatiProtettiTipo3;
	}
	public void setFlgDatiProtettiTipo3(Boolean flgDatiProtettiTipo3) {
		this.flgDatiProtettiTipo3 = flgDatiProtettiTipo3;
	}
	public Boolean getFlgDatiProtettiTipo4() {
		return flgDatiProtettiTipo4;
	}
	public void setFlgDatiProtettiTipo4(Boolean flgDatiProtettiTipo4) {
		this.flgDatiProtettiTipo4 = flgDatiProtettiTipo4;
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
	public String getIdTask() {
		return idTask;
	}
	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}
	public String getIdTaskVer() {
		return idTaskVer;
	}
	public void setIdTaskVer(String idTaskVer) {
		this.idTaskVer = idTaskVer;
	}
	public Boolean getFlgNoPubblAllegato() {
		return flgNoPubblAllegato;
	}
	public void setFlgNoPubblAllegato(Boolean flgNoPubblAllegato) {
		this.flgNoPubblAllegato = flgNoPubblAllegato;
	}
	public Boolean getFlgPubblicaSeparato() {
		return flgPubblicaSeparato;
	}
	public void setFlgPubblicaSeparato(Boolean flgPubblicaSeparato) {
		this.flgPubblicaSeparato = flgPubblicaSeparato;
	}	
	public String getFlgPaginaFileUnione() {
		return flgPaginaFileUnione;
	}
	public void setFlgPaginaFileUnione(String flgPaginaFileUnione) {
		this.flgPaginaFileUnione = flgPaginaFileUnione;
	}
	public String getNroPagFileUnione() {
		return nroPagFileUnione;
	}
	public void setNroPagFileUnione(String nroPagFileUnione) {
		this.nroPagFileUnione = nroPagFileUnione;
	}
	public String getNroPagFileUnioneOmissis() {
		return nroPagFileUnioneOmissis;
	}
	public void setNroPagFileUnioneOmissis(String nroPagFileUnioneOmissis) {
		this.nroPagFileUnioneOmissis = nroPagFileUnioneOmissis;
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
	public Boolean getFlgDaFirmare() {
		return flgDaFirmare;
	}
	public void setFlgDaFirmare(Boolean flgDaFirmare) {
		this.flgDaFirmare = flgDaFirmare;
	}
	public Boolean getFlgDaProtocollare() {
		return flgDaProtocollare;
	}
	public void setFlgDaProtocollare(Boolean flgDaProtocollare) {
		this.flgDaProtocollare = flgDaProtocollare;
	}
	public String getIdUdAppartenenza() {
		return idUdAppartenenza;
	}
	public void setIdUdAppartenenza(String idUdAppartenenza) {
		this.idUdAppartenenza = idUdAppartenenza;
	}	
	public String getRuoloUd() {
		return ruoloUd;
	}
	public void setRuoloUd(String ruoloUd) {
		this.ruoloUd = ruoloUd;
	}
	public Boolean getIsUdSenzaFileImportata() {
		return isUdSenzaFileImportata;
	}
	public void setIsUdSenzaFileImportata(Boolean isUdSenzaFileImportata) {
		this.isUdSenzaFileImportata = isUdSenzaFileImportata;
	}
	public String getEstremiProtUd() {
		return estremiProtUd;
	}
	public void setEstremiProtUd(String estremiProtUd) {
		this.estremiProtUd = estremiProtUd;
	}
	public Date getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public Boolean getFileImportato() {
		return fileImportato;
	}
	public void setFileImportato(Boolean fileImportato) {
		this.fileImportato = fileImportato;
	}
	public Boolean getCollegaDocumentoImportato() {
		return collegaDocumentoImportato;
	}
	public void setCollegaDocumentoImportato(Boolean collegaDocumentoImportato) {
		this.collegaDocumentoImportato = collegaDocumentoImportato;
	}
	public String getNroUltimaVersione() {
		return nroUltimaVersione;
	}
	public void setNroUltimaVersione(String nroUltimaVersione) {
		this.nroUltimaVersione = nroUltimaVersione;
	}
	public String getNumeroProgrAllegato() {
		return numeroProgrAllegato;
	}
	public void setNumeroProgrAllegato(String numeroProgrAllegato) {
		this.numeroProgrAllegato = numeroProgrAllegato;
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
	public Boolean getIsPubblicato() {
		return isPubblicato;
	}
	public void setIsPubblicato(Boolean isPubblicato) {
		this.isPubblicato = isPubblicato;
	}
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
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
	public String getEsitoInvioACTASerieAttiIntegrali() {
		return esitoInvioACTASerieAttiIntegrali;
	}
	public void setEsitoInvioACTASerieAttiIntegrali(String esitoInvioACTASerieAttiIntegrali) {
		this.esitoInvioACTASerieAttiIntegrali = esitoInvioACTASerieAttiIntegrali;
	}
	public String getEsitoInvioACTASeriePubbl() {
		return esitoInvioACTASeriePubbl;
	}
	public void setEsitoInvioACTASeriePubbl(String esitoInvioACTASeriePubbl) {
		this.esitoInvioACTASeriePubbl = esitoInvioACTASeriePubbl;
	}	
	public String getTipoSezioneContratto() {
		return tipoSezioneContratto;
	}
	public void setTipoSezioneContratto(String tipoSezioneContratto) {
		this.tipoSezioneContratto = tipoSezioneContratto;
	}
	public String getCodContratto() {
		return codContratto;
	}
	public void setCodContratto(String codContratto) {
		this.codContratto = codContratto;
	}
	public String getFlgPresentiFirmeContratto() {
		return flgPresentiFirmeContratto;
	}
	public void setFlgPresentiFirmeContratto(String flgPresentiFirmeContratto) {
		this.flgPresentiFirmeContratto = flgPresentiFirmeContratto;
	}
	public String getFlgFirmeCompleteContratto() {
		return flgFirmeCompleteContratto;
	}
	public void setFlgFirmeCompleteContratto(String flgFirmeCompleteContratto) {
		this.flgFirmeCompleteContratto = flgFirmeCompleteContratto;
	}
	public String getNroFirmePrevisteContratto() {
		return nroFirmePrevisteContratto;
	}
	public void setNroFirmePrevisteContratto(String nroFirmePrevisteContratto) {
		this.nroFirmePrevisteContratto = nroFirmePrevisteContratto;
	}
	public String getNroFirmeCompilateContratto() {
		return nroFirmeCompilateContratto;
	}
	public void setNroFirmeCompilateContratto(String nroFirmeCompilateContratto) {
		this.nroFirmeCompilateContratto = nroFirmeCompilateContratto;
	}
	public BigDecimal getIdDocOmissis() {
		return idDocOmissis;
	}
	public void setIdDocOmissis(BigDecimal idDocOmissis) {
		this.idDocOmissis = idDocOmissis;
	}
	public String getNomeFileOmissis() {
		return nomeFileOmissis;
	}
	public void setNomeFileOmissis(String nomeFileOmissis) {
		this.nomeFileOmissis = nomeFileOmissis;
	}
	public String getUriFileOmissis() {
		return uriFileOmissis;
	}
	public void setUriFileOmissis(String uriFileOmissis) {
		this.uriFileOmissis = uriFileOmissis;
	}
	public Boolean getRemoteUriOmissis() {
		return remoteUriOmissis;
	}
	public void setRemoteUriOmissis(Boolean remoteUriOmissis) {
		this.remoteUriOmissis = remoteUriOmissis;
	}
	public MimeTypeFirmaBean getInfoFileOmissis() {
		return infoFileOmissis;
	}
	public void setInfoFileOmissis(MimeTypeFirmaBean infoFileOmissis) {
		this.infoFileOmissis = infoFileOmissis;
	}
	public Boolean getIsChangedOmissis() {
		return isChangedOmissis;
	}
	public void setIsChangedOmissis(Boolean isChangedOmissis) {
		this.isChangedOmissis = isChangedOmissis;
	}
	public String getMimetypeVerPreFirmaOmissis() {
		return mimetypeVerPreFirmaOmissis;
	}
	public void setMimetypeVerPreFirmaOmissis(String mimetypeVerPreFirmaOmissis) {
		this.mimetypeVerPreFirmaOmissis = mimetypeVerPreFirmaOmissis;
	}
	public String getUriFileVerPreFirmaOmissis() {
		return uriFileVerPreFirmaOmissis;
	}
	public void setUriFileVerPreFirmaOmissis(String uriFileVerPreFirmaOmissis) {
		this.uriFileVerPreFirmaOmissis = uriFileVerPreFirmaOmissis;
	}
	public String getNomeFileVerPreFirmaOmissis() {
		return nomeFileVerPreFirmaOmissis;
	}
	public void setNomeFileVerPreFirmaOmissis(String nomeFileVerPreFirmaOmissis) {
		this.nomeFileVerPreFirmaOmissis = nomeFileVerPreFirmaOmissis;
	}
	public String getFlgConvertibilePdfVerPreFirmaOmissis() {
		return flgConvertibilePdfVerPreFirmaOmissis;
	}
	public void setFlgConvertibilePdfVerPreFirmaOmissis(String flgConvertibilePdfVerPreFirmaOmissis) {
		this.flgConvertibilePdfVerPreFirmaOmissis = flgConvertibilePdfVerPreFirmaOmissis;
	}
	public String getImprontaVerPreFirmaOmissis() {
		return improntaVerPreFirmaOmissis;
	}
	public void setImprontaVerPreFirmaOmissis(String improntaVerPreFirmaOmissis) {
		this.improntaVerPreFirmaOmissis = improntaVerPreFirmaOmissis;
	}
	public MimeTypeFirmaBean getInfoFileVerPreFirmaOmissis() {
		return infoFileVerPreFirmaOmissis;
	}
	public void setInfoFileVerPreFirmaOmissis(MimeTypeFirmaBean infoFileVerPreFirmaOmissis) {
		this.infoFileVerPreFirmaOmissis = infoFileVerPreFirmaOmissis;
	}
	public Boolean getFlgSostituisciVerPrecOmissis() {
		return flgSostituisciVerPrecOmissis;
	}
	public void setFlgSostituisciVerPrecOmissis(Boolean flgSostituisciVerPrecOmissis) {
		this.flgSostituisciVerPrecOmissis = flgSostituisciVerPrecOmissis;
	}
	public String getNroUltimaVersioneOmissis() {
		return nroUltimaVersioneOmissis;
	}
	public void setNroUltimaVersioneOmissis(String nroUltimaVersioneOmissis) {
		this.nroUltimaVersioneOmissis = nroUltimaVersioneOmissis;
	}
	public Boolean getFlgTimbratoFilePostRegOmissis() {
		return flgTimbratoFilePostRegOmissis;
	}
	public void setFlgTimbratoFilePostRegOmissis(Boolean flgTimbratoFilePostRegOmissis) {
		this.flgTimbratoFilePostRegOmissis = flgTimbratoFilePostRegOmissis;
	}
	public Boolean getFlgTimbraFilePostRegOmissis() {
		return flgTimbraFilePostRegOmissis;
	}
	public void setFlgTimbraFilePostRegOmissis(Boolean flgTimbraFilePostRegOmissis) {
		this.flgTimbraFilePostRegOmissis = flgTimbraFilePostRegOmissis;
	}
	public OpzioniTimbroDocBean getOpzioniTimbroOmissis() {
		return opzioniTimbroOmissis;
	}
	public void setOpzioniTimbroOmissis(OpzioniTimbroDocBean opzioniTimbroOmissis) {
		this.opzioniTimbroOmissis = opzioniTimbroOmissis;
	}
	public String getNroProtocollo() {
		return nroProtocollo;
	}
	public void setNroProtocollo(String nroProtocollo) {
		this.nroProtocollo = nroProtocollo;
	}
	public String getAnnoProtocollo() {
		return annoProtocollo;
	}
	public void setAnnoProtocollo(String annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}
	public Date getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public String getFlgTipoProvXProt() {
		return flgTipoProvXProt;
	}
	public void setFlgTipoProvXProt(String flgTipoProvXProt) {
		this.flgTipoProvXProt = flgTipoProvXProt;
	}
	public String getIdUDScansione() {
		return idUDScansione;
	}
	public void setIdUDScansione(String idUDScansione) {
		this.idUDScansione = idUDScansione;
	}
	public String getTipoBarcodeContratto() {
		return tipoBarcodeContratto;
	}
	public String getBarcodeContratto() {
		return barcodeContratto;
	}
	public String getEnergiaGasContratto() {
		return energiaGasContratto;
	}
	public void setTipoBarcodeContratto(String tipoBarcodeContratto) {
		this.tipoBarcodeContratto = tipoBarcodeContratto;
	}
	public void setBarcodeContratto(String barcodeContratto) {
		this.barcodeContratto = barcodeContratto;
	}
	public void setEnergiaGasContratto(String energiaGasContratto) {
		this.energiaGasContratto = energiaGasContratto;
	}
	public String getFlgIncertezzaRilevazioneFirmeContratto() {
		return flgIncertezzaRilevazioneFirmeContratto;
	}
	public void setFlgIncertezzaRilevazioneFirmeContratto(String flgIncertezzaRilevazioneFirmeContratto) {
		this.flgIncertezzaRilevazioneFirmeContratto = flgIncertezzaRilevazioneFirmeContratto;
	}
	public Boolean getFlgIdUdAppartenenzaContieneAllegati() {
		return flgIdUdAppartenenzaContieneAllegati;
	}
	public void setFlgIdUdAppartenenzaContieneAllegati(Boolean flgIdUdAppartenenzaContieneAllegati) {
		this.flgIdUdAppartenenzaContieneAllegati = flgIdUdAppartenenzaContieneAllegati;
	}
	public Boolean getFlgAllegatoDaImportare() {
		return flgAllegatoDaImportare;
	}
	public void setFlgAllegatoDaImportare(Boolean flgAllegatoDaImportare) {
		this.flgAllegatoDaImportare = flgAllegatoDaImportare;
	}
	public String getNroAllegato() {
		return nroAllegato;
	}
	public void setNroAllegato(String nroAllegato) {
		this.nroAllegato = nroAllegato;
	}
	public Boolean getFlgGenAutoDaModello() {
		return flgGenAutoDaModello;
	}
	public void setFlgGenAutoDaModello(Boolean flgGenAutoDaModello) {
		this.flgGenAutoDaModello = flgGenAutoDaModello;
	}
	public Boolean getFlgGenDaModelloDaFirmare() {
		return flgGenDaModelloDaFirmare;
	}
	public void setFlgGenDaModelloDaFirmare(Boolean flgGenDaModelloDaFirmare) {
		this.flgGenDaModelloDaFirmare = flgGenDaModelloDaFirmare;
	}
	public String getIdUdAllegato() {
		return idUdAllegato;
	}
	public void setIdUdAllegato(String idUdAllegato) {
		this.idUdAllegato = idUdAllegato;
	}
	
}