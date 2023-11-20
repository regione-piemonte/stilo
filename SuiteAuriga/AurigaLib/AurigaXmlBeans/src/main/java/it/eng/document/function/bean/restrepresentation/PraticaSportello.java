/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "praticaSportello")
public class PraticaSportello implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codice;
	private BigDecimal numeroProtocollo;
	private String dataOraProtocollo;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public BigDecimal getNroProtocollo() {
		return numeroProtocollo;
	}

	public void setNroProtocollo(BigDecimal nroProtocollo) {
		this.numeroProtocollo = nroProtocollo;
	}

	public String getDataOraProtocollo() {
		return dataOraProtocollo;
	}

	public void setDataOraProtocollo(String dataOraProtocollo) {
		this.dataOraProtocollo = dataOraProtocollo;
	}

}
