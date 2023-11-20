/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspGetRichiestaResponse extends ContabilitaAdspResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ContabilitaAdspRichiesteContabiliResponse richiesta;
	private List<String> datiDisallineati;
	
	public ContabilitaAdspRichiesteContabiliResponse getRichiesta() {
		return richiesta;
	}
	
	public void setRichiesta(ContabilitaAdspRichiesteContabiliResponse richiesta) {
		this.richiesta = richiesta;
	}
	
	public List<String> getDatiDisallineati() {
		return datiDisallineati;
	}
	
	public void setDatiDisallineati(List<String> datiDisallineati) {
		this.datiDisallineati = datiDisallineati;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspGetRichiestaResponse [richiesta=" + richiesta + ", datiDisallineati=" + datiDisallineati
				+ "]";
	}
	
}
