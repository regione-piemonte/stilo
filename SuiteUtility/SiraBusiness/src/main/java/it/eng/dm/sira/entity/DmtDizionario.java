package it.eng.dm.sira.entity;

// Generated 20-nov-2014 17.15.30 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * DmtDizionario generated by hbm2java
 */
@Entity
@Table(name = "DMT_DIZIONARIO", uniqueConstraints = @UniqueConstraint(columnNames = { "ID_SP_AOO", "DICTIONARY_ENTRY", "VALUE" }))
public class DmtDizionario implements java.io.Serializable {

	public DmtDizionario() {
	}

	private BigDecimal idSpAoo;

	private String dictionaryEntry;

	private String value;

	private String codValue;

	private String explanation;

	private String vincolatoAValGen;

	private Date dtInizioVld;

	private Date dtFineVld;

	private Boolean flgHidden;

	private Boolean flgLocked;

	private Date tsIns;

	private BigDecimal idUserIns;

	private Date tsLastUpd;

	private BigDecimal idUserLastUpd;

	@Column(name = "ID_SP_AOO", precision = 22, scale = 0)
	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	@Column(name = "DICTIONARY_ENTRY", nullable = false, length = 30)
	public String getDictionaryEntry() {
		return this.dictionaryEntry;
	}

	public void setDictionaryEntry(String dictionaryEntry) {
		this.dictionaryEntry = dictionaryEntry;
	}
	
	@Id
	@Column(name = "VALUE", nullable = false, length = 150)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "COD_VALUE", length = 10)
	public String getCodValue() {
		return this.codValue;
	}

	public void setCodValue(String codValue) {
		this.codValue = codValue;
	}

	@Column(name = "EXPLANATION", length = 2000)
	public String getExplanation() {
		return this.explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@Column(name = "VINCOLATO_A_VAL_GEN", length = 150)
	public String getVincolatoAValGen() {
		return this.vincolatoAValGen;
	}

	public void setVincolatoAValGen(String vincolatoAValGen) {
		this.vincolatoAValGen = vincolatoAValGen;
	}

	@Column(name = "DT_INIZIO_VLD", length = 7)
	public Date getDtInizioVld() {
		return this.dtInizioVld;
	}

	public void setDtInizioVld(Date dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}

	@Column(name = "DT_FINE_VLD", length = 7)
	public Date getDtFineVld() {
		return this.dtFineVld;
	}

	public void setDtFineVld(Date dtFineVld) {
		this.dtFineVld = dtFineVld;
	}

	@Column(name = "FLG_HIDDEN", precision = 1, scale = 0)
	public Boolean getFlgHidden() {
		return this.flgHidden;
	}

	public void setFlgHidden(Boolean flgHidden) {
		this.flgHidden = flgHidden;
	}

	@Column(name = "FLG_LOCKED", precision = 1, scale = 0)
	public Boolean getFlgLocked() {
		return this.flgLocked;
	}

	public void setFlgLocked(Boolean flgLocked) {
		this.flgLocked = flgLocked;
	}

	@Column(name = "TS_INS", length = 7)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "ID_USER_INS", precision = 22, scale = 0)
	public BigDecimal getIdUserIns() {
		return this.idUserIns;
	}

	public void setIdUserIns(BigDecimal idUserIns) {
		this.idUserIns = idUserIns;
	}

	@Column(name = "TS_LAST_UPD", length = 7)
	public Date getTsLastUpd() {
		return this.tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	@Column(name = "ID_USER_LAST_UPD", precision = 22, scale = 0)
	public BigDecimal getIdUserLastUpd() {
		return this.idUserLastUpd;
	}

	public void setIdUserLastUpd(BigDecimal idUserLastUpd) {
		this.idUserLastUpd = idUserLastUpd;
	}

}
