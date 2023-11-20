/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AmcoContiImputazioneResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<AmcoContoImputazione> contiImputazione;
	private String descrizioneErrore;
	
	public List<AmcoContoImputazione> getContiImputazione() {
		return contiImputazione;
	}
	
	public void setContiImputazione(List<AmcoContoImputazione> contiImputazione) {
		this.contiImputazione = contiImputazione;
	}
	
	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}
	
	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	@Override
	public String toString() {
		return "AmcoContiImputazioneResponse [contiImputazione=" + contiImputazione + ", descrizioneErrore="
				+ descrizioneErrore + "]";
	}
	
}
