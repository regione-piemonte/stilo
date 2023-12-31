/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

// Generated 6-feb-2017 12.14.44 by Hibernate Tools 3.5.0.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TAttachEmailMgo generated by hbm2java
 */
@Entity
@Table(name = "T_ATTACH_EMAIL_MGO")
public class TAttachEmailMgo implements java.io.Serializable {

	private String idAttachEmail;
	private TRegProtVsEmail TRegProtVsEmail;
	private TSchedeDocVsEmail TSchedeDocVsEmail;
	private TEmailMgo TEmailMgo;
	private Byte nroAllegRegProt;
	private Byte nroAllegSchedaDoc;
	private String nomeOriginale;
	private String displayFilename;
	private long dimensione;
	private boolean flgFirmato;
	private boolean flgFirmaValida;
	private String mimetype;
	private String impronta;
	private String algoritmoImpronta;
	private String encodingImpronta;
	private Date tsIns;
	private String idUteIns;
	private Date tsUltimoAgg;
	private String idUteUltimoAgg;
	private BigDecimal nroProgr;
	private String nomeOriginaleCtx;
	private boolean flgPdfEditabile;
	private boolean flgPdfConCommenti;
	private String segnatura;
    private String uriAttach;
    private String uriFileXOcr;
    
	public TAttachEmailMgo() {
	}

