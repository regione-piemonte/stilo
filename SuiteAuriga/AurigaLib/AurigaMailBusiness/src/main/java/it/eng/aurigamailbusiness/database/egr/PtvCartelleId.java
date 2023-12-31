/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PtvCartelleId generated by hbm2java
 */
@Embeddable
public class PtvCartelleId implements java.io.Serializable {

	private String codEnte;
	private int idCartella;
	private short anno;
	private BigDecimal idTitolazione;
	private int progrFasc;
	private String txtOgg;
	private Date dtApertura;
	private Date dtChiusura;
	private Date dtArch;
	private String noteIperfasc;
	private Integer idProc;
	private int uoInCarico;

	public PtvCartelleId() {
	}

	public PtvCartelleId(String codEnte, int idCartella, short anno,
			BigDecimal idTitolazione, int progrFasc, String txtOgg,
			int uoInCarico) {
		this.codEnte = codEnte;
		this.idCartella = idCartella;
		this.anno = anno;
		this.idTitolazione = idTitolazione;
		this.progrFasc = progrFasc;
		this.txtOgg = txtOgg;
		this.uoInCarico = uoInCarico;
	}

	public PtvCartelleId(String codEnte, int idCartella, short anno,
			BigDecimal idTitolazione, int progrFasc, String txtOgg,
			Date dtApertura, Date dtChiusura, Date dtArch, String noteIperfasc,
			Integer idProc, int uoInCarico) {
		this.codEnte = codEnte;
		this.idCartella = idCartella;
		this.anno = anno;
		this.idTitolazione = idTitolazione;
		this.progrFasc = progrFasc;
		this.txtOgg = txtOgg;
		this.dtApertura = dtApertura;
		this.dtChiusura = dtChiusura;
		this.dtArch = dtArch;
		this.noteIperfasc = noteIperfasc;
		this.idProc = idProc;
		this.uoInCarico = uoInCarico;
	}

	@Column(name = "COD_ENTE", nullable = false, length = 3)
	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	@Column(name = "ID_CARTELLA", nullable = false, precision = 8, scale = 0)
	public int getIdCartella() {
		return this.idCartella;
	}

	public void setIdCartella(int idCartella) {
		this.idCartella = idCartella;
	}

	@Column(name = "ANNO", nullable = false, precision = 4, scale = 0)
	public short getAnno() {
		return this.anno;
	}

	public void setAnno(short anno) {
		this.anno = anno;
	}

	@Column(name = "ID_TITOLAZIONE", nullable = false, precision = 25, scale = 0)
	public BigDecimal getIdTitolazione() {
		return this.idTitolazione;
	}

	public void setIdTitolazione(BigDecimal idTitolazione) {
		this.idTitolazione = idTitolazione;
	}

	@Column(name = "PROGR_FASC", nullable = false, precision = 8, scale = 0)
	public int getProgrFasc() {
		return this.progrFasc;
	}

	public void setProgrFasc(int progrFasc) {
		this.progrFasc = progrFasc;
	}

	@Column(name = "TXT_OGG", nullable = false, length = 250)
	public String getTxtOgg() {
		return this.txtOgg;
	}

	public void setTxtOgg(String txtOgg) {
		this.txtOgg = txtOgg;
	}

	@Column(name = "DT_APERTURA", length = 7)
	public Date getDtApertura() {
		return this.dtApertura;
	}

	public void setDtApertura(Date dtApertura) {
		this.dtApertura = dtApertura;
	}

	@Column(name = "DT_CHIUSURA", length = 7)
	public Date getDtChiusura() {
		return this.dtChiusura;
	}

	public void setDtChiusura(Date dtChiusura) {
		this.dtChiusura = dtChiusura;
	}

	@Column(name = "DT_ARCH", length = 7)
	public Date getDtArch() {
		return this.dtArch;
	}

	public void setDtArch(Date dtArch) {
		this.dtArch = dtArch;
	}

	@Column(name = "NOTE_IPERFASC", length = 250)
	public String getNoteIperfasc() {
		return this.noteIperfasc;
	}

	public void setNoteIperfasc(String noteIperfasc) {
		this.noteIperfasc = noteIperfasc;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PtvCartelleId))
			return false;
		PtvCartelleId castOther = (PtvCartelleId) other;

		return ((this.getCodEnte() == castOther.getCodEnte()) || (this
				.getCodEnte() != null && castOther.getCodEnte() != null && this
				.getCodEnte().equals(castOther.getCodEnte())))
				&& (this.getIdCartella() == castOther.getIdCartella())
				&& (this.getAnno() == castOther.getAnno())
				&& ((this.getIdTitolazione() == castOther.getIdTitolazione()) || (this
						.getIdTitolazione() != null
						&& castOther.getIdTitolazione() != null && this
						.getIdTitolazione()
						.equals(castOther.getIdTitolazione())))
				&& (this.getProgrFasc() == castOther.getProgrFasc())
				&& ((this.getTxtOgg() == castOther.getTxtOgg()) || (this
						.getTxtOgg() != null && castOther.getTxtOgg() != null && this
						.getTxtOgg().equals(castOther.getTxtOgg())))
				&& ((this.getDtApertura() == castOther.getDtApertura()) || (this
						.getDtApertura() != null
						&& castOther.getDtApertura() != null && this
						.getDtApertura().equals(castOther.getDtApertura())))
				&& ((this.getDtChiusura() == castOther.getDtChiusura()) || (this
						.getDtChiusura() != null
						&& castOther.getDtChiusura() != null && this
						.getDtChiusura().equals(castOther.getDtChiusura())))
				&& ((this.getDtArch() == castOther.getDtArch()) || (this
						.getDtArch() != null && castOther.getDtArch() != null && this
						.getDtArch().equals(castOther.getDtArch())))
				&& ((this.getNoteIperfasc() == castOther.getNoteIperfasc()) || (this
						.getNoteIperfasc() != null
						&& castOther.getNoteIperfasc() != null && this
						.getNoteIperfasc().equals(castOther.getNoteIperfasc())))
				&& ((this.getIdProc() == castOther.getIdProc()) || (this
						.getIdProc() != null && castOther.getIdProc() != null && this
						.getIdProc().equals(castOther.getIdProc())))
				&& (this.getUoInCarico() == castOther.getUoInCarico());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCodEnte() == null ? 0 : this.getCodEnte().hashCode());
		result = 37 * result + this.getIdCartella();
		result = 37 * result + this.getAnno();
		result = 37
				* result
				+ (getIdTitolazione() == null ? 0 : this.getIdTitolazione()
						.hashCode());
		result = 37 * result + this.getProgrFasc();
		result = 37 * result
				+ (getTxtOgg() == null ? 0 : this.getTxtOgg().hashCode());
		result = 37
				* result
				+ (getDtApertura() == null ? 0 : this.getDtApertura()
						.hashCode());
		result = 37
				* result
				+ (getDtChiusura() == null ? 0 : this.getDtChiusura()
						.hashCode());
		result = 37 * result
				+ (getDtArch() == null ? 0 : this.getDtArch().hashCode());
		result = 37
				* result
				+ (getNoteIperfasc() == null ? 0 : this.getNoteIperfasc()
						.hashCode());
		result = 37 * result
				+ (getIdProc() == null ? 0 : this.getIdProc().hashCode());
		result = 37 * result + this.getUoInCarico();
		return result;
	}

}
