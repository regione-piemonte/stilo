/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class CogitoResponseBean {
	
	private List<CategorizzazioneCogitoBean> listaCategorizzazioni;
	private int durataChiamata;
	private String risposta;
	
	public List<CategorizzazioneCogitoBean> getListaCategorizzazioni() {
		return listaCategorizzazioni;
	}
	public void setListaCategorizzazioni(List<CategorizzazioneCogitoBean> listaCategorizzazioni) {
		this.listaCategorizzazioni = listaCategorizzazioni;
	}
	public int getDurataChiamata() {
		return durataChiamata;
	}
	public void setDurataChiamata(int durataChiamata) {
		this.durataChiamata = durataChiamata;
	}
	public String getRisposta() {
		return risposta;
	}
	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
}