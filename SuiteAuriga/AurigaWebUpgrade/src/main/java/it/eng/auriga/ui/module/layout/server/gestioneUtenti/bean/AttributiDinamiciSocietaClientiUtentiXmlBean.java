/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class AttributiDinamiciSocietaClientiUtentiXmlBean {	
		
	@XmlVariabile(nome = "CID_APPL_SOCIETA",      tipo = TipoVariabile.LISTA)	
	private List<SocietaUtenteXmlBean> listaSocietaUtenti;
	
	@XmlVariabile(nome = "ID_CLIENTE",      tipo = TipoVariabile.LISTA)	
	private List<ClientiUtenteXmlBean> listaClientiUtente;

	public List<ClientiUtenteXmlBean> getListaClientiUtente() {
		return listaClientiUtente;
	}


	public void setListaClientiUtente(List<ClientiUtenteXmlBean> listaClientiUtente) {
		this.listaClientiUtente = listaClientiUtente;
	}


	public List<SocietaUtenteXmlBean> getListaSocietaUtenti() {
		return listaSocietaUtenti;
	}


	public void setListaSocietaUtenti(List<SocietaUtenteXmlBean> listaSocietaUtenti) {
		this.listaSocietaUtenti = listaSocietaUtenti;
	}


	
	

	
}
