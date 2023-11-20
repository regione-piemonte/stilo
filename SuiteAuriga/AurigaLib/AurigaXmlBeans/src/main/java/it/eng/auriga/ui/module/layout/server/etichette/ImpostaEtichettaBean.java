/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ImpostaEtichettaBean {

	private TipoStampaEtichetta tipoStampa;
	private String nomeStampante;
	
	public TipoStampaEtichetta getTipoStampa() {
		return tipoStampa;
	}
	public void setTipoStampa(TipoStampaEtichetta tipoStampa) {
		this.tipoStampa = tipoStampa;
	}
	public String getNomeStampante() {
		return nomeStampante;
	}
	public void setNomeStampante(String nomeStampante) {
		this.nomeStampante = nomeStampante;
	}
}
