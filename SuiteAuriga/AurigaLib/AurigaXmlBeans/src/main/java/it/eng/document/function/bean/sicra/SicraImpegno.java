/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraImpegno implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigInteger progressivo;
	private SicraTestataImpegno testata;
	private List<SicraDettaglioImpegno> dettaglio;

	public BigInteger getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(BigInteger progressivo) {
		this.progressivo = progressivo;
	}

	public SicraTestataImpegno getTestata() {
		return testata;
	}

	public void setTestata(SicraTestataImpegno testata) {
		this.testata = testata;
	}

	public List<SicraDettaglioImpegno> getDettaglio() {
		return dettaglio;
	}

	public void setDettaglio(List<SicraDettaglioImpegno> dettaglio) {
		this.dettaglio = dettaglio;
	}

}