	public TAttachEmailMgo(String idAttachEmail, TEmailMgo TEmailMgo, String nomeOriginale, long dimensione, boolean flgFirmato, Date tsIns, Date tsUltimoAgg) {
		this.idAttachEmail = idAttachEmail;
		this.TEmailMgo = TEmailMgo;
		this.nomeOriginale = nomeOriginale;
		this.dimensione = dimensione;
		this.flgFirmato = flgFirmato;
		this.tsIns = tsIns;
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public TAttachEmailMgo(String idAttachEmail, TEmailMgo TEmailMgo, String nomeOriginale, long dimensione, boolean flgFirmato, boolean flgFirmaValida,
			Date tsIns, Date tsUltimoAgg) {
		this.idAttachEmail = idAttachEmail;
		this.TEmailMgo = TEmailMgo;
		this.nomeOriginale = nomeOriginale;
		this.dimensione = dimensione;
		this.flgFirmato = flgFirmato;
		this.flgFirmaValida = flgFirmaValida;
		this.tsIns = tsIns;
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public TAttachEmailMgo(String idAttachEmail, TRegProtVsEmail TRegProtVsEmail, TSchedeDocVsEmail TSchedeDocVsEmail, TEmailMgo TEmailMgo,
			Byte nroAllegRegProt, Byte nroAllegSchedaDoc, String nomeOriginale, String displayFilename, long dimensione, boolean flgFirmato,
			boolean flgFirmaValida, String mimetype, String impronta, String algoritmoImpronta, String encodingImpronta, Date tsIns, String idUteIns,
			Date tsUltimoAgg, String idUteUltimoAgg, BigDecimal nroProgr, String nomeOriginaleCtx) {
		this.idAttachEmail = idAttachEmail;
		this.TRegProtVsEmail = TRegProtVsEmail;
		this.TSchedeDocVsEmail = TSchedeDocVsEmail;
		this.TEmailMgo = TEmailMgo;
		this.nroAllegRegProt = nroAllegRegProt;
		this.nroAllegSchedaDoc = nroAllegSchedaDoc;
		this.nomeOriginale = nomeOriginale;
		this.displayFilename = displayFilename;
		this.dimensione = dimensione;
		this.flgFirmato = flgFirmato;
		this.flgFirmaValida = flgFirmaValida;
		this.mimetype = mimetype;
		this.impronta = impronta;
		this.algoritmoImpronta = algoritmoImpronta;
		this.encodingImpronta = encodingImpronta;
		this.tsIns = tsIns;
		this.idUteIns = idUteIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.idUteUltimoAgg = idUteUltimoAgg;
		this.nroProgr = nroProgr;
		this.nomeOriginaleCtx = nomeOriginaleCtx;
	}

	public TAttachEmailMgo(String idAttachEmail, TRegProtVsEmail TRegProtVsEmail, TSchedeDocVsEmail TSchedeDocVsEmail, TEmailMgo TEmailMgo,
			Byte nroAllegRegProt, Byte nroAllegSchedaDoc, String nomeOriginale, String displayFilename, long dimensione, boolean flgFirmato, String mimetype,
			String impronta, String algoritmoImpronta, String encodingImpronta, Date tsIns, String idUteIns, Date tsUltimoAgg, String idUteUltimoAgg,
			BigDecimal nroProgr, String nomeOriginaleCtx) {
		this.idAttachEmail = idAttachEmail;
		this.TRegProtVsEmail = TRegProtVsEmail;
		this.TSchedeDocVsEmail = TSchedeDocVsEmail;
		this.TEmailMgo = TEmailMgo;
		this.nroAllegRegProt = nroAllegRegProt;
		this.nroAllegSchedaDoc = nroAllegSchedaDoc;
		this.nomeOriginale = nomeOriginale;
		this.displayFilename = displayFilename;
		this.dimensione = dimensione;
		this.flgFirmato = flgFirmato;
		this.mimetype = mimetype;
		this.impronta = impronta;
		this.algoritmoImpronta = algoritmoImpronta;
		this.encodingImpronta = encodingImpronta;
		this.tsIns = tsIns;
		this.idUteIns = idUteIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.idUteUltimoAgg = idUteUltimoAgg;
		this.nroProgr = nroProgr;
		this.nomeOriginaleCtx = nomeOriginaleCtx;
	}
	
	
    
	

	public TAttachEmailMgo(String idAttachEmail,
			it.eng.aurigamailbusiness.database.mail.TRegProtVsEmail tRegProtVsEmail,
			it.eng.aurigamailbusiness.database.mail.TSchedeDocVsEmail tSchedeDocVsEmail,
			it.eng.aurigamailbusiness.database.mail.TEmailMgo tEmailMgo, Byte nroAllegRegProt, Byte nroAllegSchedaDoc,
			String nomeOriginale, String displayFilename, long dimensione, boolean flgFirmato, boolean flgFirmaValida,
			String mimetype, String impronta, String algoritmoImpronta, String encodingImpronta, Date tsIns,
			String idUteIns, Date tsUltimoAgg, String idUteUltimoAgg, BigDecimal nroProgr, String nomeOriginaleCtx,
			boolean flgPdfEditabile, boolean flgPdfConCommenti, String segnatura, String uriAttach,
			String uriFileXOcr) {
		super();
		this.idAttachEmail = idAttachEmail;
		TRegProtVsEmail = tRegProtVsEmail;
		TSchedeDocVsEmail = tSchedeDocVsEmail;
		TEmailMgo = tEmailMgo;
		this.nroAllegRegProt = nroAllegRegProt;
		this.nroAllegSchedaDoc = nroAllegSchedaDoc;
		this.nomeOriginale = nomeOriginale;
		this.displayFilename = displayFilename;
		this.dimensione = dimensione;
		this.flgFirmato = flgFirmato;
		this.flgFirmaValida = flgFirmaValida;
		this.mimetype = mimetype;
		this.impronta = impronta;
		this.algoritmoImpronta = algoritmoImpronta;
		this.encodingImpronta = encodingImpronta;
		this.tsIns = tsIns;
		this.idUteIns = idUteIns;
		this.tsUltimoAgg = tsUltimoAgg;
		this.idUteUltimoAgg = idUteUltimoAgg;
		this.nroProgr = nroProgr;
		this.nomeOriginaleCtx = nomeOriginaleCtx;
		this.flgPdfEditabile = flgPdfEditabile;
		this.flgPdfConCommenti = flgPdfConCommenti;
		this.segnatura = segnatura;
		this.uriAttach = uriAttach;
		this.uriFileXOcr = uriFileXOcr;
	}

	@Id
	@Column(name = "ID_ATTACH_EMAIL", unique = true, nullable = false, length = 64)
	public String getIdAttachEmail() {
		return this.idAttachEmail;
	}

	public void setIdAttachEmail(String idAttachEmail) {
		this.idAttachEmail = idAttachEmail;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_REG_PROT_EMAIL")
	public TRegProtVsEmail getTRegProtVsEmail() {
		return this.TRegProtVsEmail;
	}

	public void setTRegProtVsEmail(TRegProtVsEmail TRegProtVsEmail) {
		this.TRegProtVsEmail = TRegProtVsEmail;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SCHEDA_DOC_EMAIL")
	public TSchedeDocVsEmail getTSchedeDocVsEmail() {
		return this.TSchedeDocVsEmail;
	}

	public void setTSchedeDocVsEmail(TSchedeDocVsEmail TSchedeDocVsEmail) {
		this.TSchedeDocVsEmail = TSchedeDocVsEmail;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMAIL", nullable = false)
	public TEmailMgo getTEmailMgo() {
		return this.TEmailMgo;
	}

	public void setTEmailMgo(TEmailMgo TEmailMgo) {
		this.TEmailMgo = TEmailMgo;
	}

	@Column(name = "NRO_ALLEG_REG_PROT", precision = 2, scale = 0)
	public Byte getNroAllegRegProt() {
		return this.nroAllegRegProt;
	}

	public void setNroAllegRegProt(Byte nroAllegRegProt) {
		this.nroAllegRegProt = nroAllegRegProt;
	}

	@Column(name = "NRO_ALLEG_SCHEDA_DOC", precision = 2, scale = 0)
	public Byte getNroAllegSchedaDoc() {
		return this.nroAllegSchedaDoc;
	}

	public void setNroAllegSchedaDoc(Byte nroAllegSchedaDoc) {
		this.nroAllegSchedaDoc = nroAllegSchedaDoc;
	}

	@Column(name = "NOME_ORIGINALE", nullable = false, length = 500)
	public String getNomeOriginale() {
		return this.nomeOriginale;
	}

	public void setNomeOriginale(String nomeOriginale) {
		this.nomeOriginale = nomeOriginale;
	}

	@Column(name = "DISPLAY_FILENAME", length = 500)
	public String getDisplayFilename() {
		return this.displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	@Column(name = "DIMENSIONE", nullable = false, precision = 10, scale = 0)
	public long getDimensione() {
		return this.dimensione;
	}

	public void setDimensione(long dimensione) {
		this.dimensione = dimensione;
	}

	@Column(name = "FLG_FIRMATO", nullable = false, precision = 1, scale = 0)
	public boolean isFlgFirmato() {
		return this.flgFirmato;
	}

	public void setFlgFirmato(boolean flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	@Column(name = "FLG_FIRMA_VALIDA", nullable = false, precision = 1, scale = 0)
	public boolean isFlgFirmaValida() {
		return flgFirmaValida;
	}

	public void setFlgFirmaValida(boolean flgFirmaValida) {
		this.flgFirmaValida = flgFirmaValida;
	}

	@Column(name = "MIMETYPE", length = 100)
	public String getMimetype() {
		return this.mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	@Column(name = "IMPRONTA", length = 500)
	public String getImpronta() {
		return this.impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	@Column(name = "ALGORITMO_IMPRONTA", length = 30)
	public String getAlgoritmoImpronta() {
		return this.algoritmoImpronta;
	}

	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}

	@Column(name = "ENCODING_IMPRONTA", length = 10)
	public String getEncodingImpronta() {
		return this.encodingImpronta;
	}

	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}

	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "ID_UTE_INS", length = 64)
	public String getIdUteIns() {
		return this.idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	@Column(name = "TS_ULTIMO_AGG", nullable = false)
	public Date getTsUltimoAgg() {
		return this.tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	@Column(name = "ID_UTE_ULTIMO_AGG", length = 64)
	public String getIdUteUltimoAgg() {
		return this.idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	@Column(name = "NRO_PROGR", precision = 22, scale = 0)
	public BigDecimal getNroProgr() {
		return this.nroProgr;
	}

	public void setNroProgr(BigDecimal nroProgr) {
		this.nroProgr = nroProgr;
	}

	@Column(name = "NOME_ORIGINALE_CTX", length = 500)
	public String getNomeOriginaleCtx() {
		return this.nomeOriginaleCtx;
	}

	public void setNomeOriginaleCtx(String nomeOriginaleCtx) {
		this.nomeOriginaleCtx = nomeOriginaleCtx;
	}

	@Column(name = "FLG_PDF_EDITABILE", nullable = false, precision = 1, scale = 0)
	public boolean isFlgPdfEditabile() {
		return flgPdfEditabile;
	}

	public void setFlgPdfEditabile(boolean flgPdfEditabile) {
		this.flgPdfEditabile = flgPdfEditabile;
	}

	@Column(name = "FLG_PDF_CON_COMMENTI", nullable = false, precision = 1, scale = 0)
	public boolean isFlgPdfConCommenti() {
		return flgPdfConCommenti;
	}

	public void setFlgPdfConCommenti(boolean flgPdfConCommenti) {
		this.flgPdfConCommenti = flgPdfConCommenti;
	}
	@Column(name = "SEGNATURA")
	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}

	
    @Column(name = "URI_ATTACH")
    public String getUriAttach() {
		return uriAttach;
	}

	public void setUriAttach(String uriAttach) {
		this.uriAttach = uriAttach;
	}
	@Column(name = "URI_FILE_X_OCR")
	public String getUriFileXOcr() {
		return uriFileXOcr;
	}

	public void setUriFileXOcr(String uriFileXOcr) {
		this.uriFileXOcr = uriFileXOcr;
	}
	
	
	

}
