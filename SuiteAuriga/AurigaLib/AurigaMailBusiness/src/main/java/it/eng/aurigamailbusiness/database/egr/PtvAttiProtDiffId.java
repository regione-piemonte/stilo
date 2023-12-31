/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PtvAttiProtDiffId generated by hbm2java
 */
@Embeddable
public class PtvAttiProtDiffId implements java.io.Serializable {

	private int idAtto;
	private String codEnte;
	private int idDoc;
	private String desAtto;
	private Date dtAtto;
	private Date dtInizioVld;
	private Date dtFineVld;
	private Date dtTermineReg;
	private String noteProtDiff;
	private String flgAnn;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private String tpPtg;
	private Short annoPtg;
	private Integer numPtg;

	public PtvAttiProtDiffId() {
	}

	public PtvAttiProtDiffId(int idAtto, String codEnte, int idDoc,
			String desAtto, Date dtAtto, Date dtInizioVld, Date dtFineVld) {
		this.idAtto = idAtto;
		this.codEnte = codEnte;
		this.idDoc = idDoc;
		this.desAtto = desAtto;
		this.dtAtto = dtAtto;
		this.dtInizioVld = dtInizioVld;
		this.dtFineVld = dtFineVld;
	}

	public PtvAttiProtDiffId(int idAtto, String codEnte, int idDoc,
			String desAtto, Date dtAtto, Date dtInizioVld, Date dtFineVld,
			Date dtTermineReg, String noteProtDiff, String flgAnn, Date dtIns,
			Integer uteIns, Date dtUltMod, Integer uteUltMod, String tpPtg,
			Short annoPtg, Integer numPtg) {
		this.idAtto = idAtto;
		this.codEnte = codEnte;
		this.idDoc = idDoc;
		this.desAtto = desAtto;
		this.dtAtto = dtAtto;
		this.dtInizioVld = dtInizioVld;
		this.dtFineVld = dtFineVld;
		this.dtTermineReg = dtTermineReg;
		this.noteProtDiff = noteProtDiff;
		this.flgAnn = flgAnn;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.tpPtg = tpPtg;
		this.annoPtg = annoPtg;
		this.numPtg = numPtg;
	}

	@Column(name = "ID_ATTO", nullable = false, precision = 8, scale = 0)
	public int getIdAtto() {
		return this.idAtto;
	}

	public void setIdAtto(int idAtto) {
		this.idAtto = idAtto;
	}

