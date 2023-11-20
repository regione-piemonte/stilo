/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;


public class AttiAutorizzazioneAnnRegBean  extends AttiAutorizzazioneAnnRegXmlBean {
	
	private String desUteBozza;
	private String desUteAtto;
	private List<RegDaAnnullareBean> listaRegDaAnnullare;
	
	public String getDesUteBozza() {
		return desUteBozza;
	}
	public void setDesUteBozza(String desUteBozza) {
		this.desUteBozza = desUteBozza;
	}
	public String getDesUteAtto() {
		return desUteAtto;
	}
	public void setDesUteAtto(String desUteAtto) {
		this.desUteAtto = desUteAtto;
	}
	public List<RegDaAnnullareBean> getListaRegDaAnnullare() {
		return listaRegDaAnnullare;
	}
	public void setListaRegDaAnnullare(List<RegDaAnnullareBean> listaRegDaAnnullare) {
		this.listaRegDaAnnullare = listaRegDaAnnullare;
	}
	
}
