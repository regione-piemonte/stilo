/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PtvTitolazioniId generated by hbm2java
 */
@Embeddable
public class PtvTitolazioniId implements java.io.Serializable {

	private BigDecimal idTitolazione;
	private String livelli;
	private String descrizione;
	private String ordine;
	private Date dtInizioVld;
	private Date dtFineVld;

	public PtvTitolazioniId() {
	}

	public PtvTitolazioniId(BigDecimal idTitolazione, String descrizione,
			Date dtInizioVld) {
		this.idTitolazione = idTitolazione;
		this.descrizione = descrizione;
		this.dtInizioVld = dtInizioVld;
	}

	public PtvTitolazioniId(BigDecimal idTitolazione, String livelli,
			String descrizione, String ordine, Date dtInizioVld, Date dtFineVld) {
		this.idTitolazione = idTitolazione;
		this.livelli = livelli;
		this.descrizione = descrizione;
		this.ordine = ordine;
		this.dtInizioVld = dtInizioVld;
		this.dtFineVld = dtFineVld;
	}

	@Column(name = "ID_TITOLAZIONE", nullable = false, precision = 25, scale = 0)
	public BigDecimal getIdTitolazione() {
		return this.idTitolazione;
	}

	public void setIdTitolazione(BigDecimal idTitolazione) {
		this.idTitolazione = idTitolazione;
	}

	@Column(name = "LIVELLI", length = 97)
	public String getLivelli() {
		return this.livelli;
	}

	public void setLivelli(String livelli) {
		this.livelli = livelli;
	}

	@Column(name = "DESCRIZIONE", nullable = false, length = 250)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "ORDINE", length = 15)
	public String getOrdine() {
		return this.ordine;
	}

	public void setOrdine(String ordine) {
		this.ordine = ordine;
	}

	@Column(name = "DT_INIZIO_VLD", nullable = false, length = 7)
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PtvTitolazioniId))
			return false;
		PtvTitolazioniId castOther = (PtvTitolazioniId) other;

		return ((this.getIdTitolazione() == castOther.getIdTitolazione()) || (this
				.getIdTitolazione() != null
				&& castOther.getIdTitolazione() != null && this
				.getIdTitolazione().equals(castOther.getIdTitolazione())))
				&& ((this.getLivelli() == castOther.getLivelli()) || (this
						.getLivelli() != null && castOther.getLivelli() != null && this
						.getLivelli().equals(castOther.getLivelli())))
				&& ((this.getDescrizione() == castOther.getDescrizione()) || (this
						.getDescrizione() != null
						&& castOther.getDescrizione() != null && this
						.getDescrizione().equals(castOther.getDescrizione())))
				&& ((this.getOrdine() == castOther.getOrdine()) || (this
						.getOrdine() != null && castOther.getOrdine() != null && this
						.getOrdine().equals(castOther.getOrdine())))
				&& ((this.getDtInizioVld() == castOther.getDtInizioVld()) || (this
						.getDtInizioVld() != null
						&& castOther.getDtInizioVld() != null && this
						.getDtInizioVld().equals(castOther.getDtInizioVld())))
				&& ((this.getDtFineVld() == castOther.getDtFineVld()) || (this
						.getDtFineVld() != null
						&& castOther.getDtFineVld() != null && this
						.getDtFineVld().equals(castOther.getDtFineVld())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdTitolazione() == null ? 0 : this.getIdTitolazione()
						.hashCode());
		result = 37 * result
				+ (getLivelli() == null ? 0 : this.getLivelli().hashCode());
		result = 37
				* result
				+ (getDescrizione() == null ? 0 : this.getDescrizione()
						.hashCode());
		result = 37 * result
				+ (getOrdine() == null ? 0 : this.getOrdine().hashCode());
		result = 37
				* result
				+ (getDtInizioVld() == null ? 0 : this.getDtInizioVld()
						.hashCode());
		result = 37 * result
				+ (getDtFineVld() == null ? 0 : this.getDtFineVld().hashCode());
		return result;
	}

}