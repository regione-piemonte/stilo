/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlAttributiCustom;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.jaxb.variabili.SezioneCache;

@XmlRootElement
public class XmlFascicoloIn implements Serializable {
	
	@XmlVariabile(nome="#FlgFascTitolario", tipo = TipoVariabile.SEMPLICE)
	private Flag flgFascTitolario = Flag.SETTED;
	
	@XmlVariabile(nome="#FlgSottoFascInserto", tipo = TipoVariabile.SEMPLICE)
	private FlagSottoFasc flgSottoFascInserto;
	
	@XmlVariabile(nome="#IdFolderType", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idFolderType;
	
	@XmlVariabile(nome="#NomeFolder", tipo = TipoVariabile.SEMPLICE)
	private String nomeFolder;
	
	@XmlVariabile(nome="#IdClassificazione", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idClassificazione;
	
	@XmlVariabile(nome="#@FolderAppartenenza", tipo = TipoVariabile.LISTA)
	private List<FolderAppartenenzaBean> folderAppartenenza; 
	
	@XmlVariabile(nome="#NroSecondario", tipo = TipoVariabile.SEMPLICE)
	private String nroSecondario; 
	
	@XmlVariabile(nome="#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String desOgg; 
	
	@XmlVariabile(nome="#IdUOResponsabile", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idUOResponsabile; 
	
	@XmlVariabile(nome="#IdScrivResponsabile", tipo = TipoVariabile.SEMPLICE)
	private BigDecimal idScrivResponsabile; 
	
	@XmlVariabile(nome="#FlgArchivio", tipo = TipoVariabile.SEMPLICE)
	private FlagArchivio flgArchivio = FlagArchivio.ARCHIVIO;
	
	@XmlVariabile(nome="#LivRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private String livRiservatezza; 
	
	@XmlVariabile(nome="#DtTermineRiservatezza", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dtTermineRiservatezza; 
	
	@XmlVariabile(nome="#PropagaRiservatezzaContenuti", tipo = TipoVariabile.SEMPLICE)
	private String propagaRiservatezzaContenuti; 
	
	@XmlVariabile(nome="#LivEvidenza", tipo = TipoVariabile.SEMPLICE)
	private String livEvidenza; 
	
	@XmlVariabile(nome="#CollocazioneFisica", tipo = TipoVariabile.NESTED)
	private CollocazioneFisicaFascicoloBean collocazioneFisica; 
	
	@XmlVariabile(nome="#Note", tipo = TipoVariabile.SEMPLICE)
	private String note;
	
	@XmlVariabile(nome="#@Assegnatari", tipo=TipoVariabile.LISTA)
	private List<AssegnatariBean> assegnatari;
	
	@XmlVariabile(nome="#@InvioDestCC", tipo=TipoVariabile.LISTA)
	private List<AssegnatariBean> invioDestCC;
	
	@XmlVariabile(nome="#@ACL", tipo=TipoVariabile.LISTA)
	private List<ACLFolderBean> permessi;
	
	@XmlVariabile(nome="#IdLibrary", tipo=TipoVariabile.SEMPLICE)
	private String idLibrary;

	@XmlVariabile(nome="#FlgInteresseCessato", tipo = TipoVariabile.SEMPLICE)
	private String flgInteresseCessato;		
	
	@XmlVariabile(nome="#FlgEreditaPermessi", tipo = TipoVariabile.SEMPLICE)
	private String flgEreditaPermessi;	
	
	@XmlVariabile(nome="#IdFolderEreditaPerm", tipo = TipoVariabile.SEMPLICE)
	private String idFolderEreditaPerm;	
	
	@XmlAttributiCustom
	private SezioneCache sezioneCacheAttributiDinamici;	
	
	@XmlVariabile(nome="#TsChiusura", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo=Tipo.DATA)
	private Date tsChiusura; 

	@XmlVariabile(nome="#IdUserChiusura", tipo = TipoVariabile.SEMPLICE)
	private String idUserChiusura;	
	
	@XmlVariabile(nome="#IstruttoreProc.Tipo", tipo = TipoVariabile.SEMPLICE)
	private String tipoIstruttoreProc;
	
	@XmlVariabile(nome="#IstruttoreProc.Id", tipo = TipoVariabile.SEMPLICE)
	private String idIstruttoreProc;
	
	@XmlVariabile(nome="#IstruttoreProc.CodRapido", tipo = TipoVariabile.SEMPLICE)
	private String codRapidoIstruttoreProc;	
	
	@XmlVariabile(nome="#IstruttoreProc.Nome", tipo = TipoVariabile.SEMPLICE)
	private String nomeIstruttoreProc;
	
	@XmlVariabile(nome="FLG_DOCUMENTAZIONE_COMPLETA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDocumentazioneCompleta;	
	
	@XmlVariabile(nome="PG_CAPOFILA_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numProtocolloGenerale;
	
	@XmlVariabile(nome="PG_CAPOFILA_ANNO", tipo = TipoVariabile.SEMPLICE)
	private String annoProtocolloGenerale;
	
	@XmlVariabile(nome="PROT_CAPOFILA_REGISTRO", tipo = TipoVariabile.SEMPLICE)
	private String siglaProtocolloSettore;
	
	@XmlVariabile(nome="PROT_CAPOFILA_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numProtocolloSettore;
	
	@XmlVariabile(nome="PROT_CAPOFILA_SUB_NRO", tipo = TipoVariabile.SEMPLICE)
	private String subProtocolloSettore;
	
	@XmlVariabile(nome="PROT_CAPOFILA_ANNO", tipo = TipoVariabile.SEMPLICE)
	private String annoProtocolloSettore;
	
	@XmlVariabile(nome="DT_RICHIESTA_PRATICA_URBANISTICA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtRichPraticaUrbanistica;
	
	@XmlVariabile(nome="COD_CLASSIF_FASC_EDILIZIO", tipo = TipoVariabile.SEMPLICE)
	private String codClassFascEdilizio;
	
	@XmlVariabile(nome="NRO_DEPOSITO", tipo = TipoVariabile.SEMPLICE)
	private String numeroDeposito;
	
	@XmlVariabile(nome="ANNO_DEPOSITO", tipo = TipoVariabile.SEMPLICE)
	private String annoDeposito;

	@XmlVariabile(nome="NRO_EP_PRAT_URBANISTICA", tipo = TipoVariabile.SEMPLICE)
	private String numeroEP;

	@XmlVariabile(nome="ANNO_EP_PRAT_URBANISTICA", tipo = TipoVariabile.SEMPLICE)
	private String annoEP;
	
	@XmlVariabile(nome="NRO_LICENZA_PRAT_URBANISTICA", tipo = TipoVariabile.SEMPLICE)
	private String numeroLicenza;
	
	@XmlVariabile(nome="ANNO_LICENZA_PRAT_URBANISTICA", tipo = TipoVariabile.SEMPLICE)
	private String annoLicenza;
	
	@XmlVariabile(nome="VISURA_PROT_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numProtocolloGeneraleRichPratica;
	
	@XmlVariabile(nome="VISURA_PROT_ANNO", tipo = TipoVariabile.SEMPLICE)
	private String annoProtocolloGeneraleRichPratica;
	
	@XmlVariabile(nome="VISURA_PRATICA_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numProtocolloGeneraleRichPraticaWF;
	
	@XmlVariabile(nome="VISURA_PRATICA_ANNO", tipo = TipoVariabile.SEMPLICE)
	private String annoProtocolloGeneraleRichPraticaWF;
	
	@XmlVariabile(nome = "DENOM_ESIBENTE_PRATICA_URB", tipo = TipoVariabile.LISTA)
	private List<ValueBean> listaEsibentiPraticaPregressa;
	
	@XmlVariabile(nome = "ALTRE_UBICAZIONI", tipo = TipoVariabile.LISTA)
	private List<AltreUbicazioniBean> altreUbicazioni;
	
	@XmlVariabile(nome="#@RelazioniVsFolder", tipo=TipoVariabile.LISTA)
	private List<RelazioneVsFolderBean> relazioniVsFolder;
	
	@XmlVariabile(nome="#CodStato", tipo = TipoVariabile.SEMPLICE)
	private String codStato;
	
	@XmlVariabile(nome="#CodStatoDett", tipo = TipoVariabile.SEMPLICE)
	private String codStatoDett;
	
	@XmlVariabile(nome="#TimestampGetData", tipo = TipoVariabile.SEMPLICE)
	private String timestampGetData;
	
	public Flag getFlgFascTitolario() {
		return flgFascTitolario;
	}

	public void setFlgFascTitolario(Flag flgFascTitolario) {
		this.flgFascTitolario = flgFascTitolario;
	}

	public FlagSottoFasc getFlgSottoFascInserto() {
		return flgSottoFascInserto;
	}

	public void setFlgSottoFascInserto(FlagSottoFasc flgSottoFascInserto) {
		this.flgSottoFascInserto = flgSottoFascInserto;
	}

	public BigDecimal getIdFolderType() {
		return idFolderType;
	}

	public void setIdFolderType(BigDecimal idFolderType) {
		this.idFolderType = idFolderType;
	}

	public String getNomeFolder() {
		return nomeFolder;
	}

	public void setNomeFolder(String nomeFolder) {
		this.nomeFolder = nomeFolder;
	}

	public BigDecimal getIdClassificazione() {
		return idClassificazione;
	}

	public void setIdClassificazione(BigDecimal idClassificazione) {
		this.idClassificazione = idClassificazione;
	}

	public List<FolderAppartenenzaBean> getFolderAppartenenza() {
		return folderAppartenenza;
	}

	public void setFolderAppartenenza(
			List<FolderAppartenenzaBean> folderAppartenenza) {
		this.folderAppartenenza = folderAppartenenza;
	}

	public String getNroSecondario() {
		return nroSecondario;
	}

	public void setNroSecondario(String nroSecondario) {
		this.nroSecondario = nroSecondario;
	}

	public String getDesOgg() {
		return desOgg;
	}

	public void setDesOgg(String desOgg) {
		this.desOgg = desOgg;
	}

	public BigDecimal getIdUOResponsabile() {
		return idUOResponsabile;
	}

	public void setIdUOResponsabile(BigDecimal idUOResponsabile) {
		this.idUOResponsabile = idUOResponsabile;
	}

	public BigDecimal getIdScrivResponsabile() {
		return idScrivResponsabile;
	}

	public void setIdScrivResponsabile(BigDecimal idScrivResponsabile) {
		this.idScrivResponsabile = idScrivResponsabile;
	}

	public FlagArchivio getFlgArchivio() {
		return flgArchivio;
	}

	public void setFlgArchivio(FlagArchivio flgArchivio) {
		this.flgArchivio = flgArchivio;
	}

	public String getLivRiservatezza() {
		return livRiservatezza;
	}

	public void setLivRiservatezza(String livRiservatezza) {
		this.livRiservatezza = livRiservatezza;
	}

	public Date getDtTermineRiservatezza() {
		return dtTermineRiservatezza;
	}

	public void setDtTermineRiservatezza(Date dtTermineRiservatezza) {
		this.dtTermineRiservatezza = dtTermineRiservatezza;
	}

	public String getLivEvidenza() {
		return livEvidenza;
	}

	public void setLivEvidenza(String livEvidenza) {
		this.livEvidenza = livEvidenza;
	}

	public CollocazioneFisicaFascicoloBean getCollocazioneFisica() {
		return collocazioneFisica;
	}

	public void setCollocazioneFisica(CollocazioneFisicaFascicoloBean collocazioneFisica) {
		this.collocazioneFisica = collocazioneFisica;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<AssegnatariBean> getAssegnatari() {
		return assegnatari;
	}

	public void setAssegnatari(List<AssegnatariBean> assegnatari) {
		this.assegnatari = assegnatari;
	}

	public List<AssegnatariBean> getInvioDestCC() {
		return invioDestCC;
	}

	public void setInvioDestCC(List<AssegnatariBean> invioDestCC) {
		this.invioDestCC = invioDestCC;
	}

	public List<ACLFolderBean> getPermessi() {
		return permessi;
	}

	public void setPermessi(List<ACLFolderBean> permessi) {
		this.permessi = permessi;
	}

	public String getPropagaRiservatezzaContenuti() {
		return propagaRiservatezzaContenuti;
	}

	public void setPropagaRiservatezzaContenuti(String propagaRiservatezzaContenuti) {
		this.propagaRiservatezzaContenuti = propagaRiservatezzaContenuti;
	}

	public String getIdLibrary() {
		return idLibrary;
	}

	public void setIdLibrary(String idLibrary) {
		this.idLibrary = idLibrary;
	}

	public String getFlgInteresseCessato() {
		return flgInteresseCessato;
	}

	public void setFlgInteresseCessato(String flgInteresseCessato) {
		this.flgInteresseCessato = flgInteresseCessato;
	}

	public String getFlgEreditaPermessi() {
		return flgEreditaPermessi;
	}

	public void setFlgEreditaPermessi(String flgEreditaPermessi) {
		this.flgEreditaPermessi = flgEreditaPermessi;
	}

	public String getIdFolderEreditaPerm() {
		return idFolderEreditaPerm;
	}

	public void setIdFolderEreditaPerm(String idFolderEreditaPerm) {
		this.idFolderEreditaPerm = idFolderEreditaPerm;
	}

	public SezioneCache getSezioneCacheAttributiDinamici() {
		return sezioneCacheAttributiDinamici;
	}

	public void setSezioneCacheAttributiDinamici(
			SezioneCache sezioneCacheAttributiDinamici) {
		this.sezioneCacheAttributiDinamici = sezioneCacheAttributiDinamici;
	}

	public Date getTsChiusura() {
		return tsChiusura;
	}

	public String getIdUserChiusura() {
		return idUserChiusura;
	}

	public void setTsChiusura(Date tsChiusura) {
		this.tsChiusura = tsChiusura;
	}

	public void setIdUserChiusura(String idUserChiusura) {
		this.idUserChiusura = idUserChiusura;
	}

	
	public String getTipoIstruttoreProc() {
		return tipoIstruttoreProc;
	}

	
	public void setTipoIstruttoreProc(String tipoIstruttoreProc) {
		this.tipoIstruttoreProc = tipoIstruttoreProc;
	}

	
	public String getIdIstruttoreProc() {
		return idIstruttoreProc;
	}

	
	public void setIdIstruttoreProc(String idIstruttoreProc) {
		this.idIstruttoreProc = idIstruttoreProc;
	}

	
	public String getCodRapidoIstruttoreProc() {
		return codRapidoIstruttoreProc;
	}

	
	public void setCodRapidoIstruttoreProc(String codRapidoIstruttoreProc) {
		this.codRapidoIstruttoreProc = codRapidoIstruttoreProc;
	}

	
	public Flag getFlgDocumentazioneCompleta() {
		return flgDocumentazioneCompleta;
	}

	public void setFlgDocumentazioneCompleta(Flag flgDocumentazioneCompleta) {
		this.flgDocumentazioneCompleta = flgDocumentazioneCompleta;
	}

	public String getNomeIstruttoreProc() {
		return nomeIstruttoreProc;
	}

	
	public void setNomeIstruttoreProc(String nomeIstruttoreProc) {
		this.nomeIstruttoreProc = nomeIstruttoreProc;
	}

	public String getNumProtocolloGenerale() {
		return numProtocolloGenerale;
	}

	public void setNumProtocolloGenerale(String numProtocolloGenerale) {
		this.numProtocolloGenerale = numProtocolloGenerale;
	}

	public String getAnnoProtocolloGenerale() {
		return annoProtocolloGenerale;
	}

	public void setAnnoProtocolloGenerale(String annoProtocolloGenerale) {
		this.annoProtocolloGenerale = annoProtocolloGenerale;
	}

	public String getSiglaProtocolloSettore() {
		return siglaProtocolloSettore;
	}

	public void setSiglaProtocolloSettore(String siglaProtocolloSettore) {
		this.siglaProtocolloSettore = siglaProtocolloSettore;
	}

	public String getNumProtocolloSettore() {
		return numProtocolloSettore;
	}

	public void setNumProtocolloSettore(String numProtocolloSettore) {
		this.numProtocolloSettore = numProtocolloSettore;
	}

	public String getSubProtocolloSettore() {
		return subProtocolloSettore;
	}

	public void setSubProtocolloSettore(String subProtocolloSettore) {
		this.subProtocolloSettore = subProtocolloSettore;
	}

	public String getAnnoProtocolloSettore() {
		return annoProtocolloSettore;
	}

	public void setAnnoProtocolloSettore(String annoProtocolloSettore) {
		this.annoProtocolloSettore = annoProtocolloSettore;
	}
	
	public String getCodClassFascEdilizio() {
		return codClassFascEdilizio;
	}

	public void setCodClassFascEdilizio(String codClassFascEdilizio) {
		this.codClassFascEdilizio = codClassFascEdilizio;
	}	
	
	public Date getDtRichPraticaUrbanistica() {
		return dtRichPraticaUrbanistica;
	}

	public void setDtRichPraticaUrbanistica(Date dtRichPraticaUrbanistica) {
		this.dtRichPraticaUrbanistica = dtRichPraticaUrbanistica;
	}
	
	public String getNumeroDeposito() {
		return numeroDeposito;
	}

	public void setNumeroDeposito(String numeroDeposito) {
		this.numeroDeposito = numeroDeposito;
	}

	public String getAnnoDeposito() {
		return annoDeposito;
	}

	public void setAnnoDeposito(String annoDeposito) {
		this.annoDeposito = annoDeposito;
	}
	
	public String getNumeroEP() {
		return numeroEP;
	}

	public void setNumeroEP(String numeroEP) {
		this.numeroEP = numeroEP;
	}

	public String getAnnoEP() {
		return annoEP;
	}

	public void setAnnoEP(String annoEP) {
		this.annoEP = annoEP;
	}

	public String getNumeroLicenza() {
		return numeroLicenza;
	}

	public void setNumeroLicenza(String numeroLicenza) {
		this.numeroLicenza = numeroLicenza;
	}

	public String getAnnoLicenza() {
		return annoLicenza;
	}

	public void setAnnoLicenza(String annoLicenza) {
		this.annoLicenza = annoLicenza;
	}
	
	public String getNumProtocolloGeneraleRichPratica() {
		return numProtocolloGeneraleRichPratica;
	}

	public void setNumProtocolloGeneraleRichPratica(String numProtocolloGeneraleRichPratica) {
		this.numProtocolloGeneraleRichPratica = numProtocolloGeneraleRichPratica;
	}

	public String getAnnoProtocolloGeneraleRichPratica() {
		return annoProtocolloGeneraleRichPratica;
	}

	public void setAnnoProtocolloGeneraleRichPratica(String annoProtocolloGeneraleRichPratica) {
		this.annoProtocolloGeneraleRichPratica = annoProtocolloGeneraleRichPratica;
	}

	public String getNumProtocolloGeneraleRichPraticaWF() {
		return numProtocolloGeneraleRichPraticaWF;
	}

	public void setNumProtocolloGeneraleRichPraticaWF(String numProtocolloGeneraleRichPraticaWF) {
		this.numProtocolloGeneraleRichPraticaWF = numProtocolloGeneraleRichPraticaWF;
	}

	public String getAnnoProtocolloGeneraleRichPraticaWF() {
		return annoProtocolloGeneraleRichPraticaWF;
	}

	public void setAnnoProtocolloGeneraleRichPraticaWF(String annoProtocolloGeneraleRichPraticaWF) {
		this.annoProtocolloGeneraleRichPraticaWF = annoProtocolloGeneraleRichPraticaWF;
	}
	
	public List<ValueBean> getListaEsibentiPraticaPregressa() {
		return listaEsibentiPraticaPregressa;
	}

	public void setListaEsibentiPraticaPregressa(List<ValueBean> listaEsibentiPraticaPregressa) {
		this.listaEsibentiPraticaPregressa = listaEsibentiPraticaPregressa;
	}

	public List<AltreUbicazioniBean> getAltreUbicazioni() {
		return altreUbicazioni;
	}

	public void setAltreUbicazioni(List<AltreUbicazioniBean> altreUbicazioni) {
		this.altreUbicazioni = altreUbicazioni;
	}

	public List<RelazioneVsFolderBean> getRelazioniVsFolder() {
		return relazioniVsFolder;
	}

	public void setRelazioniVsFolder(List<RelazioneVsFolderBean> relazioniVsFolder) {
		this.relazioniVsFolder = relazioniVsFolder;
	}

	public String getCodStato() {
		return codStato;
	}

	public String getCodStatoDett() {
		return codStatoDett;
	}

	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}

	public void setCodStatoDett(String codStatoDett) {
		this.codStatoDett = codStatoDett;
	}

	public String getTimestampGetData() {
		return timestampGetData;
	}

	public void setTimestampGetData(String timestampGetData) {
		this.timestampGetData = timestampGetData;
	}

}