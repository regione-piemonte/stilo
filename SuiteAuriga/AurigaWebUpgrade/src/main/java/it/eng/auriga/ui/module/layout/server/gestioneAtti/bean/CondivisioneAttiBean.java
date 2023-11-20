/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioCCBean;

public class CondivisioneAttiBean extends OperazioneMassivaAttiBean implements Serializable {
	
		private static final long serialVersionUID = 1L;
	
		private List<DestInvioCCBean> listaCondivisione;
		
		public List<DestInvioCCBean> getListaCondivisione() {
			return listaCondivisione;
		}
		public void setListaCondivisione(List<DestInvioCCBean> listaCondivisione) {
			this.listaCondivisione = listaCondivisione;
		}
		
}