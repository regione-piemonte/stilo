/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AmcoTipiDocumentoResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<AmcoTipoDocumento> tipiDocumento;
	private String descrizioneErrore;
	
	public List<AmcoTipoDocumento> getTipiDocumento() {
		return tipiDocumento;
	}
	
	public void setTipiDocumento(List<AmcoTipoDocumento> tipiDocumento) {
		this.tipiDocumento = tipiDocumento;
	}
	
	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}
	
	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	@Override
	public String toString() {
		return "AmcoTipiDocumentoResponse [tipiDocumento=" + tipiDocumento + ", descrizioneErrore=" + descrizioneErrore
				+ "]";
	}
	
}
