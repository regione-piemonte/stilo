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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PttTmpProtoReport generated by hbm2java
 */
@Entity
@Table(name = "PTT_TMP_PROTO_REPORT")
public class PttTmpProtoReport implements java.io.Serializable {

	private PttTmpProtoReportId id;
	private Date dtProt;
	private String tpReg;
	private Short annoReg;
	private Integer numReg;
	private Date dtRpr;
	private String tpRpr;
	private Short annoRpr;
	private Integer numRpr;
	private String oggetto;
	private String destinatario;
	private Short copia;
	private BigDecimal ufficio;
	private Clob a;
	private Clob b;
	private Clob c;
	private String mitt;
	private String docricevuto;

	public PttTmpProtoReport() {
	}

	public PttTmpProtoReport(PttTmpProtoReportId id) {
		this.id = id;
	}

	public PttTmpProtoReport(PttTmpProtoReportId id, Date dtProt, String tpReg,
			Short annoReg, Integer numReg, Date dtRpr, String tpRpr,
			Short annoRpr, Integer numRpr, String oggetto, String destinatario,
			Short copia, BigDecimal ufficio, Clob a, Clob b, Clob c,
			String mitt, String docricevuto) {
		this.id = id;
		this.dtProt = dtProt;
		this.tpReg = tpReg;
		this.annoReg = annoReg;
		this.numReg = numReg;
		this.dtRpr = dtRpr;
		this.tpRpr = tpRpr;
		this.annoRpr = annoRpr;
		this.numRpr = numRpr;
		this.oggetto = oggetto;
		this.destinatario = destinatario;
		this.copia = copia;
		this.ufficio = ufficio;
		this.a = a;
		this.b = b;
		this.c = c;
		this.mitt = mitt;
		this.docricevuto = docricevuto;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idEstrazione", column = @Column(name = "ID_ESTRAZIONE", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "progr", column = @Column(name = "PROGR", nullable = false, precision = 22, scale = 0)) })
	public PttTmpProtoReportId getId() {
		return this.id;
	}

	public void setId(PttTmpProtoReportId id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_PROT", length = 7)
	public Date getDtProt() {
		return this.dtProt;
	}

	public void setDtProt(Date dtProt) {
		this.dtProt = dtProt;
	}

	@Column(name = "TP_REG", length = 5)
	public String getTpReg() {
		return this.tpReg;
	}

	public void setTpReg(String tpReg) {
		this.tpReg = tpReg;
	}

	@Column(name = "ANNO_REG", precision = 4, scale = 0)
	public Short getAnnoReg() {
		return this.annoReg;
	}

	public void setAnnoReg(Short annoReg) {
		this.annoReg = annoReg;
	}

	@Column(name = "NUM_REG", precision = 7, scale = 0)
	public Integer getNumReg() {
		return this.numReg;
	}

	public void setNumReg(Integer numReg) {
		this.numReg = numReg;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_RPR", length = 7)
	public Date getDtRpr() {
		return this.dtRpr;
	}

	public void setDtRpr(Date dtRpr) {
		this.dtRpr = dtRpr;
	}

	@Column(name = "TP_RPR", length = 5)
	public String getTpRpr() {
		return this.tpRpr;
	}

	public void setTpRpr(String tpRpr) {
		this.tpRpr = tpRpr;
	}

	@Column(name = "ANNO_RPR", precision = 4, scale = 0)
	public Short getAnnoRpr() {
		return this.annoRpr;
	}

	public void setAnnoRpr(Short annoRpr) {
		this.annoRpr = annoRpr;
	}

	@Column(name = "NUM_RPR", precision = 7, scale = 0)
	public Integer getNumRpr() {
		return this.numRpr;
	}

	public void setNumRpr(Integer numRpr) {
		this.numRpr = numRpr;
	}

	@Column(name = "OGGETTO", length = 500)
	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	@Column(name = "DESTINATARIO", length = 200)
	public String getDestinatario() {
		return this.destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	@Column(name = "COPIA", precision = 4, scale = 0)
	public Short getCopia() {
		return this.copia;
	}

	public void setCopia(Short copia) {
		this.copia = copia;
	}

	@Column(name = "UFFICIO", precision = 20, scale = 0)
	public BigDecimal getUfficio() {
		return this.ufficio;
	}

	public void setUfficio(BigDecimal ufficio) {
		this.ufficio = ufficio;
	}

	@Column(name = "A")
	public Clob getA() {
		return this.a;
	}

	public void setA(Clob a) {
		this.a = a;
	}

	@Column(name = "B")
	public Clob getB() {
		return this.b;
	}

	public void setB(Clob b) {
		this.b = b;
	}

	@Column(name = "C")
	public Clob getC() {
		return this.c;
	}

	public void setC(Clob c) {
		this.c = c;
	}

	@Column(name = "MITT", length = 4000)
	public String getMitt() {
		return this.mitt;
	}

	public void setMitt(String mitt) {
		this.mitt = mitt;
	}

	@Column(name = "DOCRICEVUTO", length = 100)
	public String getDocricevuto() {
		return this.docricevuto;
	}

	public void setDocricevuto(String docricevuto) {
		this.docricevuto = docricevuto;
	}

}
