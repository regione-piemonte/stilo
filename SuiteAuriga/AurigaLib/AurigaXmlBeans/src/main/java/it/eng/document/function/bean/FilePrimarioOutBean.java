/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class FilePrimarioOutBean implements Serializable {
	
	private static final long serialVersionUID = 7158880440347580787L;
	
	@XmlVariabile(nome="NroLastVisibleVer", tipo=TipoVariabile.SEMPLICE)
	private Integer nroLastVisibleVer;
	@XmlVariabile(nome="NroLastVer", tipo=TipoVariabile.SEMPLICE)
	private Integer nroLastVer;
	@XmlVariabile(nome="DisplayFilename", tipo=TipoVariabile.SEMPLICE)
	private String displayFilename;
	@XmlVariabile(nome="NomeOriginale", tipo=TipoVariabile.SEMPLICE)
	private String nomeOriginale;
	@XmlVariabile(nome="URI", tipo=TipoVariabile.SEMPLICE)
	private String uri;
	@XmlVariabile(nome="TsInsLastVerFile", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsInsLastVerFile;
	@XmlVariabile(nome="Dimensione", tipo=TipoVariabile.SEMPLICE)
	private BigDecimal dimensione;
	@XmlVariabile(nome="FlgFirmato", tipo=TipoVariabile.SEMPLICE)
	private Flag flgFirmato;
	@XmlVariabile(nome="FlgConvertibilePdf", tipo=TipoVariabile.SEMPLICE)
	private Flag flgConvertibilePdf;
	@XmlVariabile(nome="Mimetype", tipo=TipoVariabile.SEMPLICE)
	private String mimetype;
	@XmlVariabile(nome="Impronta", tipo=TipoVariabile.SEMPLICE)
	private String impronta;
	@XmlVariabile(nome="AlgoritmoImpronta", tipo=TipoVariabile.SEMPLICE)
	private String algoritmoImpronta;
	@XmlVariabile(nome="EncodingImpronta", tipo=TipoVariabile.SEMPLICE)
	private String encodingImpronta;
	@XmlVariabile(nome="FlgPubblicato", tipo=TipoVariabile.SEMPLICE)
	private String flgPubblicato;
	@XmlVariabile(nome="IdAttachEmail", tipo=TipoVariabile.SEMPLICE)
	private String idAttachEmail;
	@XmlVariabile(nome="Firmatari", tipo=TipoVariabile.SEMPLICE)
	private String firmatari;
	@XmlVariabile(nome="VerPreFirma.Mimetype", tipo=TipoVariabile.SEMPLICE)
	private String mimetypeVerPreFirma;
	@XmlVariabile(nome="VerPreFirma.URI", tipo=TipoVariabile.SEMPLICE)
	private String uriVerPreFirma;
	@XmlVariabile(nome="VerPreFirma.DisplayFilename", tipo=TipoVariabile.SEMPLICE)
	private String displayFilenameVerPreFirma;
	@XmlVariabile(nome="VerPreFirma.FlgConvertibilePdf", tipo=TipoVariabile.SEMPLICE)
	private String flgConvertibilePdfVerPreFirma;
	@XmlVariabile(nome="VerPreFirma.Impronta", tipo=TipoVariabile.SEMPLICE)
	private String improntaVerPreFirma;
	@XmlVariabile(nome="FlgTimbratoPostReg", tipo=TipoVariabile.SEMPLICE)
	private Flag flgTimbratoPostReg;
	@XmlVariabile(nome="FlgBustaCrittograficaAuriga", tipo=TipoVariabile.SEMPLICE)
	private Flag flgBustaCrittograficaAuriga;
	@XmlVariabile(nome="FlgFirmeNonValide", tipo=TipoVariabile.SEMPLICE)
	private Flag flgFirmeNonValideBustaCrittografica;
	@XmlVariabile(nome="FlgFirmeExtraAuriga", tipo=TipoVariabile.SEMPLICE)
	private Flag flgFirmeExtraAuriga;
	@XmlVariabile(nome="FlgMarcaTemporaleAuriga", tipo=TipoVariabile.SEMPLICE)
	private Flag flgMarcaTemporaleAuriga;
	@XmlVariabile(nome="DataOraMarcaTemporale", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataOraMarcaTemporale;
	@XmlVariabile(nome="FlgMarcaTemporaleNonValida", tipo=TipoVariabile.SEMPLICE)
	private Flag flgMarcaTemporaleNonValida;
	@XmlVariabile(nome="FlgPdfProtetto", tipo=TipoVariabile.SEMPLICE)
	private Flag flgPdfProtetto;
	@XmlVariabile(nome="FlgPdfEditabile", tipo=TipoVariabile.SEMPLICE)
	private Flag flgPdfEditabile;
	@XmlVariabile(nome="FlgPdfConCommenti", tipo=TipoVariabile.SEMPLICE)
	private Flag flgPdfConCommenti;
	@XmlVariabile(nome="IdUDScansione", tipo=TipoVariabile.SEMPLICE)
	private String idUDScansione;
	
	// Dettaglio doc. contratti con barcode
	@XmlVariabile(nome="TipoBarcode", tipo=TipoVariabile.SEMPLICE)
	private String tipoBarcode;
	@XmlVariabile(nome="Barcode", tipo=TipoVariabile.SEMPLICE)
	private String barcode;
	@XmlVariabile(nome="ContrattoEnergiaGas", tipo=TipoVariabile.SEMPLICE)
	private String energiaGas;
	@XmlVariabile(nome="TipoSezioneContratto", tipo=TipoVariabile.SEMPLICE)
	private String tipoSezioneContrattoPrimario;
	@XmlVariabile(nome="CodContratto", tipo=TipoVariabile.SEMPLICE)
	private String codContrattoPrimario;
	@XmlVariabile(nome="FlgPresentiFirmeContratto", tipo=TipoVariabile.SEMPLICE)
	private String flgPresentiFirmeContrattoPrimario;
	@XmlVariabile(nome="FlgFirmeCompleteContratto", tipo=TipoVariabile.SEMPLICE)
	private String flgFirmeCompleteContrattoPrimario;
	@XmlVariabile(nome="NroFirmePrevisteContratto", tipo=TipoVariabile.SEMPLICE)
	private String nroFirmePrevisteContrattoPrimario;
	@XmlVariabile(nome="NroFirmeCompilateContratto", tipo=TipoVariabile.SEMPLICE)
	private String nroFirmeCompilateContrattoPrimario;
	@XmlVariabile(nome="FlgIncertezzaRilevazioneFirme", tipo=TipoVariabile.SEMPLICE)
	private String flgIncertezzaRilevazioneFirmePrimario;

	// Vers. con omissis
	@XmlVariabile(nome="VerConOmissis.IdDoc", tipo=TipoVariabile.SEMPLICE)
	private String idDocOmissis;	
	@XmlVariabile(nome="VerConOmissis.NroLastVisibleVer", tipo=TipoVariabile.SEMPLICE)
	private Integer nroLastVisibleVerOmissis;
	@XmlVariabile(nome="VerConOmissis.NroLastVer", tipo=TipoVariabile.SEMPLICE)
	private Integer nroLastVerOmissis;
	@XmlVariabile(nome="VerConOmissis.DisplayFilename", tipo=TipoVariabile.SEMPLICE)
	private String displayFilenameOmissis;
	@XmlVariabile(nome="VerConOmissis.NomeOriginale", tipo=TipoVariabile.SEMPLICE)
	private String nomeOriginaleOmissis;
	@XmlVariabile(nome="VerConOmissis.URI", tipo=TipoVariabile.SEMPLICE)
	private String uriOmissis;
	@XmlVariabile(nome="VerConOmissis.Dimensione", tipo=TipoVariabile.SEMPLICE)
	private BigDecimal dimensioneOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgFirmato", tipo=TipoVariabile.SEMPLICE)
	private Flag flgFirmatoOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgConvertibilePdf", tipo=TipoVariabile.SEMPLICE)
	private Flag flgConvertibilePdfOmissis;
	@XmlVariabile(nome="VerConOmissis.Mimetype", tipo=TipoVariabile.SEMPLICE)
	private String mimetypeOmissis;
	@XmlVariabile(nome="VerConOmissis.Impronta", tipo=TipoVariabile.SEMPLICE)
	private String improntaOmissis;
	@XmlVariabile(nome="VerConOmissis.AlgoritmoImpronta", tipo=TipoVariabile.SEMPLICE)
	private String algoritmoImprontaOmissis;
	@XmlVariabile(nome="VerConOmissis.EncodingImpronta", tipo=TipoVariabile.SEMPLICE)
	private String encodingImprontaOmissis;
	@XmlVariabile(nome="VerConOmissis.Firmatari", tipo=TipoVariabile.SEMPLICE)
	private String firmatariOmissis;
	@XmlVariabile(nome="VerConOmissis.VerPreFirma.Mimetype", tipo=TipoVariabile.SEMPLICE)
	private String mimetypeVerPreFirmaOmissis;
	@XmlVariabile(nome="VerConOmissis.VerPreFirma.URI", tipo=TipoVariabile.SEMPLICE)
	private String uriVerPreFirmaOmissis;
	@XmlVariabile(nome="VerConOmissis.VerPreFirma.DisplayFilename", tipo=TipoVariabile.SEMPLICE)
	private String displayFilenameVerPreFirmaOmissis;
	@XmlVariabile(nome="VerConOmissis.VerPreFirma.FlgConvertibilePdf", tipo=TipoVariabile.SEMPLICE)
	private String flgConvertibilePdfVerPreFirmaOmissis;
	@XmlVariabile(nome="VerConOmissis.VerPreFirma.Impronta", tipo=TipoVariabile.SEMPLICE)
	private String improntaVerPreFirmaOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgTimbratoPostReg", tipo=TipoVariabile.SEMPLICE)
	private Flag flgTimbratoPostRegOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgBustaCrittograficaAuriga", tipo=TipoVariabile.SEMPLICE)
	private Flag flgBustaCrittograficaAurigaOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgFirmeNonValide", tipo=TipoVariabile.SEMPLICE)
	private Flag flgFirmeNonValideBustaCrittograficaOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgFirmeExtraAuriga", tipo=TipoVariabile.SEMPLICE)
	private Flag flgFirmeExtraAurigaOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgMarcaTemporaleAuriga", tipo=TipoVariabile.SEMPLICE)
	private Flag flgMarcaTemporaleAurigaOmissis;
	@XmlVariabile(nome="VerConOmissis.DataOraMarcaTemporale", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataOraMarcaTemporaleOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgMarcaTemporaleNonValida", tipo=TipoVariabile.SEMPLICE)
	private Flag flgMarcaTemporaleNonValidaOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgPdfProtetto", tipo=TipoVariabile.SEMPLICE)
	private Flag flgPdfProtettoOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgPdfEditabile", tipo=TipoVariabile.SEMPLICE)
	private Flag flgPdfEditabileOmissis;
	@XmlVariabile(nome="VerConOmissis.FlgPdfConCommenti", tipo=TipoVariabile.SEMPLICE)
	private Flag flgPdfConCommentiOmissis;
	@XmlVariabile(nome = "TipoDocConFirma", tipo = TipoVariabile.SEMPLICE)
	private String flgRichiestaFirmaDigitale;
	
	// Archiviazione in ACTA
	@XmlVariabile(nome="EsitoInvioActaSerieAttiIntegrali", tipo=TipoVariabile.SEMPLICE)
	private String esitoInvioACTASerieAttiIntegrali;
	@XmlVariabile(nome="EsitoInvioActaSeriePubbl", tipo=TipoVariabile.SEMPLICE)
	private String esitoInvioACTASeriePubbl;

	public Integer getNroLastVisibleVer() {
		return nroLastVisibleVer;
	}
	public void setNroLastVisibleVer(Integer nroLastVisibleVer) {
		this.nroLastVisibleVer = nroLastVisibleVer;
	}
	public Integer getNroLastVer() {
		return nroLastVer;
	}
	public void setNroLastVer(Integer nroLastVer) {
		this.nroLastVer = nroLastVer;
	}
	public String getDisplayFilename() {
		return displayFilename;
	}
	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}
	public String getNomeOriginale() {
		return nomeOriginale;
	}
	public void setNomeOriginale(String nomeOriginale) {
		this.nomeOriginale = nomeOriginale;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Date getTsInsLastVerFile() {
		return tsInsLastVerFile;
	}
	public void setTsInsLastVerFile(Date tsInsLastVerFile) {
		this.tsInsLastVerFile = tsInsLastVerFile;
	}
	public BigDecimal getDimensione() {
		return dimensione;
	}
	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}
	public Flag getFlgFirmato() {
		return flgFirmato;
	}
	public void setFlgFirmato(Flag flgFirmato) {
		this.flgFirmato = flgFirmato;
	}
	public Flag getFlgConvertibilePdf() {
		return flgConvertibilePdf;
	}
	public void setFlgConvertibilePdf(Flag flgConvertibilePdf) {
		this.flgConvertibilePdf = flgConvertibilePdf;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}
	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}
	public String getEncodingImpronta() {
		return encodingImpronta;
	}
	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}
	public String getFlgPubblicato() {
		return flgPubblicato;
	}
	public void setFlgPubblicato(String flgPubblicato) {
		this.flgPubblicato = flgPubblicato;
	}
	public String getIdAttachEmail() {
		return idAttachEmail;
	}
	public void setIdAttachEmail(String idAttachEmail) {
		this.idAttachEmail = idAttachEmail;
	}
	public String getFirmatari() {
		return firmatari;
	}
	public void setFirmatari(String firmatari) {
		this.firmatari = firmatari;
	}
	public String getMimetypeVerPreFirma() {
		return mimetypeVerPreFirma;
	}
	public void setMimetypeVerPreFirma(String mimetypeVerPreFirma) {
		this.mimetypeVerPreFirma = mimetypeVerPreFirma;
	}
	public String getUriVerPreFirma() {
		return uriVerPreFirma;
	}
	public void setUriVerPreFirma(String uriVerPreFirma) {
		this.uriVerPreFirma = uriVerPreFirma;
	}
	public String getDisplayFilenameVerPreFirma() {
		return displayFilenameVerPreFirma;
	}
	public void setDisplayFilenameVerPreFirma(String displayFilenameVerPreFirma) {
		this.displayFilenameVerPreFirma = displayFilenameVerPreFirma;
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
	public Flag getFlgTimbratoPostReg() {
		return flgTimbratoPostReg;
	}
	public void setFlgTimbratoPostReg(Flag flgTimbratoPostReg) {
		this.flgTimbratoPostReg = flgTimbratoPostReg;
	}
	public Flag getFlgBustaCrittograficaAuriga() {
		return flgBustaCrittograficaAuriga;
	}
	public void setFlgBustaCrittograficaAuriga(Flag flgBustaCrittograficaAuriga) {
		this.flgBustaCrittograficaAuriga = flgBustaCrittograficaAuriga;
	}
	public Flag getFlgFirmeNonValideBustaCrittografica() {
		return flgFirmeNonValideBustaCrittografica;
	}
	public void setFlgFirmeNonValideBustaCrittografica(Flag flgFirmeNonValideBustaCrittografica) {
		this.flgFirmeNonValideBustaCrittografica = flgFirmeNonValideBustaCrittografica;
	}
	public Flag getFlgFirmeExtraAuriga() {
		return flgFirmeExtraAuriga;
	}
	public void setFlgFirmeExtraAuriga(Flag flgFirmeExtraAuriga) {
		this.flgFirmeExtraAuriga = flgFirmeExtraAuriga;
	}
	public Flag getFlgMarcaTemporaleAuriga() {
		return flgMarcaTemporaleAuriga;
	}
	public void setFlgMarcaTemporaleAuriga(Flag flgMarcaTemporaleAuriga) {
		this.flgMarcaTemporaleAuriga = flgMarcaTemporaleAuriga;
	}
	public Date getDataOraMarcaTemporale() {
		return dataOraMarcaTemporale;
	}
	public void setDataOraMarcaTemporale(Date dataOraMarcaTemporale) {
		this.dataOraMarcaTemporale = dataOraMarcaTemporale;
	}
	public Flag getFlgMarcaTemporaleNonValida() {
		return flgMarcaTemporaleNonValida;
	}
	public void setFlgMarcaTemporaleNonValida(Flag flgMarcaTemporaleNonValida) {
		this.flgMarcaTemporaleNonValida = flgMarcaTemporaleNonValida;
	}
	public Flag getFlgPdfProtetto() {
		return flgPdfProtetto;
	}
	public void setFlgPdfProtetto(Flag flgPdfProtetto) {
		this.flgPdfProtetto = flgPdfProtetto;
	}
	public Flag getFlgPdfEditabile() {
		return flgPdfEditabile;
	}
	public void setFlgPdfEditabile(Flag flgPdfEditabile) {
		this.flgPdfEditabile = flgPdfEditabile;
	}
	public Flag getFlgPdfConCommenti() {
		return flgPdfConCommenti;
	}
	public void setFlgPdfConCommenti(Flag flgPdfConCommenti) {
		this.flgPdfConCommenti = flgPdfConCommenti;
	}
	public String getIdUDScansione() {
		return idUDScansione;
	}
	public void setIdUDScansione(String idUDScansione) {
		this.idUDScansione = idUDScansione;
	}
	public String getTipoBarcode() {
		return tipoBarcode;
	}
	public void setTipoBarcode(String tipoBarcode) {
		this.tipoBarcode = tipoBarcode;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getEnergiaGas() {
		return energiaGas;
	}
	public void setEnergiaGas(String energiaGas) {
		this.energiaGas = energiaGas;
	}
	public String getTipoSezioneContrattoPrimario() {
		return tipoSezioneContrattoPrimario;
	}
	public void setTipoSezioneContrattoPrimario(String tipoSezioneContrattoPrimario) {
		this.tipoSezioneContrattoPrimario = tipoSezioneContrattoPrimario;
	}
	public String getCodContrattoPrimario() {
		return codContrattoPrimario;
	}
	public void setCodContrattoPrimario(String codContrattoPrimario) {
		this.codContrattoPrimario = codContrattoPrimario;
	}
	public String getFlgPresentiFirmeContrattoPrimario() {
		return flgPresentiFirmeContrattoPrimario;
	}
	public void setFlgPresentiFirmeContrattoPrimario(String flgPresentiFirmeContrattoPrimario) {
		this.flgPresentiFirmeContrattoPrimario = flgPresentiFirmeContrattoPrimario;
	}
	public String getFlgFirmeCompleteContrattoPrimario() {
		return flgFirmeCompleteContrattoPrimario;
	}
	public void setFlgFirmeCompleteContrattoPrimario(String flgFirmeCompleteContrattoPrimario) {
		this.flgFirmeCompleteContrattoPrimario = flgFirmeCompleteContrattoPrimario;
	}
	public String getNroFirmePrevisteContrattoPrimario() {
		return nroFirmePrevisteContrattoPrimario;
	}
	public void setNroFirmePrevisteContrattoPrimario(String nroFirmePrevisteContrattoPrimario) {
		this.nroFirmePrevisteContrattoPrimario = nroFirmePrevisteContrattoPrimario;
	}
	public String getNroFirmeCompilateContrattoPrimario() {
		return nroFirmeCompilateContrattoPrimario;
	}
	public void setNroFirmeCompilateContrattoPrimario(String nroFirmeCompilateContrattoPrimario) {
		this.nroFirmeCompilateContrattoPrimario = nroFirmeCompilateContrattoPrimario;
	}
	public String getFlgIncertezzaRilevazioneFirmePrimario() {
		return flgIncertezzaRilevazioneFirmePrimario;
	}
	public void setFlgIncertezzaRilevazioneFirmePrimario(String flgIncertezzaRilevazioneFirmePrimario) {
		this.flgIncertezzaRilevazioneFirmePrimario = flgIncertezzaRilevazioneFirmePrimario;
	}
	public String getIdDocOmissis() {
		return idDocOmissis;
	}
	public void setIdDocOmissis(String idDocOmissis) {
		this.idDocOmissis = idDocOmissis;
	}
	public Integer getNroLastVisibleVerOmissis() {
		return nroLastVisibleVerOmissis;
	}
	public void setNroLastVisibleVerOmissis(Integer nroLastVisibleVerOmissis) {
		this.nroLastVisibleVerOmissis = nroLastVisibleVerOmissis;
	}
	public Integer getNroLastVerOmissis() {
		return nroLastVerOmissis;
	}
	public void setNroLastVerOmissis(Integer nroLastVerOmissis) {
		this.nroLastVerOmissis = nroLastVerOmissis;
	}
	public String getDisplayFilenameOmissis() {
		return displayFilenameOmissis;
	}
	public void setDisplayFilenameOmissis(String displayFilenameOmissis) {
		this.displayFilenameOmissis = displayFilenameOmissis;
	}
	public String getNomeOriginaleOmissis() {
		return nomeOriginaleOmissis;
	}
	public void setNomeOriginaleOmissis(String nomeOriginaleOmissis) {
		this.nomeOriginaleOmissis = nomeOriginaleOmissis;
	}
	public String getUriOmissis() {
		return uriOmissis;
	}
	public void setUriOmissis(String uriOmissis) {
		this.uriOmissis = uriOmissis;
	}
	public BigDecimal getDimensioneOmissis() {
		return dimensioneOmissis;
	}
	public void setDimensioneOmissis(BigDecimal dimensioneOmissis) {
		this.dimensioneOmissis = dimensioneOmissis;
	}
	public Flag getFlgFirmatoOmissis() {
		return flgFirmatoOmissis;
	}
	public void setFlgFirmatoOmissis(Flag flgFirmatoOmissis) {
		this.flgFirmatoOmissis = flgFirmatoOmissis;
	}
	public Flag getFlgConvertibilePdfOmissis() {
		return flgConvertibilePdfOmissis;
	}
	public void setFlgConvertibilePdfOmissis(Flag flgConvertibilePdfOmissis) {
		this.flgConvertibilePdfOmissis = flgConvertibilePdfOmissis;
	}
	public String getMimetypeOmissis() {
		return mimetypeOmissis;
	}
	public void setMimetypeOmissis(String mimetypeOmissis) {
		this.mimetypeOmissis = mimetypeOmissis;
	}
	public String getImprontaOmissis() {
		return improntaOmissis;
	}
	public void setImprontaOmissis(String improntaOmissis) {
		this.improntaOmissis = improntaOmissis;
	}
	public String getAlgoritmoImprontaOmissis() {
		return algoritmoImprontaOmissis;
	}
	public void setAlgoritmoImprontaOmissis(String algoritmoImprontaOmissis) {
		this.algoritmoImprontaOmissis = algoritmoImprontaOmissis;
	}
	public String getEncodingImprontaOmissis() {
		return encodingImprontaOmissis;
	}
	public void setEncodingImprontaOmissis(String encodingImprontaOmissis) {
		this.encodingImprontaOmissis = encodingImprontaOmissis;
	}
	public String getFirmatariOmissis() {
		return firmatariOmissis;
	}
	public void setFirmatariOmissis(String firmatariOmissis) {
		this.firmatariOmissis = firmatariOmissis;
	}
	public String getMimetypeVerPreFirmaOmissis() {
		return mimetypeVerPreFirmaOmissis;
	}
	public void setMimetypeVerPreFirmaOmissis(String mimetypeVerPreFirmaOmissis) {
		this.mimetypeVerPreFirmaOmissis = mimetypeVerPreFirmaOmissis;
	}
	public String getUriVerPreFirmaOmissis() {
		return uriVerPreFirmaOmissis;
	}
	public void setUriVerPreFirmaOmissis(String uriVerPreFirmaOmissis) {
		this.uriVerPreFirmaOmissis = uriVerPreFirmaOmissis;
	}
	public String getDisplayFilenameVerPreFirmaOmissis() {
		return displayFilenameVerPreFirmaOmissis;
	}
	public void setDisplayFilenameVerPreFirmaOmissis(String displayFilenameVerPreFirmaOmissis) {
		this.displayFilenameVerPreFirmaOmissis = displayFilenameVerPreFirmaOmissis;
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
	public Flag getFlgTimbratoPostRegOmissis() {
		return flgTimbratoPostRegOmissis;
	}
	public void setFlgTimbratoPostRegOmissis(Flag flgTimbratoPostRegOmissis) {
		this.flgTimbratoPostRegOmissis = flgTimbratoPostRegOmissis;
	}
	public Flag getFlgBustaCrittograficaAurigaOmissis() {
		return flgBustaCrittograficaAurigaOmissis;
	}
	public void setFlgBustaCrittograficaAurigaOmissis(Flag flgBustaCrittograficaAurigaOmissis) {
		this.flgBustaCrittograficaAurigaOmissis = flgBustaCrittograficaAurigaOmissis;
	}
	public Flag getFlgFirmeNonValideBustaCrittograficaOmissis() {
		return flgFirmeNonValideBustaCrittograficaOmissis;
	}
	public void setFlgFirmeNonValideBustaCrittograficaOmissis(Flag flgFirmeNonValideBustaCrittograficaOmissis) {
		this.flgFirmeNonValideBustaCrittograficaOmissis = flgFirmeNonValideBustaCrittograficaOmissis;
	}
	public Flag getFlgFirmeExtraAurigaOmissis() {
		return flgFirmeExtraAurigaOmissis;
	}
	public void setFlgFirmeExtraAurigaOmissis(Flag flgFirmeExtraAurigaOmissis) {
		this.flgFirmeExtraAurigaOmissis = flgFirmeExtraAurigaOmissis;
	}
	public Flag getFlgMarcaTemporaleAurigaOmissis() {
		return flgMarcaTemporaleAurigaOmissis;
	}
	public void setFlgMarcaTemporaleAurigaOmissis(Flag flgMarcaTemporaleAurigaOmissis) {
		this.flgMarcaTemporaleAurigaOmissis = flgMarcaTemporaleAurigaOmissis;
	}
	public Date getDataOraMarcaTemporaleOmissis() {
		return dataOraMarcaTemporaleOmissis;
	}
	public void setDataOraMarcaTemporaleOmissis(Date dataOraMarcaTemporaleOmissis) {
		this.dataOraMarcaTemporaleOmissis = dataOraMarcaTemporaleOmissis;
	}
	public Flag getFlgMarcaTemporaleNonValidaOmissis() {
		return flgMarcaTemporaleNonValidaOmissis;
	}
	public void setFlgMarcaTemporaleNonValidaOmissis(Flag flgMarcaTemporaleNonValidaOmissis) {
		this.flgMarcaTemporaleNonValidaOmissis = flgMarcaTemporaleNonValidaOmissis;
	}
	public Flag getFlgPdfProtettoOmissis() {
		return flgPdfProtettoOmissis;
	}
	public void setFlgPdfProtettoOmissis(Flag flgPdfProtettoOmissis) {
		this.flgPdfProtettoOmissis = flgPdfProtettoOmissis;
	}
	public Flag getFlgPdfEditabileOmissis() {
		return flgPdfEditabileOmissis;
	}
	public void setFlgPdfEditabileOmissis(Flag flgPdfEditabileOmissis) {
		this.flgPdfEditabileOmissis = flgPdfEditabileOmissis;
	}
	public Flag getFlgPdfConCommentiOmissis() {
		return flgPdfConCommentiOmissis;
	}
	public void setFlgPdfConCommentiOmissis(Flag flgPdfConCommentiOmissis) {
		this.flgPdfConCommentiOmissis = flgPdfConCommentiOmissis;
	}
	public String getFlgRichiestaFirmaDigitale() {
		return flgRichiestaFirmaDigitale;
	}
	public void setFlgRichiestaFirmaDigitale(String flgRichiestaFirmaDigitale) {
		this.flgRichiestaFirmaDigitale = flgRichiestaFirmaDigitale;
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
	
}
