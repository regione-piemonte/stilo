/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class ListaTipoFascicoliBean 
{
	private String idUo;
	private List<TipoFascicoliAggregatiBean> listaTipoFascicoli;
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	
	public List<TipoFascicoliAggregatiBean> getListaTipoFascicoli() 
	{
		return listaTipoFascicoli;
	}
	
	public void setListaTipoFascicoli(List<TipoFascicoliAggregatiBean> listaTipoFascicoli) 
	{
		this.listaTipoFascicoli = listaTipoFascicoli;
	}
	
	

}
