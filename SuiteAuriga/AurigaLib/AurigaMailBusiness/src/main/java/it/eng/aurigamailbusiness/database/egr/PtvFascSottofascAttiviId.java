/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PtvFascSottofascAttiviId generated by hbm2java
 */
@Embeddable
public class PtvFascSottofascAttiviId implements java.io.Serializable {

	private int idFascicolo;
	private String codEnte;
	private short anno;
	private int progrFasc;
	private BigDecimal numSottofasc;
	private Date dtApertura;
	private BigDecimal idTitolazione;
	private String txtOgg;
	private String note;
	private String flgProcObbl;
	private String flgProcInterr;
	private Integer idProc;
	private int uoInCarico;
	private String flgAcc;
	private String noteStoria;

	public PtvFascSottofascAttiviId() {
	}

	public PtvFascSottofascAttiviId(int idFascicolo, String codEnte,
			short anno, int progrFasc, BigDecimal idTitolazione, String txtOgg,
			int uoInCarico) {
		this.idFascicolo = idFascicolo;
		this.codEnte = codEnte;
		this.anno = anno;
		this.progrFasc = progrFasc;
		this.idTitolazione = idTitolazione;
		this.txtOgg = txtOgg;
		this.uoInCarico = uoInCarico;
	}

	public PtvFascSottofascAttiviId(int idFascicolo, String codEnte,
			short anno, int progrFasc, BigDecimal numSottofasc,
			Date dtApertura, BigDecimal idTitolazione, String txtOgg,
			String note, String flgProcObbl, String flgProcInterr,
			Integer idProc, int uoInCarico, String flgAcc, String noteStoria) {
		this.idFascicolo = idFascicolo;
		this.codEnte = codEnte;
		this.anno = anno;
		this.progrFasc = progrFasc;
		this.numSottofasc = numSottofasc;
		this.dtApertura = dtApertura;
		this.idTitolazione = idTitolazione;
		this.txtOgg = txtOgg;
		this.note = note;
		this.flgProcObbl = flgProcObbl;
		this.flgProcInterr = flgProcInterr;
		this.idProc = idProc;
		this.uoInCarico = uoInCarico;
		this.flgAcc = flgAcc;
		this.noteStoria = noteStoria;
	}

	@Column(name = "ID_FASCICOLO", nullable = false, precision = 8, scale = 0)
	public int getIdFascicolo() {
		return this.idFascicolo;
	}

	public void setIdFascicolo(int idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	@Column(name = "COD_ENTE", nullable = false, length = 3)
	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	@Column(name = "ANNO", nullable = false, precision = 4, scale = 0)
	public short getAnno() {
		return this.anno;
	}

	public void setAnno(short anno) {
		this.anno = anno;
	}

	@Column(name = "PROGR_FASC", nullable = false, precision = 8, scale = 0)
	public int getProgrFasc() {
		return this.progrFasc;
	}

	public void setProgrFasc(int progrFasc) {
		this.progrFasc = progrFasc;
	}

	@Column(name = "NUM_SOTTOFASC", precision = 22, scale = 0)
	public BigDecimal getNumSottofasc() {
		return this.numSottofasc;
	}

	public void setNumSottofasc(BigDecimal numSottofasc) {
		this.numSottofasc = numSottofasc;
	}

	@Column(name = "DT_APERTURA", length = 7)
	public Date getDtApertura() {
		return this.dtApertura;
	}

	public void setDtApertura(Date dtApertura) {
		this.dtApertura = dtApertura;
	}

	@Column(name = "ID_TITOLAZIONE", nullable = false, precision = 25, scale = 0)
	public BigDecimal getIdTitolazione() {
		return this.idTitolazione;
	}

	public void setIdTitolazione(BigDecimal idTitolazione) {
		this.idTitolazione = idTitolazione;
	}

	@Column(name = "TXT_OGG", nullable = false, length = 250)
	public String getTxtOgg() {
		return this.txtOgg;
	}

	public void setTxtOgg(String txtOgg) {
		this.txtOgg = txtOgg;
	}

	@Column(name = "NOTE", length = 75)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "FLG_PROC_OBBL", length = 1)
	public String getFlgProcObbl() {
		return this.flgProcObbl;
	}

	public void setFlgProcObbl(String flgProcObbl) {
		this.flgProcObbl = flgProcObbl;
	}

	@Column(name = "FLG_PROC_INTERR", length = 1)
	public String getFlgProcInterr() {
		return this.flgProcInterr;
	}

	public void setFlgProcInterr(String flgProcInterr) {
		this.flgProcInterr = flgProcInterr;
	}

	@Column(name = "ID_PROC", precision = 8, scale = 0)
	public Integer getIdProc() {
		return this.idProc;
	}

	public void setIdProc(Integer idProc) {
		this.idProc = idProc;
	}

	@Column(name = "UO_IN_CARICO", nullable = false, precision = 8, scale = 0)
	public int getUoInCarico() {
		return this.uoInCarico;
	}

