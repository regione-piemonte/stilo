/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspGetListaDocumentiResponse extends ContabilitaAdspResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<ContabilitaAdspDatiDocumentoResponse> documenti;

	public List<ContabilitaAdspDatiDocumentoResponse> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(List<ContabilitaAdspDatiDocumentoResponse> documenti) {
		this.documenti = documenti;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspGetListaDocumentiResponse [documenti=" + documenti + "]";
	}
	
}
