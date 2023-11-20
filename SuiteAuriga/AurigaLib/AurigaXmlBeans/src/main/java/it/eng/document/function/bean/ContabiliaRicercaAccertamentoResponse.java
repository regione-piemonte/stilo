/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaRicercaAccertamentoResponse extends ContabiliaBaseRicercaResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<ContabiliaAccertamento> accertamenti;
	
	public List<ContabiliaAccertamento> getAccertamenti() {
		return accertamenti;
	}
	
	public void setAccertamenti(List<ContabiliaAccertamento> accertamenti) {
		this.accertamenti = accertamenti;
	}
	
	@Override
	public String toString() {
		return "ContabiliaRicercaAccertamentoResponse [accertamenti=" + accertamenti + "]";
	}
	
}
