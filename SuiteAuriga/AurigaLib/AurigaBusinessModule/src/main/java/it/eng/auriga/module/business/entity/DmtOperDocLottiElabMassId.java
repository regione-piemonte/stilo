/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Generated 15-giu-2018 12.03.29 by Hibernate Tools 3.6.0.Final

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DmtOperDocLottiElabMassId generated by hbm2java
 */
@Embeddable
public class DmtOperDocLottiElabMassId implements java.io.Serializable {

	private BigDecimal idJob;
	private BigDecimal nroPosizDoc;
	private String tipoOperazione;

	public DmtOperDocLottiElabMassId() {
	}

	public DmtOperDocLottiElabMassId(BigDecimal idJob, BigDecimal nroPosizDoc, String tipoOperazione) {
		this.idJob = idJob;
		this.nroPosizDoc = nroPosizDoc;
		this.tipoOperazione = tipoOperazione;
	}

	@Column(name = "ID_JOB", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdJob() {
		return this.idJob;
	}

	public void setIdJob(BigDecimal idJob) {
		this.idJob = idJob;
	}

	@Column(name = "NRO_POSIZ_DOC", nullable = false, precision = 22, scale = 0)
	public BigDecimal getNroPosizDoc() {
		return this.nroPosizDoc;
	}

	public void setNroPosizDoc(BigDecimal nroPosizDoc) {
		this.nroPosizDoc = nroPosizDoc;
	}

	@Column(name = "TIPO_OPERAZIONE", nullable = false, length = 100)
	public String getTipoOperazione() {
		return this.tipoOperazione;
	}

	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DmtOperDocLottiElabMassId))
			return false;
		DmtOperDocLottiElabMassId castOther = (DmtOperDocLottiElabMassId) other;

		return ((this.getIdJob() == castOther.getIdJob())
				|| (this.getIdJob() != null && castOther.getIdJob() != null && this.getIdJob().equals(castOther.getIdJob())))
				&& ((this.getNroPosizDoc() == castOther.getNroPosizDoc())
						|| (this.getNroPosizDoc() != null && castOther.getNroPosizDoc() != null && this.getNroPosizDoc().equals(castOther.getNroPosizDoc())))
				&& ((this.getTipoOperazione() == castOther.getTipoOperazione()) || (this.getTipoOperazione() != null && castOther.getTipoOperazione() != null
						&& this.getTipoOperazione().equals(castOther.getTipoOperazione())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdJob() == null ? 0 : this.getIdJob().hashCode());
		result = 37 * result + (getNroPosizDoc() == null ? 0 : this.getNroPosizDoc().hashCode());
		result = 37 * result + (getTipoOperazione() == null ? 0 : this.getTipoOperazione().hashCode());
		return result;
	}

}
