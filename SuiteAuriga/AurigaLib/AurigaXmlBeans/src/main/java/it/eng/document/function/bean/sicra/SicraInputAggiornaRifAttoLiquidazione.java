/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraInputAggiornaRifAttoLiquidazione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private SicraPropostaAttoLiquidazione proposta;
	private SicraAttoDefinitivoLiquidazione attoDefinitivo;
	
	public SicraPropostaAttoLiquidazione getProposta() {
		return proposta;
	}
	
	public void setProposta(SicraPropostaAttoLiquidazione proposta) {
		this.proposta = proposta;
	}
	
	public SicraAttoDefinitivoLiquidazione getAttoDefinitivo() {
		return attoDefinitivo;
	}
	
	public void setAttoDefinitivo(SicraAttoDefinitivoLiquidazione attoDefinitivo) {
		this.attoDefinitivo = attoDefinitivo;
	}
	
}
