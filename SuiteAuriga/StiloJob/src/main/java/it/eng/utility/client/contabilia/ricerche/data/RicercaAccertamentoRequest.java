/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.csi.siac.ricerche.svc._1.RicercaAccertamento;

public class RicercaAccertamentoRequest extends RicercaAccertamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idSpAoo;
	
	public String getIdSpAoo() {
		return idSpAoo;
	}
	
	public void setIdSpAoo(String idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	
}
