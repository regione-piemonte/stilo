/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListCreaModDocumentoOutBean implements Serializable{

	private static final long serialVersionUID = 6308387899919563360L;
	private List<CreaModDocumentoOutBean> risultati;
	public List<CreaModDocumentoOutBean> getRisultati() {
		return risultati;
	}
	public void setRisultati(List<CreaModDocumentoOutBean> risultati) {
		this.risultati = risultati;
	}
}