	public void setUoInCarico(int uoInCarico) {
		this.uoInCarico = uoInCarico;
	}

	@Column(name = "FLG_ACC", length = 1)
	public String getFlgAcc() {
		return this.flgAcc;
	}

	public void setFlgAcc(String flgAcc) {
		this.flgAcc = flgAcc;
	}

	@Column(name = "NOTE_STORIA", length = 250)
	public String getNoteStoria() {
		return this.noteStoria;
	}

	public void setNoteStoria(String noteStoria) {
		this.noteStoria = noteStoria;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PtvFascSottofascAttiviId))
			return false;
		PtvFascSottofascAttiviId castOther = (PtvFascSottofascAttiviId) other;

		return (this.getIdFascicolo() == castOther.getIdFascicolo())
				&& ((this.getCodEnte() == castOther.getCodEnte()) || (this
						.getCodEnte() != null && castOther.getCodEnte() != null && this
						.getCodEnte().equals(castOther.getCodEnte())))
				&& (this.getAnno() == castOther.getAnno())
				&& (this.getProgrFasc() == castOther.getProgrFasc())
				&& ((this.getNumSottofasc() == castOther.getNumSottofasc()) || (this
						.getNumSottofasc() != null
						&& castOther.getNumSottofasc() != null && this
						.getNumSottofasc().equals(castOther.getNumSottofasc())))
				&& ((this.getDtApertura() == castOther.getDtApertura()) || (this
						.getDtApertura() != null
						&& castOther.getDtApertura() != null && this
						.getDtApertura().equals(castOther.getDtApertura())))
				&& ((this.getIdTitolazione() == castOther.getIdTitolazione()) || (this
						.getIdTitolazione() != null
						&& castOther.getIdTitolazione() != null && this
						.getIdTitolazione()
						.equals(castOther.getIdTitolazione())))
				&& ((this.getTxtOgg() == castOther.getTxtOgg()) || (this
						.getTxtOgg() != null && castOther.getTxtOgg() != null && this
						.getTxtOgg().equals(castOther.getTxtOgg())))
				&& ((this.getNote() == castOther.getNote()) || (this.getNote() != null
						&& castOther.getNote() != null && this.getNote()
						.equals(castOther.getNote())))
				&& ((this.getFlgProcObbl() == castOther.getFlgProcObbl()) || (this
						.getFlgProcObbl() != null
						&& castOther.getFlgProcObbl() != null && this
						.getFlgProcObbl().equals(castOther.getFlgProcObbl())))
				&& ((this.getFlgProcInterr() == castOther.getFlgProcInterr()) || (this
						.getFlgProcInterr() != null
						&& castOther.getFlgProcInterr() != null && this
						.getFlgProcInterr()
						.equals(castOther.getFlgProcInterr())))
				&& ((this.getIdProc() == castOther.getIdProc()) || (this
						.getIdProc() != null && castOther.getIdProc() != null && this
						.getIdProc().equals(castOther.getIdProc())))
				&& (this.getUoInCarico() == castOther.getUoInCarico())
				&& ((this.getFlgAcc() == castOther.getFlgAcc()) || (this
						.getFlgAcc() != null && castOther.getFlgAcc() != null && this
						.getFlgAcc().equals(castOther.getFlgAcc())))
				&& ((this.getNoteStoria() == castOther.getNoteStoria()) || (this
						.getNoteStoria() != null
						&& castOther.getNoteStoria() != null && this
						.getNoteStoria().equals(castOther.getNoteStoria())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdFascicolo();
		result = 37 * result
				+ (getCodEnte() == null ? 0 : this.getCodEnte().hashCode());
		result = 37 * result + this.getAnno();
		result = 37 * result + this.getProgrFasc();
		result = 37
				* result
				+ (getNumSottofasc() == null ? 0 : this.getNumSottofasc()
						.hashCode());
		result = 37
				* result
				+ (getDtApertura() == null ? 0 : this.getDtApertura()
						.hashCode());
		result = 37
				* result
				+ (getIdTitolazione() == null ? 0 : this.getIdTitolazione()
						.hashCode());
		result = 37 * result
				+ (getTxtOgg() == null ? 0 : this.getTxtOgg().hashCode());
		result = 37 * result
				+ (getNote() == null ? 0 : this.getNote().hashCode());
		result = 37
				* result
				+ (getFlgProcObbl() == null ? 0 : this.getFlgProcObbl()
						.hashCode());
		result = 37
				* result
				+ (getFlgProcInterr() == null ? 0 : this.getFlgProcInterr()
						.hashCode());
		result = 37 * result
				+ (getIdProc() == null ? 0 : this.getIdProc().hashCode());
		result = 37 * result + this.getUoInCarico();
		result = 37 * result
				+ (getFlgAcc() == null ? 0 : this.getFlgAcc().hashCode());
		result = 37
				* result
				+ (getNoteStoria() == null ? 0 : this.getNoteStoria()
						.hashCode());
		return result;
	}

}
