/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraAttoLiqContabile implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nomeFileAttoLiq;
	private String contenutoFileAttoLiq;
	
	public String getNomeFileAttoLiq() {
		return nomeFileAttoLiq;
	}
	
	public void setNomeFileAttoLiq(String nomeFileAttoLiq) {
		this.nomeFileAttoLiq = nomeFileAttoLiq;
	}
	
	public String getContenutoFileAttoLiq() {
		return contenutoFileAttoLiq;
	}
	
	public void setContenutoFileAttoLiq(String contenutoFileAttoLiq) {
		this.contenutoFileAttoLiq = contenutoFileAttoLiq;
	}
	
}
