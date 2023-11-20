/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;

public class SmistamentoAttiBean extends OperazioneMassivaAttiBean implements Serializable {
	
		private static final long serialVersionUID = 1L;
	
		private List<UfficioLiquidatoreBean> listaUfficioLiquidatore;
		private List<AssegnazioneBean> listaSmistamento;
		private String smistamentoRagioneria;
		private String assegnatarioSmistamentoRagioneria;
		private String smistamentoGruppi;
		
		public List<UfficioLiquidatoreBean> getListaUfficioLiquidatore() {
			return listaUfficioLiquidatore;
		}
		public void setListaUfficioLiquidatore(List<UfficioLiquidatoreBean> listaUfficioLiquidatore) {
			this.listaUfficioLiquidatore = listaUfficioLiquidatore;
		}
		public List<AssegnazioneBean> getListaSmistamento() {
			return listaSmistamento;
		}
		public void setListaSmistamento(List<AssegnazioneBean> listaSmistamento) {
			this.listaSmistamento = listaSmistamento;
		}
		public String getSmistamentoRagioneria() {
			return smistamentoRagioneria;
		}
		public void setSmistamentoRagioneria(String smistamentoRagioneria) {
			this.smistamentoRagioneria = smistamentoRagioneria;
		}
		public String getAssegnatarioSmistamentoRagioneria() {
			return assegnatarioSmistamentoRagioneria;
		}
		public void setAssegnatarioSmistamentoRagioneria(String assegnatarioSmistamentoRagioneria) {
			this.assegnatarioSmistamentoRagioneria = assegnatarioSmistamentoRagioneria;
		}
		public String getSmistamentoGruppi() {
			return smistamentoGruppi;
		}
		public void setSmistamentoGruppi(String smistamentoGruppi) {
			this.smistamentoGruppi = smistamentoGruppi;
		}
}