/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PttCopieCRichSpost generated by hbm2java
 */
@Entity
@Table(name = "PTT_COPIE_C_RICH_SPOST")
public class PttCopieCRichSpost implements java.io.Serializable {

	private PttCopieCRichSpostId id;
	private PttRichSpostamento pttRichSpostamento;
	private String esitoElab;
	private Date tsUltElab;
	private BigDecimal numTentativi;
	private String codErr;
	private String msgErr;
	private Clob xmlInfo;

	public PttCopieCRichSpost() {
	}

	public PttCopieCRichSpost(PttCopieCRichSpostId id,
			PttRichSpostamento pttRichSpostamento) {
		this.id = id;
		this.pttRichSpostamento = pttRichSpostamento;
	}

	public PttCopieCRichSpost(PttCopieCRichSpostId id,
			PttRichSpostamento pttRichSpostamento, String esitoElab,
			Date tsUltElab, BigDecimal numTentativi, String codErr,
			String msgErr, Clob xmlInfo) {
		this.id = id;
		this.pttRichSpostamento = pttRichSpostamento;
		this.esitoElab = esitoElab;
		this.tsUltElab = tsUltElab;
		this.numTentativi = numTentativi;
		this.codErr = codErr;
		this.msgErr = msgErr;
		this.xmlInfo = xmlInfo;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idRichiesta", column = @Column(name = "ID_RICHIESTA", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "idDoc", column = @Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "idUoAss", column = @Column(name = "ID_UO_ASS", nullable = false, precision = 8, scale = 0)) })
	public PttCopieCRichSpostId getId() {
		return this.id;
	}

	public void setId(PttCopieCRichSpostId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RICHIESTA", nullable = false, insertable = false, updatable = false)
	public PttRichSpostamento getPttRichSpostamento() {
		return this.pttRichSpostamento;
	}

	public void setPttRichSpostamento(PttRichSpostamento pttRichSpostamento) {
		this.pttRichSpostamento = pttRichSpostamento;
	}

	@Column(name = "ESITO_ELAB", length = 2)
	public String getEsitoElab() {
		return this.esitoElab;
	}

	public void setEsitoElab(String esitoElab) {
		this.esitoElab = esitoElab;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TS_ULT_ELAB", length = 7)
	public Date getTsUltElab() {
		return this.tsUltElab;
	}

	public void setTsUltElab(Date tsUltElab) {
		this.tsUltElab = tsUltElab;
	}

	@Column(name = "NUM_TENTATIVI", precision = 22, scale = 0)
	public BigDecimal getNumTentativi() {
		return this.numTentativi;
	}

	public void setNumTentativi(BigDecimal numTentativi) {
		this.numTentativi = numTentativi;
	}

	@Column(name = "COD_ERR", length = 250)
	public String getCodErr() {
		return this.codErr;
	}

	public void setCodErr(String codErr) {
		this.codErr = codErr;
	}

	@Column(name = "MSG_ERR", length = 4000)
	public String getMsgErr() {
		return this.msgErr;
	}

	public void setMsgErr(String msgErr) {
		this.msgErr = msgErr;
	}

	@Column(name = "XML_INFO")
	public Clob getXmlInfo() {
		return this.xmlInfo;
	}

	public void setXmlInfo(Clob xmlInfo) {
		this.xmlInfo = xmlInfo;
	}

}
