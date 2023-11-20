/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import it.eng.document.function.bean.DatiLiquidazioneAVBXmlBean;

public class ListaLiquidazioneBean {

	private List<DatiLiquidazioneAVBBean> listaLiquidazioni = new ArrayList<DatiLiquidazioneAVBBean>();

	public List<DatiLiquidazioneAVBBean> getListaLiquidazioni() {
		return listaLiquidazioni;
	}

	public void setListaLiquidazioni(List<DatiLiquidazioneAVBBean> listaLiquidazioni) {
		this.listaLiquidazioni = listaLiquidazioni;
	}
	
}
