/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the T_BUILT_SIP database table.
 * 
 */
@Entity
@Table(name="T_BUILT_SIP")
public class TBuiltSip implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TBuiltSipPK id;

	@Lob()
	private String attributi;
	
	@Column(name="ID_DOC_TYPE")
	private BigDecimal idDocType;
	
	@Column(name="SIP_FILE")
	private String sipFile;

	@Column(name="DENOMINAZIONE_SOGG")
	private String denominazioneSogg;

    @Temporal( TemporalType.DATE)
	@Column(name="TS_INS")
	private java.util.Date tsIns;

	@Column(name="FLG_ANN")
	private long flgAnn;

	@Column(name="ID_UD")
	private BigDecimal idUd;

	@Column(name="INDEX_FILE")
	private String indexFile;
	
	@Column(name="DOC_FILE_NAME")
	private String docFileName;
	
	@Column(name="PROCESSED")
	private BigDecimal processed;

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}
	
	public String getSipFile() {
		return this.sipFile;
	}
	public void setSipFile(String sipFile) {
		this.sipFile = sipFile;
	}
	public String getDenominazioneSogg() {
		return this.denominazioneSogg;
	}
	public void setDenominazioneSogg(String denominazioneSogg) {
		this.denominazioneSogg = denominazioneSogg;
	}
	public java.util.Date getTsIns() {
		return this.tsIns;
	}
	public void setTsIns(java.util.Date tsIns) {
		this.tsIns = tsIns;
	}
	public long getFlgAnn() {
		return this.flgAnn;
	}
	public void setFlgAnn(long flgAnn) {
		this.flgAnn = flgAnn;
	}

	public TBuiltSipPK getId() {
		return this.id;
	}

	public void setId(TBuiltSipPK id) {
		this.id = id;
	}
	
	public String getAttributi() {
		return this.attributi;
	}

	public void setAttributi(String attributi) {
		this.attributi = attributi;
	}

	public BigDecimal getIdDocType() {
		return this.idDocType;
	}

	public void setIdDocType(BigDecimal idDocType) {
		this.idDocType = idDocType;
	}

	public BigDecimal getIdUd() {
		return this.idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	public String getIndexFile() {
		return this.indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	public BigDecimal getProcessed() {
		return processed;
	}

	public void setProcessed(BigDecimal processed) {
		this.processed = processed;
	}	
}