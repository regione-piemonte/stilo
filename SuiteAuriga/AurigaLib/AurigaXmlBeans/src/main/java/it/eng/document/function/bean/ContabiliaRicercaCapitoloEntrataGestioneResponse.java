/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaRicercaCapitoloEntrataGestioneResponse extends ContabiliaBaseResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<ContabiliaCapitoloEntrataGestione> capitoliEntrataGestione;
	
	public List<ContabiliaCapitoloEntrataGestione> getCapitoliEntrataGestione() {
		return capitoliEntrataGestione;
	}
	
	public void setCapitoliEntrataGestione(List<ContabiliaCapitoloEntrataGestione> capitoliEntrataGestione) {
		this.capitoliEntrataGestione = capitoliEntrataGestione;
	}
	
	@Override
	public String toString() {
		return "ContabiliaRicercaCapitoloEntrataGestioneResponse [capitoliEntrataGestione=" + capitoliEntrataGestione
				+ "]";
	}
	
}