	@Column(name = "COD_ENTE", nullable = false, length = 3)
	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	@Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)
	public int getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	@Column(name = "DES_ATTO", nullable = false, length = 500)
	public String getDesAtto() {
		return this.desAtto;
	}

	public void setDesAtto(String desAtto) {
		this.desAtto = desAtto;
	}

	@Column(name = "DT_ATTO", nullable = false, length = 7)
	public Date getDtAtto() {
		return this.dtAtto;
	}

	public void setDtAtto(Date dtAtto) {
		this.dtAtto = dtAtto;
	}

	@Column(name = "DT_INIZIO_VLD", nullable = false, length = 7)
	public Date getDtInizioVld() {
		return this.dtInizioVld;
	}

	public void setDtInizioVld(Date dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}

	@Column(name = "DT_FINE_VLD", nullable = false, length = 7)
	public Date getDtFineVld() {
		return this.dtFineVld;
	}

	public void setDtFineVld(Date dtFineVld) {
		this.dtFineVld = dtFineVld;
	}

	@Column(name = "DT_TERMINE_REG", length = 7)
	public Date getDtTermineReg() {
		return this.dtTermineReg;
	}

	public void setDtTermineReg(Date dtTermineReg) {
		this.dtTermineReg = dtTermineReg;
	}

	@Column(name = "NOTE_PROT_DIFF", length = 250)
	public String getNoteProtDiff() {
		return this.noteProtDiff;
	}

	public void setNoteProtDiff(String noteProtDiff) {
		this.noteProtDiff = noteProtDiff;
	}

	@Column(name = "FLG_ANN", length = 1)
	public String getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(String flgAnn) {
		this.flgAnn = flgAnn;
	}

	@Column(name = "DT_INS", length = 7)
	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	@Column(name = "UTE_INS", precision = 6, scale = 0)
	public Integer getUteIns() {
		return this.uteIns;
	}

	public void setUteIns(Integer uteIns) {
		this.uteIns = uteIns;
	}

	@Column(name = "DT_ULT_MOD", length = 7)
	public Date getDtUltMod() {
		return this.dtUltMod;
	}

	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	@Column(name = "UTE_ULT_MOD", precision = 6, scale = 0)
	public Integer getUteUltMod() {
		return this.uteUltMod;
	}

	public void setUteUltMod(Integer uteUltMod) {
		this.uteUltMod = uteUltMod;
	}

	@Column(name = "TP_PTG", length = 5)
	public String getTpPtg() {
		return this.tpPtg;
	}

	public void setTpPtg(String tpPtg) {
		this.tpPtg = tpPtg;
	}

	@Column(name = "ANNO_PTG", precision = 4, scale = 0)
	public Short getAnnoPtg() {
		return this.annoPtg;
	}

	public void setAnnoPtg(Short annoPtg) {
		this.annoPtg = annoPtg;
	}

	@Column(name = "NUM_PTG", precision = 7, scale = 0)
	public Integer getNumPtg() {
		return this.numPtg;
	}

	public void setNumPtg(Integer numPtg) {
		this.numPtg = numPtg;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PtvAttiProtDiffId))
			return false;
		PtvAttiProtDiffId castOther = (PtvAttiProtDiffId) other;

		return (this.getIdAtto() == castOther.getIdAtto())
				&& ((this.getCodEnte() == castOther.getCodEnte()) || (this
						.getCodEnte() != null && castOther.getCodEnte() != null && this
						.getCodEnte().equals(castOther.getCodEnte())))
				&& (this.getIdDoc() == castOther.getIdDoc())
				&& ((this.getDesAtto() == castOther.getDesAtto()) || (this
						.getDesAtto() != null && castOther.getDesAtto() != null && this
						.getDesAtto().equals(castOther.getDesAtto())))
				&& ((this.getDtAtto() == castOther.getDtAtto()) || (this
						.getDtAtto() != null && castOther.getDtAtto() != null && this
						.getDtAtto().equals(castOther.getDtAtto())))
				&& ((this.getDtInizioVld() == castOther.getDtInizioVld()) || (this
						.getDtInizioVld() != null
						&& castOther.getDtInizioVld() != null && this
						.getDtInizioVld().equals(castOther.getDtInizioVld())))
				&& ((this.getDtFineVld() == castOther.getDtFineVld()) || (this
						.getDtFineVld() != null
						&& castOther.getDtFineVld() != null && this
						.getDtFineVld().equals(castOther.getDtFineVld())))
				&& ((this.getDtTermineReg() == castOther.getDtTermineReg()) || (this
						.getDtTermineReg() != null
						&& castOther.getDtTermineReg() != null && this
						.getDtTermineReg().equals(castOther.getDtTermineReg())))
				&& ((this.getNoteProtDiff() == castOther.getNoteProtDiff()) || (this
						.getNoteProtDiff() != null
						&& castOther.getNoteProtDiff() != null && this
						.getNoteProtDiff().equals(castOther.getNoteProtDiff())))
				&& ((this.getFlgAnn() == castOther.getFlgAnn()) || (this
						.getFlgAnn() != null && castOther.getFlgAnn() != null && this
						.getFlgAnn().equals(castOther.getFlgAnn())))
				&& ((this.getDtIns() == castOther.getDtIns()) || (this
						.getDtIns() != null && castOther.getDtIns() != null && this
						.getDtIns().equals(castOther.getDtIns())))
				&& ((this.getUteIns() == castOther.getUteIns()) || (this
						.getUteIns() != null && castOther.getUteIns() != null && this
						.getUteIns().equals(castOther.getUteIns())))
				&& ((this.getDtUltMod() == castOther.getDtUltMod()) || (this
						.getDtUltMod() != null
						&& castOther.getDtUltMod() != null && this
						.getDtUltMod().equals(castOther.getDtUltMod())))
				&& ((this.getUteUltMod() == castOther.getUteUltMod()) || (this
						.getUteUltMod() != null
						&& castOther.getUteUltMod() != null && this
						.getUteUltMod().equals(castOther.getUteUltMod())))
				&& ((this.getTpPtg() == castOther.getTpPtg()) || (this
						.getTpPtg() != null && castOther.getTpPtg() != null && this
						.getTpPtg().equals(castOther.getTpPtg())))
				&& ((this.getAnnoPtg() == castOther.getAnnoPtg()) || (this
						.getAnnoPtg() != null && castOther.getAnnoPtg() != null && this
						.getAnnoPtg().equals(castOther.getAnnoPtg())))
				&& ((this.getNumPtg() == castOther.getNumPtg()) || (this
						.getNumPtg() != null && castOther.getNumPtg() != null && this
						.getNumPtg().equals(castOther.getNumPtg())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdAtto();
		result = 37 * result
				+ (getCodEnte() == null ? 0 : this.getCodEnte().hashCode());
		result = 37 * result + this.getIdDoc();
		result = 37 * result
				+ (getDesAtto() == null ? 0 : this.getDesAtto().hashCode());
		result = 37 * result
				+ (getDtAtto() == null ? 0 : this.getDtAtto().hashCode());
		result = 37
				* result
				+ (getDtInizioVld() == null ? 0 : this.getDtInizioVld()
						.hashCode());
		result = 37 * result
				+ (getDtFineVld() == null ? 0 : this.getDtFineVld().hashCode());
		result = 37
				* result
				+ (getDtTermineReg() == null ? 0 : this.getDtTermineReg()
						.hashCode());
		result = 37
				* result
				+ (getNoteProtDiff() == null ? 0 : this.getNoteProtDiff()
						.hashCode());
		result = 37 * result
				+ (getFlgAnn() == null ? 0 : this.getFlgAnn().hashCode());
		result = 37 * result
				+ (getDtIns() == null ? 0 : this.getDtIns().hashCode());
		result = 37 * result
				+ (getUteIns() == null ? 0 : this.getUteIns().hashCode());
		result = 37 * result
				+ (getDtUltMod() == null ? 0 : this.getDtUltMod().hashCode());
		result = 37 * result
				+ (getUteUltMod() == null ? 0 : this.getUteUltMod().hashCode());
		result = 37 * result
				+ (getTpPtg() == null ? 0 : this.getTpPtg().hashCode());
		result = 37 * result
				+ (getAnnoPtg() == null ? 0 : this.getAnnoPtg().hashCode());
		result = 37 * result
				+ (getNumPtg() == null ? 0 : this.getNumPtg().hashCode());
		return result;
	}

}
