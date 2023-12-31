/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 12-gen-2015 12.46.05 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DmtCorrispettiviId generated by hbm2java
 */
@Embeddable
public class DmtCorrispettiviId implements java.io.Serializable {

	private BigDecimal idCorrispettivo;
	private BigDecimal tipoCorrispettivo;
	private Date dataCorrispettivo;
	private String orderSource;
	private BigDecimal taxRate;
	private BigDecimal taxLine;
	private BigDecimal amount;
	private BigDecimal anno;
	private BigDecimal mese;
	private BigDecimal giorno;
	private BigDecimal idUd;
	private String descrAde;

	public DmtCorrispettiviId() {
	}

	public DmtCorrispettiviId(BigDecimal idCorrispettivo, BigDecimal tipoCorrispettivo, Date dataCorrispettivo, String orderSource, BigDecimal taxRate, BigDecimal taxLine, BigDecimal amount,
			BigDecimal anno, BigDecimal mese, BigDecimal giorno, BigDecimal idUd, String descrAde) {
		this.idCorrispettivo = idCorrispettivo;
		this.tipoCorrispettivo = tipoCorrispettivo;
		this.dataCorrispettivo = dataCorrispettivo;
		this.orderSource = orderSource;
		this.taxRate = taxRate;
		this.taxLine = taxLine;
		this.amount = amount;
		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
		this.idUd = idUd;
		this.descrAde = descrAde;
	}

	@Column(name = "ID_CORRISPETTIVO", precision = 22, scale = 0)
	public BigDecimal getIdCorrispettivo() {
		return this.idCorrispettivo;
	}

	public void setIdCorrispettivo(BigDecimal idCorrispettivo) {
		this.idCorrispettivo = idCorrispettivo;
	}

	@Column(name = "TIPO_CORRISPETTIVO", precision = 22, scale = 0)
	public BigDecimal getTipoCorrispettivo() {
		return this.tipoCorrispettivo;
	}

	public void setTipoCorrispettivo(BigDecimal tipoCorrispettivo) {
		this.tipoCorrispettivo = tipoCorrispettivo;
	}

	@Column(name = "DATA_CORRISPETTIVO", length = 7)
	public Date getDataCorrispettivo() {
		return this.dataCorrispettivo;
	}

	public void setDataCorrispettivo(Date dataCorrispettivo) {
		this.dataCorrispettivo = dataCorrispettivo;
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

	@Column(name = "GIORNO", precision = 22, scale = 0)
	public BigDecimal getGiorno() {
		return this.giorno;
	}

	public void setGiorno(BigDecimal giorno) {
		this.giorno = giorno;
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
		if (!(other instanceof DmtCorrispettiviId))
			return false;
		DmtCorrispettiviId castOther = (DmtCorrispettiviId) other;

		return ((this.getIdCorrispettivo() == castOther.getIdCorrispettivo()) || (this.getIdCorrispettivo() != null && castOther.getIdCorrispettivo() != null && this.getIdCorrispettivo().equals(
				castOther.getIdCorrispettivo())))
				&& ((this.getTipoCorrispettivo() == castOther.getTipoCorrispettivo()) || (this.getTipoCorrispettivo() != null && castOther.getTipoCorrispettivo() != null && this
						.getTipoCorrispettivo().equals(castOther.getTipoCorrispettivo())))
				&& ((this.getDataCorrispettivo() == castOther.getDataCorrispettivo()) || (this.getDataCorrispettivo() != null && castOther.getDataCorrispettivo() != null && this
						.getDataCorrispettivo().equals(castOther.getDataCorrispettivo())))
				&& ((this.getOrderSource() == castOther.getOrderSource()) || (this.getOrderSource() != null && castOther.getOrderSource() != null && this.getOrderSource().equals(
						castOther.getOrderSource())))
				&& ((this.getTaxRate() == castOther.getTaxRate()) || (this.getTaxRate() != null && castOther.getTaxRate() != null && this.getTaxRate().equals(castOther.getTaxRate())))
				&& ((this.getTaxLine() == castOther.getTaxLine()) || (this.getTaxLine() != null && castOther.getTaxLine() != null && this.getTaxLine().equals(castOther.getTaxLine())))
				&& ((this.getAmount() == castOther.getAmount()) || (this.getAmount() != null && castOther.getAmount() != null && this.getAmount().equals(castOther.getAmount())))
				&& ((this.getAnno() == castOther.getAnno()) || (this.getAnno() != null && castOther.getAnno() != null && this.getAnno().equals(castOther.getAnno())))
				&& ((this.getMese() == castOther.getMese()) || (this.getMese() != null && castOther.getMese() != null && this.getMese().equals(castOther.getMese())))
				&& ((this.getGiorno() == castOther.getGiorno()) || (this.getGiorno() != null && castOther.getGiorno() != null && this.getGiorno().equals(castOther.getGiorno())))
				&& ((this.getIdUd() == castOther.getIdUd()) || (this.getIdUd() != null && castOther.getIdUd() != null && this.getIdUd().equals(castOther.getIdUd())))
				&& ((this.getDescrAde() == castOther.getDescrAde()) || (this.getDescrAde() != null && castOther.getDescrAde() != null && this.getDescrAde().equals(castOther.getDescrAde())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdCorrispettivo() == null ? 0 : this.getIdCorrispettivo().hashCode());
		result = 37 * result + (getTipoCorrispettivo() == null ? 0 : this.getTipoCorrispettivo().hashCode());
		result = 37 * result + (getDataCorrispettivo() == null ? 0 : this.getDataCorrispettivo().hashCode());
		result = 37 * result + (getOrderSource() == null ? 0 : this.getOrderSource().hashCode());
		result = 37 * result + (getTaxRate() == null ? 0 : this.getTaxRate().hashCode());
		result = 37 * result + (getTaxLine() == null ? 0 : this.getTaxLine().hashCode());
		result = 37 * result + (getAmount() == null ? 0 : this.getAmount().hashCode());
		result = 37 * result + (getAnno() == null ? 0 : this.getAnno().hashCode());
		result = 37 * result + (getMese() == null ? 0 : this.getMese().hashCode());
		result = 37 * result + (getGiorno() == null ? 0 : this.getGiorno().hashCode());
		result = 37 * result + (getIdUd() == null ? 0 : this.getIdUd().hashCode());
		result = 37 * result + (getDescrAde() == null ? 0 : this.getDescrAde().hashCode());
		return result;
	}

}
