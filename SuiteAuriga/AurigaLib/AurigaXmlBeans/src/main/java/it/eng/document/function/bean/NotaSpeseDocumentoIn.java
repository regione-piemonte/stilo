/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NotaSpeseDocumentoIn extends CreaModDocumentoInBean {
	
	private static final long serialVersionUID = -4011655723485405230L;
	
	@XmlVariabile(nome="COD_SOCIETA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codSocieta;
	
	@XmlVariabile(nome="N_DOCUMENTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String nroDocumento;

	@XmlVariabile(nome="ANNO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String anno;
	
	@XmlVariabile(nome="COD_TIPO_DOC_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codTipoDoc;
	
	@XmlVariabile(nome="RANGE_NUMERAZIONE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String rangeNumerazione;
	
	@XmlVariabile(nome="IDENT_ESTERNO1_Doc", tipo = TipoVariabile.SEMPLICE)
	private String identificativoEsterno1;
	
	@XmlVariabile(nome="IDENT_ESTERNO2_Doc", tipo = TipoVariabile.SEMPLICE)
	private String identificativoEsterno2;
	
	@XmlVariabile(nome="DATA_DOC_Doc", tipo = TipoVariabile.SEMPLICE)
	private String dataDocumento;
	
	@XmlVariabile(nome="DATA_REGISTRAZIONE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String dataRegistrazione;
	
	@XmlVariabile(nome="CF_FORNITORE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String cfFornitore;

	@XmlVariabile(nome="PIVA_FORNITORE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String pivaFornitore;
	
	@XmlVariabile(nome="DES_FORNITORE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String desFornitore;
	
	@XmlVariabile(nome="COD_FORNITORE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codFornitore;
	
	@XmlVariabile(nome="TIPO_CONTABILE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String tipologiaContabile;
	
	@XmlVariabile(nome="IMPORTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String importo;
	
	@XmlVariabile(nome="VALUTA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String valuta;
	
	@XmlVariabile(nome="NOME_FILE_PDF_Doc", tipo = TipoVariabile.SEMPLICE)
	private String nomeFilePdf;

	@XmlVariabile(nome="FLAG_SALTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private Flag flagSalto;
	
	
	public String getCodSocieta() {
		return codSocieta;
	}

	public void setCodSocieta(String codSocieta) {
		this.codSocieta = codSocieta;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getCodTipoDoc() {
		return codTipoDoc;
	}

	public void setCodTipoDoc(String codTipoDoc) {
		this.codTipoDoc = codTipoDoc;
	}

	public String getRangeNumerazione() {
		return rangeNumerazione;
	}

	public void setRangeNumerazione(String rangeNumerazione) {
		this.rangeNumerazione = rangeNumerazione;
	}

	public String getIdentificativoEsterno1() {
		return identificativoEsterno1;
	}

	public void setIdentificativoEsterno1(String identificativoEsterno1) {
		this.identificativoEsterno1 = identificativoEsterno1;
	}

	public String getIdentificativoEsterno2() {
		return identificativoEsterno2;
	}

	public void setIdentificativoEsterno2(String identificativoEsterno2) {
		this.identificativoEsterno2 = identificativoEsterno2;
	}

	public String getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getCfFornitore() {
		return cfFornitore;
	}

	public void setCfFornitore(String cfFornitore) {
		this.cfFornitore = cfFornitore;
	}
	
	public String getPivaFornitore() {
		return pivaFornitore;
	}

	public void setPivaFornitore(String pIvaFornitore) {
		this.pivaFornitore = pIvaFornitore;
	}

	public String getDesFornitore() {
		return desFornitore;
	}

	public void setDesFornitore(String desFornitore) {
		this.desFornitore = desFornitore;
	}

	public String getCodFornitore() {
		return codFornitore;
	}

	public void setCodFornitore(String codFornitore) {
		this.codFornitore = codFornitore;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getValuta() {
		return valuta;
	}

	public void setValuta(String valuta) {
		this.valuta = valuta;
	}

	public String getNomeFilePdf() {
		return nomeFilePdf;
	}

	public void setNomeFilePdf(String nomeFilePdf) {
		this.nomeFilePdf = nomeFilePdf;
	}

	public String getTipologiaContabile() {
		return tipologiaContabile;
	}

	public void setTipologiaContabile(String tipologiaContabile) {
		this.tipologiaContabile = tipologiaContabile;
	}

	public Flag getFlagSalto() {
		return flagSalto;
	}

	public void setFlagSalto(Flag flagSalto) {
		this.flagSalto = flagSalto;
	}
	
}

