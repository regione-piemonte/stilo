/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 18-dic-2014 17.26.45 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DmtCorrispettiviQuadId generated by hbm2java
 */
@Embeddable
public class DmtCorrispettiviQuadId implements java.io.Serializable {

	private BigDecimal idQuadratura;
	private BigDecimal anno;
	private BigDecimal mese;
	private String orderSource;
	private BigDecimal taxRate;
	private BigDecimal taxLine;
	private BigDecimal amount;
	private BigDecimal idUd;
	private String descrAde;

	public DmtCorrispettiviQuadId() {
	}

	public DmtCorrispettiviQuadId(BigDecimal idQuadratura, BigDecimal anno, BigDecimal mese, String orderSource, BigDecimal taxRate, BigDecimal taxLine, BigDecimal amount, BigDecimal idUd,
			String descrAde) {
		this.idQuadratura = idQuadratura;
		this.anno = anno;
		this.mese = mese;
		this.orderSource = orderSource;
		this.taxRate = taxRate;
		this.taxLine = taxLine;
		this.amount = amount;
		this.idUd = idUd;
		this.descrAde = descrAde;
	}

	@Column(name = "ID_QUADRATURA", precision = 22, scale = 0)
	public BigDecimal getIdQuadratura() {
		return this.idQuadratura;
	}

	public void setIdQuadratura(BigDecimal idQuadratura) {
		this.idQuadratura = idQuadratura;
	}

	@Column(name = "ANNO", precision = 22, scale = 0)
	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	@Column(name = "MESE", precision = 22, scale = 0)
	public BigDecimal getMese() {
		return this.mese;
	}

	public void setMese(BigDecimal mese) {
		this.mese = mese;
	}

	@Column(name = "ORDER_SOURCE", length = 20)
	public String getOrderSource() {
		return this.orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	@Column(name = "TAX_RATE", precision = 12)
	public BigDecimal getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name = "TAX_LINE", precision = 22, scale = 0)
	public BigDecimal getTaxLine() {
		return this.taxLine;
	}

	public void setTaxLine(BigDecimal taxLine) {
		this.taxLine = taxLine;
	}

	@Column(name = "AMOUNT", precision = 12)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "ID_UD", precision = 22, scale = 0)
	public BigDecimal getIdUd() {
		return this.idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	@Column(name = "DESCR_ADE", length = 50)
	public String getDescrAde() {
		return this.descrAde;
	}

	public void setDescrAde(String descrAde) {
		this.descrAde = descrAde;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DmtCorrispettiviQuadId))
			return false;
		DmtCorrispettiviQuadId castOther = (DmtCorrispettiviQuadId) other;

		return ((this.getIdQuadratura() == castOther.getIdQuadratura()) || (this.getIdQuadratura() != null && castOther.getIdQuadratura() != null && this.getIdQuadratura().equals(
				castOther.getIdQuadratura())))
				&& ((this.getAnno() == castOther.getAnno()) || (this.getAnno() != null && castOther.getAnno() != null && this.getAnno().equals(castOther.getAnno())))
				&& ((this.getMese() == castOther.getMese()) || (this.getMese() != null && castOther.getMese() != null && this.getMese().equals(castOther.getMese())))
				&& ((this.getOrderSource() == castOther.getOrderSource()) || (this.getOrderSource() != null && castOther.getOrderSource() != null && this.getOrderSource().equals(
						castOther.getOrderSource())))
				&& ((this.getTaxRate() == castOther.getTaxRate()) || (this.getTaxRate() != null && castOther.getTaxRate() != null && this.getTaxRate().equals(castOther.getTaxRate())))
				&& ((this.getTaxLine() == castOther.getTaxLine()) || (this.getTaxLine() != null && castOther.getTaxLine() != null && this.getTaxLine().equals(castOther.getTaxLine())))
				&& ((this.getAmount() == castOther.getAmount()) || (this.getAmount() != null && castOther.getAmount() != null && this.getAmount().equals(castOther.getAmount())))
				&& ((this.getIdUd() == castOther.getIdUd()) || (this.getIdUd() != null && castOther.getIdUd() != null && this.getIdUd().equals(castOther.getIdUd())))
				&& ((this.getDescrAde() == castOther.getDescrAde()) || (this.getDescrAde() != null && castOther.getDescrAde() != null && this.getDescrAde().equals(castOther.getDescrAde())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdQuadratura() == null ? 0 : this.getIdQuadratura().hashCode());
		result = 37 * result + (getAnno() == null ? 0 : this.getAnno().hashCode());
		result = 37 * result + (getMese() == null ? 0 : this.getMese().hashCode());
		result = 37 * result + (getOrderSource() == null ? 0 : this.getOrderSource().hashCode());
		result = 37 * result + (getTaxRate() == null ? 0 : this.getTaxRate().hashCode());
		result = 37 * result + (getTaxLine() == null ? 0 : this.getTaxLine().hashCode());
		result = 37 * result + (getAmount() == null ? 0 : this.getAmount().hashCode());
		result = 37 * result + (getIdUd() == null ? 0 : this.getIdUd().hashCode());
		result = 37 * result + (getDescrAde() == null ? 0 : this.getDescrAde().hashCode());
		return result;
	}

}