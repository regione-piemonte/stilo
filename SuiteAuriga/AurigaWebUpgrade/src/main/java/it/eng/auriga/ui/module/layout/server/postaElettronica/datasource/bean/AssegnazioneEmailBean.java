/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;

import java.io.Serializable;
import java.util.List;

public class AssegnazioneEmailBean extends OperazioneMassivaPostaElettronicaBean implements Serializable {
	
		private static final long serialVersionUID = 1L;
	
		private List<AssegnazioneBean> listaAssegnazioni;
		private String messaggio;
		
		public List<AssegnazioneBean> getListaAssegnazioni() {
			return listaAssegnazioni;
		}
		public void setListaAssegnazioni(List<AssegnazioneBean> listaAssegnazioni) {
			this.listaAssegnazioni = listaAssegnazioni;
		}
		public String getMessaggio() {
			return messaggio;
		}
		public void setMessaggio(String messaggio) {
			this.messaggio = messaggio;
		}

}
