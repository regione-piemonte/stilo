/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraImpegnoSintetico implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigInteger progressivo;
	private BigInteger idImpegno;
	private BigInteger codice;
	private BigInteger codAnnuale;
	private Integer anno;

	public BigInteger getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(BigInteger progressivo) {
		this.progressivo = progressivo;
	}

	public BigInteger getIdImpegno() {
		return idImpegno;
	}

	public void setIdImpegno(BigInteger idImpegno) {
		this.idImpegno = idImpegno;
	}

	public BigInteger getCodice() {
		return codice;
	}

	public void setCodice(BigInteger codice) {
		this.codice = codice;
	}

	public BigInteger getCodAnnuale() {
		return codAnnuale;
	}

	public void setCodAnnuale(BigInteger codAnnuale) {
		this.codAnnuale = codAnnuale;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

}// SicraImpegnoSintetico
