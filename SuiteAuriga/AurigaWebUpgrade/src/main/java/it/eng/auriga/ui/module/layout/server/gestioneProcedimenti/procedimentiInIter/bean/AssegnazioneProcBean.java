/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class AssegnazioneProcBean extends OperazioneMassivaProcedimentiBean implements Serializable {
	
		private static final long serialVersionUID = 1L;
	
		private List<IstruttoreProcBean> listaAssegnazione;

		public List<IstruttoreProcBean> getListaAssegnazione() {
			return listaAssegnazione;
		}

		public void setListaAssegnazione(List<IstruttoreProcBean> listaAssegnazione) {
			this.listaAssegnazione = listaAssegnazione;
		}
		
}
