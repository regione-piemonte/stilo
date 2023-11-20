/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class ListaDettaglioEmailBean 
{
	private List<DettaglioEmailBean> listaDettagli;
	private String emlSbustato;
	public List<DettaglioEmailBean> getListaDettagli() {
		return listaDettagli;
	}
	public void setListaDettagli(List<DettaglioEmailBean> listaDettagli) {
		this.listaDettagli = listaDettagli;
	}
	public String getEmlSbustato() {
		return emlSbustato;
	}
	public void setEmlSbustato(String emlSbustato) {
		this.emlSbustato = emlSbustato;
	}

	
}
