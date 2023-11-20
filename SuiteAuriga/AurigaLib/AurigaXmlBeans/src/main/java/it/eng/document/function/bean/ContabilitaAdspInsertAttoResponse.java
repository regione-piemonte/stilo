/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspInsertAttoResponse extends ContabilitaAdspResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ContabilitaAdspDatiAttoResponse dati;

	public ContabilitaAdspDatiAttoResponse getDati() {
		return dati;
	}
	
	public void setDati(ContabilitaAdspDatiAttoResponse dati) {
		this.dati = dati;
	}
	
	@Override
	public String toString() {
		return "ContabilitaAdspInsertAttoResponse [dati=" + dati + "]";
	}
	
}
