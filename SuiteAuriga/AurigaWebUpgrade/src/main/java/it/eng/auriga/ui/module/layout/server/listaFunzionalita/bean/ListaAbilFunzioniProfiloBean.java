/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;


public class ListaAbilFunzioniProfiloBean {

	private String flgTpObjPrivTo;
	private String idProfilo;
	private List<ListaFunzionalitaBean> listaFunzioni;
	
	public String getIdProfilo() {
		return idProfilo;
	}
	public void setIdProfilo(String idProfilo) {
		this.idProfilo = idProfilo;
	}
	public List<ListaFunzionalitaBean> getListaFunzioni() {
		return listaFunzioni;
	}
	public void setListaFunzioni(List<ListaFunzionalitaBean> listaFunzioni) {
		this.listaFunzioni = listaFunzioni;
	}
	public String getFlgTpObjPrivTo() {
		return flgTpObjPrivTo;
	}
	public void setFlgTpObjPrivTo(String flgTpObjPrivTo) {
		this.flgTpObjPrivTo = flgTpObjPrivTo;
	}	
	
}
