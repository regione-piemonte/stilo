/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraImpegnoDaRendereEsecutivo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String parte;
	private SicraAttoRicerca attoRicerca;
	private SicraAttoDefinitivo attoDefinitivo;

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

	public SicraAttoRicerca getAttoRicerca() {
		return attoRicerca;
	}

	public void setAttoRicerca(SicraAttoRicerca attoRicerca) {
		this.attoRicerca = attoRicerca;
	}

	public SicraAttoDefinitivo getAttoDefinitivo() {
		return attoDefinitivo;
	}

	public void setAttoDefinitivo(SicraAttoDefinitivo attoDefinitivo) {
		this.attoDefinitivo = attoDefinitivo;
	}

}// SicraImpegnoDaRendereEsecutivo
