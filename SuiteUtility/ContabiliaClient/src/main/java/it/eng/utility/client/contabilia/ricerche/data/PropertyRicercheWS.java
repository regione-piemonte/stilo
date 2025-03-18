/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.csi.siac.ricerche.svc._1.RicercaAccertamento;
import it.csi.siac.ricerche.svc._1.RicercaImpegno;

public class PropertyRicercheWS implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RicercaAccertamento accertamento;
	private RicercaImpegno impegno;
	private String endpoint;
	
	public RicercaAccertamento getAccertamento() {
		return accertamento;
	}
	
	public void setAccertamento(RicercaAccertamento accertamento) {
		this.accertamento = accertamento;
	}
	
	public RicercaImpegno getImpegno() {
		return impegno;
	}
	
	public void setImpegno(RicercaImpegno impegno) {
		this.impegno = impegno;
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	@Override
	public String toString() {
		return "PropertyRicercheWS [accertamento=" + accertamento + ", impegno=" + impegno + ", endpoint=" + endpoint
				+ "]";
	}
	
}
