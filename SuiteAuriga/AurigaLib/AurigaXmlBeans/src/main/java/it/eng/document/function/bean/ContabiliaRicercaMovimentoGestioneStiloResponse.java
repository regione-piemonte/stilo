/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaRicercaMovimentoGestioneStiloResponse extends ContabiliaBaseResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<ContabiliaMovimentoGestioneStilo> movimentiGestione;

	public List<ContabiliaMovimentoGestioneStilo> getMovimentiGestione() {
		return movimentiGestione;
	}

	public void setMovimentiGestione(List<ContabiliaMovimentoGestioneStilo> movimentiGestione) {
		this.movimentiGestione = movimentiGestione;
	}
	
	@Override
	public String toString() {
		return "ContabiliaRicercaMovimentoGestioneStiloResponse [movimentiGestione=" + movimentiGestione + "]";
	}
	
}
